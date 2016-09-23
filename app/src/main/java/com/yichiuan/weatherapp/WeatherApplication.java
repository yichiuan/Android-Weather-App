package com.yichiuan.weatherapp;

import android.app.Application;

import org.greenrobot.eventbus.EventBus;


public class WeatherApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        EventBus.builder().addIndex(new EventBusIndex()).installDefaultEventBus();
    }
}
