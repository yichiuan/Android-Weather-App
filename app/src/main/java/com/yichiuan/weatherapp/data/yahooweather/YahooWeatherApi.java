package com.yichiuan.weatherapp.data.yahooweather;

import android.support.annotation.NonNull;
import android.support.annotation.VisibleForTesting;

import com.yichiuan.weatherapp.data.WeatherApi;
import com.yichiuan.weatherapp.data.WeatherException;
import com.yichiuan.weatherapp.data.yahooweather.model.Atmosphere;
import com.yichiuan.weatherapp.data.yahooweather.model.Channel;
import com.yichiuan.weatherapp.data.yahooweather.model.Condition;
import com.yichiuan.weatherapp.data.yahooweather.model.Forecast;
import com.yichiuan.weatherapp.data.yahooweather.model.Units;
import com.yichiuan.weatherapp.data.yahooweather.model.Wind;
import com.yichiuan.weatherapp.data.yahooweather.model.YahooWeatherResponse;
import com.yichiuan.weatherapp.entity.Weather;
import com.yichiuan.weatherapp.entity.WeatherCode;
import com.yichiuan.weatherapp.util.UnitUtil;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Single;
import retrofit2.Retrofit;


public class YahooWeatherApi implements WeatherApi {

    private static final String YAHOO_WEATHER_TAG = "Yahoo";

    private static final String YQL_TEMPLATE = "select * from weather.forecast where woeid in " +
            "(select woeid from geo.places(1) where text=\"(%.6f, %.6f)\")";

    private YahooWeatherService yahooWeatherService;

    public YahooWeatherApi(@NonNull Retrofit retrofit) {
        yahooWeatherService = retrofit.create(YahooWeatherService.class);
    }

    public YahooWeatherApi(@NonNull YahooWeatherService yahooWeatherService) {
        this.yahooWeatherService = yahooWeatherService;
    }

    @Override
    public Single<Weather> getWeather(double latitude, double longitude) {
        String yql = String.format(YQL_TEMPLATE, latitude, longitude);

        return yahooWeatherService.getWeather(yql)
                .map(this::processWeather);
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
                UnitUtil.convertToCelsiusFromFahrenheit(condition.temp()) : condition.temp();

        Atmosphere atmosphere = channel.atmosphere();
        Wind yahooWind = channel.wind();

        float windSpeed = units.speed().equals("mph") ?
                UnitUtil.convertToMSFromMPH(yahooWind.speed()) :
                UnitUtil.convertToMSFromKPH(yahooWind.speed());

        com.yichiuan.weatherapp.entity.Wind wind =
                new com.yichiuan.weatherapp.entity.Wind(windSpeed,
                                                       yahooWind.direction());

        List<com.yichiuan.weatherapp.entity.Forecast> forecasts = new ArrayList<>();

        for (Forecast forecast : channel.item().forecast()) {

            com.yichiuan.weatherapp.entity.Forecast forecastEntity =
                    new com.yichiuan.weatherapp.entity.Forecast(
                            getWeatherCodeFromYahoo((short)forecast.code()),
                            forecast.date(),
                            forecast.high(),
                            forecast.low(),
                            forecast.text());
            forecasts.add(forecastEntity);
        }

        return new Weather(getWeatherCodeFromYahoo(condition.code()),
                                      temperature,
                                      condition.text(),
                                      atmosphere.humidity(),
                                      wind,
                                      forecasts);
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
