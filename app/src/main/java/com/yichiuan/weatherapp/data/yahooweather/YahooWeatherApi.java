package com.yichiuan.weatherapp.data.yahooweather;

import android.support.annotation.NonNull;
import android.support.annotation.VisibleForTesting;

import com.yichiuan.weatherapp.data.WeatherException;
import com.yichiuan.weatherapp.data.yahooweather.model.Atmosphere;
import com.yichiuan.weatherapp.data.yahooweather.model.Channel;
import com.yichiuan.weatherapp.data.yahooweather.model.Condition;
import com.yichiuan.weatherapp.data.yahooweather.model.Units;
import com.yichiuan.weatherapp.data.yahooweather.model.Wind;
import com.yichiuan.weatherapp.data.yahooweather.model.YahooWeatherResponse;
import com.yichiuan.weatherapp.entity.Weather;
import com.yichiuan.weatherapp.entity.WeatherCode;
import com.yichiuan.weatherapp.util.TemperatureUtil;

import retrofit2.Retrofit;
import rx.Observable;


public class YahooWeatherApi {

    private static final String YAHOO_WEATHER_TAG = "Yahoo";

    private static final String YQL_TEMPLATE = "select * from weather.forecast where woeid in " +
            "(select woeid from geo.places(1) where text=\"(%.6f, %.6f)\")";

    private YahooWeatherService yahooWeatherService;

    public YahooWeatherApi(Retrofit retrofit) {
        yahooWeatherService = retrofit.create(YahooWeatherService.class);
    }

    public YahooWeatherApi(YahooWeatherService yahooWeatherService) {
        this.yahooWeatherService = yahooWeatherService;
    }

    public Observable<Weather> getWeather(double latitude, double longitude) {
        String yql = String.format(YQL_TEMPLATE, latitude, longitude);

        return yahooWeatherService.getWeather(yql)
                .map(response -> {return processWeather(response);});
    }

    @VisibleForTesting
    @NonNull
    Weather processWeather(YahooWeatherResponse response) {
        if (response.query().results() == null) {
            throw new WeatherException("Can't find weather info.");
        }

        Channel channel = response.query().results().channel();
        Condition condition = channel.item().condition();
        Units units = channel.units();

        float temperature = units.temperature() == 'F' ?
                TemperatureUtil.convertToCelsiusFromFahrenheit(condition.temp()) : condition.temp();

        Atmosphere atmosphere = channel.atmosphere();
        Wind yahooWind = channel.wind();
        com.yichiuan.weatherapp.entity.Wind wind =
                new com.yichiuan.weatherapp.entity.Wind(yahooWind.speed(),
                                                       yahooWind.direction());

        return new Weather(getWeatherCodeFromYahoo(condition.code()),
                                      temperature,
                                      condition.text(),
                                      atmosphere.humidity(),
                                      wind);
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
