package com.yichiuan.weatherapp.event;

import com.yichiuan.weatherapp.weatherapi.WeatherInfo;

public class WeatherInfoEvent {
    public final WeatherInfo weatherInfo;

    public WeatherInfoEvent(WeatherInfo weatherInfo) {
        this.weatherInfo = weatherInfo;
    }
}
