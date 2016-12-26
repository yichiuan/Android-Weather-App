package com.yichiuan.weatherapp.data.yahooweather;


import com.yichiuan.weatherapp.data.yahooweather.model.YahooWeatherResponse;

import retrofit2.http.GET;
import retrofit2.http.Query;
import rx.Observable;

public interface YahooWeatherService {
    @GET("yql?format=json")
    Observable<YahooWeatherResponse> getWeather(@Query("q") String yql);
}
