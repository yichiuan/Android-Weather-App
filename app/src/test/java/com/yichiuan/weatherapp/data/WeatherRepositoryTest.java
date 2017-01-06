package com.yichiuan.weatherapp.data;

import android.annotation.SuppressLint;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.yichiuan.weatherapp.data.yahooweather.YahooWeatherApi;
import com.yichiuan.weatherapp.data.yahooweather.YahooWeatherService;
import com.yichiuan.weatherapp.data.yahooweather.model.YahooWeatherResponse;
import com.yichiuan.weatherapp.entity.Weather;
import com.yichiuan.weatherapp.entity.WeatherApiSourceType;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.net.URL;

import rx.Observable;
import rx.observers.TestSubscriber;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.within;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;


@RunWith(MockitoJUnitRunner.class)
public class WeatherRepositoryTest {

    private WeatherRepository weatherRepository;

    @Mock
    YahooWeatherService yahooWeatherService;

    @Mock
    WeatherPrefs weatherPrefs;

    @Before
    public void setUp() {
        when(weatherPrefs.getWeatherApiSourceType()).thenReturn(WeatherApiSourceType.YAHOO);
        weatherRepository = WeatherRepository.getInstance(weatherPrefs);
        weatherRepository.setWeatherApi(new YahooWeatherApi(yahooWeatherService));
    }

    @Test
    public void getWeather_OkResponse_obtainCorrectWeather() throws IOException {
        //Given
        when(yahooWeatherService.getWeather(anyString())).
                thenReturn(Observable.just(getFakeWeather()));

        //When
        double latitude = 10.0;
        double longitude = 10.0;
        TestSubscriber<Weather> subscriber = new TestSubscriber<>();
        weatherRepository.getWeather(latitude, longitude).subscribe(subscriber);

        //Then
        subscriber.awaitTerminalEvent();
        subscriber.assertNoErrors();

        Weather weather = subscriber.getOnNextEvents().get(0);
        assertThat(weather.getTemperature())
                .isCloseTo(13.888f, within(0.001f));
    }

    @SuppressLint("NewApi")
    private YahooWeatherResponse getFakeWeather() throws IOException {
        StringBuilder builder = new StringBuilder();
        try (BufferedReader bufferedReader =
                     new BufferedReader(new FileReader(getFileFromPath(this, "weather.json")))) {
            String line;
            while ((line = bufferedReader.readLine()) != null) {
                builder.append(line).append('\n');
            }
        }

        Gson gson = new GsonBuilder()
                .registerTypeAdapterFactory(WeatherAdapterFactory.create())
                .create();

        return gson.fromJson(builder.toString(), YahooWeatherResponse.class);
    }

    private static String getFileFromPath(Object obj, String fileName) {
        ClassLoader classLoader = obj.getClass().getClassLoader();
        URL resource = classLoader.getResource(fileName);
        return resource.getPath();
    }
}
