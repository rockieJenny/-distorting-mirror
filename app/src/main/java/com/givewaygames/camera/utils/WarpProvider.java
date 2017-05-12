package com.givewaygames.camera.utils;

import com.givewaygames.gwgl.utils.GLHelper;
import com.givewaygames.gwgl.utils.gl.meshes.GLEquationMesh.ExtendedMeshPointProvider;
import com.givewaygames.gwgl.utils.gl.meshes.providers.BigEarsPointProvider;
import com.givewaygames.gwgl.utils.gl.meshes.providers.BigForeheadPointProvider;
import com.givewaygames.gwgl.utils.gl.meshes.providers.BugEyesPointProvider;
import com.givewaygames.gwgl.utils.gl.meshes.providers.ChubbyCheeksPointProvider;
import com.givewaygames.gwgl.utils.gl.meshes.providers.ConeheadPointProvider;
import com.givewaygames.gwgl.utils.gl.meshes.providers.DoubleBumpHoreheadPointProvider;
import com.givewaygames.gwgl.utils.gl.meshes.providers.FatChinPointProvider;
import com.givewaygames.gwgl.utils.gl.meshes.providers.GorillaNosePointProvider;
import com.givewaygames.gwgl.utils.gl.meshes.providers.ListPointProvider;
import com.givewaygames.gwgl.utils.gl.meshes.providers.MonkeyMouthPointProvider;
import com.givewaygames.gwgl.utils.gl.meshes.providers.PointyChinPointProvider;
import com.givewaygames.gwgl.utils.gl.meshes.providers.SpikyHairPointProvider;
import com.givewaygames.gwgl.utils.gl.meshes.providers.SquareNosePointProvider;
import com.givewaygames.gwgl.utils.gl.meshes.providers.SquintyEyesPointProvider;
import com.givewaygames.gwgl.utils.gl.meshes.providers.TinyMouthPointProvider;

public class WarpProvider {
    ExtendedMeshPointProvider pointProvider;
    int providerIdx = 0;
    private ListPointProvider[] providers;

    public WarpProvider(GLHelper glHelper) {
        this.providers = new ListPointProvider[]{new ConeheadPointProvider(glHelper), new BigEarsPointProvider(glHelper), new BigForeheadPointProvider(glHelper), new PointyChinPointProvider(glHelper), new BugEyesPointProvider(glHelper), new SquintyEyesPointProvider(glHelper), new TinyMouthPointProvider(glHelper), new FatChinPointProvider(glHelper), new SquareNosePointProvider(glHelper), new MonkeyMouthPointProvider(glHelper), new ChubbyCheeksPointProvider(glHelper), new SpikyHairPointProvider(glHelper), new DoubleBumpHoreheadPointProvider(glHelper), new GorillaNosePointProvider(glHelper)};
    }

    public ListPointProvider getPointProvider() {
        return this.providers[this.providerIdx];
    }

    public ListPointProvider getNextPointProvider() {
        this.providerIdx = (this.providerIdx + 1) % this.providers.length;
        return this.providers[this.providerIdx];
    }
}
