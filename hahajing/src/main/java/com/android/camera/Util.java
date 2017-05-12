package com.android.camera;

import android.content.ActivityNotFoundException;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.ParcelFileDescriptor;
import com.givewaygames.camera.utils.Toast;
import com.givewaygames.goofyglass.R;
import com.givewaygames.gwgl.utils.Log;
import java.io.IOException;

public class Util {
    public static final String REVIEW_ACTION = "com.android.camera.action.REVIEW";
    private static final String TAG = "Util";

    public static boolean isUriValid(Uri uri, ContentResolver resolver) {
        if (uri == null) {
            return false;
        }
        try {
            ParcelFileDescriptor pfd = resolver.openFileDescriptor(uri, "r");
            if (pfd == null) {
                Log.e(TAG, "Fail to open URI. URI=" + uri);
                return false;
            }
            pfd.close();
            return true;
        } catch (IOException e) {
            return false;
        }
    }

    public static void viewUri(Uri uri, Context context) {
        if (isUriValid(uri, context.getContentResolver())) {
            Intent intent;
            try {
                intent = new Intent(REVIEW_ACTION);
                intent.setDataAndType(uri, "image/jpeg");
                context.startActivity(intent);
                return;
            } catch (ActivityNotFoundException e) {
                try {
                    intent = new Intent("android.intent.action.VIEW");
                    intent.setDataAndType(uri, "image/jpeg");
                    context.startActivity(intent);
                    return;
                } catch (ActivityNotFoundException e2) {
                    Toast.makeText(R.string.no_internet_permission, 0);
                    return;
                }
            }
        }
        Log.e(TAG, "Uri invalid. uri=" + uri);
    }
}
