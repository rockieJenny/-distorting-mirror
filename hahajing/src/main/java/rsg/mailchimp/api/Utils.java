package rsg.mailchimp.api;

import com.google.android.gms.common.Scopes;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;

public class Utils {
    public static final Hashtable<String, Boolean> getWebHookActions(int flags) {
        Hashtable<String, Boolean> retVal = new Hashtable();
        if ((flags & 2) != 0) {
            retVal.put("subscribe", Boolean.valueOf(true));
        } else {
            retVal.put("subscribe", Boolean.valueOf(false));
        }
        if ((flags & 4) != 0) {
            retVal.put("unsubscribe", Boolean.valueOf(true));
        } else {
            retVal.put("unsubscribe", Boolean.valueOf(false));
        }
        if ((flags & 8) != 0) {
            retVal.put(Scopes.PROFILE, Boolean.valueOf(true));
        } else {
            retVal.put(Scopes.PROFILE, Boolean.valueOf(false));
        }
        if ((flags & 16) != 0) {
            retVal.put("cleaned", Boolean.valueOf(true));
        } else {
            retVal.put("cleaned", Boolean.valueOf(false));
        }
        if ((flags & 32) != 0) {
            retVal.put("upemail", Boolean.valueOf(true));
        } else {
            retVal.put("upemail", Boolean.valueOf(false));
        }
        return retVal;
    }

    public static final Hashtable<String, Boolean> getWebHookSources(int flags) {
        Hashtable<String, Boolean> retVal = new Hashtable();
        if ((flags & 2) != 0) {
            retVal.put("user", Boolean.valueOf(true));
        } else {
            retVal.put("user", Boolean.valueOf(false));
        }
        if ((flags & 4) != 0) {
            retVal.put("admin", Boolean.valueOf(true));
        } else {
            retVal.put("admin", Boolean.valueOf(false));
        }
        if ((flags & 8) != 0) {
            retVal.put("api", Boolean.valueOf(true));
        } else {
            retVal.put("api", Boolean.valueOf(false));
        }
        return retVal;
    }

    /* JADX WARNING: inconsistent code. */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public static void populateObjectFromRPCStruct(java.lang.Object r16, java.util.Map r17, boolean r18, java.lang.String... r19) throws rsg.mailchimp.api.MailChimpApiException {
        /*
        r3 = r17.entrySet();
        r9 = 0;
        r7 = 0;
        r6 = 0;
        r10 = 0;
        r12 = r3.iterator();
    L_0x000c:
        r11 = r12.hasNext();
        if (r11 != 0) goto L_0x0013;
    L_0x0012:
        return;
    L_0x0013:
        r4 = r12.next();
        r4 = (java.util.Map.Entry) r4;
        r11 = r4.getKey();
        r7 = r11.toString();
        r6 = translateFieldName(r7);
        r0 = r19;
        r11 = ignoreFieldName(r0, r6);
        if (r11 != 0) goto L_0x000c;
    L_0x002d:
        r11 = r16.getClass();	 Catch:{ SecurityException -> 0x0054, NoSuchFieldException -> 0x00b1, IllegalArgumentException -> 0x0107, IllegalAccessException -> 0x0162, ParseException -> 0x019f, ClassCastException -> 0x01dc }
        r13 = translateFieldName(r7);	 Catch:{ SecurityException -> 0x0054, NoSuchFieldException -> 0x00b1, IllegalArgumentException -> 0x0107, IllegalAccessException -> 0x0162, ParseException -> 0x019f, ClassCastException -> 0x01dc }
        r5 = r11.getField(r13);	 Catch:{ SecurityException -> 0x0054, NoSuchFieldException -> 0x00b1, IllegalArgumentException -> 0x0107, IllegalAccessException -> 0x0162, ParseException -> 0x019f, ClassCastException -> 0x01dc }
        r9 = r5.getType();	 Catch:{ SecurityException -> 0x0054, NoSuchFieldException -> 0x00b1, IllegalArgumentException -> 0x0107, IllegalAccessException -> 0x0162, ParseException -> 0x019f, ClassCastException -> 0x01dc }
        r11 = java.lang.Boolean.class;
        if (r9 != r11) goto L_0x0086;
    L_0x0041:
        r11 = new java.lang.Boolean;	 Catch:{ SecurityException -> 0x0054, NoSuchFieldException -> 0x00b1, IllegalArgumentException -> 0x0107, IllegalAccessException -> 0x0162, ParseException -> 0x019f, ClassCastException -> 0x01dc }
        r13 = r4.getValue();	 Catch:{ SecurityException -> 0x0054, NoSuchFieldException -> 0x00b1, IllegalArgumentException -> 0x0107, IllegalAccessException -> 0x0162, ParseException -> 0x019f, ClassCastException -> 0x01dc }
        r13 = r13.toString();	 Catch:{ SecurityException -> 0x0054, NoSuchFieldException -> 0x00b1, IllegalArgumentException -> 0x0107, IllegalAccessException -> 0x0162, ParseException -> 0x019f, ClassCastException -> 0x01dc }
        r11.<init>(r13);	 Catch:{ SecurityException -> 0x0054, NoSuchFieldException -> 0x00b1, IllegalArgumentException -> 0x0107, IllegalAccessException -> 0x0162, ParseException -> 0x019f, ClassCastException -> 0x01dc }
        r0 = r16;
        r5.set(r0, r11);	 Catch:{ SecurityException -> 0x0054, NoSuchFieldException -> 0x00b1, IllegalArgumentException -> 0x0107, IllegalAccessException -> 0x0162, ParseException -> 0x019f, ClassCastException -> 0x01dc }
        goto L_0x000c;
    L_0x0054:
        r2 = move-exception;
        r11 = new rsg.mailchimp.api.MailChimpApiException;
        r12 = new java.lang.StringBuilder;
        r13 = "Couldn't translate values to ";
        r12.<init>(r13);
        r13 = r16.getClass();
        r12 = r12.append(r13);
        r13 = ", key: ";
        r12 = r12.append(r13);
        r12 = r12.append(r7);
        r13 = " causing SecurityException, value: ";
        r12 = r12.append(r13);
        r13 = r4.getValue();
        r12 = r12.append(r13);
        r12 = r12.toString();
        r11.<init>(r12, r2);
        throw r11;
    L_0x0086:
        r11 = java.util.Date.class;
        if (r9 != r11) goto L_0x00d8;
    L_0x008a:
        r11 = r4.getValue();	 Catch:{ SecurityException -> 0x0054, NoSuchFieldException -> 0x00b1, IllegalArgumentException -> 0x0107, IllegalAccessException -> 0x0162, ParseException -> 0x019f, ClassCastException -> 0x01dc }
        r10 = r11.toString();	 Catch:{ SecurityException -> 0x0054, NoSuchFieldException -> 0x00b1, IllegalArgumentException -> 0x0107, IllegalAccessException -> 0x0162, ParseException -> 0x019f, ClassCastException -> 0x01dc }
        r11 = r10.trim();	 Catch:{ SecurityException -> 0x0054, NoSuchFieldException -> 0x00b1, IllegalArgumentException -> 0x0107, IllegalAccessException -> 0x0162, ParseException -> 0x019f, ClassCastException -> 0x01dc }
        r11 = r11.length();	 Catch:{ SecurityException -> 0x0054, NoSuchFieldException -> 0x00b1, IllegalArgumentException -> 0x0107, IllegalAccessException -> 0x0162, ParseException -> 0x019f, ClassCastException -> 0x01dc }
        if (r11 <= 0) goto L_0x000c;
    L_0x009c:
        r11 = rsg.mailchimp.api.Constants.TIME_FMT;	 Catch:{ SecurityException -> 0x0054, NoSuchFieldException -> 0x00b1, IllegalArgumentException -> 0x0107, IllegalAccessException -> 0x0162, ParseException -> 0x019f, ClassCastException -> 0x01dc }
        r13 = r4.getValue();	 Catch:{ SecurityException -> 0x0054, NoSuchFieldException -> 0x00b1, IllegalArgumentException -> 0x0107, IllegalAccessException -> 0x0162, ParseException -> 0x019f, ClassCastException -> 0x01dc }
        r13 = r13.toString();	 Catch:{ SecurityException -> 0x0054, NoSuchFieldException -> 0x00b1, IllegalArgumentException -> 0x0107, IllegalAccessException -> 0x0162, ParseException -> 0x019f, ClassCastException -> 0x01dc }
        r11 = r11.parse(r13);	 Catch:{ SecurityException -> 0x0054, NoSuchFieldException -> 0x00b1, IllegalArgumentException -> 0x0107, IllegalAccessException -> 0x0162, ParseException -> 0x019f, ClassCastException -> 0x01dc }
        r0 = r16;
        r5.set(r0, r11);	 Catch:{ SecurityException -> 0x0054, NoSuchFieldException -> 0x00b1, IllegalArgumentException -> 0x0107, IllegalAccessException -> 0x0162, ParseException -> 0x019f, ClassCastException -> 0x01dc }
        goto L_0x000c;
    L_0x00b1:
        r2 = move-exception;
        if (r18 == 0) goto L_0x021c;
    L_0x00b4:
        r11 = "MailChimp";
        r13 = new java.lang.StringBuilder;
        r14 = "Unable to find field named: ";
        r13.<init>(r14);
        r13 = r13.append(r6);
        r14 = " in class ";
        r13 = r13.append(r14);
        r14 = r16.getClass();
        r13 = r13.append(r14);
        r13 = r13.toString();
        android.util.Log.e(r11, r13);
        goto L_0x000c;
    L_0x00d8:
        r11 = java.lang.Integer.class;
        if (r9 != r11) goto L_0x0139;
    L_0x00dc:
        r8 = r4.getValue();	 Catch:{ ClassCastException -> 0x00f1, SecurityException -> 0x0054, NoSuchFieldException -> 0x00b1, IllegalArgumentException -> 0x0107, IllegalAccessException -> 0x0162, ParseException -> 0x019f }
        r8 = (java.lang.Number) r8;	 Catch:{ ClassCastException -> 0x00f1, SecurityException -> 0x0054, NoSuchFieldException -> 0x00b1, IllegalArgumentException -> 0x0107, IllegalAccessException -> 0x0162, ParseException -> 0x019f }
        r11 = r8.intValue();	 Catch:{ ClassCastException -> 0x00f1, SecurityException -> 0x0054, NoSuchFieldException -> 0x00b1, IllegalArgumentException -> 0x0107, IllegalAccessException -> 0x0162, ParseException -> 0x019f }
        r11 = java.lang.Integer.valueOf(r11);	 Catch:{ ClassCastException -> 0x00f1, SecurityException -> 0x0054, NoSuchFieldException -> 0x00b1, IllegalArgumentException -> 0x0107, IllegalAccessException -> 0x0162, ParseException -> 0x019f }
        r0 = r16;
        r5.set(r0, r11);	 Catch:{ ClassCastException -> 0x00f1, SecurityException -> 0x0054, NoSuchFieldException -> 0x00b1, IllegalArgumentException -> 0x0107, IllegalAccessException -> 0x0162, ParseException -> 0x019f }
        goto L_0x000c;
    L_0x00f1:
        r2 = move-exception;
        r11 = r4.getValue();	 Catch:{ SecurityException -> 0x0054, NoSuchFieldException -> 0x00b1, IllegalArgumentException -> 0x0107, IllegalAccessException -> 0x0162, ParseException -> 0x019f, ClassCastException -> 0x01dc }
        r11 = (java.lang.String) r11;	 Catch:{ SecurityException -> 0x0054, NoSuchFieldException -> 0x00b1, IllegalArgumentException -> 0x0107, IllegalAccessException -> 0x0162, ParseException -> 0x019f, ClassCastException -> 0x01dc }
        r11 = java.lang.Integer.parseInt(r11);	 Catch:{ SecurityException -> 0x0054, NoSuchFieldException -> 0x00b1, IllegalArgumentException -> 0x0107, IllegalAccessException -> 0x0162, ParseException -> 0x019f, ClassCastException -> 0x01dc }
        r11 = java.lang.Integer.valueOf(r11);	 Catch:{ SecurityException -> 0x0054, NoSuchFieldException -> 0x00b1, IllegalArgumentException -> 0x0107, IllegalAccessException -> 0x0162, ParseException -> 0x019f, ClassCastException -> 0x01dc }
        r0 = r16;
        r5.set(r0, r11);	 Catch:{ SecurityException -> 0x0054, NoSuchFieldException -> 0x00b1, IllegalArgumentException -> 0x0107, IllegalAccessException -> 0x0162, ParseException -> 0x019f, ClassCastException -> 0x01dc }
        goto L_0x000c;
    L_0x0107:
        r2 = move-exception;
        r11 = new rsg.mailchimp.api.MailChimpApiException;
        r12 = new java.lang.StringBuilder;
        r13 = "Couldn't translate values to ";
        r12.<init>(r13);
        r13 = r16.getClass();
        r12 = r12.append(r13);
        r13 = ", key: ";
        r12 = r12.append(r13);
        r12 = r12.append(r7);
        r13 = " causing IllegalArgumentException, value: ";
        r12 = r12.append(r13);
        r13 = r4.getValue();
        r12 = r12.append(r13);
        r12 = r12.toString();
        r11.<init>(r12, r2);
        throw r11;
    L_0x0139:
        r11 = java.lang.Double.class;
        if (r9 != r11) goto L_0x01d1;
    L_0x013d:
        r11 = r4.getValue();	 Catch:{ SecurityException -> 0x0054, NoSuchFieldException -> 0x00b1, IllegalArgumentException -> 0x0107, IllegalAccessException -> 0x0162, ParseException -> 0x019f, ClassCastException -> 0x01dc }
        r11 = r11.getClass();	 Catch:{ SecurityException -> 0x0054, NoSuchFieldException -> 0x00b1, IllegalArgumentException -> 0x0107, IllegalAccessException -> 0x0162, ParseException -> 0x019f, ClassCastException -> 0x01dc }
        r13 = java.lang.String.class;
        r11 = r11.equals(r13);	 Catch:{ SecurityException -> 0x0054, NoSuchFieldException -> 0x00b1, IllegalArgumentException -> 0x0107, IllegalAccessException -> 0x0162, ParseException -> 0x019f, ClassCastException -> 0x01dc }
        if (r11 == 0) goto L_0x0194;
    L_0x014d:
        r11 = r4.getValue();	 Catch:{ SecurityException -> 0x0054, NoSuchFieldException -> 0x00b1, IllegalArgumentException -> 0x0107, IllegalAccessException -> 0x0162, ParseException -> 0x019f, ClassCastException -> 0x01dc }
        r11 = (java.lang.String) r11;	 Catch:{ SecurityException -> 0x0054, NoSuchFieldException -> 0x00b1, IllegalArgumentException -> 0x0107, IllegalAccessException -> 0x0162, ParseException -> 0x019f, ClassCastException -> 0x01dc }
        r14 = java.lang.Double.parseDouble(r11);	 Catch:{ SecurityException -> 0x0054, NoSuchFieldException -> 0x00b1, IllegalArgumentException -> 0x0107, IllegalAccessException -> 0x0162, ParseException -> 0x019f, ClassCastException -> 0x01dc }
        r11 = java.lang.Double.valueOf(r14);	 Catch:{ SecurityException -> 0x0054, NoSuchFieldException -> 0x00b1, IllegalArgumentException -> 0x0107, IllegalAccessException -> 0x0162, ParseException -> 0x019f, ClassCastException -> 0x01dc }
        r0 = r16;
        r5.set(r0, r11);	 Catch:{ SecurityException -> 0x0054, NoSuchFieldException -> 0x00b1, IllegalArgumentException -> 0x0107, IllegalAccessException -> 0x0162, ParseException -> 0x019f, ClassCastException -> 0x01dc }
        goto L_0x000c;
    L_0x0162:
        r2 = move-exception;
        r11 = new rsg.mailchimp.api.MailChimpApiException;
        r12 = new java.lang.StringBuilder;
        r13 = "Couldn't translate values to ";
        r12.<init>(r13);
        r13 = r16.getClass();
        r12 = r12.append(r13);
        r13 = ", key: ";
        r12 = r12.append(r13);
        r12 = r12.append(r7);
        r13 = " causing IllegalAccessException, value: ";
        r12 = r12.append(r13);
        r13 = r4.getValue();
        r12 = r12.append(r13);
        r12 = r12.toString();
        r11.<init>(r12, r2);
        throw r11;
    L_0x0194:
        r11 = r4.getValue();	 Catch:{ SecurityException -> 0x0054, NoSuchFieldException -> 0x00b1, IllegalArgumentException -> 0x0107, IllegalAccessException -> 0x0162, ParseException -> 0x019f, ClassCastException -> 0x01dc }
        r0 = r16;
        r5.set(r0, r11);	 Catch:{ SecurityException -> 0x0054, NoSuchFieldException -> 0x00b1, IllegalArgumentException -> 0x0107, IllegalAccessException -> 0x0162, ParseException -> 0x019f, ClassCastException -> 0x01dc }
        goto L_0x000c;
    L_0x019f:
        r2 = move-exception;
        r11 = new rsg.mailchimp.api.MailChimpApiException;
        r12 = new java.lang.StringBuilder;
        r13 = "Couldn't translate values to ";
        r12.<init>(r13);
        r13 = r16.getClass();
        r12 = r12.append(r13);
        r13 = ", key: ";
        r12 = r12.append(r13);
        r12 = r12.append(r7);
        r13 = " causing parse failure, value that couldn't be parsed: ";
        r12 = r12.append(r13);
        r13 = r4.getValue();
        r12 = r12.append(r13);
        r12 = r12.toString();
        r11.<init>(r12, r2);
        throw r11;
    L_0x01d1:
        r11 = r4.getValue();	 Catch:{ SecurityException -> 0x0054, NoSuchFieldException -> 0x00b1, IllegalArgumentException -> 0x0107, IllegalAccessException -> 0x0162, ParseException -> 0x019f, ClassCastException -> 0x01dc }
        r0 = r16;
        r5.set(r0, r11);	 Catch:{ SecurityException -> 0x0054, NoSuchFieldException -> 0x00b1, IllegalArgumentException -> 0x0107, IllegalAccessException -> 0x0162, ParseException -> 0x019f, ClassCastException -> 0x01dc }
        goto L_0x000c;
    L_0x01dc:
        r2 = move-exception;
        r11 = new rsg.mailchimp.api.MailChimpApiException;
        r12 = new java.lang.StringBuilder;
        r13 = "Couldn't transalte values to ";
        r12.<init>(r13);
        r13 = r16.getClass();
        r12 = r12.append(r13);
        r13 = ", key: ";
        r12 = r12.append(r13);
        r12 = r12.append(r7);
        r13 = " is of type: ";
        r12 = r12.append(r13);
        r13 = r4.getValue();
        r13 = r13.getClass();
        r12 = r12.append(r13);
        r13 = " and field is of type: ";
        r12 = r12.append(r13);
        r12 = r12.append(r9);
        r12 = r12.toString();
        r11.<init>(r12);
        throw r11;
    L_0x021c:
        r11 = new rsg.mailchimp.api.MailChimpApiException;
        r12 = new java.lang.StringBuilder;
        r13 = "Couldn't translate values to ";
        r12.<init>(r13);
        r13 = r16.getClass();
        r12 = r12.append(r13);
        r13 = ", key: ";
        r12 = r12.append(r13);
        r12 = r12.append(r7);
        r13 = " field doens't exist: ";
        r12 = r12.append(r13);
        r12 = r12.append(r6);
        r12 = r12.toString();
        r11.<init>(r12, r2);
        throw r11;
        */
        throw new UnsupportedOperationException("Method not decompiled: rsg.mailchimp.api.Utils.populateObjectFromRPCStruct(java.lang.Object, java.util.Map, boolean, java.lang.String[]):void");
    }

    static boolean ignoreFieldName(String[] ignoreFieldNames, String fieldName) {
        for (String ignore : ignoreFieldNames) {
            if (ignore.equals(fieldName)) {
                return true;
            }
        }
        return false;
    }

    public static String translateFieldName(String key) {
        StringBuffer buf = new StringBuffer();
        int i = 0;
        while (i < key.length()) {
            char c = key.charAt(i);
            switch (c) {
                case '_':
                    buf.append(Character.toUpperCase(key.charAt(i + 1)));
                    i++;
                    break;
                default:
                    buf.append(c);
                    break;
            }
            i++;
        }
        return buf.toString();
    }

    public static final <T extends RPCStructConverter> ArrayList<T> extractObjectsFromList(Class<T> clazz, Object[] callResult) throws MailChimpApiException {
        Object[] callResultArray = callResult;
        ArrayList<T> retVal = new ArrayList(callResultArray.length);
        int length = callResultArray.length;
        int i = 0;
        while (i < length) {
            try {
                RPCStructConverter newInst = (RPCStructConverter) clazz.newInstance();
                newInst.populateFromRPCStruct(null, callResultArray[i]);
                retVal.add(newInst);
                i++;
            } catch (IllegalAccessException e) {
                throw new MailChimpApiException("Couldn't create instance of class (" + clazz.getName() + ") make sure it is publically accessible");
            } catch (InstantiationException e2) {
                throw new MailChimpApiException("Couldn't create instance of class (" + clazz.getName() + ") make sure it has a zero-args constructor");
            }
        }
        return retVal;
    }

    public static List<String> convertObjectArrayToString(Object[] array) {
        ArrayList<String> vals = new ArrayList(array.length);
        for (Object o : array) {
            vals.add(o.toString());
        }
        return vals;
    }
}
