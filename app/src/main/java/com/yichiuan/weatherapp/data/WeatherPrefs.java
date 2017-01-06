package com.yichiuan.weatherapp.data;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.annotation.NonNull;

import com.yichiuan.weatherapp.entity.WeatherApiSourceType;


public class WeatherPrefs {
    private static final String WEATHER_API_SOURCE_TAG = "weather_api_source";

    private static volatile WeatherPrefs instance;

    private final SharedPreferences preferences;

    private WeatherPrefs(@NonNull Context context) {
        preferences = PreferenceManager.getDefaultSharedPreferences(context);
    }

    public static WeatherPrefs getInstance(@NonNull Context context) {
        if (instance == null) {
            synchronized (WeatherPrefs.class) {
                if (instance == null) {
                    instance = new WeatherPrefs(context);
                }
            }
        }
        return instance;
    }

    @WeatherApiSourceType
    public int getWeatherApiSourceType() {
        final int type = preferences.getInt(WeatherPrefs.WEATHER_API_SOURCE_TAG, 0);
        switch (type) {
            case WeatherApiSourceType.YAHOO:
                return WeatherApiSourceType.YAHOO;
            case WeatherApiSourceType.OPENWEATHERMAP:
                return WeatherApiSourceType.OPENWEATHERMAP;
            default:
                return WeatherApiSourceType.YAHOO;
        }
    }

    public void saveWeatherApiSourceType(@WeatherApiSourceType int type) {
        preferences.edit().putInt(WeatherPrefs.WEATHER_API_SOURCE_TAG, type).apply();
    }
}