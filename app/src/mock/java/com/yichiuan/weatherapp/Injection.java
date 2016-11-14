package com.yichiuan.weatherapp;

import com.yichiuan.weatherapp.data.WeatherRepository;

public class Injection {
    public static WeatherRepository provideWeatherRepository() {
        return WeatherRepository.getInstance();
    }
}
