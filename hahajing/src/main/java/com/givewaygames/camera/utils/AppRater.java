package com.givewaygames.camera.utils;

import android.app.Dialog;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.net.Uri;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.givewaygames.goofyglass.R;

public class AppRater {
    private static final int DAYS_UNTIL_PROMPT = 3;
    private static final int LAUNCHES_UNTIL_PROMPT = 10;

    public static void app_launched(Context mContext) {
        SharedPreferences prefs = mContext.getSharedPreferences("apprater", 0);
        if (!prefs.getBoolean("dontshowagain", false)) {
            Editor editor = prefs.edit();
            long launch_count = prefs.getLong("launch_count", 0) + 1;
            editor.putLong("launch_count", launch_count);
            Long date_firstLaunch = Long.valueOf(prefs.getLong("date_firstlaunch", 0));
            if (date_firstLaunch.longValue() == 0) {
                date_firstLaunch = Long.valueOf(System.currentTimeMillis());
                editor.putLong("date_firstlaunch", date_firstLaunch.longValue());
            }
            if (launch_count >= 10 && System.currentTimeMillis() >= date_firstLaunch.longValue() + 259200000) {
                showRateDialog(mContext, editor);
            }
            editor.commit();
        }
    }

    public static void showRateDialog(Context mContext) {
        Editor editor = mContext.getSharedPreferences("apprater", 0).edit();
        showRateDialog(mContext, editor);
        editor.commit();
    }

    public static void showRateDialog(final Context mContext, final Editor editor) {
        final Dialog dialog = new Dialog(mContext);
        String appName = mContext.getString(R.string.app_name);
        dialog.setTitle("Rate " + appName);
        LinearLayout ll = new LinearLayout(mContext);
        ll.setOrientation(1);
        TextView tv = new TextView(mContext);
        tv.setText("If you enjoy using " + appName + ", please take a moment to rate it. Thanks for your support!");
        tv.setWidth(240);
        tv.setPadding(4, 0, 4, 10);
        ll.addView(tv);
        Button b1 = new Button(mContext);
        b1.setText("Rate " + appName);
        b1.setOnClickListener(new OnClickListener() {
            public void onClick(View v) {
                AppRater.goToRatingPage(mContext);
                if (editor != null) {
                    editor.putBoolean("dontshowagain", true);
                    editor.commit();
                }
                dialog.dismiss();
            }
        });
        ll.addView(b1);
        Button b2 = new Button(mContext);
        b2.setText("Remind me later");
        b2.setOnClickListener(new OnClickListener() {
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        ll.addView(b2);
        Button b3 = new Button(mContext);
        b3.setText("No, thanks");
        b3.setOnClickListener(new OnClickListener() {
            public void onClick(View v) {
                if (editor != null) {
                    editor.putBoolean("dontshowagain", true);
                    editor.commit();
                }
                dialog.dismiss();
            }
        });
        ll.addView(b3);
        dialog.setContentView(ll);
        dialog.show();
    }

    public static boolean hasBeenShown(Context c) {
        return c.getSharedPreferences("apprater", 0).getBoolean("dontshowagain", false);
    }

    public static void goToRatingPage(Context c) {
        goToApp(c, c.getPackageName());
        Editor editor = c.getSharedPreferences("apprater", 0).edit();
        if (editor != null) {
            editor.putBoolean("dontshowagain", true);
            editor.commit();
        }
    }

    public static void goToApp(Context c, String packageName) {
        try {
            c.startActivity(new Intent("android.intent.action.VIEW", Uri.parse((c.getResources().getBoolean(R.bool.amazon) ? "http://www.amazon.com/gp/mas/dl/android?p=" : "market://details?id=") + packageName)));
        } catch (ActivityNotFoundException e) {
            Toast.makeText(R.string.no_internet_permission, 0);
        }
    }
}
