package com.yichiuan.weatherapp.util;


public class UnitUtil {
    public static float convertToFahrenheitFromCelsius(float celsius) {
        return celsius * 1.8f + 32;
    }

    public static float convertToCelsiusFromFahrenheit(float fahrenheit) {
        return (fahrenheit - 32) * 5 / 9;
    }

    public static float convertToMSFromMPH(float mph) {
        return mph * 0.44704f;
    }

    public static float convertToMSFromKPH(float kph) {
        return kph * 0.27778f;
    }
}
