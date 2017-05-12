package com.google.android.gms.analytics;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Build.VERSION;
import android.text.TextUtils;
import com.google.analytics.tracking.android.Fields;
import com.google.android.gms.analytics.internal.Command;
import com.google.android.gms.internal.ha;
import com.google.android.gms.internal.ld;
import com.google.android.gms.internal.lf;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import org.apache.http.impl.client.DefaultHttpClient;
import org.xmlrpc.android.IXMLRPCSerializer;

class ag implements d {
    private static final String BS = String.format("CREATE TABLE IF NOT EXISTS %s ( '%s' INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, '%s' INTEGER NOT NULL, '%s' TEXT NOT NULL, '%s' TEXT NOT NULL, '%s' INTEGER);", new Object[]{"hits2", "hit_id", "hit_time", "hit_url", "hit_string", "hit_app_id"});
    private final a BT;
    private volatile r BU;
    private final String BV;
    private af BW;
    private long BX;
    private final int BY;
    private final Context mContext;
    private ld wb;
    private o ys;
    private volatile boolean yt;
    private final e zc;

    class a extends SQLiteOpenHelper {
        private boolean BZ;
        private long Ca = 0;
        final /* synthetic */ ag Cb;

        a(ag agVar, Context context, String str) {
            this.Cb = agVar;
            super(context, str, null, 1);
        }

        private void a(SQLiteDatabase sQLiteDatabase) {
            Object obj = null;
            Cursor rawQuery = sQLiteDatabase.rawQuery("SELECT * FROM hits2 WHERE 0", null);
            Set hashSet = new HashSet();
            try {
                String[] columnNames = rawQuery.getColumnNames();
                for (Object add : columnNames) {
                    hashSet.add(add);
                }
                if (hashSet.remove("hit_id") && hashSet.remove("hit_url") && hashSet.remove("hit_string") && hashSet.remove("hit_time")) {
                    if (!hashSet.remove("hit_app_id")) {
                        obj = 1;
                    }
                    if (!hashSet.isEmpty()) {
                        throw new SQLiteException("Database has extra columns");
                    } else if (obj != null) {
                        sQLiteDatabase.execSQL("ALTER TABLE hits2 ADD COLUMN hit_app_id");
                        return;
                    } else {
                        return;
                    }
                }
                throw new SQLiteException("Database column missing");
            } finally {
                rawQuery.close();
            }
        }

        private boolean a(String str, SQLiteDatabase sQLiteDatabase) {
            Cursor cursor;
            Throwable th;
            Cursor cursor2 = null;
            try {
                SQLiteDatabase sQLiteDatabase2 = sQLiteDatabase;
                Cursor query = sQLiteDatabase2.query("SQLITE_MASTER", new String[]{IXMLRPCSerializer.TAG_NAME}, "name=?", new String[]{str}, null, null, null);
                try {
                    boolean moveToFirst = query.moveToFirst();
                    if (query == null) {
                        return moveToFirst;
                    }
                    query.close();
                    return moveToFirst;
                } catch (SQLiteException e) {
                    cursor = query;
                    try {
                        ae.W("Error querying for table " + str);
                        if (cursor != null) {
                            cursor.close();
                        }
                        return false;
                    } catch (Throwable th2) {
                        cursor2 = cursor;
                        th = th2;
                        if (cursor2 != null) {
                            cursor2.close();
                        }
                        throw th;
                    }
                } catch (Throwable th3) {
                    th = th3;
                    cursor2 = query;
                    if (cursor2 != null) {
                        cursor2.close();
                    }
                    throw th;
                }
            } catch (SQLiteException e2) {
                cursor = null;
                ae.W("Error querying for table " + str);
                if (cursor != null) {
                    cursor.close();
                }
                return false;
            } catch (Throwable th4) {
                th = th4;
                if (cursor2 != null) {
                    cursor2.close();
                }
                throw th;
            }
        }

        public SQLiteDatabase getWritableDatabase() {
            if (!this.BZ || this.Ca + 3600000 <= this.Cb.wb.currentTimeMillis()) {
                SQLiteDatabase sQLiteDatabase = null;
                this.BZ = true;
                this.Ca = this.Cb.wb.currentTimeMillis();
                try {
                    sQLiteDatabase = super.getWritableDatabase();
                } catch (SQLiteException e) {
                    this.Cb.mContext.getDatabasePath(this.Cb.BV).delete();
                }
                if (sQLiteDatabase == null) {
                    sQLiteDatabase = super.getWritableDatabase();
                }
                this.BZ = false;
                return sQLiteDatabase;
            }
            throw new SQLiteException("Database creation failed");
        }

        public void onCreate(SQLiteDatabase db) {
            t.ag(db.getPath());
        }

        public void onOpen(SQLiteDatabase db) {
            if (VERSION.SDK_INT < 15) {
                Cursor rawQuery = db.rawQuery("PRAGMA journal_mode=memory", null);
                try {
                    rawQuery.moveToFirst();
                } finally {
                    rawQuery.close();
                }
            }
            if (a("hits2", db)) {
                a(db);
            } else {
                db.execSQL(ag.BS);
            }
        }

        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        }
    }

    ag(e eVar, Context context, o oVar) {
        this(eVar, context, "google_analytics_v4.db", 2000, oVar);
    }

    ag(e eVar, Context context, String str, int i, o oVar) {
        this.yt = true;
        this.mContext = context.getApplicationContext();
        this.ys = oVar;
        this.BV = str;
        this.zc = eVar;
        this.wb = lf.if();
        this.BT = new a(this, this.mContext, this.BV);
        this.BU = new h(new DefaultHttpClient(), this.mContext, this.ys);
        this.BX = 0;
        this.BY = i;
    }

    static String A(Map<String, String> map) {
        Iterable arrayList = new ArrayList(map.size());
        for (Entry entry : map.entrySet()) {
            arrayList.add(ac.encode((String) entry.getKey()) + "=" + ac.encode((String) entry.getValue()));
        }
        return TextUtils.join("&", arrayList);
    }

    private void a(Map<String, String> map, long j, String str) {
        SQLiteDatabase al = al("Error opening database for putHit");
        if (al != null) {
            long parseLong;
            ContentValues contentValues = new ContentValues();
            contentValues.put("hit_string", A(map));
            contentValues.put("hit_time", Long.valueOf(j));
            if (map.containsKey(Fields.ANDROID_APP_UID)) {
                try {
                    parseLong = Long.parseLong((String) map.get(Fields.ANDROID_APP_UID));
                } catch (NumberFormatException e) {
                    parseLong = 0;
                }
            } else {
                parseLong = 0;
            }
            contentValues.put("hit_app_id", Long.valueOf(parseLong));
            if (str == null) {
                str = "http://www.google-analytics.com/collect";
            }
            if (str.length() == 0) {
                ae.W("Empty path: not sending hit");
                return;
            }
            contentValues.put("hit_url", str);
            try {
                al.insert("hits2", null, contentValues);
                this.zc.B(false);
            } catch (SQLiteException e2) {
                ae.W("Error storing hit");
            }
        }
    }

    private void a(Map<String, String> map, Collection<ha> collection) {
        String substring = "&_v".substring(1);
        if (collection != null) {
            for (ha haVar : collection) {
                if (Command.APPEND_VERSION.equals(haVar.getId())) {
                    map.put(substring, haVar.getValue());
                    return;
                }
            }
        }
    }

    private SQLiteDatabase al(String str) {
        try {
            return this.BT.getWritableDatabase();
        } catch (SQLiteException e) {
            ae.W(str);
            return null;
        }
    }

    private void fh() {
        int fj = (fj() - this.BY) + 1;
        if (fj > 0) {
            List G = G(fj);
            ae.V("Store full, deleting " + G.size() + " hits to make room.");
            b((String[]) G.toArray(new String[0]));
        }
    }

    List<String> G(int i) {
        SQLiteException e;
        Throwable th;
        List<String> arrayList = new ArrayList();
        if (i <= 0) {
            ae.W("Invalid maxHits specified. Skipping");
            return arrayList;
        }
        SQLiteDatabase al = al("Error opening database for peekHitIds.");
        if (al == null) {
            return arrayList;
        }
        Cursor query;
        try {
            query = al.query("hits2", new String[]{"hit_id"}, null, null, null, null, String.format("%s ASC", new Object[]{"hit_id"}), Integer.toString(i));
            try {
                if (query.moveToFirst()) {
                    do {
                        arrayList.add(String.valueOf(query.getLong(0)));
                    } while (query.moveToNext());
                }
                if (query != null) {
                    query.close();
                }
            } catch (SQLiteException e2) {
                e = e2;
                try {
                    ae.W("Error in peekHits fetching hitIds: " + e.getMessage());
                    if (query != null) {
                        query.close();
                    }
                    return arrayList;
                } catch (Throwable th2) {
                    th = th2;
                    if (query != null) {
                        query.close();
                    }
                    throw th;
                }
            }
        } catch (SQLiteException e3) {
            e = e3;
            query = null;
            ae.W("Error in peekHits fetching hitIds: " + e.getMessage());
            if (query != null) {
                query.close();
            }
            return arrayList;
        } catch (Throwable th3) {
            th = th3;
            query = null;
            if (query != null) {
                query.close();
            }
            throw th;
        }
        return arrayList;
    }

    /* JADX WARNING: inconsistent code. */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public java.util.List<com.google.android.gms.analytics.ab> H(int r17) {
        /*
        r16 = this;
        r11 = new java.util.ArrayList;
        r11.<init>();
        r2 = "Error opening database for peekHits";
        r0 = r16;
        r2 = r0.al(r2);
        if (r2 != 0) goto L_0x0011;
    L_0x000f:
        r2 = r11;
    L_0x0010:
        return r2;
    L_0x0011:
        r12 = 0;
        r3 = "hits2";
        r4 = 2;
        r4 = new java.lang.String[r4];	 Catch:{ SQLiteException -> 0x00d6, all -> 0x00fb }
        r5 = 0;
        r6 = "hit_id";
        r4[r5] = r6;	 Catch:{ SQLiteException -> 0x00d6, all -> 0x00fb }
        r5 = 1;
        r6 = "hit_time";
        r4[r5] = r6;	 Catch:{ SQLiteException -> 0x00d6, all -> 0x00fb }
        r5 = 0;
        r6 = 0;
        r7 = 0;
        r8 = 0;
        r9 = "%s ASC";
        r10 = 1;
        r10 = new java.lang.Object[r10];	 Catch:{ SQLiteException -> 0x00d6, all -> 0x00fb }
        r13 = 0;
        r14 = "hit_id";
        r10[r13] = r14;	 Catch:{ SQLiteException -> 0x00d6, all -> 0x00fb }
        r9 = java.lang.String.format(r9, r10);	 Catch:{ SQLiteException -> 0x00d6, all -> 0x00fb }
        r10 = java.lang.Integer.toString(r17);	 Catch:{ SQLiteException -> 0x00d6, all -> 0x00fb }
        r13 = r2.query(r3, r4, r5, r6, r7, r8, r9, r10);	 Catch:{ SQLiteException -> 0x00d6, all -> 0x00fb }
        r12 = new java.util.ArrayList;	 Catch:{ SQLiteException -> 0x017d, all -> 0x0177 }
        r12.<init>();	 Catch:{ SQLiteException -> 0x017d, all -> 0x0177 }
        r3 = r13.moveToFirst();	 Catch:{ SQLiteException -> 0x0183, all -> 0x0177 }
        if (r3 == 0) goto L_0x0061;
    L_0x0046:
        r4 = new com.google.android.gms.analytics.ab;	 Catch:{ SQLiteException -> 0x0183, all -> 0x0177 }
        r5 = 0;
        r3 = 0;
        r6 = r13.getLong(r3);	 Catch:{ SQLiteException -> 0x0183, all -> 0x0177 }
        r3 = 1;
        r8 = r13.getLong(r3);	 Catch:{ SQLiteException -> 0x0183, all -> 0x0177 }
        r10 = "";
        r4.<init>(r5, r6, r8, r10);	 Catch:{ SQLiteException -> 0x0183, all -> 0x0177 }
        r12.add(r4);	 Catch:{ SQLiteException -> 0x0183, all -> 0x0177 }
        r3 = r13.moveToNext();	 Catch:{ SQLiteException -> 0x0183, all -> 0x0177 }
        if (r3 != 0) goto L_0x0046;
    L_0x0061:
        if (r13 == 0) goto L_0x0066;
    L_0x0063:
        r13.close();
    L_0x0066:
        r11 = 0;
        r3 = "hits2";
        r4 = 3;
        r4 = new java.lang.String[r4];	 Catch:{ SQLiteException -> 0x0175 }
        r5 = 0;
        r6 = "hit_id";
        r4[r5] = r6;	 Catch:{ SQLiteException -> 0x0175 }
        r5 = 1;
        r6 = "hit_string";
        r4[r5] = r6;	 Catch:{ SQLiteException -> 0x0175 }
        r5 = 2;
        r6 = "hit_url";
        r4[r5] = r6;	 Catch:{ SQLiteException -> 0x0175 }
        r5 = 0;
        r6 = 0;
        r7 = 0;
        r8 = 0;
        r9 = "%s ASC";
        r10 = 1;
        r10 = new java.lang.Object[r10];	 Catch:{ SQLiteException -> 0x0175 }
        r14 = 0;
        r15 = "hit_id";
        r10[r14] = r15;	 Catch:{ SQLiteException -> 0x0175 }
        r9 = java.lang.String.format(r9, r10);	 Catch:{ SQLiteException -> 0x0175 }
        r10 = java.lang.Integer.toString(r17);	 Catch:{ SQLiteException -> 0x0175 }
        r3 = r2.query(r3, r4, r5, r6, r7, r8, r9, r10);	 Catch:{ SQLiteException -> 0x0175 }
        r2 = r3.moveToFirst();	 Catch:{ SQLiteException -> 0x0120, all -> 0x0172 }
        if (r2 == 0) goto L_0x00ce;
    L_0x009b:
        r4 = r11;
    L_0x009c:
        r0 = r3;
        r0 = (android.database.sqlite.SQLiteCursor) r0;	 Catch:{ SQLiteException -> 0x0120, all -> 0x0172 }
        r2 = r0;
        r2 = r2.getWindow();	 Catch:{ SQLiteException -> 0x0120, all -> 0x0172 }
        r2 = r2.getNumRows();	 Catch:{ SQLiteException -> 0x0120, all -> 0x0172 }
        if (r2 <= 0) goto L_0x0102;
    L_0x00aa:
        r2 = r12.get(r4);	 Catch:{ SQLiteException -> 0x0120, all -> 0x0172 }
        r2 = (com.google.android.gms.analytics.ab) r2;	 Catch:{ SQLiteException -> 0x0120, all -> 0x0172 }
        r5 = 1;
        r5 = r3.getString(r5);	 Catch:{ SQLiteException -> 0x0120, all -> 0x0172 }
        r2.aj(r5);	 Catch:{ SQLiteException -> 0x0120, all -> 0x0172 }
        r2 = r12.get(r4);	 Catch:{ SQLiteException -> 0x0120, all -> 0x0172 }
        r2 = (com.google.android.gms.analytics.ab) r2;	 Catch:{ SQLiteException -> 0x0120, all -> 0x0172 }
        r5 = 2;
        r5 = r3.getString(r5);	 Catch:{ SQLiteException -> 0x0120, all -> 0x0172 }
        r2.ak(r5);	 Catch:{ SQLiteException -> 0x0120, all -> 0x0172 }
    L_0x00c6:
        r2 = r4 + 1;
        r4 = r3.moveToNext();	 Catch:{ SQLiteException -> 0x0120, all -> 0x0172 }
        if (r4 != 0) goto L_0x0189;
    L_0x00ce:
        if (r3 == 0) goto L_0x00d3;
    L_0x00d0:
        r3.close();
    L_0x00d3:
        r2 = r12;
        goto L_0x0010;
    L_0x00d6:
        r2 = move-exception;
        r3 = r2;
        r4 = r12;
        r2 = r11;
    L_0x00da:
        r5 = new java.lang.StringBuilder;	 Catch:{ all -> 0x017a }
        r5.<init>();	 Catch:{ all -> 0x017a }
        r6 = "Error in peekHits fetching hitIds: ";
        r5 = r5.append(r6);	 Catch:{ all -> 0x017a }
        r3 = r3.getMessage();	 Catch:{ all -> 0x017a }
        r3 = r5.append(r3);	 Catch:{ all -> 0x017a }
        r3 = r3.toString();	 Catch:{ all -> 0x017a }
        com.google.android.gms.analytics.ae.W(r3);	 Catch:{ all -> 0x017a }
        if (r4 == 0) goto L_0x0010;
    L_0x00f6:
        r4.close();
        goto L_0x0010;
    L_0x00fb:
        r2 = move-exception;
    L_0x00fc:
        if (r12 == 0) goto L_0x0101;
    L_0x00fe:
        r12.close();
    L_0x0101:
        throw r2;
    L_0x0102:
        r5 = "HitString for hitId %d too large.  Hit will be deleted.";
        r2 = 1;
        r6 = new java.lang.Object[r2];	 Catch:{ SQLiteException -> 0x0120, all -> 0x0172 }
        r7 = 0;
        r2 = r12.get(r4);	 Catch:{ SQLiteException -> 0x0120, all -> 0x0172 }
        r2 = (com.google.android.gms.analytics.ab) r2;	 Catch:{ SQLiteException -> 0x0120, all -> 0x0172 }
        r8 = r2.fb();	 Catch:{ SQLiteException -> 0x0120, all -> 0x0172 }
        r2 = java.lang.Long.valueOf(r8);	 Catch:{ SQLiteException -> 0x0120, all -> 0x0172 }
        r6[r7] = r2;	 Catch:{ SQLiteException -> 0x0120, all -> 0x0172 }
        r2 = java.lang.String.format(r5, r6);	 Catch:{ SQLiteException -> 0x0120, all -> 0x0172 }
        com.google.android.gms.analytics.ae.W(r2);	 Catch:{ SQLiteException -> 0x0120, all -> 0x0172 }
        goto L_0x00c6;
    L_0x0120:
        r2 = move-exception;
        r13 = r3;
    L_0x0122:
        r3 = new java.lang.StringBuilder;	 Catch:{ all -> 0x016b }
        r3.<init>();	 Catch:{ all -> 0x016b }
        r4 = "Error in peekHits fetching hitString: ";
        r3 = r3.append(r4);	 Catch:{ all -> 0x016b }
        r2 = r2.getMessage();	 Catch:{ all -> 0x016b }
        r2 = r3.append(r2);	 Catch:{ all -> 0x016b }
        r2 = r2.toString();	 Catch:{ all -> 0x016b }
        com.google.android.gms.analytics.ae.W(r2);	 Catch:{ all -> 0x016b }
        r3 = new java.util.ArrayList;	 Catch:{ all -> 0x016b }
        r3.<init>();	 Catch:{ all -> 0x016b }
        r4 = 0;
        r5 = r12.iterator();	 Catch:{ all -> 0x016b }
    L_0x0146:
        r2 = r5.hasNext();	 Catch:{ all -> 0x016b }
        if (r2 == 0) goto L_0x015e;
    L_0x014c:
        r2 = r5.next();	 Catch:{ all -> 0x016b }
        r2 = (com.google.android.gms.analytics.ab) r2;	 Catch:{ all -> 0x016b }
        r6 = r2.fa();	 Catch:{ all -> 0x016b }
        r6 = android.text.TextUtils.isEmpty(r6);	 Catch:{ all -> 0x016b }
        if (r6 == 0) goto L_0x0167;
    L_0x015c:
        if (r4 == 0) goto L_0x0166;
    L_0x015e:
        if (r13 == 0) goto L_0x0163;
    L_0x0160:
        r13.close();
    L_0x0163:
        r2 = r3;
        goto L_0x0010;
    L_0x0166:
        r4 = 1;
    L_0x0167:
        r3.add(r2);	 Catch:{ all -> 0x016b }
        goto L_0x0146;
    L_0x016b:
        r2 = move-exception;
    L_0x016c:
        if (r13 == 0) goto L_0x0171;
    L_0x016e:
        r13.close();
    L_0x0171:
        throw r2;
    L_0x0172:
        r2 = move-exception;
        r13 = r3;
        goto L_0x016c;
    L_0x0175:
        r2 = move-exception;
        goto L_0x0122;
    L_0x0177:
        r2 = move-exception;
        r12 = r13;
        goto L_0x00fc;
    L_0x017a:
        r2 = move-exception;
        r12 = r4;
        goto L_0x00fc;
    L_0x017d:
        r2 = move-exception;
        r3 = r2;
        r4 = r13;
        r2 = r11;
        goto L_0x00da;
    L_0x0183:
        r2 = move-exception;
        r3 = r2;
        r4 = r13;
        r2 = r12;
        goto L_0x00da;
    L_0x0189:
        r4 = r2;
        goto L_0x009c;
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.gms.analytics.ag.H(int):java.util.List<com.google.android.gms.analytics.ab>");
    }

    public void a(Map<String, String> map, long j, String str, Collection<ha> collection) {
        fi();
        fh();
        a(map, collection);
        a(map, j, str);
    }

    @Deprecated
    void b(Collection<ab> collection) {
        if (collection == null || collection.isEmpty()) {
            ae.W("Empty/Null collection passed to deleteHits.");
            return;
        }
        String[] strArr = new String[collection.size()];
        int i = 0;
        for (ab fb : collection) {
            int i2 = i + 1;
            strArr[i] = String.valueOf(fb.fb());
            i = i2;
        }
        b(strArr);
    }

    void b(String[] strArr) {
        boolean z = true;
        if (strArr == null || strArr.length == 0) {
            ae.W("Empty hitIds passed to deleteHits.");
            return;
        }
        SQLiteDatabase al = al("Error opening database for deleteHits.");
        if (al != null) {
            try {
                al.delete("hits2", String.format("HIT_ID in (%s)", new Object[]{TextUtils.join(",", Collections.nCopies(strArr.length, "?"))}), strArr);
                e eVar = this.zc;
                if (fj() != 0) {
                    z = false;
                }
                eVar.B(z);
            } catch (SQLiteException e) {
                ae.W("Error deleting hits " + TextUtils.join(",", strArr));
            }
        }
    }

    public r dV() {
        return this.BU;
    }

    public void dispatch() {
        boolean z = true;
        ae.V("Dispatch running...");
        if (this.BU.ea()) {
            List H = H(20);
            if (H.isEmpty()) {
                ae.V("...nothing to dispatch");
                this.zc.B(true);
                return;
            }
            if (this.BW == null) {
                this.BW = new af("_t=dispatch&_v=ma4.0.4", false);
            }
            if (fj() > H.size()) {
                z = false;
            }
            int a = this.BU.a(H, this.BW, z);
            ae.V("sent " + a + " of " + H.size() + " hits");
            b(H.subList(0, Math.min(a, H.size())));
            if (a != H.size() || fj() <= 0) {
                this.BW = null;
            } else {
                GoogleAnalytics.getInstance(this.mContext).dispatchLocalHits();
            }
        }
    }

    int fi() {
        boolean z = true;
        long currentTimeMillis = this.wb.currentTimeMillis();
        if (currentTimeMillis <= this.BX + 86400000) {
            return 0;
        }
        this.BX = currentTimeMillis;
        SQLiteDatabase al = al("Error opening database for deleteStaleHits.");
        if (al == null) {
            return 0;
        }
        int delete = al.delete("hits2", "HIT_TIME < ?", new String[]{Long.toString(this.wb.currentTimeMillis() - 2592000000L)});
        e eVar = this.zc;
        if (fj() != 0) {
            z = false;
        }
        eVar.B(z);
        return delete;
    }

    int fj() {
        Cursor cursor = null;
        int i = 0;
        SQLiteDatabase al = al("Error opening database for getNumStoredHits.");
        if (al != null) {
            try {
                cursor = al.rawQuery("SELECT COUNT(*) from hits2", null);
                if (cursor.moveToFirst()) {
                    i = (int) cursor.getLong(0);
                }
                if (cursor != null) {
                    cursor.close();
                }
            } catch (SQLiteException e) {
                ae.W("Error getting numStoredHits");
                if (cursor != null) {
                    cursor.close();
                }
            } catch (Throwable th) {
                if (cursor != null) {
                    cursor.close();
                }
            }
        }
        return i;
    }

    public void l(long j) {
        boolean z = true;
        SQLiteDatabase al = al("Error opening database for clearHits");
        if (al != null) {
            if (j == 0) {
                al.delete("hits2", null, null);
            } else {
                al.delete("hits2", "hit_app_id = ?", new String[]{Long.valueOf(j).toString()});
            }
            e eVar = this.zc;
            if (fj() != 0) {
                z = false;
            }
            eVar.B(z);
        }
    }

    public void setDryRun(boolean dryRun) {
        this.yt = dryRun;
        if (this.BU != null) {
            this.BU.setDryRun(dryRun);
        }
    }
}
