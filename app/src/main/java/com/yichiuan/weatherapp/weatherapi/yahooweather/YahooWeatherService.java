package com.yichiuan.weatherapp.weatherapi.yahooweather;


import retrofit2.http.GET;
import retrofit2.http.Query;
import rx.Observable;

public interface YahooWeatherService {
    @GET("yql?format=json")
    Observable<YahooWeatherResponse> getWeather(@Query("q") String yql);
}
