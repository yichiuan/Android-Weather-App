package com.yichiuan.weatherapp.entity;


import android.support.annotation.IntDef;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@IntDef({WeatherApiSourceType.YAHOO, WeatherApiSourceType.OPENWEATHERMAP})
@Retention(RetentionPolicy.SOURCE)
public @interface WeatherApiSourceType {
    int YAHOO = 0;
    int OPENWEATHERMAP = 1;
}
