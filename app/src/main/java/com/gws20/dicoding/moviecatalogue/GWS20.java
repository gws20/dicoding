package com.gws20.dicoding.moviecatalogue;

import android.app.Application;

import timber.log.Timber;

public class GWS20 extends Application {
    // Singleton instance
    private static GWS20 sInstance = null;

    @Override
    public void onCreate() {
        super.onCreate();
        // Setup singleton instance
        sInstance = this;
        Timber.plant(new Timber.DebugTree());
    }

    // Getter to access Singleton instance
    public static GWS20 getInstance() {
        return sInstance ;
    }
}
