package com.inmobi.commons.analytics.db;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import com.google.android.gms.plus.PlusShare;
import com.inmobi.commons.analytics.db.AnalyticsEvent.TRANSACTION_ITEM_TYPE;
import com.inmobi.commons.analytics.db.AnalyticsEvent.TRANSACTION_STATUS_GOOGLE_API_VALUES;
import com.inmobi.commons.analytics.db.AnalyticsEvent.TRANSACTION_STATUS_SERVER_CODE;
import com.inmobi.commons.analytics.util.AnalyticsUtils;
import com.inmobi.commons.analytics.util.SessionInfo;
import com.inmobi.commons.internal.Log;
import java.util.ArrayList;
import org.json.JSONObject;

public class FunctionTagTransaction extends AnalyticsFunctions {
    private Context a;
    private Intent b;
    private Bundle c;

    public FunctionTagTransaction(Context context, Intent intent, Bundle bundle) {
        this.a = context;
        this.b = intent;
        this.c = bundle;
    }

    private AnalyticsEvent a() {
        AnalyticsEvent a;
        Throwable e;
        try {
            if (!(SessionInfo.getSessionId(this.a) == null || this.b == null)) {
                AnalyticsEvent analyticsEvent = new AnalyticsEvent(AnalyticsEvent.TYPE_TAG_TRANSACTION);
                int intExtra = this.b.getIntExtra("RESPONSE_CODE", 0);
                String stringExtra = this.b.getStringExtra("INAPP_PURCHASE_DATA");
                if (intExtra != 0) {
                    return analyticsEvent;
                }
                try {
                    JSONObject jSONObject = new JSONObject(stringExtra);
                    analyticsEvent.setTransactionId(jSONObject.getString("orderId"));
                    analyticsEvent.setTransactionProductId(jSONObject.getString(AnalyticsSQLiteHelper.TRANSACTION_PRODUCT_ID));
                    long j = jSONObject.getLong("purchaseTime");
                    if (0 == j) {
                        j = System.currentTimeMillis();
                    }
                    analyticsEvent.setEventTimeStamp(j / 1000);
                    intExtra = jSONObject.getInt("purchaseState");
                    if (TRANSACTION_STATUS_GOOGLE_API_VALUES.PURCHASED.getValue() == intExtra) {
                        intExtra = TRANSACTION_STATUS_SERVER_CODE.PURCHASED.getValue();
                    } else if (TRANSACTION_STATUS_GOOGLE_API_VALUES.FAILED.getValue() == intExtra) {
                        intExtra = TRANSACTION_STATUS_SERVER_CODE.FAILED.getValue();
                    } else if (TRANSACTION_STATUS_GOOGLE_API_VALUES.REFUNDED.getValue() == intExtra) {
                        intExtra = TRANSACTION_STATUS_SERVER_CODE.REFUNDED.getValue();
                    } else {
                        intExtra = TRANSACTION_STATUS_SERVER_CODE.RESTORED.getValue();
                    }
                    analyticsEvent.setTransactionStatus(intExtra);
                    try {
                        a = a(analyticsEvent);
                        try {
                            a.setEventSessionId(SessionInfo.getSessionId(this.a));
                            a.setEventSessionTimeStamp(SessionInfo.getSessionTime(this.a));
                            analyticsEvent = a;
                        } catch (Exception e2) {
                            e = e2;
                            Log.debug(AnalyticsUtils.ANALYTICS_LOGGING_TAG, "Error sending transaction info. Bundle details invalid");
                            Log.internal(AnalyticsUtils.ANALYTICS_LOGGING_TAG, "Failed to send extra params transaction", e);
                            analyticsEvent = a;
                            insertInDatabase(analyticsEvent);
                            return analyticsEvent;
                        }
                    } catch (Throwable e3) {
                        Throwable th = e3;
                        a = analyticsEvent;
                        e = th;
                        Log.debug(AnalyticsUtils.ANALYTICS_LOGGING_TAG, "Error sending transaction info. Bundle details invalid");
                        Log.internal(AnalyticsUtils.ANALYTICS_LOGGING_TAG, "Failed to send extra params transaction", e);
                        analyticsEvent = a;
                        insertInDatabase(analyticsEvent);
                        return analyticsEvent;
                    }
                    insertInDatabase(analyticsEvent);
                    return analyticsEvent;
                } catch (Throwable e4) {
                    Log.debug(AnalyticsUtils.ANALYTICS_LOGGING_TAG, "Error sending transaction info. Transaction details invalid");
                    Log.internal(AnalyticsUtils.ANALYTICS_LOGGING_TAG, "Failed transaction", e4);
                    return null;
                }
            }
        } catch (Throwable e42) {
            Log.internal(AnalyticsUtils.ANALYTICS_LOGGING_TAG, "Exception in tag transaction", e42);
        }
        return null;
    }

    private AnalyticsEvent a(AnalyticsEvent analyticsEvent) {
        try {
            if (this.c.getInt("RESPONSE_CODE") == 0) {
                ArrayList stringArrayList = this.c.getStringArrayList("DETAILS_LIST");
                int i = 0;
                while (i < stringArrayList.size()) {
                    JSONObject jSONObject = new JSONObject((String) stringArrayList.get(i));
                    String string = jSONObject.getString(AnalyticsSQLiteHelper.TRANSACTION_PRODUCT_ID);
                    long j = jSONObject.getLong("price_amount_micros");
                    String string2 = jSONObject.getString("price_currency_code");
                    String optString = jSONObject.optString(AnalyticsSQLiteHelper.EVENT_LIST_TYPE);
                    String optString2 = jSONObject.optString("title");
                    String optString3 = jSONObject.optString(PlusShare.KEY_CONTENT_DEEP_LINK_METADATA_DESCRIPTION);
                    if (string.equals(analyticsEvent.getTransactionProductId())) {
                        analyticsEvent.setTransactionItemCount(1);
                        analyticsEvent.setTransactionItemDescription(optString3);
                        analyticsEvent.setTransactionItemName(optString2);
                        if (AnalyticsEvent.IN_APP.equals(optString)) {
                            analyticsEvent.setTransactionItemType(TRANSACTION_ITEM_TYPE.INAPP.getValue());
                        } else if (AnalyticsEvent.SUBS.equals(optString)) {
                            analyticsEvent.setTransactionItemType(TRANSACTION_ITEM_TYPE.SUBSCRIPTION.getValue());
                        }
                        analyticsEvent.setTransactionItemPrice(((double) j) / 1000000.0d);
                        analyticsEvent.setTransactionCurrencyCode(string2);
                    } else {
                        i++;
                    }
                }
            }
        } catch (Throwable e) {
            Log.internal(AnalyticsUtils.ANALYTICS_LOGGING_TAG, "Failed to set purchase bundle info", e);
        }
        return analyticsEvent;
    }

    public AnalyticsEvent processFunction() {
        return a();
    }
}
