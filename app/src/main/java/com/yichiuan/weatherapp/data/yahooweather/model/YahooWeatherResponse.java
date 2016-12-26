package com.yichiuan.weatherapp.data.yahooweather.model;

import com.google.auto.value.AutoValue;
import com.google.gson.Gson;
import com.google.gson.TypeAdapter;


@AutoValue
public abstract class YahooWeatherResponse {
    public abstract Query query();

    public static TypeAdapter<YahooWeatherResponse> typeAdapter(Gson gson) {
        return new AutoValue_YahooWeatherResponse.GsonTypeAdapter(gson);
    }
}
