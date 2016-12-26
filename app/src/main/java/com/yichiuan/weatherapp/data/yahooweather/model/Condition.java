package com.yichiuan.weatherapp.data.yahooweather.model;

import com.google.auto.value.AutoValue;
import com.google.gson.Gson;
import com.google.gson.TypeAdapter;


@AutoValue
public abstract class Condition {

    public abstract short code();
    public abstract String date();
    public abstract short temp();
    public abstract String text();

    public static TypeAdapter<Condition> typeAdapter(Gson gson) {
        return new AutoValue_Condition.GsonTypeAdapter(gson);
    }
}
