package com.yichiuan.weatherapp.data.yahooweather;

import java.util.ArrayList;

public class Item {
    private Condition condition;
    private ArrayList<Forecast> forecast;

    public Condition getCondition() {
        return condition;
    }

    public ArrayList<Forecast> getForecast() {
        return forecast;
    }
}
