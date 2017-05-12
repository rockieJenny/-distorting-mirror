package com.givewaygames.gwgl.events;

import com.givewaygames.gwgl.utils.GLErrorChecker;

public class OpenGLErrorEvent {
    public final String errorString;
    public final Throwable lastError = GLErrorChecker.lastError;

    public OpenGLErrorEvent(String errorString) {
        this.errorString = errorString;
    }
}
