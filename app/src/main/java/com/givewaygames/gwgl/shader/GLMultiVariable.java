package com.givewaygames.gwgl.shader;

import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Random;

public class GLMultiVariable implements IGLSetValue {
    float high = 0.0f;
    float low = 0.0f;
    ArrayList<Integer> map = new ArrayList();
    ArrayList<Float> scale = new ArrayList();
    ArrayList<IGLSetValue> values = new ArrayList();

    public void addVariable(IGLSetValue variable, int indexTo, float scale) {
        this.values.add(variable);
        this.map.add(Integer.valueOf(indexTo));
        this.scale.add(Float.valueOf(scale));
    }

    public void setValueAt(int idx, float value) {
        int i = 0;
        Iterator i$ = this.values.iterator();
        while (i$.hasNext()) {
            ((IGLSetValue) i$.next()).setValueAt(((Integer) this.map.get(i)).intValue(), value * ((Float) this.scale.get(i)).floatValue());
            i++;
        }
    }

    public float getValueAt(int idx) {
        throw new RuntimeException("Unsupported operation");
    }

    public float getVariableValue(int variable) {
        return ((IGLSetValue) this.values.get(variable)).getValueAt(((Integer) this.map.get(variable)).intValue());
    }

    public void saveValues(Editor sharedPreferences, String prefix) {
        Iterator i$ = this.values.iterator();
        while (i$.hasNext()) {
            ((IGLSetValue) i$.next()).saveValues(sharedPreferences, prefix);
        }
    }

    public void loadValues(SharedPreferences sharedPreferences, String prefix) {
        Iterator i$ = this.values.iterator();
        while (i$.hasNext()) {
            ((IGLSetValue) i$.next()).loadValues(sharedPreferences, prefix);
        }
    }

    public float getRangeHighValue() {
        return this.high;
    }

    public float getRangeLowValue() {
        return this.low;
    }

    public GLMultiVariable setValidRange(float low, float high, float step) {
        this.low = low;
        this.high = high;
        return this;
    }

    public void setRandomValue(Random random) {
        setValueAt(0, getRangeLowValue() + (random.nextFloat() * (getRangeHighValue() - getRangeLowValue())));
    }

    public int getCount() {
        return 1;
    }
}
