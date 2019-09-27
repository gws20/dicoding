package com.gws20.dicoding.favoritemovieapp;

import android.app.Application;

public class GWS20 extends Application {
    public static final String ARG_ACTIVITY = "activity";//0:mainActivity, 1:FavoriteActivity
    // Singleton instance
    private static GWS20 sInstance = null;

    @Override
    public void onCreate() {
        super.onCreate();
        // Setup singleton instance
        sInstance = this;
    }

    // Getter to access Singleton instance
    public static GWS20 getInstance() {
        return sInstance ;
    }
}
