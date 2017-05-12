package com.givewaygames.gwgl.shader;

import android.content.Context;
import java.io.IOException;
import java.io.InputStream;

public class ShaderUtils {
    public static String assetToString(Context c, String assetName) {
        try {
            return streamToString(c.getAssets().open(assetName));
        } catch (IOException e) {
            return "";
        }
    }

    public static String rawToString(Context c, int res) {
        return streamToString(c.getResources().openRawResource(res));
    }

    public static String streamToString(InputStream is) {
        try {
            byte[] buffer = new byte[is.available()];
            is.read(buffer);
            is.close();
            return new String(buffer);
        } catch (IOException e) {
            return "";
        }
    }
}
