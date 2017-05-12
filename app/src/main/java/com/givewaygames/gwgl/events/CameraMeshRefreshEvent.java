package com.givewaygames.gwgl.events;

import com.givewaygames.gwgl.CameraWrapper.MeshOrientation;

public class CameraMeshRefreshEvent {
    public final boolean isFrontFacingCamera;
    public final MeshOrientation meshOrientation;

    public CameraMeshRefreshEvent(boolean ffc, MeshOrientation orient) {
        this.isFrontFacingCamera = ffc;
        this.meshOrientation = orient;
    }
}
