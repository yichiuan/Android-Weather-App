package com.yichiuan.weatherapp.event;

import com.yichiuan.weatherapp.entity.Weather;

public class WeatherInfoEvent {
    public final Weather weather;

    public WeatherInfoEvent(Weather weather) {
        this.weather = weather;
    }
}
