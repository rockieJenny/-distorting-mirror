package com.givewaygames.gwgl.shader;

import android.util.SparseBooleanArray;
import android.util.SparseIntArray;
import com.givewaygames.gwgl.utils.GLHelper;
import com.givewaygames.gwgl.utils.gl.GLProgram;
import com.givewaygames.gwgl.utils.gl.GLScene;
import com.givewaygames.gwgl.utils.gl.meshes.GLEquationMesh.MeshPointProvider;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class ProgramSet extends ArrayList<GLProgram> {
    private static final long serialVersionUID = 6651744878753431198L;
    Map<GLProgram, GLProgram> backupPrograms;
    int defaultTextureType;
    ShaderFactory factory;
    SparseIntArray typeLookup;

    public ProgramSet(ShaderFactory factory, GLScene glScene, int defaultTextureType) {
        this(new SparseBooleanArray(), factory, glScene, defaultTextureType);
    }

    public ProgramSet(SparseBooleanArray blockedProgs, ShaderFactory factory, GLScene glScene, int defaultTextureType) {
        this.typeLookup = new SparseIntArray();
        this.backupPrograms = new HashMap();
        this.defaultTextureType = defaultTextureType;
        this.factory = factory;
        for (int i = 0; i < 46; i++) {
            GLProgram program = factory.getProgram(i, defaultTextureType);
            if (!blockedProgs.get(i, false)) {
                this.typeLookup.append(i, size());
                add(program);
                if (glScene != null) {
                    glScene.add(program);
                }
            }
        }
    }

    public ProgramSet(int[] programs, ShaderFactory factory, GLScene glScene, int defaultTextureType) {
        this.typeLookup = new SparseIntArray();
        this.backupPrograms = new HashMap();
        this.defaultTextureType = defaultTextureType;
        this.factory = factory;
        for (int p : programs) {
            GLProgram program = factory.getProgram(p, defaultTextureType);
            this.typeLookup.append(p, size());
            add(program);
            if (glScene != null) {
                glScene.add(program);
            }
        }
    }

    public GLProgram get(int idx, int textureType) {
        GLProgram program = (GLProgram) get(idx);
        if (textureType == this.defaultTextureType) {
            return program;
        }
        GLProgram backupProgram = this.backupPrograms.containsKey(program) ? (GLProgram) this.backupPrograms.get(program) : this.factory.getProgram(program.getTag(), textureType);
        backupProgram.copyVariableValuesFrom(program);
        this.backupPrograms.put(program, backupProgram);
        return backupProgram;
    }

    public MeshPointProvider getPointProvider(GLHelper glHelper, int idx) {
        return ShaderFactory.getPointProvider(glHelper, ((GLProgram) get(idx)).getTag());
    }

    public int getIndexForType(int type) {
        return this.typeLookup.get(type);
    }

    public GLProgram getOfType(int shaderType) {
        return (GLProgram) get(this.typeLookup.get(shaderType));
    }

    public GLProgram getOfType(int shaderType, int textureType) {
        return get(this.typeLookup.get(shaderType), textureType);
    }

    public void invalidateBackups() {
        for (GLProgram value : this.backupPrograms.values()) {
            value.release();
        }
    }
}
