package com.yichiuan.weatherapp.data.yahooweather.model;

import com.google.auto.value.AutoValue;
import com.google.gson.Gson;
import com.google.gson.TypeAdapter;


@AutoValue
public abstract class Forecast {

    public abstract int code();
    public abstract String date();
    public abstract String day();
    public abstract int high();
    public abstract int low();
    public abstract String text();

    public static TypeAdapter<Forecast> typeAdapter(Gson gson) {
        return new AutoValue_Forecast.GsonTypeAdapter(gson);
    }
}
