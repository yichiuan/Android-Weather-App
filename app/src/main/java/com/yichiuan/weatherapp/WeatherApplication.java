package com.yichiuan.weatherapp;

import android.app.Application;
import android.os.StrictMode;

import org.greenrobot.eventbus.EventBus;


public class WeatherApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();

        // StrictMode setup
        if (BuildConfig.DEBUG) {
            StrictMode.setThreadPolicy(new StrictMode.ThreadPolicy.Builder()
                    .detectCustomSlowCalls()
                    .detectDiskReads()
                    .detectDiskWrites()
                    .detectNetwork()
                    .penaltyLog()
                    .build());
            StrictMode.setVmPolicy(new StrictMode.VmPolicy.Builder()
                    .detectLeakedSqlLiteObjects()
                    .detectLeakedClosableObjects()
                    .penaltyLog()
                    .build());
        }

        EventBus.builder().addIndex(new EventBusIndex()).installDefaultEventBus();
    }
}
