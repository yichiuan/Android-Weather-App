package com.yichiuan.weatherapp.data.openweathermap.model;

import com.google.auto.value.AutoValue;
import com.google.gson.Gson;
import com.google.gson.TypeAdapter;
import com.google.gson.annotations.SerializedName;


@AutoValue
public abstract class WeatherItem {

    @SerializedName("id")
    public abstract int conditionCode();

    public abstract String description();

    public abstract String main();

    public static TypeAdapter<WeatherItem> typeAdapter(Gson gson) {
        return new AutoValue_WeatherItem.GsonTypeAdapter(gson);
    }
}