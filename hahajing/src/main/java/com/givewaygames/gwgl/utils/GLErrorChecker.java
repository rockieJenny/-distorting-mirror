package com.givewaygames.gwgl.utils;

import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.opengl.GLES20;
import com.givewaygames.gwgl.CameraWrapper;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.io.Writer;
import javax.microedition.khronos.egl.EGL10;

public class GLErrorChecker {
    private static final boolean LOG_ONCE = true;
    private static boolean hasLogged = false;
    public static Throwable lastError = null;

    public interface ToastHelper {
        void makeText(Context context, int i, boolean z);
    }

    public static void setGLError(Throwable e) {
        lastError = e;
        hasLogged = true;
    }

    public static boolean checkGlError(String LOG_TAG) {
        int error = GLES20.glGetError();
        if (error != 0) {
            String errorStr = "GL error = 0x" + Integer.toHexString(error);
            CameraWrapper.logCrashlyticsValue(errorStr);
            if (!hasLogged) {
                hasLogged = true;
                lastError = new Exception(errorStr);
                CameraWrapper.logCrashlyticsValue("gl_error", "0x" + Integer.toHexString(error));
                if (Log.isW) {
                    Log.w(LOG_TAG, LOG_TAG + ": GL error = 0x" + Integer.toHexString(error), lastError);
                }
            }
        }
        if (error != 0) {
            return true;
        }
        return false;
    }

    public static boolean checkEglError(EGL10 mEgl, String LOG_TAG) {
        int error = mEgl.eglGetError();
        if (error != 12288) {
            String errorStr = "EGL error = 0x" + Integer.toHexString(error);
            CameraWrapper.logCrashlyticsValue(errorStr);
            if (!hasLogged) {
                hasLogged = true;
                lastError = new Exception(errorStr);
                CameraWrapper.logCrashlyticsValue("egl_error", "0x" + Integer.toHexString(error));
                if (Log.isW) {
                    Log.w(LOG_TAG, "EGL error = 0x" + Integer.toHexString(error), lastError);
                }
            }
        }
        if (error != 0) {
            return true;
        }
        return false;
    }

    public static void sendEmail(Context c, String appName, Throwable e, ToastHelper toastHelper, int noEmail) {
        String body = "{Insert any notes to developer here}";
        if (e != null) {
            Writer writer = new StringWriter();
            e.printStackTrace(new PrintWriter(writer));
            body = body + "\n\nThe following error occured:\n\n" + writer.toString();
        }
        Intent i = new Intent("android.intent.action.SEND");
        i.setType("text/plain");
        i.putExtra("android.intent.extra.EMAIL", new String[]{"givewaygames@gmail.com"});
        i.putExtra("android.intent.extra.SUBJECT", appName + " - Bug Report");
        i.putExtra("android.intent.extra.TEXT", body);
        try {
            c.startActivity(Intent.createChooser(i, "Send mail..."));
        } catch (ActivityNotFoundException e2) {
            toastHelper.makeText(c, noEmail, false);
        }
    }
}
