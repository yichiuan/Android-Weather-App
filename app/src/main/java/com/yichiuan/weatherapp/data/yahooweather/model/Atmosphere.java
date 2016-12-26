package com.yichiuan.weatherapp.data.yahooweather.model;

import com.google.auto.value.AutoValue;
import com.google.gson.Gson;
import com.google.gson.TypeAdapter;


@AutoValue
public abstract class Atmosphere {

    public abstract byte humidity();
    public abstract float pressure();
    public abstract byte rising();
    public abstract float visibility();

    public static TypeAdapter<Atmosphere> typeAdapter(Gson gson) {
        return new AutoValue_Atmosphere.GsonTypeAdapter(gson);
    }
}
