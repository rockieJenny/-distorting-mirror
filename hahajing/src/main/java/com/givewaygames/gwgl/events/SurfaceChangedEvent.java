package com.givewaygames.gwgl.events;

public class SurfaceChangedEvent {
    public final int height;
    public final int width;

    public SurfaceChangedEvent(int w, int h) {
        this.width = w;
        this.height = h;
    }
}
