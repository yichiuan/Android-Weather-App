package com.yichiuan.weatherapp.data.yahooweather.model;

import android.support.annotation.Nullable;

import com.google.auto.value.AutoValue;
import com.google.gson.Gson;
import com.google.gson.TypeAdapter;


@AutoValue
public abstract class Query {

    public abstract short count();
    public abstract String created();
    public abstract String lang();
    @Nullable
    public abstract Result results();

    public static TypeAdapter<Query> typeAdapter(Gson gson) {
        return new AutoValue_Query.GsonTypeAdapter(gson);
    }
}