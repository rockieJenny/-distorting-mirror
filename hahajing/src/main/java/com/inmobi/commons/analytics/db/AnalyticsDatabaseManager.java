package com.inmobi.commons.analytics.db;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import com.inmobi.commons.analytics.db.AnalyticsEvent.TRANSACTION_ITEM_TYPE;
import com.inmobi.commons.analytics.db.AnalyticsEvent.TRANSACTION_STATUS_SERVER_CODE;
import com.inmobi.commons.analytics.net.AnalyticsNetworkManager;
import com.inmobi.commons.analytics.util.AnalyticsUtils;
import com.inmobi.commons.analytics.util.SessionInfo;
import com.inmobi.commons.internal.InternalSDKUtil;
import com.inmobi.commons.internal.Log;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

public final class AnalyticsDatabaseManager {
    private static AnalyticsDatabaseManager c;
    private static final String[] d = new String[]{AnalyticsSQLiteHelper.GENERAL_ID, AnalyticsSQLiteHelper.EVENT_LIST_EVENT_ID, AnalyticsSQLiteHelper.EVENT_LIST_TYPE, AnalyticsSQLiteHelper.EVENT_LIST_SID, "ts", AnalyticsSQLiteHelper.EVENT_LIST_SSTS, AnalyticsSQLiteHelper.EVENT_LIST_AM};
    private static final String[] e = new String[]{AnalyticsSQLiteHelper.GENERAL_ID, "levelid", "levelname"};
    private static final String[] f = new String[]{AnalyticsSQLiteHelper.GENERAL_ID, "levelid", "levelname", AnalyticsSQLiteHelper.LEVEL_END_LEVEL_STATUS, AnalyticsSQLiteHelper.LEVEL_END_TIME_TAKEN, AnalyticsSQLiteHelper.LEVEL_END_ATTEMPT_COUNT, AnalyticsSQLiteHelper.LEVEL_END_ATTEMPT_TIME};
    private static final String[] g = new String[]{AnalyticsSQLiteHelper.GENERAL_ID, AnalyticsSQLiteHelper.CUSTOM_EVENT_EVENT_NAME};
    private static final String[] h = new String[]{AnalyticsSQLiteHelper.GENERAL_ID, AnalyticsSQLiteHelper.USER_ATTRIBUTE_NAME, AnalyticsSQLiteHelper.USER_ATTRIBUTE_VALUE};
    private static final String[] i = new String[]{AnalyticsSQLiteHelper.GENERAL_ID, "levelid", AnalyticsSQLiteHelper.ATTEMPT_DATA_BEGIN_TIME, AnalyticsSQLiteHelper.ATTEMPT_DATA_TOTAL_COUNT, AnalyticsSQLiteHelper.ATTEMPT_DATA_TOTAL_TIME};
    private static final String[] j = new String[]{AnalyticsSQLiteHelper.GENERAL_ID, AnalyticsSQLiteHelper.TRANSACTION_ITEM_NAME, AnalyticsSQLiteHelper.TRANSACTION_ITEM_TYPE, AnalyticsSQLiteHelper.TRANSACTION_ITEM_COUNT, AnalyticsSQLiteHelper.TRANSACTION_ITEM_DESCRIPTION, AnalyticsSQLiteHelper.TRANSACTION_ITEM_ITEM_PRICE, AnalyticsSQLiteHelper.TRANSACTION_ITEM_CURRENCY_CODE, AnalyticsSQLiteHelper.TRANSACTION_PRODUCT_ID, AnalyticsSQLiteHelper.TRANSACTION_ID, AnalyticsSQLiteHelper.TRANSACTION_STATUS};
    private AnalyticsSQLiteHelper a;
    private SQLiteDatabase b;

    private AnalyticsDatabaseManager() {
    }

    public static synchronized AnalyticsDatabaseManager getInstance() {
        AnalyticsDatabaseManager analyticsDatabaseManager;
        synchronized (AnalyticsDatabaseManager.class) {
            if (c == null) {
                c = new AnalyticsDatabaseManager();
                File databasePath = InternalSDKUtil.getContext().getDatabasePath(AnalyticsSQLiteHelper.OLD_DATABASE_NAME);
                if (databasePath.isFile()) {
                    databasePath.renameTo(InternalSDKUtil.getContext().getDatabasePath(AnalyticsSQLiteHelper.DATABASE_NAME));
                    SessionInfo.updatedFromOldSDK(InternalSDKUtil.getContext());
                }
                c.a = new AnalyticsSQLiteHelper(InternalSDKUtil.getContext());
            }
            analyticsDatabaseManager = c;
        }
        return analyticsDatabaseManager;
    }

    private void a() {
        this.b = this.a.getWritableDatabase();
    }

    private void b() {
        this.a.close();
    }

    public synchronized void insertEvents(AnalyticsEvent analyticsEvent) {
        try {
            a();
            a(analyticsEvent, this.b.compileStatement("SELECT COUNT(*) FROM eventlist").simpleQueryForLong());
            b();
        } catch (Throwable e) {
            Log.internal(AnalyticsUtils.ANALYTICS_LOGGING_TAG, "Error in inserting into DB.", e);
            e.printStackTrace();
        }
    }

    private void a(AnalyticsEvent analyticsEvent, long j) {
        Log.debug(AnalyticsUtils.ANALYTICS_LOGGING_TAG, "IMAppDatabaseManager->insertEvents-" + analyticsEvent.getEventType());
        if (j < AnalyticsUtils.getMaxdbcount()) {
            ContentValues contentValues = new ContentValues();
            long j2 = -1;
            long eventTimeStamp = analyticsEvent.getEventTimeStamp();
            if (analyticsEvent.getEventType().equals(AnalyticsEvent.TYPE_LEVEL_BEGIN)) {
                ContentValues contentValues2 = new ContentValues();
                contentValues2.put(AnalyticsSQLiteHelper.ATTEMPT_DATA_BEGIN_TIME, Long.valueOf(eventTimeStamp));
                if (this.b.update(AnalyticsSQLiteHelper.TABLE_ATTEMPT_DATA, contentValues2, "levelid = ?", new String[]{analyticsEvent.getEventLevelId()}) <= 0) {
                    contentValues2.put("levelid", analyticsEvent.getEventLevelId());
                    contentValues2.put(AnalyticsSQLiteHelper.ATTEMPT_DATA_TOTAL_COUNT, Integer.toString(0));
                    contentValues2.put(AnalyticsSQLiteHelper.ATTEMPT_DATA_TOTAL_TIME, Integer.toString(0));
                    this.b.insert(AnalyticsSQLiteHelper.TABLE_ATTEMPT_DATA, null, contentValues2);
                }
                contentValues.put("levelid", analyticsEvent.getEventLevelId());
                contentValues.put("levelname", analyticsEvent.getEventLevelName());
                j2 = this.b.insert(AnalyticsSQLiteHelper.TABLE_LEVEL_BEGIN, null, contentValues);
            } else if (analyticsEvent.getEventType().equals(AnalyticsEvent.TYPE_LEVEL_END)) {
                String l;
                String l2;
                String num;
                String str = "0";
                String str2 = "0";
                String str3 = "0";
                Cursor query = this.b.query(AnalyticsSQLiteHelper.TABLE_ATTEMPT_DATA, i, "levelid = ?", new String[]{analyticsEvent.getEventLevelId()}, null, null, null, "1");
                if (query.getCount() > 0) {
                    query.moveToFirst();
                    j2 = eventTimeStamp - Long.parseLong(query.getString(2));
                    l = Long.toString(j2);
                    l2 = Long.toString(j2 + Long.parseLong(query.getString(4)));
                    num = Integer.toString(Integer.parseInt(query.getString(3)) + 1);
                    ContentValues contentValues3 = new ContentValues();
                    contentValues3.put(AnalyticsSQLiteHelper.ATTEMPT_DATA_TOTAL_COUNT, num);
                    contentValues3.put(AnalyticsSQLiteHelper.ATTEMPT_DATA_TOTAL_TIME, l2);
                    this.b.update(AnalyticsSQLiteHelper.TABLE_ATTEMPT_DATA, contentValues3, "levelid = ?", new String[]{analyticsEvent.getEventLevelId()});
                } else {
                    l2 = str3;
                    num = str2;
                    l = str;
                }
                query.close();
                contentValues.put("levelid", analyticsEvent.getEventLevelId());
                contentValues.put(AnalyticsSQLiteHelper.LEVEL_END_LEVEL_STATUS, analyticsEvent.getEventLevelStatus());
                contentValues.put("levelname", analyticsEvent.getEventLevelName());
                contentValues.put(AnalyticsSQLiteHelper.LEVEL_END_TIME_TAKEN, l);
                contentValues.put(AnalyticsSQLiteHelper.LEVEL_END_ATTEMPT_COUNT, num);
                contentValues.put(AnalyticsSQLiteHelper.LEVEL_END_ATTEMPT_TIME, l2);
                j2 = this.b.insert(AnalyticsSQLiteHelper.TABLE_LEVEL_END, null, contentValues);
            } else if (analyticsEvent.getEventType().equals(AnalyticsEvent.TYPE_TAG_TRANSACTION)) {
                contentValues.put(AnalyticsSQLiteHelper.TRANSACTION_ITEM_NAME, analyticsEvent.getTransactionItemName());
                int transactionItemType = analyticsEvent.getTransactionItemType();
                if (TRANSACTION_ITEM_TYPE.INVALID.getValue() != transactionItemType) {
                    contentValues.put(AnalyticsSQLiteHelper.TRANSACTION_ITEM_TYPE, Integer.valueOf(transactionItemType));
                }
                transactionItemType = analyticsEvent.getTransactionItemCount();
                if (transactionItemType > 0) {
                    contentValues.put(AnalyticsSQLiteHelper.TRANSACTION_ITEM_COUNT, Integer.valueOf(transactionItemType));
                }
                contentValues.put(AnalyticsSQLiteHelper.TRANSACTION_ITEM_DESCRIPTION, analyticsEvent.getTransactionItemDescription());
                contentValues.put(AnalyticsSQLiteHelper.TRANSACTION_ITEM_ITEM_PRICE, Double.valueOf(analyticsEvent.getTransactionItemPrice()));
                contentValues.put(AnalyticsSQLiteHelper.TRANSACTION_ITEM_CURRENCY_CODE, analyticsEvent.getTransactionCurrencyCode());
                contentValues.put(AnalyticsSQLiteHelper.TRANSACTION_PRODUCT_ID, analyticsEvent.getTransactionProductId());
                contentValues.put(AnalyticsSQLiteHelper.TRANSACTION_ID, analyticsEvent.getTransactionId());
                transactionItemType = analyticsEvent.getTransactionStatus();
                if (TRANSACTION_STATUS_SERVER_CODE.INVALID.getValue() != transactionItemType) {
                    contentValues.put(AnalyticsSQLiteHelper.TRANSACTION_STATUS, Integer.valueOf(transactionItemType));
                }
                j2 = this.b.insert(AnalyticsSQLiteHelper.TABLE_TRANSACTION, null, contentValues);
            } else if (analyticsEvent.getEventType().equals(AnalyticsEvent.TYPE_CUSTOM_EVENT)) {
                contentValues.put(AnalyticsSQLiteHelper.CUSTOM_EVENT_EVENT_NAME, analyticsEvent.getEventCustomName());
                j2 = this.b.insert(AnalyticsSQLiteHelper.TABLE_CUSTOM_EVENT, null, contentValues);
            } else if (analyticsEvent.getEventType().equals(AnalyticsEvent.TYPE_USER_ATTRIBUTE)) {
                contentValues.put(AnalyticsSQLiteHelper.USER_ATTRIBUTE_NAME, analyticsEvent.getAttributeName());
                contentValues.put(AnalyticsSQLiteHelper.USER_ATTRIBUTE_VALUE, analyticsEvent.getAttributeValue());
                j2 = this.b.insert(AnalyticsSQLiteHelper.TABLE_USER_ATTRIBUTE, null, contentValues);
            }
            ContentValues contentValues4 = new ContentValues();
            contentValues4.put(AnalyticsSQLiteHelper.EVENT_LIST_EVENT_ID, Long.valueOf(j2));
            contentValues4.put(AnalyticsSQLiteHelper.EVENT_LIST_TYPE, analyticsEvent.getEventType());
            contentValues4.put(AnalyticsSQLiteHelper.EVENT_LIST_SID, analyticsEvent.getEventSessionId());
            contentValues4.put("ts", Long.valueOf(analyticsEvent.getEventTimeStamp()));
            contentValues4.put(AnalyticsSQLiteHelper.EVENT_LIST_SSTS, Long.valueOf(analyticsEvent.getEventSessionTimeStamp()));
            contentValues4.put(AnalyticsSQLiteHelper.EVENT_LIST_AM, analyticsEvent.getEventAttributeMap());
            this.b.insert(AnalyticsSQLiteHelper.TABLE_EVENT_LIST, null, contentValues4);
        } else {
            Log.debug(AnalyticsUtils.ANALYTICS_LOGGING_TAG, "Database full");
        }
        if (!AnalyticsUtils.getStartHandle() && AnalyticsNetworkManager.getHandler() != null) {
            AnalyticsUtils.setStartHandle(true);
            AnalyticsNetworkManager.getHandler().sendEmptyMessageDelayed(1001, AnalyticsUtils.getTimeinterval());
        }
    }

    public synchronized List<AnalyticsEvent> getEvents() {
        List<AnalyticsEvent> c;
        try {
            a();
            c = c();
            b();
        } catch (Exception e) {
            Log.internal(AnalyticsUtils.ANALYTICS_LOGGING_TAG, "Error reading events from DB.");
            c = new ArrayList();
        }
        return c;
    }

    private List<AnalyticsEvent> c() {
        Log.debug(AnalyticsUtils.ANALYTICS_LOGGING_TAG, "IMAppDatabaseManager->getEvents");
        List<AnalyticsEvent> arrayList = new ArrayList();
        if (this.b.isOpen()) {
            Cursor query = this.b.query(AnalyticsSQLiteHelper.TABLE_EVENT_LIST, d, null, null, null, null, null, AnalyticsUtils.getMaxevents());
            query.moveToFirst();
            while (!query.isAfterLast()) {
                AnalyticsEvent a = a(query);
                a.setEventTableId(query.getLong(0));
                arrayList.add(a);
                query.moveToNext();
            }
            query.close();
            return arrayList;
        }
        android.util.Log.v(AnalyticsUtils.ANALYTICS_LOGGING_TAG, "IMAppDatabaseManager->getEvents()-database is not open");
        return arrayList;
    }

    private AnalyticsEvent a(Cursor cursor) {
        Cursor cursor2 = null;
        AnalyticsEvent analyticsEvent = new AnalyticsEvent(cursor.getString(2));
        analyticsEvent.setEventSessionId(cursor.getString(3));
        analyticsEvent.setEventId(cursor.getLong(0));
        analyticsEvent.setEventTimeStamp(cursor.getLong(4));
        analyticsEvent.setEventSessionTimeStamp(cursor.getLong(5));
        analyticsEvent.setEventAttributeMap(cursor.getString(6));
        long j = cursor.getLong(1);
        String l = Long.toString(j);
        Log.debug(AnalyticsUtils.ANALYTICS_LOGGING_TAG, "IMAppDatabaseManager->" + analyticsEvent.getEventType() + "-" + j);
        if (j > -1) {
            if (analyticsEvent.getEventType().equals(AnalyticsEvent.TYPE_LEVEL_BEGIN)) {
                cursor2 = this.b.query(AnalyticsSQLiteHelper.TABLE_LEVEL_BEGIN, e, "_id = ?", new String[]{l}, null, null, null);
                cursor2.moveToFirst();
                analyticsEvent.setEventLevelId(cursor2.getString(1));
                analyticsEvent.setEventLevelName(cursor2.getString(2));
            } else if (analyticsEvent.getEventType().equals(AnalyticsEvent.TYPE_LEVEL_END)) {
                cursor2 = this.b.query(AnalyticsSQLiteHelper.TABLE_LEVEL_END, f, "_id = ?", new String[]{l}, null, null, null);
                cursor2.moveToFirst();
                analyticsEvent.setEventLevelId(cursor2.getString(1));
                analyticsEvent.setEventLevelName(cursor2.getString(2));
                analyticsEvent.setEventLevelStatus(cursor2.getString(3));
                analyticsEvent.setEventTimeTaken(cursor2.getString(4));
                analyticsEvent.setEventAttemptCount(cursor2.getString(5));
                analyticsEvent.setEventAttemptTime(cursor2.getString(6));
            } else if (analyticsEvent.getEventType().equals(AnalyticsEvent.TYPE_CUSTOM_EVENT)) {
                cursor2 = this.b.query(AnalyticsSQLiteHelper.TABLE_CUSTOM_EVENT, g, "_id = ?", new String[]{l}, null, null, null);
                cursor2.moveToFirst();
                analyticsEvent.setEventCustomName(cursor2.getString(1));
            } else if (analyticsEvent.getEventType().equals(AnalyticsEvent.TYPE_USER_ATTRIBUTE)) {
                cursor2 = this.b.query(AnalyticsSQLiteHelper.TABLE_USER_ATTRIBUTE, h, "_id = ?", new String[]{l}, null, null, null);
                cursor2.moveToFirst();
                analyticsEvent.setUserAttribute(cursor2.getString(1), cursor2.getString(2));
            } else if (analyticsEvent.getEventType().equals(AnalyticsEvent.TYPE_TAG_TRANSACTION)) {
                cursor2 = this.b.query(AnalyticsSQLiteHelper.TABLE_TRANSACTION, j, "_id = ?", new String[]{l}, null, null, null);
                cursor2.moveToFirst();
                analyticsEvent.setTransactionItemName(cursor2.getString(1));
                analyticsEvent.setTransactionItemType(cursor2.getInt(2));
                analyticsEvent.setTransactionItemCount(cursor2.getInt(3));
                analyticsEvent.setTransactionItemDescription(cursor2.getString(4));
                analyticsEvent.setTransactionItemPrice(cursor2.getDouble(5));
                analyticsEvent.setTransactionCurrencyCode(cursor2.getString(6));
                analyticsEvent.setTransactionProductId(cursor2.getString(7));
                analyticsEvent.setTransactionId(cursor2.getString(8));
                analyticsEvent.setTransactionStatus(cursor2.getInt(9));
            }
            if (cursor2 != null) {
                cursor2.close();
            }
        }
        return analyticsEvent;
    }

    public synchronized void deleteEvents(List<Long> list) {
        try {
            a();
            a((List) list);
            b();
        } catch (Exception e) {
            Log.internal(AnalyticsUtils.ANALYTICS_LOGGING_TAG, "Error deleting from DB.");
        }
    }

    private void a(List<Long> list) {
        Log.debug(AnalyticsUtils.ANALYTICS_LOGGING_TAG, "IMAppDatabaseManager->deleteEvents");
        for (Long l : list) {
            String l2 = l.toString();
            Cursor query = this.b.query(AnalyticsSQLiteHelper.TABLE_EVENT_LIST, d, "_id=?", new String[]{l2}, null, null, null, "1");
            query.moveToFirst();
            Long valueOf = Long.valueOf(query.getLong(1));
            String l3 = valueOf.toString();
            String string = query.getString(2);
            if (valueOf.longValue() > -1) {
                if (string.equals(AnalyticsEvent.TYPE_LEVEL_BEGIN)) {
                    this.b.delete(AnalyticsSQLiteHelper.TABLE_LEVEL_BEGIN, "_id = ?", new String[]{l3});
                } else if (string.equals(AnalyticsEvent.TYPE_LEVEL_END)) {
                    this.b.delete(AnalyticsSQLiteHelper.TABLE_LEVEL_END, "_id = ?", new String[]{l3});
                } else if (string.equals(AnalyticsEvent.TYPE_CUSTOM_EVENT)) {
                    this.b.delete(AnalyticsSQLiteHelper.TABLE_CUSTOM_EVENT, "_id = ?", new String[]{l3});
                } else if (string.equals(AnalyticsEvent.TYPE_USER_ATTRIBUTE)) {
                    this.b.delete(AnalyticsSQLiteHelper.TABLE_USER_ATTRIBUTE, "_id = ?", new String[]{l3});
                } else if (string.equals(AnalyticsEvent.TYPE_TAG_TRANSACTION)) {
                    this.b.delete(AnalyticsSQLiteHelper.TABLE_TRANSACTION, "_id = ?", new String[]{l3});
                }
            }
            query.close();
            this.b.delete(AnalyticsSQLiteHelper.TABLE_EVENT_LIST, "_id = ?", new String[]{l2});
        }
    }
}
