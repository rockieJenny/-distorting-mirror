package com.millennialmedia.android;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Environment;
import android.text.TextUtils;
import io.fabric.sdk.android.services.network.HttpRequest;
import java.io.File;
import java.io.FileFilter;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Date;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

final class AdCache {
    private static final String CACHED_AD_FILE = "ad.dat";
    private static final String CACHE_INCOMPLETE_PREFIX = "incompleteDownload_";
    private static final String CACHE_PREFIX = "nextCachedAd_";
    private static final String CACHE_PREFIX_APID = "nextCachedAd_apids";
    private static final String LOCK_FILE = "ad.lock";
    static final int PRIORITY_FETCH = 3;
    static final int PRIORITY_PRECACHE = 1;
    static final int PRIORITY_REFRESH = 2;
    private static final String PRIVATE_CACHE_DIR = ".mmsyscache";
    private static final String TAG = "AdCache";
    private static Set<String> apidListSet;
    private static String cachedVideoList;
    private static boolean cachedVideoListLoaded;
    private static Set<String> cachedVideoSet;
    private static Map<String, String> incompleteDownloadHashMap;
    private static boolean incompleteDownloadHashMapLoaded;
    static boolean isExternalEnabled = true;
    private static Map<String, String> nextCachedAdHashMap;
    private static boolean nextCachedAdHashMapLoaded;

    interface AdCacheTaskListener {
        void downloadCompleted(CachedAd cachedAd, boolean z);

        void downloadStart(CachedAd cachedAd);
    }

    static class Iterator {
        static final int ITERATE_ID = 0;
        static final int ITERATE_INFO = 1;
        static final int ITERATE_OBJECT = 2;

        Iterator() {
        }

        boolean callback(String id) {
            return false;
        }

        boolean callback(String id, int type, Date expiration, String acid, long deferredViewStart, ObjectInputStream objectInputStream) {
            return false;
        }

        boolean callback(CachedAd ad) {
            return false;
        }

        void done() {
        }
    }

    private AdCache() {
    }

    static boolean startDownloadTask(Context context, String adName, CachedAd ad, AdCacheTaskListener listener) {
        return AdCacheThreadPool.sharedThreadPool().startDownloadTask(context, adName, ad, listener);
    }

    static synchronized void cachedVideoWasAdded(Context context, String acid) {
        synchronized (AdCache.class) {
            if (acid != null) {
                if (!cachedVideoListLoaded) {
                    getCachedVideoList(context);
                }
                if (cachedVideoSet == null) {
                    cachedVideoSet = new HashSet();
                }
                cachedVideoSet.add(acid);
                cachedVideoList = null;
            }
        }
    }

    static synchronized void cachedVideoWasRemoved(Context context, String acid) {
        synchronized (AdCache.class) {
            if (acid != null) {
                if (!cachedVideoListLoaded) {
                    getCachedVideoList(context);
                }
                if (cachedVideoSet != null) {
                    cachedVideoSet.remove(acid);
                    cachedVideoList = null;
                }
            }
        }
    }

    static synchronized String getCachedVideoList(final Context context) {
        String str;
        synchronized (AdCache.class) {
            if (cachedVideoList == null) {
                if (!cachedVideoListLoaded) {
                    final Set<String> hashSet = new HashSet();
                    iterateCachedAds(context, 2, new Iterator() {
                        boolean callback(CachedAd cachedAd) {
                            if (cachedAd.acid != null && cachedAd.getType() == 1 && cachedAd.isOnDisk(context)) {
                                hashSet.add(cachedAd.acid);
                            }
                            return true;
                        }
                    });
                    cachedVideoSet = hashSet;
                    cachedVideoListLoaded = true;
                }
                if (cachedVideoSet != null && cachedVideoSet.size() > 0) {
                    StringBuilder stringBuilder = new StringBuilder();
                    for (String acid : cachedVideoSet) {
                        if (stringBuilder.length() > 0) {
                            stringBuilder.append("," + acid);
                        } else {
                            stringBuilder.append(acid);
                        }
                    }
                    cachedVideoList = stringBuilder.toString();
                }
            }
            str = cachedVideoList;
        }
        return str;
    }

    private static void loadNextCachedAdHashMap(Context context) {
        SharedPreferences settings = context.getSharedPreferences("MillennialMediaSettings", 0);
        nextCachedAdHashMap = new ConcurrentHashMap();
        if (apidListSet == null) {
            loadApidListSet(settings);
        }
        for (String apid : apidListSet) {
            for (String adType : MMAdImpl.getAdTypes()) {
                String result = settings.getString(CACHE_PREFIX + adType + '_' + apid, null);
                if (result != null) {
                    nextCachedAdHashMap.put(adType + '_' + apid, result);
                }
            }
        }
        nextCachedAdHashMapLoaded = true;
    }

    private static void saveNextCachedAdHashMapValue(Context context, String adName) {
        if (adName != null) {
            Editor editor = context.getSharedPreferences("MillennialMediaSettings", 0).edit();
            saveApidListSet(editor, adName);
            editor.putString(CACHE_PREFIX + adName, (String) nextCachedAdHashMap.get(adName));
            editor.commit();
        }
    }

    static synchronized String getNextCachedAd(Context context, String adName) {
        String str;
        synchronized (AdCache.class) {
            if (!nextCachedAdHashMapLoaded) {
                loadNextCachedAdHashMap(context);
            }
            str = adName == null ? null : (String) nextCachedAdHashMap.get(adName);
        }
        return str;
    }

    static CachedAd loadNextCachedAd(Context context, String adName) {
        String nextAd = getNextCachedAd(context, adName);
        return (nextAd == null || nextAd.equals("")) ? null : load(context, nextAd);
    }

    static synchronized void setNextCachedAd(Context context, String adName, String id) {
        synchronized (AdCache.class) {
            if (!nextCachedAdHashMapLoaded) {
                loadNextCachedAdHashMap(context);
            }
            if (adName != null) {
                Map map = nextCachedAdHashMap;
                if (id == null) {
                    id = "";
                }
                map.put(adName, id);
                saveNextCachedAdHashMapValue(context, adName);
            }
        }
    }

    private static void loadIncompleteDownloadHashMap(Context context) {
        SharedPreferences settings = context.getSharedPreferences("MillennialMediaSettings", 0);
        incompleteDownloadHashMap = new ConcurrentHashMap();
        if (apidListSet == null) {
            loadApidListSet(settings);
        }
        for (String apid : apidListSet) {
            for (String adType : MMAdImpl.getAdTypes()) {
                String result = settings.getString(CACHE_INCOMPLETE_PREFIX + adType + '_' + apid, null);
                if (result != null) {
                    incompleteDownloadHashMap.put(adType + '_' + apid, result);
                }
            }
        }
        incompleteDownloadHashMapLoaded = true;
    }

    private static void saveIncompleteDownloadHashMap(Context context, String adName) {
        if (adName != null) {
            Editor editor = context.getSharedPreferences("MillennialMediaSettings", 0).edit();
            saveApidListSet(editor, adName);
            editor.putString(CACHE_INCOMPLETE_PREFIX + adName, (String) incompleteDownloadHashMap.get(adName));
            editor.commit();
        }
    }

    static synchronized String getIncompleteDownload(Context context, String adName) {
        String str;
        synchronized (AdCache.class) {
            if (!incompleteDownloadHashMapLoaded) {
                loadIncompleteDownloadHashMap(context);
            }
            str = adName == null ? null : (String) incompleteDownloadHashMap.get(adName);
        }
        return str;
    }

    static CachedAd loadIncompleteDownload(Context context, String adName) {
        String nextIncompleteAd = getIncompleteDownload(context, adName);
        return nextIncompleteAd == null ? null : load(context, nextIncompleteAd);
    }

    static synchronized void setIncompleteDownload(Context context, String adName, String id) {
        synchronized (AdCache.class) {
            if (!incompleteDownloadHashMapLoaded) {
                loadIncompleteDownloadHashMap(context);
            }
            if (adName != null) {
                Map map = incompleteDownloadHashMap;
                if (id == null) {
                    id = "";
                }
                map.put(adName, id);
                saveIncompleteDownloadHashMap(context, adName);
            }
        }
    }

    private static void loadApidListSet(SharedPreferences settings) {
        apidListSet = new HashSet();
        String apids = settings.getString(CACHE_PREFIX_APID, null);
        if (apids != null) {
            String[] apidArray = apids.split(MMSDK.COMMA);
            if (apidArray != null && apidArray.length > 0) {
                for (String apid : apidArray) {
                    apidListSet.add(apid);
                }
            }
        }
    }

    private static void saveApidListSet(Editor editor, String adName) {
        int start = adName.indexOf(95);
        if (start >= 0 && start < adName.length()) {
            String apid = adName.substring(start + 1);
            if (apid != null && !apidListSet.contains(apid)) {
                StringBuilder builder = null;
                if (!apidListSet.isEmpty()) {
                    builder = new StringBuilder();
                    for (String str : apidListSet) {
                        builder.append(str + MMSDK.COMMA);
                    }
                }
                editor.putString(CACHE_PREFIX_APID, (builder == null ? "" : builder.toString()) + apid);
                apidListSet.add(apid);
            }
        }
    }

    static void iterateCachedAds(Context context, int mode, Iterator iterator) {
        ObjectInputStream objectInputStream;
        Exception e;
        Throwable th;
        File cachedDir = getInternalCacheDirectory(context);
        if (cachedDir != null) {
            File[] adFiles = cachedDir.listFiles(new FileFilter() {
                public boolean accept(File file) {
                    return !file.isDirectory() && file.getName().endsWith(AdCache.CACHED_AD_FILE);
                }
            });
            if (adFiles != null) {
                File[] arr$ = adFiles;
                int len$ = arr$.length;
                int i$ = 0;
                ObjectInputStream objectInputStream2 = null;
                while (i$ < len$) {
                    File adFile = arr$[i$];
                    if (adFile != null) {
                        try {
                            if (adFile.exists()) {
                                if (mode == 0) {
                                    if (iterator.callback(adFile.getName())) {
                                        if (objectInputStream2 != null) {
                                            try {
                                                objectInputStream2.close();
                                                objectInputStream = null;
                                            } catch (IOException e2) {
                                                MMLog.e(TAG, "Failed to close", e2);
                                                objectInputStream = objectInputStream2;
                                            }
                                            i$++;
                                            objectInputStream2 = objectInputStream;
                                        }
                                        objectInputStream = objectInputStream2;
                                        i$++;
                                        objectInputStream2 = objectInputStream;
                                    } else {
                                        if (objectInputStream2 != null) {
                                            try {
                                                objectInputStream2.close();
                                            } catch (IOException e22) {
                                                MMLog.e(TAG, "Failed to close", e22);
                                                objectInputStream = objectInputStream2;
                                            }
                                        }
                                    }
                                } else {
                                    objectInputStream = new ObjectInputStream(new FileInputStream(adFile));
                                    try {
                                        int type = objectInputStream.readInt();
                                        Date expiration = (Date) objectInputStream.readObject();
                                        String acid = (String) objectInputStream.readObject();
                                        long deferredViewStart = objectInputStream.readLong();
                                        if (mode == 1) {
                                            if (!iterator.callback(adFile.getName(), type, expiration, acid, deferredViewStart, objectInputStream)) {
                                                objectInputStream.close();
                                                objectInputStream = null;
                                                if (objectInputStream != null) {
                                                    try {
                                                        objectInputStream.close();
                                                    } catch (IOException e222) {
                                                        MMLog.e(TAG, "Failed to close", e222);
                                                    }
                                                }
                                            }
                                        } else {
                                            if (!iterator.callback((CachedAd) objectInputStream.readObject())) {
                                                objectInputStream.close();
                                                objectInputStream = null;
                                                if (objectInputStream != null) {
                                                    try {
                                                        objectInputStream.close();
                                                    } catch (IOException e2222) {
                                                        MMLog.e(TAG, "Failed to close", e2222);
                                                    }
                                                }
                                            }
                                        }
                                        if (objectInputStream != null) {
                                            try {
                                                objectInputStream.close();
                                                objectInputStream = null;
                                            } catch (IOException e22222) {
                                                MMLog.e(TAG, "Failed to close", e22222);
                                            }
                                        }
                                    } catch (Exception e3) {
                                        e = e3;
                                        try {
                                            MMLog.e(TAG, String.format("There was a problem reading the cached ad %s.", new Object[]{adFile.getName()}), e);
                                            if (objectInputStream != null) {
                                                try {
                                                    objectInputStream.close();
                                                    objectInputStream = null;
                                                } catch (IOException e222222) {
                                                    MMLog.e(TAG, "Failed to close", e222222);
                                                }
                                            }
                                            i$++;
                                            objectInputStream2 = objectInputStream;
                                        } catch (Throwable th2) {
                                            th = th2;
                                        }
                                    }
                                    i$++;
                                    objectInputStream2 = objectInputStream;
                                }
                            }
                        } catch (Exception e4) {
                            e = e4;
                            objectInputStream = objectInputStream2;
                            MMLog.e(TAG, String.format("There was a problem reading the cached ad %s.", new Object[]{adFile.getName()}), e);
                            if (objectInputStream != null) {
                                objectInputStream.close();
                                objectInputStream = null;
                            }
                            i$++;
                            objectInputStream2 = objectInputStream;
                        } catch (Throwable th3) {
                            th = th3;
                            objectInputStream = objectInputStream2;
                        }
                    }
                    if (objectInputStream2 != null) {
                        try {
                            objectInputStream2.close();
                            objectInputStream = null;
                        } catch (IOException e2222222) {
                            MMLog.e(TAG, "Failed to close", e2222222);
                            objectInputStream = objectInputStream2;
                        }
                        i$++;
                        objectInputStream2 = objectInputStream;
                    }
                    objectInputStream = objectInputStream2;
                    i$++;
                    objectInputStream2 = objectInputStream;
                }
            }
        }
        iterator.done();
        return;
        if (objectInputStream != null) {
            try {
                objectInputStream.close();
            } catch (IOException e22222222) {
                MMLog.e(TAG, "Failed to close", e22222222);
            }
        }
        throw th;
        throw th;
    }

    static void cleanCache(final Context context) {
        ThreadUtils.execute(new Runnable() {
            public void run() {
                MMLog.d(AdCache.TAG, AdCache.TAG);
                AdCache.cleanUpExpiredAds(context);
                AdCache.cleanupCache(context);
            }
        });
    }

    static void cleanUpExpiredAds(Context context) {
        iterateCachedAds(context, 1, new Iterator() {
            boolean callback(String id, int type, Date expiration, String acid, long deferredViewStart, ObjectInputStream objectInputStream) {
                if (expiration != null && expiration.getTime() <= System.currentTimeMillis()) {
                    try {
                        CachedAd ad = (CachedAd) objectInputStream.readObject();
                        MMLog.e(AdCache.TAG, String.format("Deleting expired ad %s.", new Object[]{ad.getId()}));
                    } catch (Exception e) {
                        MMLog.e(AdCache.TAG, String.format("There was a problem reading the cached ad %s.", new Object[]{id}), e);
                    }
                }
                return true;
            }
        });
    }

    static void cleanupCache(Context context) {
        cleanupInternalCache(context);
        if (isExternalStorageAvailable(context)) {
            cleanupExternalCache(context);
        }
    }

    private static void cleanupInternalCache(Context context) {
        File internalCacheDir = getInternalCacheDirectory(context);
        if (internalCacheDir != null && internalCacheDir.exists() && internalCacheDir.isDirectory()) {
            cleanupDirectory(internalCacheDir, HandShake.sharedHandShake(context).creativeCacheTimeout);
        }
    }

    private static void cleanupExternalCache(Context context) {
        File externalCacheDir = getExternalCacheDirectory(context);
        if (externalCacheDir != null && externalCacheDir.exists() && externalCacheDir.isDirectory()) {
            cleanupDirectory(externalCacheDir, HandShake.sharedHandShake(context).creativeCacheTimeout);
        }
    }

    static void cleanupDirectory(File file, long timeout) {
        for (File entry : file.listFiles()) {
            if (entry.isFile()) {
                if (System.currentTimeMillis() - entry.lastModified() > timeout) {
                    entry.delete();
                }
            } else if (entry.isDirectory()) {
                try {
                    cleanupDirectory(entry, timeout);
                    if (entry.list() != null && entry.list().length == 0) {
                        entry.delete();
                    }
                } catch (SecurityException e) {
                    MMLog.e(TAG, "Security Exception cleaning up directory", e);
                }
            }
        }
    }

    static void resetCache(final Context context) {
        iterateCachedAds(context, 2, new Iterator() {
            boolean callback(CachedAd ad) {
                MMLog.d(AdCache.TAG, String.format("Deleting ad %s.", new Object[]{ad.getId()}));
                ad.delete(context);
                return true;
            }
        });
        cachedVideoSet = null;
        cachedVideoList = null;
        cachedVideoListLoaded = false;
        if (nextCachedAdHashMap != null) {
            for (String key : nextCachedAdHashMap.keySet()) {
                setNextCachedAd(context, key, null);
            }
        }
        if (incompleteDownloadHashMap != null) {
            for (String key2 : incompleteDownloadHashMap.keySet()) {
                setIncompleteDownload(context, key2, null);
            }
        }
    }

    static File getDownloadFile(Context context, String name) {
        File dir = getExternalCacheDirectory(context);
        if (dir != null) {
            return new File(dir, name);
        }
        return null;
    }

    static File getDownloadFile(Context context, String subDirectory, String name) {
        File dir = getExternalCacheDirectory(context);
        if (dir != null) {
            return new File(dir, subDirectory + File.separator + name);
        }
        return null;
    }

    static boolean hasDownloadFile(Context context, String name) {
        File file = getDownloadFile(context, name);
        return file != null && file.exists();
    }

    static File getCacheDirectory(Context context) {
        if (isExternalStorageAvailable(context)) {
            return getExternalCacheDirectory(context);
        }
        return getInternalCacheDirectory(context);
    }

    static File getExternalCacheDirectory(Context context) {
        File externalStorageDir = Environment.getExternalStorageDirectory();
        if (externalStorageDir == null) {
            return null;
        }
        File extCacheDir = new File(externalStorageDir, PRIVATE_CACHE_DIR);
        if (extCacheDir.exists() || extCacheDir.mkdirs()) {
            return extCacheDir;
        }
        return null;
    }

    static File getInternalCacheDirectory(Context context) {
        if (context == null) {
            return null;
        }
        File cacheDir = new File(context.getFilesDir(), PRIVATE_CACHE_DIR);
        if (cacheDir == null || cacheDir.exists() || cacheDir.mkdirs()) {
            return cacheDir;
        }
        return null;
    }

    private static File getCachedAdFile(Context context, String id) {
        String fileName = id + CACHED_AD_FILE;
        boolean isExternalStateMounted = isExternalStorageAvailable(context);
        File cacheDir = getInternalCacheDirectory(context);
        File adFile = null;
        if (cacheDir != null) {
            try {
                if (cacheDir.isDirectory()) {
                    adFile = new File(cacheDir, fileName);
                }
            } catch (Exception e) {
                MMLog.e(TAG, "getCachedAdFile: ", e);
                return null;
            }
        }
        if ((adFile == null || !adFile.exists()) && !isExternalStateMounted) {
            File internalCacheDir = context.getFilesDir();
            if (internalCacheDir != null) {
                File internalFile = new File(internalCacheDir, PRIVATE_CACHE_DIR + File.separator + fileName);
                if (internalFile.exists() && internalFile.isFile()) {
                    return internalFile;
                }
            }
        }
        return adFile;
    }

    static boolean downloadComponentInternalCache(String urlString, String name, Context context) {
        File internalDir = getInternalCacheDirectory(context);
        if (internalDir == null || !internalDir.isDirectory()) {
            return false;
        }
        return downloadComponent(urlString, name, internalDir, context);
    }

    static boolean downloadComponentExternalStorage(String urlString, String name, Context context) {
        File externalDir = getExternalCacheDirectory(context);
        if (externalDir == null || !externalDir.isDirectory()) {
            return false;
        }
        return downloadComponent(urlString, name, externalDir, context);
    }

    static boolean downloadComponentExternalStorage(String urlString, String subDirectory, String name, Context context) {
        File externalDir = getExternalCacheDirectory(context);
        if (externalDir == null || !externalDir.isDirectory()) {
            return false;
        }
        return downloadComponent(urlString, name, new File(externalDir, subDirectory), context);
    }

    private static boolean downloadComponent(String urlString, String name, File path, Context context) {
        Exception e;
        Throwable th;
        if (TextUtils.isEmpty(urlString)) {
            MMLog.d(TAG, "No Url ...");
            return false;
        }
        File file = new File(path, name);
        MMLog.v(TAG, String.format("Downloading Component: %s from %s", new Object[]{name, urlString}));
        File parent = file.getParentFile();
        if (!parent.exists() && !parent.mkdirs()) {
            MMLog.v(TAG, parent + " does not exist and cannot create directory.");
            return false;
        } else if (!file.exists() || file.length() <= 0) {
            InputStream inStream = null;
            FileOutputStream fileOutputStream = null;
            long contentLength = -1;
            try {
                URL connectURL = new URL(urlString);
                HttpURLConnection.setFollowRedirects(true);
                HttpURLConnection conn = (HttpURLConnection) connectURL.openConnection();
                conn.setConnectTimeout(30000);
                conn.setRequestMethod(HttpRequest.METHOD_GET);
                conn.connect();
                inStream = conn.getInputStream();
                String temp = conn.getHeaderField(HttpRequest.HEADER_CONTENT_LENGTH);
                if (temp != null) {
                    contentLength = Long.parseLong(temp);
                }
                if (inStream == null) {
                    MMLog.e(TAG, String.format("Connection stream is null downloading %s.", new Object[]{name}));
                    if (inStream != null) {
                        try {
                            inStream.close();
                        } catch (IOException ex) {
                            MMLog.e(TAG, "Content caching error downloading component: ", ex);
                            if (file != null) {
                                file.delete();
                            }
                            return false;
                        }
                    }
                    if (fileOutputStream == null) {
                        return false;
                    }
                    fileOutputStream.close();
                    return false;
                }
                FileOutputStream out = new FileOutputStream(file);
                try {
                    byte[] buf = new byte[4096];
                    while (true) {
                        int numread = inStream.read(buf);
                        if (numread <= 0) {
                            break;
                        }
                        out.write(buf, 0, numread);
                    }
                    if (file != null) {
                        try {
                            if (file.length() == contentLength || contentLength == -1) {
                                if (inStream != null) {
                                    try {
                                        inStream.close();
                                    } catch (IOException ex2) {
                                        MMLog.e(TAG, "Content caching error downloading component: ", ex2);
                                        if (file != null) {
                                            file.delete();
                                        }
                                        return false;
                                    }
                                }
                                if (out == null) {
                                    return true;
                                }
                                out.close();
                                return true;
                            }
                            MMLog.e(TAG, "Content-Length does not match actual length.");
                        } catch (Exception e2) {
                            MMLog.e(TAG, String.format("Exception downloading component %s: ", new Object[]{name}), e2);
                        }
                    }
                    if (inStream != null) {
                        try {
                            inStream.close();
                        } catch (IOException ex22) {
                            MMLog.e(TAG, "Content caching error downloading component: ", ex22);
                            if (file != null) {
                                file.delete();
                            }
                            return false;
                        }
                    }
                    if (out != null) {
                        out.close();
                    }
                    fileOutputStream = out;
                } catch (Exception e3) {
                    e2 = e3;
                    fileOutputStream = out;
                    try {
                        MMLog.e(TAG, String.format("Exception downloading component %s: %s", new Object[]{name, e2}));
                        if (inStream != null) {
                            try {
                                inStream.close();
                            } catch (IOException ex222) {
                                MMLog.e(TAG, "Content caching error downloading component: ", ex222);
                                if (file != null) {
                                    file.delete();
                                }
                                return false;
                            }
                        }
                        if (fileOutputStream != null) {
                            fileOutputStream.close();
                        }
                        if (file != null) {
                            file.delete();
                        }
                        return false;
                    } catch (Throwable th2) {
                        th = th2;
                        if (inStream != null) {
                            try {
                                inStream.close();
                            } catch (IOException ex2222) {
                                MMLog.e(TAG, "Content caching error downloading component: ", ex2222);
                                if (file != null) {
                                    file.delete();
                                }
                                return false;
                            }
                        }
                        if (fileOutputStream != null) {
                            fileOutputStream.close();
                        }
                        throw th;
                    }
                } catch (Throwable th3) {
                    th = th3;
                    fileOutputStream = out;
                    if (inStream != null) {
                        inStream.close();
                    }
                    if (fileOutputStream != null) {
                        fileOutputStream.close();
                    }
                    throw th;
                }
                if (file != null) {
                    file.delete();
                }
                return false;
            } catch (Exception e4) {
                e2 = e4;
                MMLog.e(TAG, String.format("Exception downloading component %s: %s", new Object[]{name, e2}));
                if (inStream != null) {
                    inStream.close();
                }
                if (fileOutputStream != null) {
                    fileOutputStream.close();
                }
                if (file != null) {
                    file.delete();
                }
                return false;
            }
        } else {
            MMLog.v(TAG, name + " already exists, skipping...");
            return true;
        }
    }

    static CachedAd load(Context context, String id) {
        FileNotFoundException fe;
        Throwable th;
        Exception e;
        if (id != null) {
            if (!id.equals("")) {
                ObjectInputStream objectInputStream = null;
                CachedAd ad = null;
                File cachedAdFile = getCachedAdFile(context, id);
                if (cachedAdFile == null) {
                    return null;
                }
                try {
                    ObjectInputStream objectInputStream2 = new ObjectInputStream(new FileInputStream(cachedAdFile));
                    try {
                        objectInputStream2.readInt();
                        Date expiration = (Date) objectInputStream2.readObject();
                        objectInputStream2.readObject();
                        long deferredViewStart = objectInputStream2.readLong();
                        ad = (CachedAd) objectInputStream2.readObject();
                        if (objectInputStream2 != null) {
                            try {
                                objectInputStream2.close();
                            } catch (IOException e2) {
                                MMLog.e(TAG, "Failed to close", e2);
                                objectInputStream = objectInputStream2;
                                return ad;
                            }
                        }
                        objectInputStream = objectInputStream2;
                        return ad;
                    } catch (FileNotFoundException e3) {
                        fe = e3;
                        objectInputStream = objectInputStream2;
                        try {
                            MMLog.e(TAG, "There was a problem loading up the cached ad " + id + ". Ad is not on disk.", fe);
                            if (objectInputStream != null) {
                                return ad;
                            }
                            try {
                                objectInputStream.close();
                                return ad;
                            } catch (IOException e22) {
                                MMLog.e(TAG, "Failed to close", e22);
                                return ad;
                            }
                        } catch (Throwable th2) {
                            th = th2;
                            if (objectInputStream != null) {
                                try {
                                    objectInputStream.close();
                                } catch (IOException e222) {
                                    MMLog.e(TAG, "Failed to close", e222);
                                }
                            }
                            throw th;
                        }
                    } catch (Exception e4) {
                        e = e4;
                        objectInputStream = objectInputStream2;
                        MMLog.e(TAG, String.format("There was a problem loading up the cached ad %s.", new Object[]{id}), e);
                        if (objectInputStream != null) {
                            return ad;
                        }
                        try {
                            objectInputStream.close();
                            return ad;
                        } catch (IOException e2222) {
                            MMLog.e(TAG, "Failed to close", e2222);
                            return ad;
                        }
                    } catch (Throwable th3) {
                        th = th3;
                        objectInputStream = objectInputStream2;
                        if (objectInputStream != null) {
                            objectInputStream.close();
                        }
                        throw th;
                    }
                } catch (FileNotFoundException e5) {
                    fe = e5;
                    MMLog.e(TAG, "There was a problem loading up the cached ad " + id + ". Ad is not on disk.", fe);
                    if (objectInputStream != null) {
                        return ad;
                    }
                    objectInputStream.close();
                    return ad;
                } catch (Exception e6) {
                    e = e6;
                    MMLog.e(TAG, String.format("There was a problem loading up the cached ad %s.", new Object[]{id}), e);
                    if (objectInputStream != null) {
                        return ad;
                    }
                    objectInputStream.close();
                    return ad;
                }
            }
        }
        return null;
    }

    static boolean save(Context context, CachedAd ad) {
        Exception e;
        Throwable th;
        File lockFile = null;
        ObjectOutputStream objectOutputStream = null;
        if (ad == null) {
            return false;
        }
        File cachedAdFile = getCachedAdFile(context, ad.getId());
        if (cachedAdFile == null) {
            return false;
        }
        MMLog.v(TAG, String.format("Saving CachedAd %s to %s", new Object[]{ad.getId(), cachedAdFile}));
        try {
            File lockFile2 = new File(cachedAdFile.getParent(), ad.getId() + LOCK_FILE);
            try {
                if (lockFile2.createNewFile()) {
                    ObjectOutputStream objectOutputStream2 = new ObjectOutputStream(new FileOutputStream(cachedAdFile));
                    try {
                        objectOutputStream2.writeInt(ad.getType());
                        objectOutputStream2.writeObject(ad.expiration);
                        objectOutputStream2.writeObject(ad.acid);
                        objectOutputStream2.writeLong(ad.deferredViewStart);
                        objectOutputStream2.writeObject(ad);
                        try {
                            lockFile2.delete();
                            if (objectOutputStream2 != null) {
                                objectOutputStream2.close();
                            }
                        } catch (IOException e2) {
                            MMLog.e(TAG, "Failed to close", e2);
                        }
                        if (ad.saveAssets(context)) {
                            lockFile = lockFile2;
                            return true;
                        }
                        ad.delete(context);
                        objectOutputStream = objectOutputStream2;
                        lockFile = lockFile2;
                        return false;
                    } catch (Exception e3) {
                        e = e3;
                        objectOutputStream = objectOutputStream2;
                        lockFile = lockFile2;
                        try {
                            MMLog.e(TAG, String.format("There was a problem saving the cached ad %s.", new Object[]{ad.getId()}), e);
                            try {
                                lockFile.delete();
                                if (objectOutputStream != null) {
                                    return false;
                                }
                                objectOutputStream.close();
                                return false;
                            } catch (IOException e22) {
                                MMLog.e(TAG, "Failed to close", e22);
                                return false;
                            }
                        } catch (Throwable th2) {
                            th = th2;
                            try {
                                lockFile.delete();
                                if (objectOutputStream != null) {
                                    objectOutputStream.close();
                                }
                            } catch (IOException e222) {
                                MMLog.e(TAG, "Failed to close", e222);
                            }
                            throw th;
                        }
                    } catch (Throwable th3) {
                        th = th3;
                        objectOutputStream = objectOutputStream2;
                        lockFile = lockFile2;
                        lockFile.delete();
                        if (objectOutputStream != null) {
                            objectOutputStream.close();
                        }
                        throw th;
                    }
                }
                MMLog.v(TAG, String.format("Could not save the cached ad %s. Ad was locked.", new Object[]{ad.getId()}));
                try {
                    lockFile2.delete();
                    if (objectOutputStream != null) {
                        objectOutputStream.close();
                    }
                } catch (IOException e2222) {
                    MMLog.e(TAG, "Failed to close", e2222);
                }
                lockFile = lockFile2;
                return false;
            } catch (Exception e4) {
                e = e4;
                lockFile = lockFile2;
                MMLog.e(TAG, String.format("There was a problem saving the cached ad %s.", new Object[]{ad.getId()}), e);
                lockFile.delete();
                if (objectOutputStream != null) {
                    return false;
                }
                objectOutputStream.close();
                return false;
            } catch (Throwable th4) {
                th = th4;
                lockFile = lockFile2;
                lockFile.delete();
                if (objectOutputStream != null) {
                    objectOutputStream.close();
                }
                throw th;
            }
        } catch (Exception e5) {
            e = e5;
            MMLog.e(TAG, String.format("There was a problem saving the cached ad %s.", new Object[]{ad.getId()}), e);
            lockFile.delete();
            if (objectOutputStream != null) {
                return false;
            }
            objectOutputStream.close();
            return false;
        }
    }

    static boolean deleteFile(Context context, String fileName) {
        File deleteFile = getCachedAdFile(context, fileName);
        if (deleteFile != null) {
            return deleteFile.delete();
        }
        return false;
    }

    static boolean isExternalStorageAvailable(Context context) {
        if (context == null) {
            return false;
        }
        String storageState = Environment.getExternalStorageState();
        if (context.checkCallingOrSelfPermission("android.permission.WRITE_EXTERNAL_STORAGE") == 0 && !TextUtils.isEmpty(storageState) && storageState.equals("mounted") && isExternalEnabled) {
            return true;
        }
        return false;
    }

    static boolean isExternalMounted() {
        return Environment.getExternalStorageState().equals("mounted");
    }

    static void setEnableExternalStorage(boolean on) {
        isExternalEnabled = on;
    }
}
