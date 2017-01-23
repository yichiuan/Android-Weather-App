package com.yichiuan.weatherapp.data.openweathermap.model;

import com.google.auto.value.AutoValue;
import com.google.gson.Gson;
import com.google.gson.TypeAdapter;
import com.google.gson.annotations.SerializedName;


@AutoValue
public abstract class Main {

    @SerializedName("temp")
    public abstract float temp();

    @SerializedName("temp_min")
    public abstract float tempMin();

    @SerializedName("temp_max")
    public abstract float tempMax();

    @SerializedName("humidity")
    public abstract byte humidity();

    @SerializedName("pressure")
    public abstract float pressure();

    public static TypeAdapter<Main> typeAdapter(Gson gson) {
        return new AutoValue_Main.GsonTypeAdapter(gson);
    }
}