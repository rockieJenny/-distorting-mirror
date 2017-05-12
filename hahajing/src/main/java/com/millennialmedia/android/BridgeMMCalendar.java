package com.millennialmedia.android;

import java.text.SimpleDateFormat;
import java.util.Map;
import org.apache.http.impl.cookie.DateParseException;
import org.apache.http.impl.cookie.DateUtils;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

class BridgeMMCalendar extends MMJSObject {
    private static final String ADD_EVENT = "addEvent";
    private static final String TAG = BridgeMMCalendar.class.getName();
    private static final String[] mraidCreateCalendarEventDateFormats = new String[]{"yyyy-MM-dd'T'HH:mmZZZ", "yyyy-MM-dd'T'HH:mm:ssZZZ"};
    private static final SimpleDateFormat rruleUntilDateFormat = new SimpleDateFormat("yyyyMMdd'T'HHmmss");

    BridgeMMCalendar() {
    }

    /* JADX WARNING: inconsistent code. */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    @android.annotation.TargetApi(14)
    public com.millennialmedia.android.MMJSResponse addEvent(java.util.Map<java.lang.String, java.lang.String> r25) {
        /*
        r24 = this;
        r20 = TAG;
        r21 = new java.lang.StringBuilder;
        r21.<init>();
        r22 = "addEvent parameters: ";
        r21 = r21.append(r22);
        r0 = r21;
        r1 = r25;
        r21 = r0.append(r1);
        r21 = r21.toString();
        com.millennialmedia.android.MMLog.d(r20, r21);
        r20 = android.os.Build.VERSION.SDK_INT;
        r21 = 14;
        r0 = r20;
        r1 = r21;
        if (r0 < r1) goto L_0x0210;
    L_0x0026:
        if (r25 == 0) goto L_0x020c;
    L_0x0028:
        r20 = "parameters";
        r0 = r25;
        r1 = r20;
        r20 = r0.get(r1);
        if (r20 == 0) goto L_0x020c;
    L_0x0034:
        r7 = 0;
        r12 = 0;
        r16 = 0;
        r9 = 0;
        r18 = 0;
        r17 = 0;
        r15 = 0;
        r14 = 0;
        r19 = 0;
        r11 = new org.json.JSONObject;	 Catch:{ JSONException -> 0x00f9 }
        r20 = "parameters";
        r0 = r25;
        r1 = r20;
        r20 = r0.get(r1);	 Catch:{ JSONException -> 0x00f9 }
        r20 = (java.lang.String) r20;	 Catch:{ JSONException -> 0x00f9 }
        r0 = r20;
        r11.<init>(r0);	 Catch:{ JSONException -> 0x00f9 }
        r20 = "description";
        r0 = r20;
        r18 = r11.getString(r0);	 Catch:{ JSONException -> 0x00ef }
    L_0x005c:
        r20 = "summary";
        r0 = r20;
        r7 = r11.getString(r0);	 Catch:{ JSONException -> 0x0108 }
    L_0x0064:
        r20 = "transparency";
        r0 = r20;
        r19 = r11.getString(r0);	 Catch:{ JSONException -> 0x0112 }
    L_0x006c:
        r20 = "reminder";
        r0 = r20;
        r15 = r11.getString(r0);	 Catch:{ JSONException -> 0x011c }
    L_0x0074:
        r20 = "location";
        r0 = r20;
        r12 = r11.getString(r0);	 Catch:{ JSONException -> 0x0126 }
    L_0x007c:
        r20 = "status";
        r0 = r20;
        r17 = r11.getString(r0);	 Catch:{ JSONException -> 0x0130 }
    L_0x0084:
        r20 = "recurrence";
        r0 = r20;
        r13 = r11.getJSONObject(r0);	 Catch:{ JSONException -> 0x013a }
        r0 = r24;
        r14 = r0.convertRecurrence(r13);	 Catch:{ JSONException -> 0x013a }
    L_0x0092:
        r20 = "start";
        r0 = r20;
        r20 = r11.getString(r0);	 Catch:{ DateParseException -> 0x0144, JSONException -> 0x014e }
        r21 = mraidCreateCalendarEventDateFormats;	 Catch:{ DateParseException -> 0x0144, JSONException -> 0x014e }
        r16 = org.apache.http.impl.cookie.DateUtils.parseDate(r20, r21);	 Catch:{ DateParseException -> 0x0144, JSONException -> 0x014e }
    L_0x00a0:
        r20 = "end";
        r0 = r20;
        r20 = r11.getString(r0);	 Catch:{ DateParseException -> 0x0158, JSONException -> 0x0162 }
        r21 = mraidCreateCalendarEventDateFormats;	 Catch:{ DateParseException -> 0x0158, JSONException -> 0x0162 }
        r9 = org.apache.http.impl.cookie.DateUtils.parseDate(r20, r21);	 Catch:{ DateParseException -> 0x0158, JSONException -> 0x0162 }
    L_0x00ae:
        r20 = TAG;	 Catch:{ JSONException -> 0x00f9 }
        r21 = "Creating calendar event: title: %s, location: %s, start: %s, end: %s, status: %s, summary: %s, rrule: %s";
        r22 = 7;
        r0 = r22;
        r0 = new java.lang.Object[r0];	 Catch:{ JSONException -> 0x00f9 }
        r22 = r0;
        r23 = 0;
        r22[r23] = r18;	 Catch:{ JSONException -> 0x00f9 }
        r23 = 1;
        r22[r23] = r12;	 Catch:{ JSONException -> 0x00f9 }
        r23 = 2;
        r22[r23] = r16;	 Catch:{ JSONException -> 0x00f9 }
        r23 = 3;
        r22[r23] = r9;	 Catch:{ JSONException -> 0x00f9 }
        r23 = 4;
        r22[r23] = r17;	 Catch:{ JSONException -> 0x00f9 }
        r23 = 5;
        r22[r23] = r7;	 Catch:{ JSONException -> 0x00f9 }
        r23 = 6;
        r22[r23] = r14;	 Catch:{ JSONException -> 0x00f9 }
        r21 = java.lang.String.format(r21, r22);	 Catch:{ JSONException -> 0x00f9 }
        com.millennialmedia.android.MMLog.d(r20, r21);	 Catch:{ JSONException -> 0x00f9 }
        if (r18 == 0) goto L_0x00e1;
    L_0x00df:
        if (r16 != 0) goto L_0x016c;
    L_0x00e1:
        r20 = TAG;
        r21 = "Description and start must be provided to create calendar event.";
        com.millennialmedia.android.MMLog.e(r20, r21);
        r20 = "Calendar Event Creation Failed.  Minimum parameters not provided";
        r20 = com.millennialmedia.android.MMJSResponse.responseWithError(r20);
    L_0x00ee:
        return r20;
    L_0x00ef:
        r8 = move-exception;
        r20 = TAG;	 Catch:{ JSONException -> 0x00f9 }
        r21 = "Unable to get calendar event description";
        com.millennialmedia.android.MMLog.e(r20, r21);	 Catch:{ JSONException -> 0x00f9 }
        goto L_0x005c;
    L_0x00f9:
        r8 = move-exception;
        r20 = TAG;
        r21 = "Unable to parse calendar addEvent parameters";
        com.millennialmedia.android.MMLog.e(r20, r21);
        r20 = "Calendar Event Creation Failed.  Invalid parameters";
        r20 = com.millennialmedia.android.MMJSResponse.responseWithError(r20);
        goto L_0x00ee;
    L_0x0108:
        r8 = move-exception;
        r20 = TAG;	 Catch:{ JSONException -> 0x00f9 }
        r21 = "Unable to get calendar event description";
        com.millennialmedia.android.MMLog.d(r20, r21);	 Catch:{ JSONException -> 0x00f9 }
        goto L_0x0064;
    L_0x0112:
        r8 = move-exception;
        r20 = TAG;	 Catch:{ JSONException -> 0x00f9 }
        r21 = "Unable to get calendar event transparency";
        com.millennialmedia.android.MMLog.d(r20, r21);	 Catch:{ JSONException -> 0x00f9 }
        goto L_0x006c;
    L_0x011c:
        r8 = move-exception;
        r20 = TAG;	 Catch:{ JSONException -> 0x00f9 }
        r21 = "Unable to get calendar event reminder";
        com.millennialmedia.android.MMLog.d(r20, r21);	 Catch:{ JSONException -> 0x00f9 }
        goto L_0x0074;
    L_0x0126:
        r8 = move-exception;
        r20 = TAG;	 Catch:{ JSONException -> 0x00f9 }
        r21 = "Unable to get calendar event location";
        com.millennialmedia.android.MMLog.d(r20, r21);	 Catch:{ JSONException -> 0x00f9 }
        goto L_0x007c;
    L_0x0130:
        r8 = move-exception;
        r20 = TAG;	 Catch:{ JSONException -> 0x00f9 }
        r21 = "Unable to get calendar event status";
        com.millennialmedia.android.MMLog.d(r20, r21);	 Catch:{ JSONException -> 0x00f9 }
        goto L_0x0084;
    L_0x013a:
        r8 = move-exception;
        r20 = TAG;	 Catch:{ JSONException -> 0x00f9 }
        r21 = "Unable to get calendar event recurrence";
        com.millennialmedia.android.MMLog.d(r20, r21);	 Catch:{ JSONException -> 0x00f9 }
        goto L_0x0092;
    L_0x0144:
        r8 = move-exception;
        r20 = TAG;	 Catch:{ JSONException -> 0x00f9 }
        r21 = "Error parsing calendar event start date";
        com.millennialmedia.android.MMLog.e(r20, r21);	 Catch:{ JSONException -> 0x00f9 }
        goto L_0x00a0;
    L_0x014e:
        r8 = move-exception;
        r20 = TAG;	 Catch:{ JSONException -> 0x00f9 }
        r21 = "Unable to get calendar event start date";
        com.millennialmedia.android.MMLog.e(r20, r21);	 Catch:{ JSONException -> 0x00f9 }
        goto L_0x00a0;
    L_0x0158:
        r8 = move-exception;
        r20 = TAG;	 Catch:{ JSONException -> 0x00f9 }
        r21 = "Error parsing calendar event end date";
        com.millennialmedia.android.MMLog.e(r20, r21);	 Catch:{ JSONException -> 0x00f9 }
        goto L_0x00ae;
    L_0x0162:
        r8 = move-exception;
        r20 = TAG;	 Catch:{ JSONException -> 0x00f9 }
        r21 = "Unable to get calendar event end date";
        com.millennialmedia.android.MMLog.d(r20, r21);	 Catch:{ JSONException -> 0x00f9 }
        goto L_0x00ae;
    L_0x016c:
        r20 = new android.content.Intent;
        r21 = "android.intent.action.INSERT";
        r20.<init>(r21);
        r21 = android.provider.CalendarContract.Events.CONTENT_URI;
        r10 = r20.setData(r21);
        if (r16 == 0) goto L_0x0188;
    L_0x017b:
        r20 = "beginTime";
        r22 = r16.getTime();
        r0 = r20;
        r1 = r22;
        r10.putExtra(r0, r1);
    L_0x0188:
        if (r9 == 0) goto L_0x0197;
    L_0x018a:
        r20 = "endTime";
        r22 = r9.getTime();
        r0 = r20;
        r1 = r22;
        r10.putExtra(r0, r1);
    L_0x0197:
        if (r18 == 0) goto L_0x01a2;
    L_0x0199:
        r20 = "title";
        r0 = r20;
        r1 = r18;
        r10.putExtra(r0, r1);
    L_0x01a2:
        if (r7 == 0) goto L_0x01ab;
    L_0x01a4:
        r20 = "description";
        r0 = r20;
        r10.putExtra(r0, r7);
    L_0x01ab:
        if (r12 == 0) goto L_0x01b4;
    L_0x01ad:
        r20 = "eventLocation";
        r0 = r20;
        r10.putExtra(r0, r12);
    L_0x01b4:
        if (r14 == 0) goto L_0x01bd;
    L_0x01b6:
        r20 = "rrule";
        r0 = r20;
        r10.putExtra(r0, r14);
    L_0x01bd:
        if (r17 == 0) goto L_0x01c6;
    L_0x01bf:
        r20 = TAG;
        r21 = "Calendar addEvent does not support status";
        com.millennialmedia.android.MMLog.w(r20, r21);
    L_0x01c6:
        if (r19 == 0) goto L_0x01cf;
    L_0x01c8:
        r20 = TAG;
        r21 = "Calendar addEvent does not support transparency";
        com.millennialmedia.android.MMLog.w(r20, r21);
    L_0x01cf:
        if (r15 == 0) goto L_0x01d8;
    L_0x01d1:
        r20 = TAG;
        r21 = "Calendar addEvent does not support reminder";
        com.millennialmedia.android.MMLog.w(r20, r21);
    L_0x01d8:
        r0 = r24;
        r0 = r0.contextRef;
        r20 = r0;
        r6 = r20.get();
        r6 = (android.content.Context) r6;
        if (r6 == 0) goto L_0x020c;
    L_0x01e6:
        com.millennialmedia.android.Utils.IntentUtils.startActivity(r6, r10);
        r20 = "PROPERTY_EXPANDING";
        r0 = r25;
        r1 = r20;
        r20 = r0.get(r1);
        r20 = (java.lang.String) r20;
        r0 = r24;
        r1 = r20;
        r4 = r0.getAdImplId(r1);
        r20 = "calendar";
        r0 = r20;
        com.millennialmedia.android.MMSDK.Event.intentStarted(r6, r0, r4);
        r20 = "Calendar Event Created";
        r20 = com.millennialmedia.android.MMJSResponse.responseWithSuccess(r20);
        goto L_0x00ee;
    L_0x020c:
        r20 = 0;
        goto L_0x00ee;
    L_0x0210:
        r20 = "Not supported";
        r20 = com.millennialmedia.android.MMJSResponse.responseWithError(r20);
        goto L_0x00ee;
        */
        throw new UnsupportedOperationException("Method not decompiled: com.millennialmedia.android.BridgeMMCalendar.addEvent(java.util.Map):com.millennialmedia.android.MMJSResponse");
    }

    private String convertRecurrence(JSONObject recurrence) {
        StringBuilder rrule = new StringBuilder();
        try {
            rrule.append("FREQ=").append(recurrence.getString("frequency")).append(";");
        } catch (JSONException e) {
            MMLog.d(TAG, "Unable to get calendar event recurrence frequency");
        }
        try {
            rrule.append("UNTIL=").append(rruleUntilDateFormat.format(DateUtils.parseDate(recurrence.getString("expires"), mraidCreateCalendarEventDateFormats))).append(";");
        } catch (DateParseException e2) {
            MMLog.e(TAG, "Error parsing calendar event recurrence expiration date");
        } catch (JSONException e3) {
            MMLog.d(TAG, "Unable to get calendar event recurrence expiration date");
        }
        try {
            JSONArray mraidDaysInWeek = recurrence.getJSONArray("daysInWeek");
            StringBuilder daysInWeek = new StringBuilder();
            for (int i = 0; i < mraidDaysInWeek.length(); i++) {
                daysInWeek.append(convertMraidDayToRRuleDay(mraidDaysInWeek.getInt(i))).append(",");
            }
            rrule.append("BYDAY=").append(daysInWeek).append(";");
        } catch (JSONException e4) {
            MMLog.d(TAG, "Unable to get days in week");
        }
        try {
            rrule.append("BYMONTHDAY=").append(recurrence.getString("daysInMonth").replaceAll("\\[", "").replaceAll("\\]", "")).append(";");
        } catch (JSONException e5) {
            MMLog.d(TAG, "Unable to get days in month");
        }
        try {
            rrule.append("BYMONTH=").append(recurrence.getString("monthsInYear:").replaceAll("\\[", "").replaceAll("\\]", "")).append(";");
        } catch (JSONException e6) {
            MMLog.d(TAG, "Unable to get months in year:");
        }
        try {
            rrule.append("BYYEARDAY=").append(recurrence.getString("daysInYear")).append(";");
        } catch (JSONException e7) {
            MMLog.d(TAG, "Unable to get days in year");
        }
        return rrule.toString().toUpperCase();
    }

    private String convertMraidDayToRRuleDay(int mraidDay) {
        switch (mraidDay) {
            case 1:
                return "MO";
            case 2:
                return "TU";
            case 3:
                return "WE";
            case 4:
                return "TH";
            case 5:
                return "FR";
            case 6:
                return "SA";
            case 7:
                return "SU";
            default:
                return null;
        }
    }

    MMJSResponse executeCommand(String name, Map<String, String> arguments) {
        if (ADD_EVENT.equals(name)) {
            return addEvent(arguments);
        }
        return null;
    }
}
