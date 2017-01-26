package com.yichiuan.weatherapp.data.openweathermap.model;

import com.google.auto.value.AutoValue;
import com.google.gson.Gson;
import com.google.gson.TypeAdapter;
import com.google.gson.annotations.SerializedName;

import java.util.List;


@AutoValue
public abstract class ForecastResponse {

    @SerializedName("list")
    public abstract List<Forecast> forecasts();

    public static TypeAdapter<ForecastResponse> typeAdapter(Gson gson) {
        return new AutoValue_ForecastResponse.GsonTypeAdapter(gson);
    }
}