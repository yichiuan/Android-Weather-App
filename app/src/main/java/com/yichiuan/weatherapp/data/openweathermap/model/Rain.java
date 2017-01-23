package com.yichiuan.weatherapp.data.openweathermap.model;

import com.google.auto.value.AutoValue;
import com.google.gson.Gson;
import com.google.gson.TypeAdapter;
import com.google.gson.annotations.SerializedName;


@AutoValue
public abstract class Rain {

    @SerializedName("3h")
    public abstract float volume3H();

    public static TypeAdapter<Rain> typeAdapter(Gson gson) {
        return new AutoValue_Rain.GsonTypeAdapter(gson);
    }
}