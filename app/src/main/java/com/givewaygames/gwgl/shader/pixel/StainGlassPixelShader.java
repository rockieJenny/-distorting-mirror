package com.givewaygames.gwgl.shader.pixel;

import android.content.Context;
import com.givewaygames.gwgl.R;
import com.givewaygames.gwgl.shader.GLUniform;
import com.givewaygames.gwgl.shader.GLVariable;
import com.givewaygames.gwgl.shader.PixelShader;
import com.google.android.gms.cast.TextTrackStyle;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;

public class StainGlassPixelShader extends PixelShader {
    public static final String UNIFORM_BORDER_SIZE = "borderSize";
    public static final String UNIFORM_HEIGHT = "height";
    public static final String UNIFORM_PATTERN = "pattern";
    public static final String UNIFORM_SIZE = "size";
    public static final String UNIFORM_WIDTH = "width";

    public StainGlassPixelShader(Context c, boolean extraValues) {
        super(c, R.raw.stain_glass_frag);
        this.variables.add(new GLUniform("width", 1, "Width"));
        this.variables.add(new GLUniform("height", 1, "Height"));
        this.variables.add(new GLUniform("size", 1, "Size").setValidRange(10.0f, BitmapDescriptorFactory.HUE_ORANGE, 0.01f));
        this.variables.add(new GLUniform(UNIFORM_BORDER_SIZE, 1, "BorderSize").setValidRange(0.01f, TextTrackStyle.DEFAULT_FONT_SCALE, 0.01f));
        this.variables.add(new GLUniform(UNIFORM_PATTERN, 1, "Pattern").setValidRange(0.0f, 10.0f, 0.01f));
        ((GLVariable) this.variables.get(0)).setValueAt(0, TextTrackStyle.DEFAULT_FONT_SCALE);
        ((GLVariable) this.variables.get(1)).setValueAt(0, TextTrackStyle.DEFAULT_FONT_SCALE);
        ((GLVariable) this.variables.get(2)).setValueAt(0, 8.0f);
        ((GLVariable) this.variables.get(3)).setValueAt(0, 3.0f);
        ((GLVariable) this.variables.get(4)).setValueAt(0, 5.0f);
        ((GLVariable) this.variables.get(0)).setSpecial(1);
        ((GLVariable) this.variables.get(1)).setSpecial(2);
        this.userEditableVariables.add(this.variables.get(2));
        if (extraValues) {
            this.userEditableVariables.add(this.variables.get(4));
        }
        PixelShader.setupUVBounds(this.variables);
    }
}
