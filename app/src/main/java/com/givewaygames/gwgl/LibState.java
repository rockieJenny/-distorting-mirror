package com.givewaygames.gwgl;

import com.squareup.otto.Bus;
import com.squareup.otto.ThreadEnforcer;

public class LibState {
    private static final LibState state = new LibState();
    Bus bus = new Bus(ThreadEnforcer.ANY);

    private LibState() {
    }

    public static LibState getInstance() {
        return state;
    }

    public Bus getBus() {
        return this.bus;
    }
}
