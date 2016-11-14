package com.yichiuan.weatherapp.presentation.weather;

import com.yichiuan.weatherapp.entity.Weather;
import com.yichiuan.weatherapp.presentation.base.BasePresenter;
import com.yichiuan.weatherapp.presentation.base.BaseView;

public interface WeatherContract {
    interface View extends BaseView<Presenter> {
        void showWeather(Weather weather);
        void showErrorMessage(String message);
    }
    interface Presenter extends BasePresenter {
        void requestWeather(double latitude, double longitude);
    }
}
