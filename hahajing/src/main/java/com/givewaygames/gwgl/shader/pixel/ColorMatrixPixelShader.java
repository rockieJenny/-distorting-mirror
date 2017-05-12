package com.givewaygames.gwgl.shader.pixel;

import android.content.Context;
import com.givewaygames.gwgl.R;
import com.givewaygames.gwgl.shader.GLUniform;
import com.givewaygames.gwgl.shader.GLVariable;
import com.givewaygames.gwgl.shader.GLVariable.Randomizer;
import com.givewaygames.gwgl.shader.PixelShader;
import com.google.android.gms.cast.TextTrackStyle;
import java.util.Random;

public class ColorMatrixPixelShader extends PixelShader {
    public static final String COLOR_MATRIX_1 = "color_matrix1";
    public static final String COLOR_MATRIX_2 = "color_matrix2";
    public static final String COLOR_MATRIX_3 = "color_matrix3";
    private static final float SPLIT_HALF_VALUE = 0.499f;
    public static final String SPLIT_VALUE = "splitValue";
    private float[] blackAndWhiteMatrix = new float[]{0.299f, 0.587f, 0.114f, 0.299f, 0.587f, 0.114f, 0.299f, 0.587f, 0.114f};
    private float[] deuterMatrix = new float[]{0.625f, 0.375f, 0.0f, 0.7f, 0.3f, 0.0f, 0.0f, 0.3f, 0.7f};
    private float[] protoMatrix = new float[]{0.567f, 0.433f, 0.0f, 0.558f, 0.442f, 0.0f, 0.0f, 0.242f, 0.758f};
    private float[] tritMatrix = new float[]{0.95f, 0.05f, 0.0f, 0.0f, 0.433f, 0.567f, 0.0f, 0.475f, 0.525f};

    public ColorMatrixPixelShader(Context c, boolean isSplitValue, boolean inYourFace) {
        super(c, R.raw.color_matrix_frag);
        this.variables.add(new GLUniform("splitValue", 1, "Split Value").setValidRange(0.0f, 0.499f, 0.01f));
        this.variables.add(new GLUniform(COLOR_MATRIX_1, 3, "Matrix Row 1").setValidRange(0.0f, TextTrackStyle.DEFAULT_FONT_SCALE, 0.01f));
        this.variables.add(new GLUniform(COLOR_MATRIX_2, 3, "Matrix Row 2").setValidRange(0.0f, TextTrackStyle.DEFAULT_FONT_SCALE, 0.01f));
        this.variables.add(new GLUniform(COLOR_MATRIX_3, 3, "Matrix Row 3").setValidRange(0.0f, TextTrackStyle.DEFAULT_FONT_SCALE, 0.01f));
        ((GLUniform) this.variables.get(0)).setValueAt(0, isSplitValue ? 0.499f : 0.0f);
        GLUniform colorMatrix1 = (GLUniform) this.variables.get(1);
        colorMatrix1.setValueAt(0, TextTrackStyle.DEFAULT_FONT_SCALE);
        colorMatrix1.setValueAt(1, 0.0f);
        colorMatrix1.setValueAt(2, 0.0f);
        GLUniform colorMatrix2 = (GLUniform) this.variables.get(2);
        colorMatrix2.setValueAt(0, 0.0f);
        colorMatrix2.setValueAt(1, TextTrackStyle.DEFAULT_FONT_SCALE);
        colorMatrix2.setValueAt(2, 0.0f);
        GLUniform colorMatrix3 = (GLUniform) this.variables.get(3);
        colorMatrix3.setValueAt(0, 0.0f);
        colorMatrix3.setValueAt(1, 0.0f);
        colorMatrix3.setValueAt(2, TextTrackStyle.DEFAULT_FONT_SCALE);
        if (inYourFace) {
            this.userEditableVariables.add(colorMatrix1);
        }
    }

    public void setFromMatrix(float[] mat) {
        GLUniform colorMatrix1 = (GLUniform) this.variables.get(1);
        colorMatrix1.setValueAt(0, mat[0]);
        colorMatrix1.setValueAt(1, mat[1]);
        colorMatrix1.setValueAt(2, mat[2]);
        GLUniform colorMatrix2 = (GLUniform) this.variables.get(2);
        colorMatrix2.setValueAt(0, mat[3]);
        colorMatrix2.setValueAt(1, mat[4]);
        colorMatrix2.setValueAt(2, mat[5]);
        GLUniform colorMatrix3 = (GLUniform) this.variables.get(3);
        colorMatrix3.setValueAt(0, mat[6]);
        colorMatrix3.setValueAt(1, mat[7]);
        colorMatrix3.setValueAt(2, mat[8]);
    }

    public Randomizer getColorblindRandomizer() {
        return new Randomizer() {
            public void setRandomValue(Random random, GLVariable glVariable) {
                boolean halfA = random.nextBoolean();
                boolean halfB = random.nextBoolean();
                if (halfA) {
                    if (halfB) {
                        ColorMatrixPixelShader.this.setFromMatrix(ColorMatrixPixelShader.this.blackAndWhiteMatrix);
                    } else {
                        ColorMatrixPixelShader.this.setFromMatrix(ColorMatrixPixelShader.this.deuterMatrix);
                    }
                } else if (halfB) {
                    ColorMatrixPixelShader.this.setFromMatrix(ColorMatrixPixelShader.this.protoMatrix);
                } else {
                    ColorMatrixPixelShader.this.setFromMatrix(ColorMatrixPixelShader.this.tritMatrix);
                }
            }
        };
    }

    public Randomizer getUberRandomizer() {
        return new Randomizer() {
            public void setRandomValue(Random random, GLVariable glVariable) {
                float[] values = new float[9];
                ColorMatrixPixelShader.this.fillNormalizedRandomVector(random, values, 0, 1);
                ColorMatrixPixelShader.this.fillNormalizedRandomVector(random, values, 3, 1);
                ColorMatrixPixelShader.this.fillNormalizedRandomVector(random, values, 6, 1);
                ColorMatrixPixelShader.this.setFromMatrix(values);
            }
        };
    }

    public Randomizer getWildRandomizer(final float scale) {
        return new Randomizer() {
            public void setRandomValue(Random random, GLVariable glVariable) {
                float[] values = new float[9];
                do {
                    ColorMatrixPixelShader.this.fillRandomVector(random, values, 0, 1, scale);
                    ColorMatrixPixelShader.this.fillRandomVector(random, values, 3, 1, scale);
                    ColorMatrixPixelShader.this.fillRandomVector(random, values, 6, 1, scale);
                } while (!ColorMatrixPixelShader.this.checkColor(TextTrackStyle.DEFAULT_FONT_SCALE, TextTrackStyle.DEFAULT_FONT_SCALE, TextTrackStyle.DEFAULT_FONT_SCALE, values));
                ColorMatrixPixelShader.this.setFromMatrix(values);
            }
        };
    }

    public boolean checkColor(float r, float g, float b, float[] values) {
        float G = ((values[3] * r) + (values[4] * g)) + (values[5] * b);
        float B = ((values[6] * r) + (values[7] * g)) + (values[8] * b);
        if (((double) (((values[0] * r) + (values[1] * g)) + (values[2] * b))) > 0.3d || ((double) G) > 0.3d || ((double) B) > 0.3d) {
            return true;
        }
        return false;
    }

    public Randomizer getFakeRandomizer() {
        return new Randomizer() {
            public void setRandomValue(Random random, GLVariable glVariable) {
            }
        };
    }

    private void fillRandomVector(Random random, float[] mat, int startIdx, int nextDelta, float scale) {
        for (int i = 0; i < 3; i++) {
            mat[startIdx + (i * nextDelta)] = ((float) (random.nextBoolean() ? 1 : -1)) * ((random.nextFloat() * 0.5f) + 0.5f);
            int i2 = (i * nextDelta) + startIdx;
            mat[i2] = mat[i2] * scale;
        }
    }

    private void fillNormalizedRandomVector(Random random, float[] mat, int startIdx, int nextDelta) {
        int i;
        float full = 0.0f;
        for (int i2 = 0; i2 < 3; i2++) {
            float v = random.nextFloat();
            mat[(i2 * nextDelta) + startIdx] = v;
            full += v;
        }
        if (((double) full) < 0.001d) {
            full = 3.0f;
            i = (nextDelta * 0) + startIdx;
            int i3 = (nextDelta * 1) + startIdx;
            mat[(nextDelta * 2) + startIdx] = TextTrackStyle.DEFAULT_FONT_SCALE;
            mat[i3] = TextTrackStyle.DEFAULT_FONT_SCALE;
            mat[i] = TextTrackStyle.DEFAULT_FONT_SCALE;
        }
        i = (nextDelta * 0) + startIdx;
        mat[i] = mat[i] / full;
        i = (nextDelta * 1) + startIdx;
        mat[i] = mat[i] / full;
        i = (nextDelta * 2) + startIdx;
        mat[i] = mat[i] / full;
    }
}
