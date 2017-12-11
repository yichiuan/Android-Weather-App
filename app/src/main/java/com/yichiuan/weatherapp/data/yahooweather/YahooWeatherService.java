package com.yichiuan.weatherapp.data.yahooweather;

import com.yichiuan.weatherapp.data.yahooweather.model.YahooWeatherResponse;

import io.reactivex.Single;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface YahooWeatherService {
    @GET("yql?format=json")
    Single<YahooWeatherResponse> getWeather(@Query("q") String yql);
}
