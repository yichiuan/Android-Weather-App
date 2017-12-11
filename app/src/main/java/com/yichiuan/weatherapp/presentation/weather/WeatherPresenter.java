package com.yichiuan.weatherapp.presentation.weather;

import com.yichiuan.weatherapp.data.WeatherRepository;
import com.yichiuan.weatherapp.entity.WeatherApiSourceType;
import com.yichiuan.weatherapp.presentation.base.BasePresenter;

import io.reactivex.Scheduler;
import timber.log.Timber;

public class WeatherPresenter extends BasePresenter implements WeatherContract.Presenter {

    private final WeatherContract.View weatherView;

    private final WeatherRepository weatherRepository;

    private final Scheduler ioScheduler;
    private final Scheduler mainScheduler;

    public WeatherPresenter(WeatherContract.View weatherView,
                            WeatherRepository weatherRepository,
                            Scheduler ioScheduler,
                            Scheduler mainScheduler) {
        this.weatherView = weatherView;
        this.weatherRepository = weatherRepository;
        this.ioScheduler = ioScheduler;
        this.mainScheduler = mainScheduler;
        weatherView.setPresenter(this);
    }

    @Override
    public void requestWeather(double latitude, double longitude) {

        addDisposable(weatherRepository.getWeather(latitude, longitude)
                .subscribeOn(ioScheduler)
                .observeOn(mainScheduler)
                .subscribe(
                        weather -> {
                            Timber.i("requestWeather onSuccess");
                            weatherView.showWeather(weather);
                            weatherView.setRefreshing(false);
                        },
                        throwable -> {
                            Timber.e(throwable);
                            weatherView.showErrorMessage(throwable.getMessage());
                            weatherView.setRefreshing(false);
                        }
                ));
    }

    @Override
    public void changeWeatherApi(@WeatherApiSourceType int type) {
        weatherRepository.changeWeatherApiSourceType(type);
    }
}
