package com.yichiuan.weatherapp.util;


public class TemperatureUtil {
    public static float convertToFahrenheitFromCelsius(float celsius) {
        return celsius * 1.8f + 32;
    }

    public static float convertToCelsiusFromFahrenheit(float fahrenheit) {
        return (fahrenheit - 32) * 5 / 9;
    }
}
