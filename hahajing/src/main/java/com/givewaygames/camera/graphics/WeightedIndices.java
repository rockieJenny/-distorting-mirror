package com.givewaygames.camera.graphics;

import com.givewaygames.gwgl.shader.ProgramSet;
import com.givewaygames.gwgl.utils.gl.GLProgram;
import com.google.android.gms.cast.TextTrackStyle;
import java.util.ArrayList;
import java.util.List;

public class WeightedIndices {
    List<Float> weights = new ArrayList();

    public int getIndexFor(float weight) {
        float cur = 0.0f;
        for (int i = 0; i < this.weights.size(); i++) {
            cur += ((Float) this.weights.get(i)).floatValue();
            if (weight <= cur) {
                return i;
            }
        }
        return this.weights.size() - 1;
    }

    private void addIndex(float weight) {
        this.weights.add(Float.valueOf(weight));
    }

    private void normalize() {
        float tot = 0.0f;
        for (Float f : this.weights) {
            tot += f.floatValue();
        }
        for (int i = 0; i < this.weights.size(); i++) {
            this.weights.set(i, Float.valueOf(((Float) this.weights.get(i)).floatValue() / tot));
        }
    }

    public void calculateWeights(ProgramSet programSet) {
        this.weights.clear();
        for (int i = 0; i < programSet.size(); i++) {
            addIndex(getWeightFor(((GLProgram) programSet.get(i)).getTag()));
        }
        normalize();
    }

    public float getWeightsOfFirst(int size) {
        float f = 0.0f;
        for (int i = 0; i < size; i++) {
            f += ((Float) this.weights.get(i)).floatValue();
        }
        return f;
    }

    public float getWeightFor(int shaderType) {
        switch (shaderType) {
            case 0:
                return 0.0f;
            case 1:
                return 2.0f;
            case 2:
                return 1.5f;
            case 3:
                return TextTrackStyle.DEFAULT_FONT_SCALE;
            case 4:
                return 2.0f;
            case 5:
                return 0.5f;
            case 6:
                return 0.5f;
            case 7:
                return 1.5f;
            case 8:
                return 2.0f;
            case 9:
                return 1.5f;
            case 10:
                return 1.5f;
            case 11:
                return 1.5f;
            case 12:
                return 1.5f;
            case 13:
                return 2.0f;
            case 14:
                return 1.5f;
            case 15:
                return 2.0f;
            case 16:
                return TextTrackStyle.DEFAULT_FONT_SCALE;
            case 17:
                return 0.0f;
            case 18:
                return TextTrackStyle.DEFAULT_FONT_SCALE;
            case 19:
                return 2.0f;
            case 20:
                return 0.0f;
            case 21:
                return TextTrackStyle.DEFAULT_FONT_SCALE;
            case 22:
                return 1.5f;
            case 23:
                return 2.0f;
            case 24:
                return 0.5f;
            case 25:
                return 0.5f;
            case 26:
                return 0.0f;
            case 27:
                return 0.0f;
            case 28:
                return 0.0f;
            case 29:
                return 1.5f;
            case 30:
                return 2.0f;
            case 31:
                return 1.5f;
            default:
                return TextTrackStyle.DEFAULT_FONT_SCALE;
        }
    }
}
