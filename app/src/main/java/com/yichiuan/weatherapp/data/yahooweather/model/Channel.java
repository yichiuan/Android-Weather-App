package com.yichiuan.weatherapp.data.yahooweather.model;

import com.google.auto.value.AutoValue;
import com.google.gson.Gson;
import com.google.gson.TypeAdapter;


@AutoValue
public abstract class Channel {

    public abstract Units units();
    public abstract String title();
    public abstract String language();
    public abstract String lastBuildDate();
    public abstract int ttl();
    public abstract Location location();
    public abstract Wind wind();
    public abstract Atmosphere atmosphere();
    public abstract Astronomy astronomy();
    public abstract Item item();

    public static TypeAdapter<Channel> typeAdapter(Gson gson) {
        return new AutoValue_Channel.GsonTypeAdapter(gson);
    }
}
