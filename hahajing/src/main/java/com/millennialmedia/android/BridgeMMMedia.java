package com.millennialmedia.android;

import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.Bitmap.CompressFormat;
import android.graphics.BitmapFactory;
import android.graphics.BitmapFactory.Options;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.media.MediaPlayer.OnCompletionListener;
import android.media.MediaScannerConnection;
import android.media.MediaScannerConnection.MediaScannerConnectionClient;
import android.media.SoundPool;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import com.givewaygames.camera.utils.Constants;
import com.google.android.gms.cast.TextTrackStyle;
import com.inmobi.commons.analytics.db.AnalyticsSQLiteHelper;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.CopyOnWriteArrayList;
import org.json.JSONArray;

class BridgeMMMedia extends MMJSObject {
    private static final String PATH = "path";
    private static final String TAG = "BridgeMMMedia";
    private static Object pickerActivityObject;
    private String AVAILABLE_SOURCE_TYPES = "availableSourceTypes";
    private String GET_DEVICE_VOLUME = "getDeviceVolume";
    private String GET_PICTURE = "getPicture";
    private String IS_SOURCE_TYPE_AVAILABLE = "isSourceTypeAvailable";
    private String PLAY_AUDIO = "playAudio";
    private String PLAY_SOUND = "playSound";
    private String PLAY_VIDEO = "playVideo";
    private String STOP_AUDIO = "stopAudio";
    private String WRITE_TO_PHOTO_LIBRARY = "writeToPhotoLibrary";
    MediaScannerConnection mediaScanner;

    static class Audio implements OnCompletionListener {
        private static final int MAX_SOUNDS = 4;
        private static Audio sharedInstance;
        private OnLoadCompleteListener completionListener;
        CopyOnWriteArrayList<OnCompletionListener> completionListeners;
        private WeakReference<Context> contextRef;
        private MediaPlayer mediaPlayer;
        CopyOnWriteArrayList<PeriodicListener> periodicListeners;
        Runnable periodicUpdater = new Runnable() {
            public void run() {
                if (Audio.this.mediaPlayer != null) {
                    if (Audio.this.mediaPlayer.isPlaying()) {
                        int currentPositionMillis = Audio.this.mediaPlayer.getCurrentPosition();
                        if (Audio.this.periodicListeners != null) {
                            Iterator i$ = Audio.this.periodicListeners.iterator();
                            while (i$.hasNext()) {
                                ((PeriodicListener) i$.next()).onUpdate(currentPositionMillis);
                            }
                        }
                    }
                    MMSDK.runOnUiThreadDelayed(this, 500);
                }
            }
        };
        private SoundPool soundPool;

        private abstract class OnLoadCompleteListener {
            private static final int TEST_PERIOD_MS = 100;
            private ArrayList<Integer> sampleIds = new ArrayList();
            private SoundPool soundPool;
            private Timer timer;

            abstract void onLoadComplete(SoundPool soundPool, int i, int i2);

            public OnLoadCompleteListener(SoundPool soundPool) {
                this.soundPool = soundPool;
            }

            synchronized void testSample(int sampleId) {
                this.sampleIds.add(Integer.valueOf(sampleId));
                if (this.sampleIds.size() == 1) {
                    this.timer = new Timer();
                    this.timer.scheduleAtFixedRate(new TimerTask() {
                        public void run() {
                            ArrayList<Integer> completedOnes = new ArrayList();
                            Iterator i$ = OnLoadCompleteListener.this.sampleIds.iterator();
                            while (i$.hasNext()) {
                                Integer sampleId = (Integer) i$.next();
                                int streamId = OnLoadCompleteListener.this.soundPool.play(sampleId.intValue(), 0.0f, 0.0f, 0, 0, TextTrackStyle.DEFAULT_FONT_SCALE);
                                if (streamId != 0) {
                                    OnLoadCompleteListener.this.soundPool.stop(streamId);
                                    OnLoadCompleteListener.this.onLoadComplete(OnLoadCompleteListener.this.soundPool, sampleId.intValue(), 0);
                                    completedOnes.add(sampleId);
                                }
                            }
                            OnLoadCompleteListener.this.sampleIds.removeAll(completedOnes);
                            if (OnLoadCompleteListener.this.sampleIds.size() == 0) {
                                OnLoadCompleteListener.this.timer.cancel();
                                OnLoadCompleteListener.this.timer.purge();
                            }
                        }
                    }, 0, 100);
                }
            }

            synchronized void release() {
                if (this.timer != null) {
                    this.timer.cancel();
                    this.timer.purge();
                }
            }
        }

        interface PeriodicListener {
            void onUpdate(int i);
        }

        private Audio() {
        }

        private Audio(Context context) {
            this.contextRef = new WeakReference(context.getApplicationContext());
        }

        boolean addCompletionListener(OnCompletionListener listener) {
            if (this.completionListeners == null) {
                this.completionListeners = new CopyOnWriteArrayList();
            }
            if (this.completionListeners.contains(listener)) {
                return false;
            }
            return this.completionListeners.add(listener);
        }

        boolean removeCompletionListener(OnCompletionListener listener) {
            if (this.completionListeners != null) {
                return this.completionListeners.remove(listener);
            }
            return false;
        }

        boolean addPeriodicListener(PeriodicListener listener) {
            if (this.periodicListeners == null) {
                this.periodicListeners = new CopyOnWriteArrayList();
            }
            if (this.periodicListeners.contains(listener)) {
                return false;
            }
            return this.periodicListeners.add(listener);
        }

        boolean removePeriodicListener(PeriodicListener listener) {
            if (this.periodicListeners != null) {
                return this.periodicListeners.remove(listener);
            }
            return false;
        }

        static synchronized Audio sharedAudio(Context context) {
            Audio audio;
            synchronized (Audio.class) {
                if (sharedInstance == null) {
                    sharedInstance = new Audio(context);
                }
                audio = sharedInstance;
            }
            return audio;
        }

        synchronized boolean isPlaying() {
            boolean z;
            z = this.mediaPlayer != null && this.mediaPlayer.isPlaying();
            return z;
        }

        public synchronized void onCompletion(MediaPlayer mp) {
            if (this.completionListeners != null) {
                Iterator i$ = this.completionListeners.iterator();
                while (i$.hasNext()) {
                    ((OnCompletionListener) i$.next()).onCompletion(this.mediaPlayer);
                }
            }
            if (this.mediaPlayer != null) {
                this.mediaPlayer.release();
                this.mediaPlayer = null;
            }
        }

        synchronized MMJSResponse playAudio(Uri uri, boolean loop) {
            try {
                if (this.mediaPlayer != null) {
                    this.mediaPlayer.release();
                    this.mediaPlayer = null;
                }
                this.mediaPlayer = MediaPlayer.create((Context) this.contextRef.get(), uri);
                this.mediaPlayer.setLooping(loop);
                this.mediaPlayer.start();
                this.mediaPlayer.setOnCompletionListener(this);
                MMSDK.runOnUiThread(this.periodicUpdater);
            } catch (Exception e) {
                MMLog.e(BridgeMMMedia.TAG, "Exception in playAudio method", e.getCause());
            }
            return MMJSResponse.responseWithSuccess("Audio playback started");
        }

        synchronized MMJSResponse playSound(File file) {
            try {
                if (this.soundPool == null) {
                    this.soundPool = new SoundPool(4, 3, 0);
                    this.completionListener = new OnLoadCompleteListener(this.soundPool) {
                        public synchronized void onLoadComplete(SoundPool soundPool, int sampleId, int status) {
                            if (soundPool != null) {
                                Context context = (Context) Audio.this.contextRef.get();
                                if (context != null) {
                                    AudioManager audioManager = (AudioManager) context.getSystemService("audio");
                                    float streamVolume = (((float) audioManager.getStreamVolume(3)) + 0.0f) / ((float) audioManager.getStreamMaxVolume(3));
                                    soundPool.play(sampleId, streamVolume, streamVolume, 1, 0, TextTrackStyle.DEFAULT_FONT_SCALE);
                                }
                            }
                        }
                    };
                }
                this.completionListener.testSample(this.soundPool.load(file.getAbsolutePath(), 1));
            } catch (Exception e) {
            }
            return MMJSResponse.responseWithSuccess("Sound playback started");
        }

        synchronized MMJSResponse stop() {
            if (this.mediaPlayer != null) {
                onCompletion(this.mediaPlayer);
            }
            if (this.soundPool != null) {
                this.soundPool.release();
                this.soundPool = null;
            }
            if (this.completionListener != null) {
                this.completionListener.release();
                this.completionListener = null;
            }
            return MMJSResponse.responseWithSuccess("Audio stopped");
        }
    }

    static class PickerActivity extends MMBaseActivity {
        private Uri fileUri;
        boolean hasRequestedPic = false;

        PickerActivity() {
        }

        public Object onRetainNonConfigurationInstance() {
            super.onRetainNonConfigurationInstance();
            Bundle outState = new Bundle();
            outState.putBoolean("hasRequestedPic", this.hasRequestedPic);
            return outState;
        }

        public void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            if (getLastNonConfigurationInstance() != null) {
                this.hasRequestedPic = ((Bundle) getLastNonConfigurationInstance()).getBoolean("hasRequestedPic");
            }
            if (!this.hasRequestedPic) {
                Intent intent;
                if (getIntent().getStringExtra(AnalyticsSQLiteHelper.EVENT_LIST_TYPE).equalsIgnoreCase("Camera")) {
                    intent = new Intent("android.media.action.IMAGE_CAPTURE");
                    this.fileUri = getIntent().getData();
                    intent.putExtra("return-data", true);
                    this.hasRequestedPic = true;
                    startActivityForResult(intent, 0);
                    return;
                }
                intent = new Intent();
                intent.setType("image/*");
                intent.setAction("android.intent.action.GET_CONTENT");
                this.hasRequestedPic = true;
                startActivityForResult(intent, 0);
            }
        }

        protected void onActivityResult(int requestCode, int resultCode, Intent data) {
            OutputStream fileOutputStream;
            Throwable e;
            Throwable th;
            super.onActivityResult(requestCode, resultCode, data);
            if (data != null) {
                InputStream in = null;
                OutputStream out = null;
                try {
                    Uri contentUri = data.getData();
                    File file;
                    byte[] buf;
                    int len;
                    if (contentUri == null) {
                        if (data.getExtras() != null) {
                            Bitmap bitmap = (Bitmap) data.getExtras().get("data");
                            try {
                                file = new File(getIntent().getData().getPath());
                                ByteArrayOutputStream bos = new ByteArrayOutputStream();
                                bitmap.compress(CompressFormat.PNG, 0, bos);
                                ByteArrayInputStream bs = new ByteArrayInputStream(bos.toByteArray());
                                fileOutputStream = new FileOutputStream(file);
                                try {
                                    buf = new byte[1024];
                                    while (true) {
                                        len = bs.read(buf);
                                        if (len <= 0) {
                                            break;
                                        }
                                        fileOutputStream.write(buf, 0, len);
                                    }
                                    if (in != null) {
                                        try {
                                            in.close();
                                        } catch (Throwable e2) {
                                            try {
                                                MMLog.e(BridgeMMMedia.TAG, "Error closing file", e2);
                                                out = fileOutputStream;
                                            } catch (Exception e3) {
                                                e2 = e3;
                                                out = fileOutputStream;
                                                MMLog.e(BridgeMMMedia.TAG, "Error with picture: ", e2);
                                                synchronized (BridgeMMMedia.pickerActivityObject) {
                                                    BridgeMMMedia.pickerActivityObject.notify();
                                                }
                                                finish();
                                            }
                                        }
                                    }
                                    if (fileOutputStream != null) {
                                        fileOutputStream.close();
                                    }
                                    out = fileOutputStream;
                                } catch (Exception e4) {
                                    e2 = e4;
                                    out = fileOutputStream;
                                    try {
                                        MMLog.e(BridgeMMMedia.TAG, "Problem getting bitmap from data", e2);
                                        if (in != null) {
                                            try {
                                                in.close();
                                            } catch (Throwable e22) {
                                                MMLog.e(BridgeMMMedia.TAG, "Error closing file", e22);
                                            }
                                        }
                                        if (out != null) {
                                            out.close();
                                        }
                                        synchronized (BridgeMMMedia.pickerActivityObject) {
                                            BridgeMMMedia.pickerActivityObject.notify();
                                        }
                                        finish();
                                    } catch (Throwable th2) {
                                        th = th2;
                                        if (in != null) {
                                            try {
                                                in.close();
                                            } catch (Throwable e222) {
                                                MMLog.e(BridgeMMMedia.TAG, "Error closing file", e222);
                                                throw th;
                                            }
                                        }
                                        if (out != null) {
                                            out.close();
                                        }
                                        throw th;
                                    }
                                } catch (Throwable th3) {
                                    th = th3;
                                    out = fileOutputStream;
                                    if (in != null) {
                                        in.close();
                                    }
                                    if (out != null) {
                                        out.close();
                                    }
                                    throw th;
                                }
                            } catch (Exception e5) {
                                e222 = e5;
                                MMLog.e(BridgeMMMedia.TAG, "Problem getting bitmap from data", e222);
                                if (in != null) {
                                    in.close();
                                }
                                if (out != null) {
                                    out.close();
                                }
                                synchronized (BridgeMMMedia.pickerActivityObject) {
                                    BridgeMMMedia.pickerActivityObject.notify();
                                }
                                finish();
                            }
                        }
                    } else if (contentUri != null) {
                        String[] proj = new String[]{"_data"};
                        ContentResolver resolver = getContentResolver();
                        if (resolver != null) {
                            Cursor cursor = resolver.query(contentUri, proj, null, null, null);
                            if (cursor != null) {
                                int index = cursor.getColumnIndex("_data");
                                if (index != -1) {
                                    cursor.moveToFirst();
                                    File chosenFile = new File(cursor.getString(index));
                                    cursor.close();
                                    try {
                                        file = new File(getIntent().getData().getPath());
                                        InputStream fileInputStream = new FileInputStream(chosenFile);
                                        try {
                                            fileOutputStream = new FileOutputStream(file);
                                            try {
                                                buf = new byte[1024];
                                                while (true) {
                                                    len = fileInputStream.read(buf);
                                                    if (len <= 0) {
                                                        break;
                                                    }
                                                    fileOutputStream.write(buf, 0, len);
                                                }
                                                if (fileInputStream != null) {
                                                    try {
                                                        fileInputStream.close();
                                                    } catch (Throwable e2222) {
                                                        try {
                                                            MMLog.e(BridgeMMMedia.TAG, "Error closing file", e2222);
                                                            out = fileOutputStream;
                                                            in = fileInputStream;
                                                        } catch (Exception e6) {
                                                            e2222 = e6;
                                                            out = fileOutputStream;
                                                            in = fileInputStream;
                                                            MMLog.e(BridgeMMMedia.TAG, "Error with picture: ", e2222);
                                                            synchronized (BridgeMMMedia.pickerActivityObject) {
                                                                BridgeMMMedia.pickerActivityObject.notify();
                                                            }
                                                            finish();
                                                        }
                                                    }
                                                }
                                                if (fileOutputStream != null) {
                                                    fileOutputStream.close();
                                                }
                                                out = fileOutputStream;
                                                in = fileInputStream;
                                            } catch (Exception e7) {
                                                e2222 = e7;
                                                out = fileOutputStream;
                                                in = fileInputStream;
                                                try {
                                                    MMLog.e(BridgeMMMedia.TAG, "Error copying image", e2222);
                                                    if (in != null) {
                                                        try {
                                                            in.close();
                                                        } catch (Throwable e22222) {
                                                            MMLog.e(BridgeMMMedia.TAG, "Error closing file", e22222);
                                                        }
                                                    }
                                                    if (out != null) {
                                                        out.close();
                                                    }
                                                    synchronized (BridgeMMMedia.pickerActivityObject) {
                                                        BridgeMMMedia.pickerActivityObject.notify();
                                                    }
                                                    finish();
                                                } catch (Throwable th4) {
                                                    th = th4;
                                                    if (in != null) {
                                                        try {
                                                            in.close();
                                                        } catch (Throwable e222222) {
                                                            MMLog.e(BridgeMMMedia.TAG, "Error closing file", e222222);
                                                            throw th;
                                                        }
                                                    }
                                                    if (out != null) {
                                                        out.close();
                                                    }
                                                    throw th;
                                                }
                                            } catch (Throwable th5) {
                                                th = th5;
                                                out = fileOutputStream;
                                                in = fileInputStream;
                                                if (in != null) {
                                                    in.close();
                                                }
                                                if (out != null) {
                                                    out.close();
                                                }
                                                throw th;
                                            }
                                        } catch (Exception e8) {
                                            e222222 = e8;
                                            in = fileInputStream;
                                            MMLog.e(BridgeMMMedia.TAG, "Error copying image", e222222);
                                            if (in != null) {
                                                in.close();
                                            }
                                            if (out != null) {
                                                out.close();
                                            }
                                            synchronized (BridgeMMMedia.pickerActivityObject) {
                                                BridgeMMMedia.pickerActivityObject.notify();
                                            }
                                            finish();
                                        } catch (Throwable th6) {
                                            th = th6;
                                            in = fileInputStream;
                                            if (in != null) {
                                                in.close();
                                            }
                                            if (out != null) {
                                                out.close();
                                            }
                                            throw th;
                                        }
                                    } catch (Exception e9) {
                                        e222222 = e9;
                                        MMLog.e(BridgeMMMedia.TAG, "Error copying image", e222222);
                                        if (in != null) {
                                            in.close();
                                        }
                                        if (out != null) {
                                            out.close();
                                        }
                                        synchronized (BridgeMMMedia.pickerActivityObject) {
                                            BridgeMMMedia.pickerActivityObject.notify();
                                        }
                                        finish();
                                    }
                                }
                            }
                        }
                    }
                } catch (Exception e10) {
                    e222222 = e10;
                    MMLog.e(BridgeMMMedia.TAG, "Error with picture: ", e222222);
                    synchronized (BridgeMMMedia.pickerActivityObject) {
                        BridgeMMMedia.pickerActivityObject.notify();
                    }
                    finish();
                }
            }
            synchronized (BridgeMMMedia.pickerActivityObject) {
                BridgeMMMedia.pickerActivityObject.notify();
            }
            finish();
        }
    }

    BridgeMMMedia() {
    }

    MMJSResponse executeCommand(String name, Map<String, String> arguments) {
        if (this.IS_SOURCE_TYPE_AVAILABLE.equals(name)) {
            return isSourceTypeAvailable(arguments);
        }
        if (this.AVAILABLE_SOURCE_TYPES.equals(name)) {
            return availableSourceTypes(arguments);
        }
        if (this.GET_PICTURE.equals(name)) {
            return getPicture(arguments);
        }
        if (this.WRITE_TO_PHOTO_LIBRARY.equals(name)) {
            return writeToPhotoLibrary(arguments);
        }
        if (this.PLAY_VIDEO.equals(name)) {
            return playVideo(arguments);
        }
        if (this.STOP_AUDIO.equals(name)) {
            return stopAudio(arguments);
        }
        if (this.GET_DEVICE_VOLUME.equals(name)) {
            return getDeviceVolume(arguments);
        }
        if (this.PLAY_AUDIO.equals(name)) {
            return playAudio(arguments);
        }
        if (this.PLAY_SOUND.equals(name)) {
            return playSound(arguments);
        }
        return null;
    }

    private boolean isCameraAvailable() {
        Context context = (Context) this.contextRef.get();
        if (context == null || context.getPackageManager().checkPermission("android.permission.CAMERA", context.getPackageName()) != 0) {
            return false;
        }
        if (context.getPackageManager().queryIntentActivities(new Intent("android.media.action.IMAGE_CAPTURE"), 65536).size() > 0) {
            return true;
        }
        return false;
    }

    private boolean isPictureChooserAvailable() {
        Context context = (Context) this.contextRef.get();
        if (context == null) {
            return false;
        }
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction("android.intent.action.GET_CONTENT");
        if (context.getPackageManager().queryIntentActivities(intent, 65536).size() > 0) {
            return true;
        }
        return false;
    }

    public MMJSResponse isSourceTypeAvailable(Map<String, String> arguments) {
        String type = (String) arguments.get("sourceType");
        if (type != null) {
            if (type.equalsIgnoreCase("Camera") && isCameraAvailable()) {
                return MMJSResponse.responseWithSuccess("true");
            }
            if (type.equalsIgnoreCase("Photo Library") && isPictureChooserAvailable()) {
                return MMJSResponse.responseWithSuccess("true");
            }
        }
        return MMJSResponse.responseWithError("false");
    }

    public MMJSResponse availableSourceTypes(Map<String, String> map) {
        JSONArray jsonArray = new JSONArray();
        if (isCameraAvailable()) {
            jsonArray.put("Camera");
        }
        if (isPictureChooserAvailable()) {
            jsonArray.put("Photo Library");
        }
        MMJSResponse response = new MMJSResponse();
        response.result = 1;
        response.response = jsonArray;
        return response;
    }

    static Bitmap resizeImage(Bitmap image, String contentMode, int toW, int toH, int quality) {
        float horizontalRatio = ((float) toW) / ((float) image.getWidth());
        float verticalRatio = ((float) toH) / ((float) image.getHeight());
        if (contentMode.equals("Center")) {
            return centerOfImage(image, toW, toH);
        }
        float ratio;
        if (contentMode.equals("ScaleToAspectFit")) {
            ratio = Math.min(horizontalRatio, verticalRatio);
            return resizeImage(image, (int) (((float) image.getWidth()) * ratio), (int) (((float) image.getHeight()) * ratio), quality);
        } else if (!contentMode.equals("ScaleToAspectFill")) {
            return resizeImage(image, toW, toH, quality);
        } else {
            ratio = Math.max(horizontalRatio, verticalRatio);
            return cropImage(resizeImage(image, (int) (((float) image.getWidth()) * ratio), (int) (((float) image.getHeight()) * ratio), quality), 0, 0, toW, toH);
        }
    }

    private static Bitmap resizeImage(Bitmap image, int newW, int newH, int quality) {
        return Bitmap.createScaledBitmap(image, newW, newH, true);
    }

    private static Bitmap centerOfImage(Bitmap image, int width, int height) {
        return cropImage(image, (int) ((float) ((image.getWidth() - width) / 2)), (int) ((float) ((image.getHeight() - height) / 2)), width, height);
    }

    private static Bitmap cropImage(Bitmap bitmap, int left, int top, int width, int height) {
        return Bitmap.createBitmap(bitmap, left, top, width, height);
    }

    private static final byte[] scaleBitmapToBytes(File file, int w, int h, String contentMode) {
        FileNotFoundException e;
        byte[] contents;
        Bitmap finalBitmap;
        ByteArrayOutputStream byteArrayOutputStream;
        ByteArrayOutputStream baos;
        Exception e2;
        Throwable th;
        FileInputStream fis = null;
        FileInputStream fis2 = null;
        Bitmap scaledBitmap = null;
        try {
            FileInputStream fis3 = new FileInputStream(file);
            try {
                Options options = new Options();
                options.inJustDecodeBounds = true;
                BitmapFactory.decodeStream(fis3, null, options);
                int height = options.outHeight;
                int width = options.outWidth;
                int inSampleSize = 1;
                if (height > h || width > w) {
                    if (width > height) {
                        inSampleSize = Math.round(((float) height) / ((float) h));
                    } else {
                        inSampleSize = Math.round(((float) width) / ((float) w));
                    }
                }
                FileInputStream fis22 = new FileInputStream(file);
                try {
                    options.inJustDecodeBounds = false;
                    options.inSampleSize = inSampleSize;
                    scaledBitmap = BitmapFactory.decodeStream(fis22, null, options);
                    if (fis3 != null) {
                        try {
                            fis3.close();
                        } catch (IOException e3) {
                            MMLog.e(TAG, "Error closing file", e3);
                            fis2 = fis22;
                            fis = fis3;
                        }
                    }
                    if (fis22 != null) {
                        fis22.close();
                    }
                    fis2 = fis22;
                    fis = fis3;
                } catch (FileNotFoundException e4) {
                    e = e4;
                    fis2 = fis22;
                    fis = fis3;
                    try {
                        MMLog.e(TAG, "Can't find file to scale bitmap", e);
                        if (fis != null) {
                            try {
                                fis.close();
                            } catch (IOException e32) {
                                MMLog.e(TAG, "Error closing file", e32);
                            }
                        }
                        if (fis2 != null) {
                            fis2.close();
                        }
                        contents = null;
                        if (scaledBitmap != null) {
                            finalBitmap = resizeImage(scaledBitmap, contentMode, w, h, 1);
                            byteArrayOutputStream = null;
                            try {
                                baos = new ByteArrayOutputStream();
                                try {
                                    if (contentMode.equals("")) {
                                        scaledBitmap.compress(CompressFormat.JPEG, 100, baos);
                                    } else {
                                        finalBitmap.compress(CompressFormat.JPEG, 100, baos);
                                    }
                                    contents = baos.toByteArray();
                                    if (scaledBitmap != null) {
                                        try {
                                            scaledBitmap.recycle();
                                        } catch (Exception e22) {
                                            MMLog.e(TAG, "Error closing file", e22);
                                        }
                                    }
                                    if (finalBitmap != null) {
                                        finalBitmap.recycle();
                                    }
                                    if (baos != null) {
                                        baos.close();
                                    }
                                } catch (Exception e5) {
                                    e22 = e5;
                                    byteArrayOutputStream = baos;
                                    contents = null;
                                    try {
                                        MMLog.e(TAG, "Error scaling bitmap", e22);
                                        if (scaledBitmap != null) {
                                            try {
                                                scaledBitmap.recycle();
                                            } catch (Exception e222) {
                                                MMLog.e(TAG, "Error closing file", e222);
                                            }
                                        }
                                        if (finalBitmap != null) {
                                            finalBitmap.recycle();
                                        }
                                        if (byteArrayOutputStream != null) {
                                            byteArrayOutputStream.close();
                                        }
                                        return contents;
                                    } catch (Throwable th2) {
                                        th = th2;
                                        if (scaledBitmap != null) {
                                            try {
                                                scaledBitmap.recycle();
                                            } catch (Exception e2222) {
                                                MMLog.e(TAG, "Error closing file", e2222);
                                                throw th;
                                            }
                                        }
                                        if (finalBitmap != null) {
                                            finalBitmap.recycle();
                                        }
                                        if (byteArrayOutputStream != null) {
                                            byteArrayOutputStream.close();
                                        }
                                        throw th;
                                    }
                                } catch (Throwable th3) {
                                    th = th3;
                                    byteArrayOutputStream = baos;
                                    if (scaledBitmap != null) {
                                        scaledBitmap.recycle();
                                    }
                                    if (finalBitmap != null) {
                                        finalBitmap.recycle();
                                    }
                                    if (byteArrayOutputStream != null) {
                                        byteArrayOutputStream.close();
                                    }
                                    throw th;
                                }
                            } catch (Exception e6) {
                                e2222 = e6;
                                contents = null;
                                MMLog.e(TAG, "Error scaling bitmap", e2222);
                                if (scaledBitmap != null) {
                                    scaledBitmap.recycle();
                                }
                                if (finalBitmap != null) {
                                    finalBitmap.recycle();
                                }
                                if (byteArrayOutputStream != null) {
                                    byteArrayOutputStream.close();
                                }
                                return contents;
                            }
                        }
                        return contents;
                    } catch (Throwable th4) {
                        th = th4;
                        if (fis != null) {
                            try {
                                fis.close();
                            } catch (IOException e322) {
                                MMLog.e(TAG, "Error closing file", e322);
                                throw th;
                            }
                        }
                        if (fis2 != null) {
                            fis2.close();
                        }
                        throw th;
                    }
                } catch (Throwable th5) {
                    th = th5;
                    fis2 = fis22;
                    fis = fis3;
                    if (fis != null) {
                        fis.close();
                    }
                    if (fis2 != null) {
                        fis2.close();
                    }
                    throw th;
                }
            } catch (FileNotFoundException e7) {
                e = e7;
                fis = fis3;
                MMLog.e(TAG, "Can't find file to scale bitmap", e);
                if (fis != null) {
                    fis.close();
                }
                if (fis2 != null) {
                    fis2.close();
                }
                contents = null;
                if (scaledBitmap != null) {
                    finalBitmap = resizeImage(scaledBitmap, contentMode, w, h, 1);
                    byteArrayOutputStream = null;
                    baos = new ByteArrayOutputStream();
                    if (contentMode.equals("")) {
                        finalBitmap.compress(CompressFormat.JPEG, 100, baos);
                    } else {
                        scaledBitmap.compress(CompressFormat.JPEG, 100, baos);
                    }
                    contents = baos.toByteArray();
                    if (scaledBitmap != null) {
                        scaledBitmap.recycle();
                    }
                    if (finalBitmap != null) {
                        finalBitmap.recycle();
                    }
                    if (baos != null) {
                        baos.close();
                    }
                }
                return contents;
            } catch (Throwable th6) {
                th = th6;
                fis = fis3;
                if (fis != null) {
                    fis.close();
                }
                if (fis2 != null) {
                    fis2.close();
                }
                throw th;
            }
        } catch (FileNotFoundException e8) {
            e = e8;
            MMLog.e(TAG, "Can't find file to scale bitmap", e);
            if (fis != null) {
                fis.close();
            }
            if (fis2 != null) {
                fis2.close();
            }
            contents = null;
            if (scaledBitmap != null) {
                finalBitmap = resizeImage(scaledBitmap, contentMode, w, h, 1);
                byteArrayOutputStream = null;
                baos = new ByteArrayOutputStream();
                if (contentMode.equals("")) {
                    scaledBitmap.compress(CompressFormat.JPEG, 100, baos);
                } else {
                    finalBitmap.compress(CompressFormat.JPEG, 100, baos);
                }
                contents = baos.toByteArray();
                if (scaledBitmap != null) {
                    scaledBitmap.recycle();
                }
                if (finalBitmap != null) {
                    finalBitmap.recycle();
                }
                if (baos != null) {
                    baos.close();
                }
            }
            return contents;
        }
        contents = null;
        if (scaledBitmap != null) {
            finalBitmap = resizeImage(scaledBitmap, contentMode, w, h, 1);
            byteArrayOutputStream = null;
            baos = new ByteArrayOutputStream();
            if (contentMode.equals("")) {
                scaledBitmap.compress(CompressFormat.JPEG, 100, baos);
            } else {
                finalBitmap.compress(CompressFormat.JPEG, 100, baos);
            }
            contents = baos.toByteArray();
            if (scaledBitmap != null) {
                scaledBitmap.recycle();
            }
            if (finalBitmap != null) {
                finalBitmap.recycle();
            }
            if (baos != null) {
                baos.close();
            }
        }
        return contents;
    }

    public synchronized MMJSResponse getPicture(Map<String, String> arguments) {
        MMJSResponse responseWithError;
        Context context = (Context) this.contextRef.get();
        String type = (String) arguments.get("sourceType");
        String height = (String) arguments.get("constrainHeight");
        String width = (String) arguments.get("constrainWidth");
        String contentMode = (String) arguments.get("contentMode");
        if (contentMode == null) {
            contentMode = "";
        }
        if (height == null || width == null) {
            responseWithError = MMJSResponse.responseWithError("Missing constrainHeight and/or constrainWidth");
        } else {
            int h = (int) Float.parseFloat(height);
            int w = (int) Float.parseFloat(width);
            if (h * w > 360000) {
                responseWithError = MMJSResponse.responseWithError("constrainHeight * constrainWidth > 360000");
            } else {
                if (!(context == null || type == null)) {
                    File file = new File(AdCache.getInternalCacheDirectory(context), "tmp_mm_" + String.valueOf(System.currentTimeMillis()) + Constants.IMAGE_EXTENSION);
                    if ((type.equalsIgnoreCase("Camera") && isCameraAvailable()) || ((type.equalsIgnoreCase("Photo Library") || type.equalsIgnoreCase("PhotoLibrary")) && isPictureChooserAvailable())) {
                        try {
                            pickerActivityObject = new Object();
                            synchronized (pickerActivityObject) {
                                IntentUtils.startPickerActivity(context, file, type);
                                pickerActivityObject.wait();
                            }
                            pickerActivityObject = null;
                        } catch (Exception e) {
                            try {
                                MMLog.e(TAG, "Error with pickerActivity synchronization", e);
                                pickerActivityObject = null;
                            } catch (Throwable th) {
                                pickerActivityObject = null;
                            }
                        }
                        if (file != null && file.exists() && file.length() > 0) {
                            byte[] contents = scaleBitmapToBytes(file, w, h, contentMode);
                            file.delete();
                            if (contents != null) {
                                responseWithError = new MMJSResponse();
                                responseWithError.result = 1;
                                responseWithError.dataResponse = contents;
                            }
                        }
                    }
                }
                responseWithError = null;
            }
        }
        return responseWithError;
    }

    public synchronized MMJSResponse writeToPhotoLibrary(Map<String, String> arguments) {
        MMJSResponse mMJSResponse;
        Context context = (Context) this.contextRef.get();
        String pathString = (String) arguments.get(PATH);
        if (context == null || TextUtils.isEmpty(pathString)) {
            mMJSResponse = null;
        } else {
            Uri path = Uri.parse((String) arguments.get(PATH));
            String subDirectory = "Pictures";
            String name = path.getLastPathSegment();
            String scheme = path.getScheme();
            if (scheme == null || !scheme.equals("http") || AdCache.downloadComponentExternalStorage(path.toString(), "Pictures", name, context)) {
                File source = AdCache.getDownloadFile(context, "Pictures", path.getLastPathSegment());
                if (source.exists()) {
                    scanMedia(source.getAbsolutePath());
                    mMJSResponse = !AdCache.isExternalMounted() ? MMJSResponse.responseWithError("Storage not mounted, cannot add image to photo library photo library") : MMJSResponse.responseWithSuccess("Image saved to photo library");
                } else {
                    mMJSResponse = MMJSResponse.responseWithError("No file at " + source.getAbsolutePath());
                }
            } else {
                mMJSResponse = MMJSResponse.responseWithError("Failed to download");
            }
        }
        return mMJSResponse;
    }

    private void scanMedia(final String path) {
        Context context = (Context) this.contextRef.get();
        if (context != null) {
            this.mediaScanner = new MediaScannerConnection(context.getApplicationContext(), new MediaScannerConnectionClient() {
                public void onScanCompleted(String path, Uri uri) {
                    if (uri == null) {
                        MMLog.d(BridgeMMMedia.TAG, "Failed to scan " + path);
                    }
                }

                public void onMediaScannerConnected() {
                    if (BridgeMMMedia.this.mediaScanner != null) {
                        BridgeMMMedia.this.mediaScanner.scanFile(path, null);
                    }
                }
            });
            if (this.mediaScanner != null) {
                this.mediaScanner.connect();
            }
        }
    }

    public MMJSResponse playVideo(Map<String, String> arguments) {
        Context context = (Context) this.contextRef.get();
        String path = (String) arguments.get(PATH);
        if (!(context == null || path == null)) {
            if (path.startsWith("http")) {
                IntentUtils.startVideoPlayerActivityWithData(context, path);
                return MMJSResponse.responseWithSuccess(path);
            }
            File file = AdCache.getDownloadFile(context, path);
            if (file.exists()) {
                IntentUtils.startVideoPlayerActivityWithData(context, file);
                return MMJSResponse.responseWithSuccess(file.getName());
            }
        }
        return null;
    }

    public MMJSResponse playAudio(Map<String, String> arguments) {
        Context context = (Context) this.contextRef.get();
        String path = (String) arguments.get(PATH);
        if (context == null || path == null) {
            return null;
        }
        Audio audio = Audio.sharedAudio(context);
        if (audio == null) {
            return null;
        }
        if (audio.isPlaying()) {
            return MMJSResponse.responseWithError("Audio already playing.");
        }
        if (path.startsWith("http")) {
            return audio.playAudio(Uri.parse(path), Boolean.parseBoolean((String) arguments.get("repeat")));
        }
        File file = AdCache.getDownloadFile(context, path);
        if (file.exists()) {
            return audio.playAudio(Uri.fromFile(file), Boolean.parseBoolean((String) arguments.get("repeat")));
        }
        return null;
    }

    public MMJSResponse playSound(Map<String, String> arguments) {
        if (this.contextRef == null) {
            return null;
        }
        Context context = (Context) this.contextRef.get();
        String path = (String) arguments.get(PATH);
        if (!(context == null || path == null)) {
            File file = AdCache.getDownloadFile(context, path);
            if (file.exists()) {
                Audio audio = Audio.sharedAudio((Context) this.contextRef.get());
                if (audio != null) {
                    return audio.playSound(file);
                }
            }
        }
        return null;
    }

    public MMJSResponse stopAudio(Map<String, String> map) {
        if (this.contextRef != null) {
            Audio audio = Audio.sharedAudio((Context) this.contextRef.get());
            if (audio != null) {
                return audio.stop();
            }
        }
        return null;
    }

    public MMJSResponse getDeviceVolume(Map<String, String> map) {
        Context context = (Context) this.contextRef.get();
        if (context == null) {
            return MMJSResponse.responseWithError("no volume available");
        }
        int volume = MMSDK.getMediaVolume(context);
        MMJSResponse response = MMJSResponse.responseWithSuccess();
        response.response = Integer.valueOf(volume);
        return response;
    }
}
