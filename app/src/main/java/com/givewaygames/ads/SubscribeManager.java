package com.givewaygames.ads;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.util.Log;
import org.xmlrpc.android.XMLRPCFault;
import rsg.mailchimp.api.Constants.EmailType;
import rsg.mailchimp.api.MailChimpApiException;
import rsg.mailchimp.api.lists.ListMethods;
import rsg.mailchimp.api.lists.MergeFieldListUtil;

public class SubscribeManager {
    private static final String API_KEY = "11c202d26a25e8ea589984e9c2330731-us8";
    public static final String FRAG_MAILING_LIST_SIGNUP = "frag_mailing_list_signup";
    private static final String GIVEWAY_LIST_ID = "ac51952b44";
    public static final String PREF_EMAIL_FOR_SUBSCRIPTION = "email_for_subscription";
    public static final String PREF_IS_SUBSCRIBED = "is_subscribed";
    public static final String TAG = "SubscribeManager";
    public static SubscribeManager subscribeManager;
    String email;
    boolean isKnownSubscriber = false;
    boolean isPotentialSubscriber = false;
    SharedPreferences prefs;
    SubscribeListener subscribeListener;

    private class RefreshSubscriberStatus extends Thread {
        private RefreshSubscriberStatus() {
        }

        public void run() {
            if (SubscribeManager.this.email != null && !SubscribeManager.this.email.isEmpty()) {
                subscribe(SubscribeManager.this.email);
            } else if (SubscribeManager.this.isPotentialSubscriber) {
                SubscribeManager.this.setIsSubscriber(false);
            }
        }

        private void subscribe(String email) {
            try {
                SubscribeManager.this.setIsSubscriber(new ListMethods(SubscribeManager.API_KEY).listsForEmail(email).contains(SubscribeManager.GIVEWAY_LIST_ID));
            } catch (MailChimpApiException e) {
                if (e.getCause() instanceof XMLRPCFault) {
                    int code = ((XMLRPCFault) e.getCause()).getFaultCode();
                    if (code == 232 || code == 215) {
                        SubscribeManager.this.setIsSubscriber(false);
                    }
                }
            }
        }
    }

    public interface SubscribeListener {
        void onSubscribeFailed();

        void onUserSubscribed();

        void onUserUnsubscribed();
    }

    private class SubscribeThread extends Thread {
        final String email;
        final String firstName;
        final String lastName;

        public SubscribeThread(String email, String firstName, String lastName) {
            this.email = email;
            this.firstName = firstName;
            this.lastName = lastName;
        }

        public void run() {
            ListMethods listMethods = new ListMethods(SubscribeManager.API_KEY);
            try {
                MergeFieldListUtil merge = new MergeFieldListUtil();
                merge.addEmail(this.email);
                merge.addField("FNAME", this.firstName);
                merge.addField("LNAME", this.lastName);
                listMethods.listSubscribe(SubscribeManager.GIVEWAY_LIST_ID, this.email, merge, EmailType.html, Boolean.valueOf(true), Boolean.valueOf(false), Boolean.valueOf(true), Boolean.valueOf(false));
            } catch (MailChimpApiException e) {
                boolean isError = true;
                if ((e.getCause() instanceof XMLRPCFault) && ((XMLRPCFault) e.getCause()).getFaultCode() == 214) {
                    SubscribeManager.this.setIsSubscriber(true);
                    isError = false;
                }
                if (isError && SubscribeManager.this.subscribeListener != null) {
                    SubscribeManager.this.subscribeListener.onSubscribeFailed();
                }
            }
        }
    }

    public static SubscribeManager getActiveManager() {
        return subscribeManager;
    }

    public static void setActiveManager(SubscribeManager activeManager) {
        subscribeManager = activeManager;
    }

    public SubscribeManager(Context context, SubscribeListener subscribeListener) {
        this.prefs = PreferenceManager.getDefaultSharedPreferences(context);
        this.email = this.prefs.getString(PREF_EMAIL_FOR_SUBSCRIPTION, null);
        this.isPotentialSubscriber = this.prefs.getBoolean(PREF_IS_SUBSCRIBED, false);
        this.subscribeListener = subscribeListener;
    }

    public boolean canShowSubscriberDialog() {
        return true;
    }

    public boolean isPotentialSubscriber() {
        return this.isPotentialSubscriber;
    }

    public boolean isSubscriber() {
        return this.isKnownSubscriber;
    }

    private void setIsSubscriber(boolean isSubscriber) {
        Log.v(TAG, "setIsSubscriber: " + isSubscriber);
        this.isKnownSubscriber = isSubscriber;
        this.isPotentialSubscriber = isSubscriber;
        this.prefs.edit().putBoolean(PREF_IS_SUBSCRIBED, isSubscriber).apply();
        if (this.subscribeListener == null) {
            return;
        }
        if (isSubscriber) {
            this.subscribeListener.onUserSubscribed();
        } else {
            this.subscribeListener.onUserUnsubscribed();
        }
    }

    public void refresh() {
        new RefreshSubscriberStatus().start();
    }

    public SubscribeThread subscribe(String email, String firstName, String lastName) {
        this.email = email;
        this.prefs.edit().putString(PREF_EMAIL_FOR_SUBSCRIPTION, email).apply();
        SubscribeThread thread = new SubscribeThread(email, firstName, lastName);
        thread.start();
        return thread;
    }
}
