package com.boring.guy.boringdemo;

import android.app.Application;

/**
 * Created by rockie on 2017/5/13.
 */
public class ApplicationEx extends Application {
    private static ApplicationEx instance;

    public static ApplicationEx getInstance(){
        return instance;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        instance= this;
    }
}
