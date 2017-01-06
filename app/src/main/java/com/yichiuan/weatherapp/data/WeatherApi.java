package com.yichiuan.weatherapp.data;

import com.yichiuan.weatherapp.entity.Weather;

import rx.Observable;


public interface WeatherApi {
    Observable<Weather> getWeather(double latitude, double longitude);
}
