package com.yichiuan.weatherapp.data.openweathermap.model;

import com.google.auto.value.AutoValue;
import com.google.gson.Gson;
import com.google.gson.TypeAdapter;
import com.google.gson.annotations.SerializedName;


@AutoValue
public abstract class Clouds {

    @SerializedName("all")
    public abstract short all();

    public static TypeAdapter<Clouds> typeAdapter(Gson gson) {
        return new AutoValue_Clouds.GsonTypeAdapter(gson);
    }
}