package com.yichiuan.weatherapp;

import android.content.Context;
import android.support.annotation.NonNull;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.yichiuan.weatherapp.data.WeatherAdapterFactory;
import com.yichiuan.weatherapp.data.WeatherPrefs;
import com.yichiuan.weatherapp.data.WeatherRepository;
import com.yichiuan.weatherapp.util.StethoHelper;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import timber.log.Timber;

public class Injection {

    private static final int CONNECT_TIMEOUT_SECINDS = 10;

    private static final String YAHOO_WEATHER_API_BASE = "https://query.yahooapis.com/v1/public/";

    private static final String OPENWEATHERMAP_API_BASE = "http://api.openweathermap.org/data/2.5/";

    private static OkHttpClient okHttpClient;

    private static Gson gson;

    public static WeatherPrefs provideWeatherPrefs(@NonNull Context context) {
        return WeatherPrefs.getInstance(context);
    }

    public static WeatherRepository provideWeatherRepository(@NonNull WeatherPrefs weatherPrefs) {
        return WeatherRepository.getInstance(weatherPrefs);
    }

    public static Gson provideGson() {
        if (gson == null) {
            gson = new GsonBuilder()
                    .registerTypeAdapterFactory(WeatherAdapterFactory.create())
                    .create();
        }

        return gson;
    }

    public static OkHttpClient provideOkHttpClient() {
        if (okHttpClient == null) {
            OkHttpClient.Builder httpClientBuilder = new OkHttpClient.Builder();

            if (BuildConfig.DEBUG) {
                HttpLoggingInterceptor logging =
                        new HttpLoggingInterceptor((message) ->
                                Timber.tag("WeatherApi").d(message));
                logging.setLevel(HttpLoggingInterceptor.Level.BASIC);

                httpClientBuilder.addInterceptor(logging);

                StethoHelper.configureInterceptor(httpClientBuilder);
            }

            httpClientBuilder.connectTimeout(CONNECT_TIMEOUT_SECINDS, TimeUnit.SECONDS);
            okHttpClient = httpClientBuilder.build();
        }

        return okHttpClient;
    }

    public static Retrofit provideRetrofitForYahooWeather() {
        return new Retrofit.Builder()
                .baseUrl(YAHOO_WEATHER_API_BASE)
                .client(provideOkHttpClient())
                .addConverterFactory(GsonConverterFactory.create(provideGson()))
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .build();
    }

    public static Retrofit provideRetrofitForOpenWeatherMap() {
        return new Retrofit.Builder()
                .baseUrl(OPENWEATHERMAP_API_BASE)
                .client(provideOkHttpClient())
                .addConverterFactory(GsonConverterFactory.create(provideGson()))
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .build();
    }
}
