package com.yichiuan.weatherapp.data.openweathermap.model;

import com.google.auto.value.AutoValue;
import com.google.gson.Gson;
import com.google.gson.TypeAdapter;
import com.google.gson.annotations.SerializedName;


@AutoValue
public abstract class Snow {

    @SerializedName("3h")
    public abstract float volume3H();

    public static TypeAdapter<Snow> typeAdapter(Gson gson) {
        return new AutoValue_Snow.GsonTypeAdapter(gson);
    }
}