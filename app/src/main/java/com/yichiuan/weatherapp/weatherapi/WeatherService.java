package com.yichiuan.weatherapp.weatherapi;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.BasicNetwork;
import com.android.volley.toolbox.HurlStack;
import com.android.volley.toolbox.NoCache;

import com.yichiuan.weatherapp.weatherapi.yahooweather.YahooWeatherApi;

public class WeatherService {

    private volatile static WeatherService instance;
    private RequestQueue requestQueue;
    private YahooWeatherApi weatherApi;

    private WeatherService() {
        requestQueue = new RequestQueue(new NoCache(), new BasicNetwork(new HurlStack()));
        requestQueue.start();

        weatherApi = new YahooWeatherApi(requestQueue);
    }

    public static WeatherService getInstance() {
        if (instance == null) {
            synchronized(WeatherService.class) {
                if (instance == null) {
                    instance = new WeatherService();
                }
            }
        }
        return instance;
    }

    public void requestWeather(double latitude, double longitude) {
        weatherApi.requestWeather(latitude, longitude);
    }
}
