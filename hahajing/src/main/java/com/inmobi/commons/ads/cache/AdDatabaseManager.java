package com.inmobi.commons.ads.cache;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import com.inmobi.commons.internal.InternalSDKUtil;
import com.inmobi.commons.internal.Log;

public class AdDatabaseManager {
    private static AdDatabaseManager c;
    private AdDatabaseHelper a;
    private SQLiteDatabase b;
    private int d = 1000;

    protected AdDatabaseManager() {
    }

    public static synchronized AdDatabaseManager getInstance() {
        AdDatabaseManager adDatabaseManager;
        synchronized (AdDatabaseManager.class) {
            if (c == null) {
                c = new AdDatabaseManager();
                c.a = new AdDatabaseHelper(InternalSDKUtil.getContext());
            }
            adDatabaseManager = c;
        }
        return adDatabaseManager;
    }

    protected void open() {
        try {
            this.b = this.a.getWritableDatabase();
        } catch (Throwable e) {
            Log.internal(InternalSDKUtil.LOGGING_TAG, "Failed to open ads db", e);
        }
    }

    protected void close() {
        try {
            this.b.close();
        } catch (Throwable e) {
            Log.internal(InternalSDKUtil.LOGGING_TAG, "Failed to close ads db", e);
        }
    }

    public synchronized void insertAd(AdData adData) {
        try {
            ContentValues contentValues = new ContentValues();
            contentValues.put("timestamp", Long.valueOf(adData.getTimestamp()));
            contentValues.put(AdDatabaseHelper.COLUMN_APPID, adData.getAppId());
            contentValues.put(AdDatabaseHelper.COLUMN_AD_CONTENT, adData.getContent());
            contentValues.put(AdDatabaseHelper.COLUMN_ADTYPE, adData.getAdType());
            if (getDBSize() >= this.d) {
                open();
                Cursor rawQuery = this.b.rawQuery("SELECT adid FROM ad WHERE timestamp= (SELECT MIN(timestamp) FROM ad Limit 1);", null);
                rawQuery.moveToFirst();
                long j = rawQuery.getLong(0);
                rawQuery.close();
                this.b.delete(AdDatabaseHelper.TABLE_AD, "adid = " + j, null);
                close();
            }
            open();
            this.b.insert(AdDatabaseHelper.TABLE_AD, null, contentValues);
            close();
        } catch (Throwable e) {
            Log.internal(InternalSDKUtil.LOGGING_TAG, "Failed to insert native ads to db", e);
        }
    }

    public synchronized AdData getAd(String str) {
        AdData adData;
        try {
            open();
            Cursor rawQuery = this.b.rawQuery("SELECT * FROM ad WHERE appid = ? Order by timestamp Limit 1;", new String[]{str});
            rawQuery.moveToFirst();
            adData = new AdData();
            adData.setAdId(rawQuery.getLong(0));
            adData.setTimestamp(rawQuery.getLong(1));
            adData.setAppId(rawQuery.getString(2));
            adData.setContent(rawQuery.getString(3));
            rawQuery.close();
            this.b.delete(AdDatabaseHelper.TABLE_AD, "adid = " + adData.getAdId(), null);
            close();
        } catch (Throwable e) {
            Log.internal(InternalSDKUtil.LOGGING_TAG, "Failed to get native ads from db", e);
            adData = null;
        }
        return adData;
    }

    public synchronized int getNoOfAds(String str) {
        int count;
        try {
            open();
            count = this.b.rawQuery("SELECT * FROM ad WHERE appid = ?; ", new String[]{str}).getCount();
            close();
        } catch (Throwable e) {
            Log.internal(InternalSDKUtil.LOGGING_TAG, "Failed to get native ads from db", e);
            count = 0;
        }
        return count;
    }

    public void setDBLimit(int i) {
        if (i > 0) {
            this.d = i;
        }
    }

    protected int getDBSize() {
        try {
            open();
            int count = this.b.rawQuery("SELECT * FROM ad; ", null).getCount();
            close();
            return count;
        } catch (Throwable e) {
            Log.internal(InternalSDKUtil.LOGGING_TAG, "Failed to get native ads from db", e);
            return 0;
        }
    }

    public void removeExpiredAds(long j, String str) {
        try {
            open();
            long currentTimeMillis = System.currentTimeMillis() - (1000 * j);
            int delete = this.b.delete(AdDatabaseHelper.TABLE_AD, "timestamp<? AND adtype=?", new String[]{Long.toString(currentTimeMillis), str});
            close();
            Log.internal(InternalSDKUtil.LOGGING_TAG, "Deleted " + delete + " expired ads from cache.");
        } catch (Throwable e) {
            Log.internal(InternalSDKUtil.LOGGING_TAG, "Failed to remove expired ads from cache", e);
        }
    }
}
