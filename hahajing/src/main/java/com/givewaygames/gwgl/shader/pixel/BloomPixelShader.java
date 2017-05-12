package com.givewaygames.gwgl.shader.pixel;

import android.content.Context;
import com.givewaygames.gwgl.R;
import com.givewaygames.gwgl.shader.GLUniform;
import com.givewaygames.gwgl.shader.GLVariable;
import com.givewaygames.gwgl.shader.PixelShader;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;

public class BloomPixelShader extends PixelShader {
    public static final String UNIFORM_BLOOM_FACTOR = "bloomfactor";

    public BloomPixelShader(Context c) {
        super(c, R.raw.bloom_frag);
        this.variables.add(new GLUniform(UNIFORM_BLOOM_FACTOR, 1, "Amount").setValidRange(0.1f, BitmapDescriptorFactory.HUE_YELLOW, 0.01f));
        ((GLVariable) this.variables.get(0)).setValueAt(0, 15.0f);
        this.userEditableVariables.add(this.variables.get(0));
    }
}
