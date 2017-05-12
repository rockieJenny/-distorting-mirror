package com.millennialmedia.android;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.os.Vibrator;
import java.util.Map;
import java.util.concurrent.Callable;

class BridgeMMNotification extends MMJSObject implements OnClickListener {
    private String ALERT = "alert";
    private String VIBRATE = "vibrate";
    private int index;

    BridgeMMNotification() {
    }

    MMJSResponse executeCommand(String name, Map<String, String> arguments) {
        if (this.ALERT.equals(name)) {
            return alert(arguments);
        }
        if (this.VIBRATE.equals(name)) {
            return vibrate(arguments);
        }
        return null;
    }

    public synchronized void onClick(DialogInterface dialog, int which) {
        if (which == -2) {
            this.index = 0;
        }
        if (which == -3) {
            this.index = 1;
        }
        if (which == -1) {
            this.index = 2;
        }
        dialog.cancel();
        notify();
    }

    public synchronized MMJSResponse alert(final Map<String, String> arguments) {
        return runOnUiThreadFuture(new Callable<MMJSResponse>() {
            public MMJSResponse call() {
                MMWebView mmWebView = (MMWebView) BridgeMMNotification.this.mmWebViewRef.get();
                if (mmWebView != null) {
                    Activity activity = mmWebView.getActivity();
                    Map<String, String> finalArguments = arguments;
                    if (activity != null) {
                        if (!activity.isFinishing()) {
                            AlertDialog alertDialog = new Builder(activity).create();
                            if (finalArguments.containsKey("title")) {
                                alertDialog.setTitle((CharSequence) finalArguments.get("title"));
                            }
                            if (finalArguments.containsKey("message")) {
                                alertDialog.setMessage((CharSequence) finalArguments.get("message"));
                            }
                            if (finalArguments.containsKey("cancelButton")) {
                                alertDialog.setButton(-2, (CharSequence) finalArguments.get("cancelButton"), BridgeMMNotification.this);
                            }
                            if (finalArguments.containsKey("buttons")) {
                                String[] buttons = ((String) finalArguments.get("buttons")).split(",");
                                if (buttons.length > 0) {
                                    alertDialog.setButton(-3, buttons[0], BridgeMMNotification.this);
                                }
                                if (buttons.length > 1) {
                                    alertDialog.setButton(-1, buttons[1], BridgeMMNotification.this);
                                }
                            }
                            alertDialog.show();
                        }
                        MMJSResponse response = new MMJSResponse();
                        response.result = 1;
                        response.response = Integer.valueOf(BridgeMMNotification.this.index);
                        return response;
                    }
                }
                return null;
            }
        });
    }

    public MMJSResponse vibrate(Map<String, String> arguments) {
        Context context = (Context) this.contextRef.get();
        long time = 0;
        if (arguments.containsKey("duration")) {
            time = (long) (((double) Float.parseFloat((String) arguments.get("duration"))) * 1000.0d);
        }
        if (context == null || time <= 0) {
            return null;
        }
        if (context.getPackageManager().checkPermission("android.permission.VIBRATE", context.getPackageName()) != 0) {
            return MMJSResponse.responseWithError("The required permissions to vibrate are not set.");
        }
        ((Vibrator) context.getSystemService("vibrator")).vibrate(time);
        return MMJSResponse.responseWithSuccess("Vibrating for " + time);
    }
}
