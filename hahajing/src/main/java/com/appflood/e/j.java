package com.appflood.e;

import android.content.Context;
import android.content.Intent;
import android.provider.CalendarContract.Events;
import android.util.Log;
import com.google.android.gms.plus.PlusShare;
import com.inmobi.commons.analytics.db.AnalyticsEvent;
import io.fabric.sdk.android.services.common.CommonUtils;
import io.fabric.sdk.android.services.network.HttpRequest;
import java.security.Key;
import java.security.MessageDigest;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Random;
import java.util.zip.Adler32;
import javax.crypto.Cipher;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;
import javax.crypto.spec.PBEParameterSpec;
import org.json.JSONArray;
import org.json.JSONObject;

public final class j {
    private static final char[] a = new char[]{'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f'};
    private static String[][] b;
    private static String[] c = new String[]{"MO", "TU", "WE", "TH", "FR", "SA", "SU"};

    public static class a {
        private static char[] a = new char[64];
        private static byte[] b = new byte[128];

        static {
            int i;
            int i2 = 0;
            char c = 'A';
            int i3 = 0;
            while (c <= 'Z') {
                i = i3 + 1;
                a[i3] = c;
                c = (char) (c + 1);
                i3 = i;
            }
            c = 'a';
            while (c <= 'z') {
                i = i3 + 1;
                a[i3] = c;
                c = (char) (c + 1);
                i3 = i;
            }
            c = '0';
            while (c <= '9') {
                i = i3 + 1;
                a[i3] = c;
                c = (char) (c + 1);
                i3 = i;
            }
            i = i3 + 1;
            a[i3] = '+';
            a[i] = '/';
            for (int i4 = 0; i4 < b.length; i4++) {
                b[i4] = (byte) -1;
            }
            while (i2 < 64) {
                b[a[i2]] = (byte) i2;
                i2++;
            }
        }

        public static String a(byte[] bArr) {
            return new String(a(bArr, bArr.length));
        }

        private static char[] a(byte[] bArr, int i) {
            int i2 = ((i << 2) + 2) / 3;
            char[] cArr = new char[(((i + 2) / 3) << 2)];
            int i3 = 0;
            int i4 = 0;
            while (i4 < i) {
                int i5;
                int i6 = i4 + 1;
                int i7 = bArr[i4] & 255;
                if (i6 < i) {
                    i5 = bArr[i6] & 255;
                    i6++;
                } else {
                    i5 = 0;
                }
                if (i6 < i) {
                    i4 = i6 + 1;
                    i6 = bArr[i6] & 255;
                } else {
                    i4 = i6;
                    i6 = 0;
                }
                int i8 = i7 >>> 2;
                i7 = ((i7 & 3) << 4) | (i5 >>> 4);
                i5 = ((i5 & 15) << 2) | (i6 >>> 6);
                int i9 = i6 & 63;
                i6 = i3 + 1;
                cArr[i3] = a[i8];
                i3 = i6 + 1;
                cArr[i6] = a[i7];
                cArr[i3] = i3 < i2 ? a[i5] : '=';
                i5 = i3 + 1;
                cArr[i5] = i5 < i2 ? a[i9] : '=';
                i3 = i5 + 1;
            }
            return cArr;
        }
    }

    public static class b {
        private Key a;
        private PBEParameterSpec b;
        private Cipher c;

        public b(String str, byte[] bArr) {
            try {
                this.a = SecretKeyFactory.getInstance("PBEWithMD5AndDES").generateSecret(new PBEKeySpec(str.toCharArray()));
                this.b = new PBEParameterSpec(bArr, 1);
                this.c = Cipher.getInstance("PBEWithMD5AndDES/CBC/PKCS5Padding");
                this.c = Cipher.getInstance("PBEWithMD5AndDES");
            } catch (Throwable th) {
            }
        }

        private String a(byte[] bArr) {
            try {
                this.c.init(1, this.a, this.b);
                return a.a(this.c.doFinal(bArr));
            } catch (Throwable th) {
                return j.a(bArr, "");
            }
        }

        public final String a(String str) {
            try {
                str = a(j.b(str));
            } catch (Throwable th) {
                j.a(th, "failed to encrypt");
            }
            return str;
        }
    }

    static {
        r0 = new String[3][];
        r0[0] = new String[]{"daily", "weekly", "monthly", "yearly"};
        r0[1] = new String[]{"", "daysInWeek", "daysInMonth", "daysInYear"};
        r0[2] = new String[]{"", "BYDAY", "BYMONTHDAY", "BYYEARDAY"};
        b = r0;
    }

    public static int a(String str, int i) {
        if (str != null) {
            try {
                i = Integer.parseInt(str);
            } catch (Throwable th) {
                b(th, "failed to parse int (" + str + ") using default value :" + i);
            }
        }
        return i;
    }

    public static int a(JSONObject jSONObject, String str, int i) {
        if (jSONObject != null) {
            try {
                i = jSONObject.getInt(str);
            } catch (Throwable th) {
            }
        }
        return i;
    }

    public static String a(String str) {
        return b(b(str));
    }

    public static String a(HashMap<String, Object> hashMap) {
        if (hashMap == null || hashMap.isEmpty()) {
            return "";
        }
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("{");
        for (Entry entry : hashMap.entrySet()) {
            String str = (String) entry.getKey();
            Object value = entry.getValue();
            if (value != null) {
                String valueOf;
                String str2 = "";
                if (value instanceof Integer) {
                    valueOf = String.valueOf(value);
                } else if (value instanceof ArrayList) {
                    ArrayList arrayList = (ArrayList) value;
                    if (arrayList.size() > 0) {
                        str2 = "[";
                        Iterator it = arrayList.iterator();
                        while (it.hasNext()) {
                            Object next = it.next();
                            if (next instanceof String) {
                                str2 = str2 + "\"" + ((String) next) + "\"";
                                if (arrayList.indexOf(next) < arrayList.size() - 1) {
                                    str2 = str2 + ",";
                                }
                            }
                        }
                        valueOf = str2 + "]";
                    }
                } else {
                    valueOf = value instanceof String ? (String) value : str2;
                }
                if (!g(valueOf)) {
                    stringBuilder.append(str);
                    stringBuilder.append(":");
                    stringBuilder.append(valueOf);
                    stringBuilder.append("; ");
                }
            }
        }
        stringBuilder.deleteCharAt(stringBuilder.length() - 1);
        stringBuilder.append("}");
        return stringBuilder.toString();
    }

    private static String a(Map<String, String> map) {
        String str = "";
        String a = a((Map) map, "frequency", "");
        "rrrrrrrr " + a;
        if (g(a)) {
            return "";
        }
        String a2;
        int i = 0;
        while (i < b[0].length) {
            Object split;
            if (a.equals(b[0][i])) {
                str = "FREQ=" + b[0][i].toUpperCase() + ";";
                if (i == 0) {
                    return str;
                }
                a2 = a((Map) map, b[1][i], "");
                if (g(a2)) {
                    if (i == 1) {
                        return str + b[2][i] + "=" + a2 + ";";
                    }
                    split = a2.split(",");
                    a = b[2][i] + "=";
                    i = 0;
                    while (i < split.length) {
                        a = a + c[a(split[i], 0)] + (i != split.length + -1 ? ";" : ",");
                        i++;
                    }
                    "sub array + " + split + "  " + split.length + " subR " + a2 + " weekley " + a;
                    return str + a;
                } else if (g(a2) || i != 3 || !map.containsKey("daysInMonth")) {
                    return str;
                } else {
                    String a3 = a((Map) map, "daysInMonth", "");
                    if (!g(a3)) {
                        str = str + b[2][2] + "=" + a3 + ";";
                    }
                    a3 = a((Map) map, "monthsInYear", "");
                    return !g(a3) ? str + "BYMONTH=" + a3 + ";" : str;
                }
            } else {
                "i ===== 444   " + i;
                if (i == b[0].length - 1) {
                    return "";
                }
                i++;
            }
        }
        i = 0;
        a2 = a((Map) map, b[1][i], "");
        if (g(a2)) {
            return g(a2) ? str : str;
        } else {
            if (i == 1) {
                return str + b[2][i] + "=" + a2 + ";";
            }
            split = a2.split(",");
            a = b[2][i] + "=";
            i = 0;
            while (i < split.length) {
                if (i != split.length + -1) {
                }
                a = a + c[a(split[i], 0)] + (i != split.length + -1 ? ";" : ",");
                i++;
            }
            "sub array + " + split + "  " + split.length + " subR " + a2 + " weekley " + a;
            return str + a;
        }
    }

    private static String a(Map<String, String> map, String str, String str2) {
        if (map == null) {
            return str2;
        }
        String str3 = (String) map.get(str);
        return str3 != null ? str3 : str2;
    }

    public static String a(JSONObject jSONObject, String str, String str2) {
        if (jSONObject == null) {
            return str2;
        }
        String str3 = "";
        try {
            str3 = jSONObject.getString(str);
        } catch (Throwable th) {
        }
        return str3;
    }

    private static String a(byte[] bArr) {
        if (bArr == null) {
            return null;
        }
        char[] cArr = new char[(bArr.length << 1)];
        for (int i = 0; i < bArr.length; i++) {
            int i2 = bArr[i] & 255;
            cArr[i << 1] = a[i2 >> 4];
            cArr[(i << 1) + 1] = a[i2 & 15];
        }
        return new String(cArr);
    }

    public static String a(byte[] bArr, String str) {
        int i = 0;
        if (bArr == null) {
            return str;
        }
        try {
            if (bArr.length >= 3 && bArr[0] == (byte) -17 && bArr[1] == (byte) -69 && bArr[2] == (byte) -65) {
                i = 3;
            }
            return new String(bArr, i, bArr.length - i, HttpRequest.CHARSET_UTF8);
        } catch (Throwable th) {
            a(th, "failed to get utf8String");
            return str;
        }
    }

    public static JSONObject a(String str, Object obj) {
        JSONObject jSONObject = new JSONObject();
        try {
            jSONObject.put(str, obj);
        } catch (Throwable th) {
            a(th, "failed to add type into json");
        }
        return jSONObject;
    }

    public static JSONObject a(JSONObject jSONObject, String str) {
        JSONObject jSONObject2 = null;
        try {
            jSONObject2 = jSONObject.getJSONObject(str);
        } catch (Throwable th) {
        }
        return jSONObject2;
    }

    public static void a() {
    }

    public static void a(Context context, Map<String, String> map) {
        int i = 1;
        Log.i("AppFlood", " params " + map);
        a((Map) map, AnalyticsEvent.EVENT_ID, "");
        String a = a((Map) map, "status", "");
        int i2 = "confirmed".equals(a) ? 1 : "cancelled".equals(a) ? 2 : 0;
        String a2 = a((Map) map, PlusShare.KEY_CONTENT_DEEP_LINK_METADATA_DESCRIPTION, "");
        String a3 = a((Map) map, "summary", "");
        String a4 = a((Map) map, "location", "");
        Date j = j(a((Map) map, "start", ""));
        Date j2 = j(a((Map) map, "end", ""));
        i(a((Map) map, "relativeReminder", ""));
        String a5 = a((Map) map);
        if (!g(a5)) {
            a5 = a5.substring(0, a5.length() - 1);
        }
        Log.i("AppFlood", " ret  " + a5);
        int i3 = "transparent".equals(a((Map) map, "transparency", "")) ? 1 : 0;
        if ((c.j >= 14 ? 1 : 0) != 0) {
            try {
                Intent data = new Intent("android.intent.action.INSERT").setData(Events.CONTENT_URI);
            } catch (Exception e) {
                Throwable e2 = e;
                Intent intent = null;
                b(e2, "first try to start calendar error");
                if (i != 0) {
                    return;
                }
            }
        }
        data = new Intent("android.intent.action.EDIT");
        try {
            data.setType("vnd.android.cursor.item/event");
        } catch (Exception e3) {
            e2 = e3;
            intent = data;
            b(e2, "first try to start calendar error");
            if (i != 0) {
            }
            return;
        }
        try {
            data.putExtra("beginTime", j.getTime());
            data.putExtra("eventStatus", i2);
            data.putExtra("transparency", i3);
            if (!g(a5)) {
                data.putExtra("rrule", a5);
            }
            data.putExtra("endTime", j2.getTime());
            data.putExtra("title", a3);
            data.putExtra("eventLocation", a4);
            data.putExtra(PlusShare.KEY_CONTENT_DEEP_LINK_METADATA_DESCRIPTION, a2);
            context.startActivity(data);
            i = 0;
            intent = data;
        } catch (Exception e4) {
            e2 = e4;
            intent = data;
            b(e2, "first try to start calendar error");
            if (i != 0) {
                return;
            }
        }
        if (i != 0 && intent != null) {
            try {
                if (intent.getAction() == "android.intent.action.EDIT") {
                    intent.setAction("android.intent.action.INSERT");
                    intent.setData(Events.CONTENT_URI);
                } else {
                    intent.setAction("android.intent.action.EDIT");
                    intent.setType("vnd.android.cursor.item/event");
                }
                context.startActivity(intent);
            } catch (Throwable e22) {
                b(e22, " start calendar error");
            }
        }
    }

    public static void a(Throwable th, String str) {
        if (th != null) {
            th.printStackTrace();
        }
        Log.e("AppFlood", str);
    }

    public static boolean a(List<?> list) {
        return list == null || list.size() == 0;
    }

    public static String b() {
        byte[] bArr = new byte[16];
        new Random().nextBytes(bArr);
        bArr[13] = (byte) 0;
        bArr[12] = (byte) 0;
        bArr[5] = (byte) 0;
        bArr[2] = (byte) 0;
        Adler32 adler32 = new Adler32();
        adler32.update(bArr);
        long value = adler32.getValue();
        bArr[2] = (byte) ((int) (15 ^ value));
        bArr[5] = (byte) ((int) ((value >> 8) ^ 113));
        bArr[12] = (byte) ((int) ((value >> 16) ^ 119));
        bArr[13] = (byte) ((int) ((value >> 24) ^ 106));
        return a(bArr);
    }

    private static String b(byte[] bArr) {
        try {
            MessageDigest instance = MessageDigest.getInstance(CommonUtils.MD5_INSTANCE);
            instance.reset();
            instance.update(bArr);
            return a(instance.digest());
        } catch (Throwable th) {
            a(th, "failed to compute md5");
            return a(bArr, "");
        }
    }

    public static void b(Throwable th, String str) {
        if (th != null) {
            th.printStackTrace();
        }
        Log.w("AppFlood", str);
    }

    public static byte[] b(String str) {
        if (str != null) {
            try {
                return str.getBytes(HttpRequest.CHARSET_UTF8);
            } catch (Throwable th) {
                b(th, "failed to getBytes: " + str);
            }
        }
        return new byte[0];
    }

    public static void c(String str) {
        Log.w("AppFlood", str);
    }

    public static void d(String str) {
        Log.i("AppFlood", str);
    }

    public static JSONObject e(String str) {
        try {
            return new JSONObject(str);
        } catch (Throwable th) {
            b(th, "failed to parseJsonObject," + str);
            return new JSONObject();
        }
    }

    public static JSONArray f(String str) {
        try {
            return new JSONArray(str);
        } catch (Throwable th) {
            b(th, "failed to parseJsonArray array = " + str);
            return new JSONArray();
        }
    }

    public static boolean g(String str) {
        return str == null || str.length() == 0;
    }

    public static int h(String str) {
        String[] split = str.split(":");
        if (split == null || split.length < 3) {
            return 0;
        }
        int parseInt = ((Integer.parseInt(split[0]) * 3600) + (Integer.parseInt(split[1]) * 60)) + Integer.parseInt(split[2]);
        Log.w("AppFlood", "dura " + str + " totalSec " + parseInt);
        return parseInt;
    }

    private static long i(String str) {
        long j = 0;
        if (str != null) {
            try {
                j = Long.parseLong(str);
            } catch (Throwable e) {
                b(e, "failed to parse long [" + str + "], using default value [0]");
            }
        }
        return j;
    }

    private static Date j(String str) {
        Date date = null;
        try {
            date = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssS").parse(str);
        } catch (Throwable e) {
            Log.w("AppFlood", "first try to parse time from w3c error. time = " + str);
            try {
                date = new SimpleDateFormat("yyyy-MM-dd'T'HH:mmS").parse(str);
            } catch (ParseException e2) {
                a(e, " parse time from w3c error again! S =  " + str);
            }
        }
        Log.i("AppFlood", " time " + str + " date " + date);
        return date;
    }
}
