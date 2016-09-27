package com.yichiuan.weatherapp.weatherapi;

public class WeatherInfo {
    private short temperature;
    private  String description;

    public WeatherInfo(short temperature, String description) {
        this.temperature = temperature;
        this.description = description;
    }

    public int getTemperature() {
        return temperature;
    }

    public void setTemperature(short temperature) {
        this.temperature = temperature;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
