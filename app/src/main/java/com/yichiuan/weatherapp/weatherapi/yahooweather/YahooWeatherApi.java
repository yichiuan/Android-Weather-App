package com.yichiuan.weatherapp.weatherapi.yahooweather;

import android.net.Uri;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.yichiuan.weatherapp.event.ErrorResponseEvent;
import com.yichiuan.weatherapp.event.WeatherInfoEvent;
import com.yichiuan.weatherapp.network.GsonRequest;
import com.yichiuan.weatherapp.weatherapi.WeatherInfo;

import org.greenrobot.eventbus.EventBus;


public class YahooWeatherApi {

    private static final String YAHOO_WEATHER_TAG = "Yahoo";

    private static final String YQL_TEMPLATE = "select * from weather.forecast where woeid in " +
            "(select woeid from geo.places(1) where text=\"(%.6f, %.6f)\")";

    private static final String API_TEMPLATE = "https://query.yahooapis.com/v1/public/yql?q=%s&format=json";

    private RequestQueue requestQueue;

    public YahooWeatherApi(RequestQueue requestQueue) {
        this.requestQueue = requestQueue;
    }

    public void requestWeather(double latitude, double longitude) {

        String YQL = String.format(YQL_TEMPLATE, latitude, longitude);
        String endpoint = String.format(API_TEMPLATE, Uri.encode(YQL));

        GsonRequest weatherRequest = new GsonRequest<>(endpoint,
                YahooWeatherResponse.class, new Response.Listener<YahooWeatherResponse>() {
            @Override
            public void onResponse(YahooWeatherResponse response) {
                Condition condition = response.getChannel().getItem().getCondition();
                WeatherInfo info = new WeatherInfo(condition.getTemp(), condition.getText());
                EventBus.getDefault().post(new WeatherInfoEvent(info));
            }
        }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                EventBus.getDefault().post(new ErrorResponseEvent(error.toString()));
            }
        });

        weatherRequest.setTag(YAHOO_WEATHER_TAG);

        requestQueue.add(weatherRequest);
    }
}
