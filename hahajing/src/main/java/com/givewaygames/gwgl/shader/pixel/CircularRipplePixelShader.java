package com.givewaygames.gwgl.shader.pixel;

import android.content.Context;
import com.givewaygames.gwgl.R;
import com.givewaygames.gwgl.shader.GLUniform;
import com.givewaygames.gwgl.shader.GLVariable;
import com.givewaygames.gwgl.shader.PixelShader;
import com.google.android.gms.cast.TextTrackStyle;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;

public class CircularRipplePixelShader extends PixelShader {
    public static final String UNIFORM_AMPLITUDE = "amplitude";
    public static final String UNIFORM_PHASE = "phase";
    public static final String UNIFORM_PROGRESS = "progress";

    public CircularRipplePixelShader(Context c, boolean inYourFace) {
        super(c, R.raw.circular_ripple_frag);
        PixelShader.setupUVBounds(this.variables);
        this.variables.add(new GLUniform("progress", 1, "Progress").setValidRange(0.0f, 100.0f, 0.01f));
        this.variables.add(new GLUniform("amplitude", 1, "Amplitude").setValidRange(0.01f, 0.06f, 0.01f));
        this.variables.add(new GLUniform(UNIFORM_PHASE, 1, "Phase").setValidRange(TextTrackStyle.DEFAULT_FONT_SCALE, BitmapDescriptorFactory.HUE_ORANGE, 0.01f));
        ((GLVariable) this.variables.get(1)).setValueAt(0, 0.0f);
        ((GLVariable) this.variables.get(2)).setValueAt(0, 0.03f);
        ((GLVariable) this.variables.get(3)).setValueAt(0, BitmapDescriptorFactory.HUE_ORANGE);
        if (inYourFace) {
            this.userEditableVariables.add(this.variables.get(2));
        }
        this.userEditableVariables.add(this.variables.get(3));
    }
}
