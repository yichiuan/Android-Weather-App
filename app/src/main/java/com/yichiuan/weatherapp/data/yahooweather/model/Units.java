package com.yichiuan.weatherapp.data.yahooweather.model;

import com.google.auto.value.AutoValue;
import com.google.gson.Gson;
import com.google.gson.TypeAdapter;


@AutoValue
public abstract class Units {

    public abstract String distance();
    public abstract String pressure();
    public abstract String speed();
    public abstract char temperature();

    public static TypeAdapter<Units> typeAdapter(Gson gson) {
        return new AutoValue_Units.GsonTypeAdapter(gson);
    }
}
