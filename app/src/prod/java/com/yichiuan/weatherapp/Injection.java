package com.yichiuan.weatherapp;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.yichiuan.weatherapp.data.WeatherAdapterFactory;
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

    private static OkHttpClient okHttpClient;

    private static Gson gson;

    public static WeatherRepository provideWeatherRepository() {
        return WeatherRepository.getInstance();
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
}
