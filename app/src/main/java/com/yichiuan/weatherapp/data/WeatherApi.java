package com.yichiuan.weatherapp.data;

import com.yichiuan.weatherapp.entity.Weather;

import io.reactivex.Single;


public interface WeatherApi {
    Single<Weather> getWeather(double latitude, double longitude);
}
