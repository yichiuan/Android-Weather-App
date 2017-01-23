package com.yichiuan.weatherapp.presentation.weather;

import com.yichiuan.weatherapp.entity.Weather;
import com.yichiuan.weatherapp.entity.WeatherApiSourceType;
import com.yichiuan.weatherapp.presentation.base.BaseView;
import com.yichiuan.weatherapp.presentation.base.MvpPresenter;

public interface WeatherContract {
    interface View extends BaseView<Presenter> {
        void showWeather(Weather weather);
        void showErrorMessage(String message);
        void setRefreshing(boolean enable);
    }
    interface Presenter extends MvpPresenter {
        void requestWeather(double latitude, double longitude);
        void changeWeatherApi(@WeatherApiSourceType int type);
    }
}
