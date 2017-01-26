package com.yichiuan.weatherapp.presentation.weather;

import com.yichiuan.weatherapp.data.WeatherRepository;
import com.yichiuan.weatherapp.entity.Weather;
import com.yichiuan.weatherapp.entity.WeatherCode;
import com.yichiuan.weatherapp.entity.Wind;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.io.IOException;

import rx.Observable;
import rx.schedulers.Schedulers;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyDouble;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class WeatherPresenterTest {

    @Mock
    WeatherContract.View view;

    @Mock
    WeatherRepository weatherRepository;

    private WeatherPresenter weatherPresenter;

    @Before
    public void setUp() throws Exception {
        weatherPresenter = new WeatherPresenter(
                view,
                weatherRepository,
                Schedulers.immediate(),
                Schedulers.immediate());
    }

    @Test
    public void requestWeather_ValidLocation_ReturnResults() {
        Weather weather = getDummyWeather();
        when(weatherRepository.getWeather(anyDouble(), anyDouble()))
                .thenReturn(Observable.just(weather));

        weatherPresenter.requestWeather(10, 10);

        verify(view).showWeather(weather);
        verify(view, never()).showErrorMessage(anyString());
    }

    @Test
    public void requestWeather_WeatherRepositoryError_ErrorMsg() {
        String errorMsg = "No internet";
        when(weatherRepository.getWeather(anyDouble(), anyDouble()))
                .thenReturn(Observable.error(new IOException(errorMsg)));

        weatherPresenter.requestWeather(10, 10);

        verify(view, never()).showWeather(any(Weather.class));
        verify(view).showErrorMessage(errorMsg);
    }

    private Weather getDummyWeather() {
        return new Weather(WeatherCode.SUNNY, 20, "Sunny", (byte)55, new Wind(10, 10), null);
    }
}