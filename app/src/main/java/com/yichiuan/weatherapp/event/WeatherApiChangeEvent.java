package com.yichiuan.weatherapp.event;

import com.yichiuan.weatherapp.entity.WeatherApiSourceType;

public class WeatherApiChangeEvent {
    @WeatherApiSourceType
    public int type;

    public WeatherApiChangeEvent(@WeatherApiSourceType int type) {
        this.type = type;
    }
}
