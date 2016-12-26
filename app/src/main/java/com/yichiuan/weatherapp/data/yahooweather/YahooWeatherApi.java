package com.yichiuan.weatherapp.data.yahooweather;

import android.support.annotation.NonNull;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.yichiuan.weatherapp.BuildConfig;
import com.yichiuan.weatherapp.data.WeatherAdapterFactory;
import com.yichiuan.weatherapp.data.yahooweather.model.Atmosphere;
import com.yichiuan.weatherapp.data.yahooweather.model.Channel;
import com.yichiuan.weatherapp.data.yahooweather.model.Condition;
import com.yichiuan.weatherapp.data.yahooweather.model.Units;
import com.yichiuan.weatherapp.data.yahooweather.model.Wind;
import com.yichiuan.weatherapp.data.yahooweather.model.YahooWeatherResponse;
import com.yichiuan.weatherapp.entity.Weather;
import com.yichiuan.weatherapp.entity.WeatherCode;
import com.yichiuan.weatherapp.util.StethoHelper;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import rx.Observable;
import timber.log.Timber;


public class YahooWeatherApi {

    private static final String YAHOO_WEATHER_TAG = "Yahoo";

    private static final String YQL_TEMPLATE = "select * from weather.forecast where woeid in " +
            "(select woeid from geo.places(1) where text=\"(%.6f, %.6f)\")";

    private static final String YAHOO_WEATHER_API_BASE= "https://query.yahooapis.com/v1/public/";

    private static final int ONNECT_TIMEOUT_SECINDS = 10;

    private YahooWeatherService yahooWeatherService;

    public YahooWeatherApi() {
        OkHttpClient.Builder httpClientBuilder = new OkHttpClient.Builder();

        if (BuildConfig.DEBUG) {
            HttpLoggingInterceptor logging =
                    new HttpLoggingInterceptor((message) ->
                            Timber.tag("YahooWeatherApi").d(message));
            logging.setLevel(HttpLoggingInterceptor.Level.BASIC);

            httpClientBuilder.addInterceptor(logging);

            StethoHelper.configureInterceptor(httpClientBuilder);
        }

        httpClientBuilder.connectTimeout(ONNECT_TIMEOUT_SECINDS, TimeUnit.SECONDS);

        OkHttpClient client = httpClientBuilder.build();

        Gson gson = new GsonBuilder()
                .registerTypeAdapterFactory(WeatherAdapterFactory.create())
                .create();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(YAHOO_WEATHER_API_BASE)
                .client(client)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .build();

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

    @NonNull
    private Weather processWeather(YahooWeatherResponse response) {
        Channel channel = response.query().results().channel();
        Condition condition = channel.item().condition();
        Units units = channel.units();

        float temperature = units.temperature() == 'F' ?
                condition.temp() : Weather.convertToFahrenheit(condition.temp());

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
