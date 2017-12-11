package com.yichiuan.weatherapp.data.openweathermap;

import com.yichiuan.weatherapp.data.openweathermap.model.ForecastResponse;
import com.yichiuan.weatherapp.data.openweathermap.model.Response;

import io.reactivex.Single;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface OpenWeatherMapService {
    @GET("weather?units=metric")
    Single<Response> getWeather(@Query("lat") double latitude,
                                @Query("lon") double longitude,
                                @Query("appid") String key);

    @GET("forecast?units=metric")
    Single<ForecastResponse> getForecasts(@Query("lat") double latitude,
                                              @Query("lon") double longitude,
                                              @Query("appid") String key);
}
