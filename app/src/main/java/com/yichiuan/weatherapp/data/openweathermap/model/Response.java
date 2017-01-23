package com.yichiuan.weatherapp.data.openweathermap.model;

import android.support.annotation.Nullable;

import com.google.auto.value.AutoValue;
import com.google.gson.Gson;
import com.google.gson.TypeAdapter;
import com.google.gson.annotations.SerializedName;

import java.util.List;


@AutoValue
public abstract class Response {

    public abstract int cod();

    @SerializedName("dt")
    public abstract int datatime();

    @SerializedName("coord")
    public abstract Coordinate coordinate();

    public abstract int visibility();

    @SerializedName("weather")
    public abstract List<WeatherItem> weathers();

    public abstract Main main();

    public abstract Clouds clouds();

    @Nullable
    public abstract Rain rain();

    @Nullable
    public abstract Snow snow();

    @SerializedName("id")
    public abstract int cityId();

    @SerializedName("name")
    public abstract String cityName();

    public abstract Sys sys();

    public abstract Wind wind();

    public static TypeAdapter<Response> typeAdapter(Gson gson) {
        return new AutoValue_Response.GsonTypeAdapter(gson);
    }
}