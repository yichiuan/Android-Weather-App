package com.yichiuan.weatherapp.entity;


import java.util.List;

public class Weather {
    private WeatherCode weatherCode;
    private float temperature; // °C
    private String description;
    private byte humidity;
    private Wind wind;

    private List<Forecast> forecasts;

    public Weather(WeatherCode weatherCode, float temperature, String description, byte humidity, Wind wind, List<Forecast> forecasts) {
        this.weatherCode = weatherCode;
        this.temperature = temperature;
        this.description = description;
        this.humidity = humidity;
        this.wind = wind;
        this.forecasts = forecasts;
    }

    public WeatherCode getWeatherCode() {
        return weatherCode;
    }

    public void setWeatherCode(WeatherCode weatherCode) {
        this.weatherCode = weatherCode;
    }

    public float getTemperature() {
        return temperature;
    }

    public void setTemperature(float temperature) {
        this.temperature = temperature;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public byte getHumidity() {
        return humidity;
    }

    public void setHumidity(byte humidity) {
        this.humidity = humidity;
    }

    public Wind getWind() {
        return wind;
    }

    public void setWind(Wind wind) {
        this.wind = wind;
    }

    public List<Forecast> getForecasts() {
        return forecasts;
    }

    public void setForecasts(List<Forecast> forecasts) {
        this.forecasts = forecasts;
    }
}
