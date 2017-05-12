package com.givewaygames.camera.billing;

import android.app.Activity;
import android.app.PendingIntent;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.IntentSender.SendIntentException;
import android.content.ServiceConnection;
import android.content.pm.ResolveInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.IBinder;
import android.os.RemoteException;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.FragmentActivity;
import com.android.vending.billing.IInAppBillingService;
import com.android.vending.billing.IInAppBillingService.Stub;
import com.givewaygames.ads.SubscribeManager;
import com.givewaygames.ads.SubscribeManager.SubscribeListener;
import com.givewaygames.camera.activities.CameraFilterActivity;
import com.givewaygames.camera.utils.Toast;
import com.givewaygames.goofyglass.R;
import com.givewaygames.gwgl.utils.Log;
import com.inmobi.commons.analytics.db.AnalyticsEvent;
import com.inmobi.commons.analytics.db.AnalyticsSQLiteHelper;
import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import org.json.JSONException;
import org.json.JSONObject;

public class BillingWrapperV3 implements SubscribeListener {
    public static final int BILLING_RESPONSE_RESULT_OK = 0;
    public static final String DAS_CODEN = "a;lkj;3oa;a34A#K$JALKadfa3=-324ADFASDJ";
    public static final String FRAG_UNLOCK = "frag_unlock";
    public static final String TAG = "BillingWrapperV3";
    public static final String UNLOCK_ALL = "unlock_all";
    boolean canUnlock = false;
    boolean isSubscribed = false;
    boolean isUnlocked = false;
    IInAppBillingService mService;
    ServiceConnection mServiceConn = new ServiceConnection() {
        public void onServiceDisconnected(ComponentName name) {
            BillingWrapperV3.this.mService = null;
        }

        public void onServiceConnected(ComponentName name, IBinder service) {
            BillingWrapperV3.this.mService = Stub.asInterface(service);
            BillingWrapperV3.this.queryTask = new QueryBillingAsyncTask();
            BillingWrapperV3.this.queryTask.execute(new Void[0]);
        }
    };
    String price = "$1.49";
    QueryBillingAsyncTask queryTask = null;
    DialogFragment unlockFrag = null;
    WeakReference<CameraFilterActivity> weakContext;

    private class QueryBillingAsyncTask extends AsyncTask<Void, Void, Void> {
        protected Void doInBackground(Void... params) {
            Context context = (Context) BillingWrapperV3.this.weakContext.get();
            if (context != null) {
                try {
                    queryOwnedItems(context);
                } catch (RemoteException e) {
                    if (Log.isE) {
                        Log.e(BillingWrapperV3.TAG, "queryOwnedItems failure", e);
                    }
                } catch (Exception e2) {
                    if (Log.isE) {
                        Log.e(BillingWrapperV3.TAG, "queryOwnedItems failure", e2);
                    }
                }
                try {
                    if (!BillingWrapperV3.this.isUnlocked) {
                        queryAvailableItems(context);
                    }
                } catch (RemoteException e3) {
                    if (Log.isE) {
                        Log.e(BillingWrapperV3.TAG, "queryAvailableItems failure", e3);
                    }
                } catch (JSONException e4) {
                    if (Log.isE) {
                        Log.e(BillingWrapperV3.TAG, "queryAvailableItems failure", e4);
                    }
                } catch (Exception e22) {
                    if (Log.isE) {
                        Log.e(BillingWrapperV3.TAG, "queryAvailableItems failure", e22);
                    }
                }
            }
            return null;
        }

        private void queryAvailableItems(Context context) throws RemoteException, JSONException {
            if (Log.isD) {
                Log.d(BillingWrapperV3.TAG, "queryAvailableItems");
            }
            ArrayList<String> skuList = new ArrayList();
            skuList.add(BillingWrapperV3.UNLOCK_ALL);
            Bundle querySkus = new Bundle();
            querySkus.putStringArrayList("ITEM_ID_LIST", skuList);
            Bundle skuDetails = BillingWrapperV3.this.mService.getSkuDetails(3, context.getPackageName(), AnalyticsEvent.IN_APP, querySkus);
            if (skuDetails.getInt("RESPONSE_CODE") == 0) {
                Iterator i$ = skuDetails.getStringArrayList("DETAILS_LIST").iterator();
                while (i$.hasNext()) {
                    JSONObject object = new JSONObject((String) i$.next());
                    String sku = object.getString(AnalyticsSQLiteHelper.TRANSACTION_PRODUCT_ID);
                    String price = object.getString("price");
                    if (Log.isD) {
                        Log.d(BillingWrapperV3.TAG, "queryAvailableItems: found=" + sku + " @ " + price);
                    }
                    if (sku.equals(BillingWrapperV3.UNLOCK_ALL)) {
                        BillingWrapperV3.this.canUnlock = true;
                        BillingWrapperV3.this.price = price;
                    }
                }
            }
        }

        private void queryOwnedItems(Context context) throws RemoteException {
            if (Log.isD) {
                Log.d(BillingWrapperV3.TAG, "queryOwnedItems");
            }
            Bundle ownedItems = BillingWrapperV3.this.mService.getPurchases(3, context.getPackageName(), AnalyticsEvent.IN_APP, null);
            if (ownedItems.getInt("RESPONSE_CODE") == 0) {
                ArrayList<String> ownedSkus = ownedItems.getStringArrayList("INAPP_PURCHASE_ITEM_LIST");
                for (int i = 0; i < ownedSkus.size(); i++) {
                    if (((String) ownedSkus.get(i)).equals(BillingWrapperV3.UNLOCK_ALL)) {
                        if (Log.isD) {
                            Log.d(BillingWrapperV3.TAG, "queryOwnedItems: found unlocked");
                        }
                        BillingWrapperV3.this.isUnlocked = true;
                        BillingWrapperV3.this.canUnlock = false;
                    }
                }
            }
        }

        protected void onPostExecute(Void v) {
            super.onPostExecute(v);
            BillingWrapperV3.this.notifyActivityOfBillingSupported();
        }
    }

    public void onCreate(CameraFilterActivity activity) {
        if (!activity.getResources().getBoolean(R.bool.amazon)) {
            this.weakContext = new WeakReference(activity);
            try {
                Intent intent = getExplicitIapIntent(activity);
                if (intent != null) {
                    activity.bindService(intent, this.mServiceConn, 1);
                }
            } catch (Exception e) {
                if (Log.isE) {
                    Log.e(TAG, "Billing exception occurred", e);
                }
            }
            SubscribeManager subscribeManager = new SubscribeManager(activity, this);
            SubscribeManager.setActiveManager(subscribeManager);
            this.isSubscribed = subscribeManager.isPotentialSubscriber();
        } else if (Log.isD) {
            Log.d(TAG, "No billing on amazon.");
        }
    }

    private Intent getExplicitIapIntent(Context context) {
        List<ResolveInfo> resolveInfos = context.getPackageManager().queryIntentServices(new Intent("com.android.vending.billing.InAppBillingService.BIND"), 0);
        if (resolveInfos == null || resolveInfos.size() != 1) {
            return null;
        }
        ResolveInfo serviceInfo = (ResolveInfo) resolveInfos.get(0);
        ComponentName component = new ComponentName(serviceInfo.serviceInfo.packageName, serviceInfo.serviceInfo.name);
        Intent iapIntent = new Intent();
        iapIntent.setComponent(component);
        return iapIntent;
    }

    public void onDestroy(Context context) {
        if (this.mService != null) {
            context.unbindService(this.mServiceConn);
            this.mService = null;
        }
        if (this.queryTask != null) {
            this.queryTask.cancel(true);
            this.queryTask = null;
        }
    }

    public void unlockButtonPressed(FragmentActivity activity) {
        if (!activity.isFinishing()) {
            if (this.unlockFrag == null || !this.unlockFrag.isVisible()) {
                this.unlockFrag = new RemoveAdsFragment();
                this.unlockFrag.show(activity.getSupportFragmentManager(), FRAG_UNLOCK);
            }
        }
    }

    public void unlockAll(Activity activity) {
        try {
            activity.startIntentSenderForResult(((PendingIntent) this.mService.getBuyIntent(3, activity.getPackageName(), UNLOCK_ALL, AnalyticsEvent.IN_APP, DAS_CODEN).getParcelable("BUY_INTENT")).getIntentSender(), 2, new Intent(), Integer.valueOf(0).intValue(), Integer.valueOf(0).intValue(), Integer.valueOf(0).intValue());
        } catch (RemoteException e) {
            if (Log.isE) {
                Log.e(TAG, "buyIntentBundle failure", e);
            }
        } catch (SendIntentException e2) {
            if (Log.isE) {
                Log.e(TAG, "buyIntentBundle failure", e2);
            }
        } catch (Exception e3) {
            if (Log.isE) {
                Log.e(TAG, "buyIntentBundle failure", e3);
            }
        }
    }

    public void onActivityResult(CameraFilterActivity activity, int resultCode, Intent data) {
        int responseCode = data.getIntExtra("RESPONSE_CODE", 0);
        String purchaseData = data.getStringExtra("INAPP_PURCHASE_DATA");
        if (resultCode == -1 && responseCode == 0) {
            try {
                JSONObject jo = new JSONObject(purchaseData);
                String sku = jo.getString(AnalyticsSQLiteHelper.TRANSACTION_PRODUCT_ID);
                String payload = jo.getString("developerPayload");
                if (UNLOCK_ALL.equals(sku) && DAS_CODEN.equals(payload)) {
                    if (Log.isE) {
                        Log.e(TAG, "Congrats, you are now unlocked.");
                    }
                    this.isUnlocked = true;
                    this.canUnlock = false;
                    notifyActivityOfBillingSupported();
                }
            } catch (JSONException e) {
                if (Log.isE) {
                    Log.e(TAG, "Failed to parse purchase data", e);
                }
            }
        }
    }

    public void onUserSubscribed() {
        this.isSubscribed = true;
        notifyActivityOfBillingSupported();
    }

    public void onSubscribeFailed() {
        Toast.makeText(R.string.subscribe_failed, 1);
    }

    public void onUserUnsubscribed() {
        this.isSubscribed = false;
        notifyActivityOfBillingSupported();
    }

    private void notifyActivityOfBillingSupported() {
        final CameraFilterActivity activity = (CameraFilterActivity) this.weakContext.get();
        if (activity != null) {
            activity.runOnUiThread(new Runnable() {
                public void run() {
                    boolean beUnlocked = BillingWrapperV3.this.isUnlocked || BillingWrapperV3.this.isSubscribed;
                    activity.setBillingSupported(beUnlocked, BillingWrapperV3.this.canUnlock);
                }
            });
        }
    }
}
