package com.yichiuan.weatherapp.data.yahooweather.model;

import com.google.auto.value.AutoValue;
import com.google.gson.Gson;
import com.google.gson.TypeAdapter;


@AutoValue
public abstract class Wind {

    public abstract short chill();
    public abstract short direction();
    public abstract short speed();

    public static TypeAdapter<Wind> typeAdapter(Gson gson) {
        return new AutoValue_Wind.GsonTypeAdapter(gson);
    }
}
