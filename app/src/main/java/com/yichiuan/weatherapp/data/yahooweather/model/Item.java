package com.yichiuan.weatherapp.data.yahooweather.model;

import com.google.auto.value.AutoValue;
import com.google.gson.Gson;
import com.google.gson.TypeAdapter;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;


@AutoValue
public abstract class Item {

    public abstract String title();
    public abstract double lat();
    @SerializedName("long") public abstract double longitude();
    public abstract String pubDate();
    public abstract Condition condition();
    public abstract ArrayList<Forecast> forecast();

    public static TypeAdapter<Item> typeAdapter(Gson gson) {
        return new AutoValue_Item.GsonTypeAdapter(gson);
    }
}
