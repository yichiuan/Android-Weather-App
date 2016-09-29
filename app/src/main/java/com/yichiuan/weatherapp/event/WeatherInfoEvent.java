package com.yichiuan.weatherapp.event;

import com.yichiuan.weatherapp.model.Weather;

public class WeatherInfoEvent {
    public final Weather weather;

    public WeatherInfoEvent(Weather weather) {
        this.weather = weather;
    }
}
