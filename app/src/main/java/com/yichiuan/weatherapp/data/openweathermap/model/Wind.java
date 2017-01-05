package com.yichiuan.weatherapp.data.openweathermap.model;

import com.google.auto.value.AutoValue;
import com.google.gson.Gson;
import com.google.gson.TypeAdapter;


@AutoValue
public abstract class Wind {

    public abstract int deg();

    public abstract float speed();

    public static TypeAdapter<Wind> typeAdapter(Gson gson) {
        return new AutoValue_Wind.GsonTypeAdapter(gson);
    }
}