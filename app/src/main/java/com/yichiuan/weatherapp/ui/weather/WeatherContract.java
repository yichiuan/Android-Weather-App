package com.yichiuan.weatherapp.ui.weather;

import com.yichiuan.weatherapp.entity.Weather;
import com.yichiuan.weatherapp.ui.base.BasePresenter;
import com.yichiuan.weatherapp.ui.base.BaseView;

public interface WeatherContract {
    interface View extends BaseView<Presenter> {
        void showWeather(Weather weather);
    }
    interface Presenter extends BasePresenter {
        void loadWeather(double latitude, double longitude);
        void exit();
    }
}
