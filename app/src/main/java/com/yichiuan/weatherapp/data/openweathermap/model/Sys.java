package com.yichiuan.weatherapp.data.openweathermap.model;

import com.google.auto.value.AutoValue;
import com.google.gson.Gson;
import com.google.gson.TypeAdapter;
import com.google.gson.annotations.SerializedName;


@AutoValue
public abstract class Sys {

    public abstract String country();

    public abstract int sunrise();

    public abstract int sunset();

    public static TypeAdapter<Sys> typeAdapter(Gson gson) {
        return new AutoValue_Sys.GsonTypeAdapter(gson);
    }
}