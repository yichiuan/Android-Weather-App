package com.yichiuan.weatherapp.data;

import android.support.annotation.NonNull;
import android.support.annotation.VisibleForTesting;

import com.yichiuan.weatherapp.Injection;
import com.yichiuan.weatherapp.data.openweathermap.OpenWeatherMapApi;
import com.yichiuan.weatherapp.data.yahooweather.YahooWeatherApi;
import com.yichiuan.weatherapp.entity.Weather;
import com.yichiuan.weatherapp.entity.WeatherApiSourceType;

import io.reactivex.Single;


public class WeatherRepository {

    private static volatile WeatherRepository instance;

    private WeatherApi weatherApi;

    private final WeatherPrefs weatherPrefs;

    private WeatherRepository(@NonNull WeatherPrefs weatherPrefs) {
        this.weatherPrefs = weatherPrefs;
        changeWeatherApiSource(weatherPrefs.getWeatherApiSourceType());
    }

    public static WeatherRepository getInstance(@NonNull WeatherPrefs weatherPrefs) {
        if (instance == null) {
            synchronized (WeatherRepository.class) {
                if (instance == null) {
                    instance = new WeatherRepository(weatherPrefs);
                }
            }
        }
        return instance;
    }

    @VisibleForTesting
    void setWeatherApi(@NonNull WeatherApi weatherApi) {
        this.weatherApi = weatherApi;
    }

    public Single<Weather> getWeather(double latitude, double longitude) {
        return weatherApi.getWeather(latitude, longitude);
    }

    private void changeWeatherApiSource(@WeatherApiSourceType int type) {
        switch (type) {
            case WeatherApiSourceType.YAHOO:
                weatherApi = new YahooWeatherApi(Injection.provideRetrofitForYahooWeather());
                break;
            case WeatherApiSourceType.OPENWEATHERMAP:
                weatherApi = new OpenWeatherMapApi(Injection.provideRetrofitForOpenWeatherMap());
                break;
            default:
                weatherApi = new YahooWeatherApi(Injection.provideRetrofitForYahooWeather());
        }
    }

    public void changeWeatherApiSourceType(@WeatherApiSourceType int type) {
        weatherPrefs.saveWeatherApiSourceType(type);
        changeWeatherApiSource(type);
    }
}
