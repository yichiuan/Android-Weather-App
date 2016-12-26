package com.yichiuan.weatherapp.data.yahooweather.model;

import com.google.auto.value.AutoValue;
import com.google.gson.Gson;
import com.google.gson.TypeAdapter;


@AutoValue
public abstract class Location {

    public abstract String city();
    public abstract String country();
    public abstract String region();

    public static TypeAdapter<Location> typeAdapter(Gson gson) {
        return new AutoValue_Location.GsonTypeAdapter(gson);
    }
}
