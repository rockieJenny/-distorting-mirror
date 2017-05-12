package com.givewaygames.gwgl.utils.gl;

import android.content.Context;
import com.givewaygames.gwgl.shader.pixel.BasicPixelShader;
import com.givewaygames.gwgl.shader.vertex.BasicVertexShader;
import com.givewaygames.gwgl.utils.GLHelper;

public class GLPrimeThePump extends GLPiece {
    GLProgram program;

    public GLPrimeThePump(Context context, GLHelper glHelper, int textureType) {
        this.program = new GLProgram(glHelper, textureType, null, null);
        this.program.setVertexShader(new BasicVertexShader(context));
        this.program.setPixelShader(new BasicPixelShader(context));
    }

    public boolean onInitialize() {
        this.program.initialize();
        return true;
    }

    public boolean draw(int pass, long time) {
        return true;
    }

    public int getNumPasses() {
        return 1;
    }
}
