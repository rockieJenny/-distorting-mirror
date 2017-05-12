package com.inmobi.commons.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import com.inmobi.commons.internal.InternalSDKUtil;
import com.inmobi.commons.internal.Log;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedHashMap;

public abstract class DatabaseHandler extends SQLiteOpenHelper {
    public static final String DATABASE_NAME = "im.db";
    private static String c = "CREATE TABLE IF NOT EXISTS ";
    private static String d = "DROP TABLE IF EXISTS ";
    private static String e = " PRIMARY KEY ";
    private static String f = " AUTOINCREMENT ";
    private static String g = " NOT NULL ";
    private static String h = "SELECT * FROM ";
    private static String i = " WHERE ";
    private static String j = " ORDER BY ";
    private static String k = "; ";
    private static String l = " Limit ?";
    private ArrayList<TableData> a;
    private SQLiteDatabase b;

    protected DatabaseHandler(Context context, ArrayList<TableData> arrayList) {
        super(context, DATABASE_NAME, null, 1);
        this.a = arrayList;
    }

    public void onCreate(SQLiteDatabase sQLiteDatabase) {
        try {
            a(sQLiteDatabase);
        } catch (Throwable e) {
            Log.internal(InternalSDKUtil.LOGGING_TAG, "Exception Creating table", e);
        }
    }

    public void onUpgrade(SQLiteDatabase sQLiteDatabase, int i, int i2) {
        try {
            Iterator it = this.a.iterator();
            while (it.hasNext()) {
                sQLiteDatabase.execSQL(d + ((TableData) it.next()).getmTableName());
            }
            onCreate(sQLiteDatabase);
        } catch (Throwable e) {
            Log.internal(InternalSDKUtil.LOGGING_TAG, "Exception Deleting table", e);
        }
    }

    public void open() {
        try {
            this.b = getWritableDatabase();
        } catch (Throwable e) {
            Log.internal(InternalSDKUtil.LOGGING_TAG, "Failed to open  db", e);
        }
    }

    public void close() {
        try {
            this.b.close();
        } catch (Throwable e) {
            Log.internal(InternalSDKUtil.LOGGING_TAG, "Failed to close  db", e);
        }
    }

    public synchronized long insert(String str, ContentValues contentValues) {
        long insert;
        try {
            insert = this.b.insert(str, null, contentValues);
        } catch (Throwable e) {
            Log.internal(InternalSDKUtil.LOGGING_TAG, "Failed to insert to db", e);
            insert = -1;
        }
        return insert;
    }

    public synchronized int delete(String str, String str2, String[] strArr) {
        int delete;
        try {
            delete = this.b.delete(str, str2, strArr);
        } catch (Throwable e) {
            Log.internal(InternalSDKUtil.LOGGING_TAG, "Failed to insert to db", e);
            delete = -1;
        }
        return delete;
    }

    public synchronized long update(String str, ContentValues contentValues, String str2, String[] strArr) {
        long update;
        try {
            update = (long) this.b.update(str, contentValues, str2, strArr);
        } catch (Throwable e) {
            Log.internal(InternalSDKUtil.LOGGING_TAG, "Failed to insert to db", e);
            update = -1;
        }
        return update;
    }

    public synchronized Cursor getRow(String str, String str2, String[] strArr) {
        Cursor cursor = null;
        synchronized (this) {
            Cursor rawQuery;
            if (str2 != null) {
                try {
                    if (!"".equals(str2.trim())) {
                        rawQuery = this.b.rawQuery(h + str + i + str2 + k, strArr);
                        rawQuery.moveToFirst();
                        cursor = rawQuery;
                    }
                } catch (Throwable e) {
                    Log.internal(InternalSDKUtil.LOGGING_TAG, "Failed to all rows", e);
                }
            }
            rawQuery = this.b.rawQuery(h + str + k, null);
            rawQuery.moveToFirst();
            cursor = rawQuery;
        }
        return cursor;
    }

    public synchronized Cursor getAll(String str, String str2) {
        Cursor cursor = null;
        synchronized (this) {
            Cursor rawQuery;
            if (str2 != null) {
                try {
                    if (!"".equals(str2.trim())) {
                        rawQuery = this.b.rawQuery(h + str + j + str2 + k, null);
                        rawQuery.moveToFirst();
                        cursor = rawQuery;
                    }
                } catch (Throwable e) {
                    Log.internal(InternalSDKUtil.LOGGING_TAG, "Failed to all rows", e);
                }
            }
            rawQuery = this.b.rawQuery(h + str + k, null);
            rawQuery.moveToFirst();
            cursor = rawQuery;
        }
        return cursor;
    }

    public synchronized Cursor getNRows(String str, String str2, int i) {
        Cursor cursor = null;
        synchronized (this) {
            Cursor rawQuery;
            if (str2 != null) {
                try {
                    if (!"".equals(str2.trim())) {
                        rawQuery = this.b.rawQuery(h + str + j + str2 + l + k, new String[]{String.valueOf(i)});
                        rawQuery.moveToFirst();
                        cursor = rawQuery;
                    }
                } catch (Throwable e) {
                    Log.internal(InternalSDKUtil.LOGGING_TAG, "Failed to all rows", e);
                }
            }
            rawQuery = this.b.rawQuery(h + str + k, null);
            rawQuery.moveToFirst();
            cursor = rawQuery;
        }
        return cursor;
    }

    public synchronized int getNoOfRows(String str, String str2, String[] strArr) {
        int count;
        Cursor rawQuery;
        if (str2 != null) {
            try {
                if (!"".equals(str2.trim())) {
                    rawQuery = this.b.rawQuery(h + str + i + str2, strArr);
                    count = rawQuery.getCount();
                    rawQuery.close();
                }
            } catch (Throwable e) {
                Log.internal(InternalSDKUtil.LOGGING_TAG, "Failed to get number of rows", e);
                count = 0;
            }
        }
        rawQuery = this.b.rawQuery(h + str + k, null);
        count = rawQuery.getCount();
        rawQuery.close();
        return count;
    }

    protected synchronized int getTableSize(String str) {
        int count;
        try {
            Cursor rawQuery = this.b.rawQuery(h + str + k, null);
            count = rawQuery.getCount();
            rawQuery.close();
        } catch (Throwable e) {
            Log.internal(InternalSDKUtil.LOGGING_TAG, "Failed to table size ", e);
            count = 0;
        }
        return count;
    }

    public synchronized Cursor executeQuery(String str, String[] strArr) {
        Cursor rawQuery;
        try {
            rawQuery = this.b.rawQuery(str, strArr);
        } catch (Throwable e) {
            Log.internal(InternalSDKUtil.LOGGING_TAG, "Failed to execute db query", e);
            rawQuery = null;
        }
        return rawQuery;
    }

    private void a(SQLiteDatabase sQLiteDatabase) {
        try {
            if (this.a != null && !this.a.isEmpty()) {
                Iterator it = this.a.iterator();
                while (it.hasNext()) {
                    String substring;
                    TableData tableData = (TableData) it.next();
                    String str = tableData.getmTableName();
                    LinkedHashMap linkedHashMap = tableData.getmColumns();
                    StringBuilder stringBuilder = new StringBuilder(c + str + " (");
                    for (String substring2 : linkedHashMap.keySet()) {
                        ColumnData columnData = (ColumnData) linkedHashMap.get(substring2);
                        stringBuilder.append(" " + substring2 + " " + columnData.getDataType().toString());
                        if (columnData.isPrimaryKey()) {
                            stringBuilder.append(e);
                        }
                        if (columnData.isAutoIncrement()) {
                            stringBuilder.append(f);
                        }
                        if (columnData.isMandatory()) {
                            stringBuilder.append(g);
                        }
                        stringBuilder.append(",");
                    }
                    if (',' == stringBuilder.charAt(stringBuilder.length() - 1)) {
                        substring2 = stringBuilder.substring(0, stringBuilder.length() - 2);
                    } else {
                        substring2 = stringBuilder.toString();
                    }
                    substring2 = substring2.concat(" );");
                    Log.internal(InternalSDKUtil.LOGGING_TAG, "Table: " + substring2);
                    sQLiteDatabase.execSQL(substring2);
                }
            }
        } catch (Throwable e) {
            Log.internal(InternalSDKUtil.LOGGING_TAG, "Exception creating table", e);
        }
    }
}
