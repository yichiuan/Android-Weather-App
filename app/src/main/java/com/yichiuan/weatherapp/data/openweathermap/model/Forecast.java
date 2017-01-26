package com.yichiuan.weatherapp.data.openweathermap.model;

import android.support.annotation.Nullable;

import com.google.auto.value.AutoValue;
import com.google.gson.Gson;
import com.google.gson.TypeAdapter;
import com.google.gson.annotations.SerializedName;

import java.util.List;


@AutoValue
public abstract class Forecast {

    @SerializedName("dt")
    public abstract int datatime();

    @SerializedName("dt_txt")
    public abstract String datatimeText();

    public abstract Main main();

    @SerializedName("weather")
    public abstract List<WeatherItem> weathers();

    public abstract Clouds clouds();

    public abstract Wind wind();

    @Nullable
    public abstract Rain rain();

    @Nullable
    public abstract Snow snow();

    public static TypeAdapter<Forecast> typeAdapter(Gson gson) {
        return new AutoValue_Forecast.GsonTypeAdapter(gson);
    }
}