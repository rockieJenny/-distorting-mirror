package com.givewaygames.gwgl.shader;

import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import java.util.Random;

public interface IGLSetValue {
    int getCount();

    float getRangeHighValue();

    float getRangeLowValue();

    float getValueAt(int i);

    void loadValues(SharedPreferences sharedPreferences, String str);

    void saveValues(Editor editor, String str);

    void setRandomValue(Random random);

    void setValueAt(int i, float f);
}
