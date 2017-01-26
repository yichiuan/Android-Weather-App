package com.yichiuan.weatherapp.entity;


public class Forecast {
    private WeatherCode weatherCode;
    private String date;
    private float high;
    private float low;
    private String description;

    public Forecast(WeatherCode weatherCode, String date, float high, float low, String description) {
        this.weatherCode = weatherCode;
        this.date = date;
        this.high = high;
        this.low = low;
        this.description = description;
    }

    public WeatherCode getWeatherCode() {
        return weatherCode;
    }

    public void setWeatherCode(WeatherCode weatherCode) {
        this.weatherCode = weatherCode;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public float getHigh() {
        return high;
    }

    public void setHigh(float high) {
        this.high = high;
    }

    public float getLow() {
        return low;
    }

    public void setLow(float low) {
        this.low = low;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
