package com.givewaygames.gwgl.shader;

import com.givewaygames.gwgl.utils.GLHelper;
import com.givewaygames.gwgl.utils.Log;
import com.google.android.gms.cast.TextTrackStyle;
import java.util.Random;

public abstract class GLVariable implements IGLSetValue {
    public static final int MULTIPLY_BY_HEIGHT = 2;
    public static final int MULTIPLY_BY_WIDTH = 1;
    public static final int NO_SPECIAL = 0;
    public static final String TAG = "GLVariable";
    public static final int TYPE_FLOAT_1 = 1;
    public static final int TYPE_FLOAT_2 = 2;
    public static final int TYPE_FLOAT_3 = 3;
    public static final int TYPE_FLOAT_4 = 4;
    public static final int TYPE_INT_1 = 101;
    int attribIndex;
    public String binding;
    public String description;
    boolean dirty = false;
    float highValue = TextTrackStyle.DEFAULT_FONT_SCALE;
    float lowValue = 0.0f;
    Randomizer randomizer;
    public int size;
    int specialValue = 0;
    float step = 0.01f;
    float[] values;

    public interface Randomizer {
        void setRandomValue(Random random, GLVariable gLVariable);
    }

    public static class BinaryRandomizer implements Randomizer {
        boolean mustHaveOne = false;
        float offValue;
        float onValue;
        float percent;

        public BinaryRandomizer(float percent, float offValue, float onValue) {
            this.percent = percent;
            this.offValue = offValue;
            this.onValue = onValue;
        }

        public void setMustHaveOne(boolean mustHaveOne) {
            this.mustHaveOne = mustHaveOne;
        }

        public void setRandomValue(Random random, GLVariable glVariable) {
            boolean hasOne = false;
            for (int i = 0; i < glVariable.size; i++) {
                float v = random.nextFloat();
                glVariable.setValueAt(i, v <= this.percent ? this.onValue : this.offValue);
                hasOne = v <= this.percent;
            }
            if (!hasOne && this.mustHaveOne) {
                glVariable.setValueAt(random.nextInt(glVariable.size), this.onValue);
            }
        }
    }

    public static class ExponentialRandomizer implements Randomizer {
        float add;
        float exponent;
        float scale;

        public ExponentialRandomizer(float exponent, float scale, float add) {
            this.exponent = exponent;
            this.scale = scale;
            this.add = add;
        }

        public void setRandomValue(Random random, GLVariable glVariable) {
            glVariable.setValueAt(0, ((float) (Math.pow((double) random.nextFloat(), (double) this.exponent) * ((double) this.scale))) + this.add);
        }
    }

    public abstract boolean initialize(int i);

    public abstract void sendValuesToOpenGL(GLHelper gLHelper);

    public GLVariable(String binding, int type, String description) {
        this.binding = binding;
        this.description = description;
        this.size = type;
        this.values = new float[(this.size >= 100 ? this.size - 100 : this.size)];
        this.dirty = true;
    }

    public GLVariable setValidRange(float low, float high, float step) {
        this.lowValue = low;
        this.highValue = high;
        this.step = step;
        return this;
    }

    public void forceDirty() {
        this.dirty = true;
    }

    public void onOrientationChange() {
        if (this.specialValue == 1 || this.specialValue == 2) {
            forceDirty();
        }
    }

    public float getRangeLowValue() {
        return this.lowValue;
    }

    public float getRangeHighValue() {
        return this.highValue;
    }

    public float getRangeStepValue() {
        return this.step;
    }

    public void setAttribIndex(int idx) {
        this.attribIndex = idx;
    }

    public int getAttribIndex() {
        return this.attribIndex;
    }

    public float getValueAt(int idx) {
        return this.values[idx];
    }

    public void setValueAt(int idx, float value) {
        this.values[idx] = value;
        this.dirty = true;
    }

    public void setValues(float[] values) {
        System.arraycopy(values, 0, this.values, 0, values.length);
        this.dirty = true;
    }

    public void setSpecial(int special) {
        this.specialValue = special;
    }

    public void setRandomValue(Random random) {
        if (this.randomizer != null) {
            this.randomizer.setRandomValue(random, this);
            return;
        }
        float low = getRangeLowValue();
        float dt = getRangeHighValue() - low;
        for (int i = 0; i < this.values.length; i++) {
            float value = low + (random.nextFloat() * dt);
            setValueAt(i, value);
            if (Log.isV) {
                Log.v(TAG, "Setting random (" + i + "): " + value);
            }
        }
    }

    public int getCount() {
        return this.values != null ? this.values.length : 0;
    }

    public void setRandomizer(Randomizer randomizer) {
        this.randomizer = randomizer;
    }

    public Runnable getRunnableToSendValuesToOpenGL(final GLHelper glHelper) {
        return new Runnable() {
            public void run() {
                GLVariable.this.sendValuesToOpenGL(glHelper);
            }
        };
    }
}
