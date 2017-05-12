package com.givewaygames.camera.events;

public class FilterChangedEvent {
    public final int filterIdx;

    public FilterChangedEvent(int filterIdx) {
        this.filterIdx = filterIdx;
    }
}
