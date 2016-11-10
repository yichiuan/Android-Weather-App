package com.yichiuan.weatherapp.weatherapi.yahooweather;

import android.support.annotation.NonNull;

import com.yichiuan.weatherapp.BuildConfig;
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
import rx.schedulers.Schedulers;
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

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(YAHOO_WEATHER_API_BASE)
                .client(client)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .build();

        yahooWeatherService = retrofit.create(YahooWeatherService.class);
    }

    public Observable<Weather> getWeather(double latitude, double longitude) {
        String yql = String.format(YQL_TEMPLATE, latitude, longitude);

        return yahooWeatherService.getWeather(yql)
                .subscribeOn(Schedulers.io())
                .map(response -> {return processWeather(response);});
    }

    @NonNull
    private Weather processWeather(YahooWeatherResponse response) {
        Condition condition = response.getChannel().getItem().getCondition();
        Units units = response.getChannel().getUnits();

        float temperature = units.getTemperature() == 'F' ?
                condition.getTemp() : Weather.convertToFahrenheit(condition.getTemp());

        Atmosphere atmosphere = response.getChannel().getAtmosphere();
        Wind yahooWind = response.getChannel().getWind();
        com.yichiuan.weatherapp.entity.Wind wind =
                new com.yichiuan.weatherapp.entity.Wind(yahooWind.getSpeed(),
                                                       yahooWind.getDirection());

        return new Weather(getWeatherCodeFromYahoo(condition.getCode()),
                                      temperature,
                                      condition.getText(),
                                      atmosphere.getHumidity(),
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
