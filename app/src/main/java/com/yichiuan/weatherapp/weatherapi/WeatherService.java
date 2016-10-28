package com.yichiuan.weatherapp.weatherapi;

import com.yichiuan.weatherapp.entity.Weather;
import com.yichiuan.weatherapp.weatherapi.yahooweather.YahooWeatherApi;

import rx.Observable;

public class WeatherService {

    private static volatile WeatherService instance;
    private YahooWeatherApi weatherApi;

    private WeatherService() {

        weatherApi = new YahooWeatherApi();
    }

    public static WeatherService getInstance() {
        if (instance == null) {
            synchronized(WeatherService.class) {
                if (instance == null) {
                    instance = new WeatherService();
                }
            }
        }
        return instance;
    }

    public Observable<Weather> getWeather(double latitude, double longitude) {
        return weatherApi.getWeather(latitude, longitude);
    }
}
