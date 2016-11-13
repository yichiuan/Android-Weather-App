package com.yichiuan.weatherapp.data;

import com.yichiuan.weatherapp.entity.Weather;
import com.yichiuan.weatherapp.data.yahooweather.YahooWeatherApi;

import rx.Observable;

public class WeatherRepository {

    private static volatile WeatherRepository instance;
    private YahooWeatherApi weatherApi;

    private WeatherRepository() {

        weatherApi = new YahooWeatherApi();
    }

    public static WeatherRepository getInstance() {
        if (instance == null) {
            synchronized(WeatherRepository.class) {
                if (instance == null) {
                    instance = new WeatherRepository();
                }
            }
        }
        return instance;
    }

    public void setWeatherApi(YahooWeatherApi weatherApi) {
        this.weatherApi = weatherApi;
    }

    public Observable<Weather> getWeather(double latitude, double longitude) {
        return weatherApi.getWeather(latitude, longitude);
    }
}
