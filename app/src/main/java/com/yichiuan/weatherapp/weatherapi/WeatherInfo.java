package com.yichiuan.weatherapp.weatherapi;

public class WeatherInfo {
    private short temperature;

    public WeatherInfo(short temperature) {
        this.temperature = temperature;
    }

    public int getTemperature() {
        return temperature;
    }

    public void setTemperature(short temperature) {
        this.temperature = temperature;
    }
}
