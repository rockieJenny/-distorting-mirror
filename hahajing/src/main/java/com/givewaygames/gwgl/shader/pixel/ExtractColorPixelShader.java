package com.givewaygames.gwgl.shader.pixel;

import android.content.Context;
import com.givewaygames.gwgl.R;
import com.givewaygames.gwgl.shader.GLUniform;
import com.givewaygames.gwgl.shader.PixelShader;
import com.google.android.gms.cast.TextTrackStyle;

public class ExtractColorPixelShader extends PixelShader {
    public static final String FADE_BACKGROUND = "fade";
    public static final String GRAY_RANGE = "grayRange";
    public static final String HUE_RANGE = "hueRange";
    public static final String MATCH_ANGLE = "matchAngle";
    public static final float SPLIT_DISABLED = 0.0f;
    public static final float SPLIT_ENABLED = 0.499f;
    private static final float SPLIT_HALF_VALUE = 0.499f;
    public static final String SPLIT_VALUE = "splitValue";
    GLUniform fadeBackground = new GLUniform(FADE_BACKGROUND, 1, "Fade background");
    GLUniform grayTolerance = new GLUniform(GRAY_RANGE, 1, "Gray tolerance");
    GLUniform hueAngle = new GLUniform(MATCH_ANGLE, 1, "Hue");
    GLUniform hueTolerance = new GLUniform(HUE_RANGE, 1, "Color tolerance");
    GLUniform splitValue = new GLUniform("splitValue", 1, "Split Value");

    public ExtractColorPixelShader(Context c, boolean inYourFace) {
        super(c, R.raw.extract_hsv_frag);
        addAllVariables();
        this.splitValue.setValueAt(0, 0.0f);
        this.hueAngle.setValueAt(0, 0.0f);
        this.hueTolerance.setValueAt(0, 0.25f);
        this.grayTolerance.setValueAt(0, 1.25f);
        this.fadeBackground.setValueAt(0, 0.0f);
        this.splitValue.setValidRange(0.0f, TextTrackStyle.DEFAULT_FONT_SCALE, TextTrackStyle.DEFAULT_FONT_SCALE);
        this.hueAngle.setValidRange(0.0f, TextTrackStyle.DEFAULT_FONT_SCALE, 0.01f);
        this.hueTolerance.setValidRange(0.1f, 0.7f, 0.01f);
        this.grayTolerance.setValidRange(TextTrackStyle.DEFAULT_FONT_SCALE, 1.5f, 0.01f);
        this.fadeBackground.setValidRange(0.0f, TextTrackStyle.DEFAULT_FONT_SCALE, 0.01f);
        if (inYourFace) {
            this.splitValue.setValueAt(0, 0.0f);
            this.fadeBackground.setValueAt(0, 0.0f);
        }
        this.userEditableVariables.add(this.hueAngle);
        this.userEditableVariables.add(this.hueTolerance);
        this.userEditableVariables.add(this.grayTolerance);
    }

    public ExtractColorPixelShader(Context c, boolean splitValueEnabled, float grayTolerance, float colorTolerance, float fadeBackground) {
        super(c, R.raw.extract_hsv_frag);
        addAllVariables();
        this.splitValue.setValueAt(0, splitValueEnabled ? 0.499f : 0.0f);
        this.hueAngle.setValueAt(0, 0.0f);
        this.hueTolerance.setValueAt(0, colorTolerance);
        this.grayTolerance.setValueAt(0, grayTolerance);
        this.fadeBackground.setValueAt(0, fadeBackground);
    }

    private void addAllVariables() {
        this.variables.add(this.splitValue);
        this.variables.add(this.hueAngle);
        this.variables.add(this.hueTolerance);
        this.variables.add(this.grayTolerance);
        this.variables.add(this.fadeBackground);
    }
}
