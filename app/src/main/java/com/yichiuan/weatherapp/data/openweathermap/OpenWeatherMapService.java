package com.yichiuan.weatherapp.data.openweathermap;

import com.yichiuan.weatherapp.data.openweathermap.model.ForecastResponse;
import com.yichiuan.weatherapp.data.openweathermap.model.Response;

import retrofit2.http.GET;
import retrofit2.http.Query;
import rx.Observable;

public interface OpenWeatherMapService {
    @GET("weather?units=metric")
    Observable<Response> getWeather(@Query("lat") double latitude,
                                    @Query("lon") double longitude,
                                    @Query("appid") String key);

    @GET("forecast?units=metric")
    Observable<ForecastResponse> getForecasts(@Query("lat") double latitude,
                                              @Query("lon") double longitude,
                                              @Query("appid") String key);
}
