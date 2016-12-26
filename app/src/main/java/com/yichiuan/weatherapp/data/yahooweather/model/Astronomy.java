package com.yichiuan.weatherapp.data.yahooweather.model;

import com.google.auto.value.AutoValue;
import com.google.gson.Gson;
import com.google.gson.TypeAdapter;


@AutoValue
public abstract class Astronomy {

    public abstract String sunrise();
    public abstract String sunset();

    public static TypeAdapter<Astronomy> typeAdapter(Gson gson) {
        return new AutoValue_Astronomy.GsonTypeAdapter(gson);
    }
}