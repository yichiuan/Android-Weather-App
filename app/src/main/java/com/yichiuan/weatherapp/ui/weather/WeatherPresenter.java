package com.yichiuan.weatherapp.ui.weather;


import com.yichiuan.weatherapp.event.WeatherInfoEvent;
import com.yichiuan.weatherapp.weatherapi.WeatherService;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

public class WeatherPresenter implements WeatherContract.Presenter {

    private final WeatherContract.View weatherView;

    public WeatherPresenter(WeatherContract.View weatherView) {
        this.weatherView = weatherView;

        weatherView.setPresenter(this);
    }

    @Override
    public void loadWeather(double latitude, double longitude) {
        WeatherService.getInstance().requestWeather(latitude, longitude);
    }

    @Override
    public void start() {
        EventBus.getDefault().register(this);
    }

    @Override
    public void exit() {
        EventBus.getDefault().unregister(this);
    }

    @Subscribe
    public void onWeatherInfoEvent(WeatherInfoEvent weatherInfoEvent) {
        weatherView.showWeather(weatherInfoEvent.weather);
    }
}
