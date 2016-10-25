package com.yichiuan.weatherapp.weatherapi.yahooweather;

import android.net.Uri;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.yichiuan.weatherapp.event.ErrorResponseEvent;
import com.yichiuan.weatherapp.event.WeatherInfoEvent;
import com.yichiuan.weatherapp.entity.Weather;
import com.yichiuan.weatherapp.entity.WeatherCode;
import com.yichiuan.weatherapp.network.GsonRequest;

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
                        Units units = response.getChannel().getUnits();

                        float temperature = units.getTemperature() == 'F' ?
                                condition.getTemp() : Weather.convertToFahrenheit(condition.getTemp());

                        Atmosphere atmosphere = response.getChannel().getAtmosphere();
                        Wind yahooWind = response.getChannel().getWind();
                        com.yichiuan.weatherapp.entity.Wind wind =
                                new com.yichiuan.weatherapp.entity.Wind(yahooWind.getSpeed(),
                                                                       yahooWind.getDirection());

                        Weather weather = new Weather(getWeatherCodeFromYahoo(condition.getCode()),
                                                      temperature,
                                                      condition.getText(),
                                                      atmosphere.getHumidity(),
                                                      wind);

                EventBus.getDefault().post(new WeatherInfoEvent(weather));
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

    private WeatherCode getWeatherCodeFromYahoo(short code) {
        for (WeatherCode weatherCode : WeatherCode.values()) {
            if (weatherCode.getCode() == code) {
                return weatherCode;
            }
        }
        return WeatherCode.NOT_AVAILABLE;
    }
}
