package com.yichiuan.weatherapp.presentation.weather;

import com.yichiuan.weatherapp.data.WeatherRepository;
import com.yichiuan.weatherapp.entity.Weather;
import com.yichiuan.weatherapp.entity.WeatherApiSourceType;
import com.yichiuan.weatherapp.presentation.base.BasePresenter;

import rx.Observer;
import rx.Scheduler;
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

        addSubscription(weatherRepository.getWeather(latitude, longitude)
                .subscribeOn(ioScheduler)
                .observeOn(mainScheduler)
                .subscribe(new Observer<Weather>() {
                    @Override
                    public void onCompleted() {
                        Timber.i("requestWeather onCompleted");
                        weatherView.setRefreshing(false);
                    }

                    @Override
                    public void onError(Throwable e) {
                        Timber.e(e);
                        weatherView.showErrorMessage(e.getMessage());
                        weatherView.setRefreshing(false);
                    }

                    @Override
                    public void onNext(Weather weather) {
                        Timber.i("requestWeather onNext");
                        weatherView.showWeather(weather);
                        weatherView.setRefreshing(false);
                    }
                }));
    }

    @Override
    public void changeWeatherApi(@WeatherApiSourceType int type) {
        weatherRepository.changeWeatherApiSourceType(type);
    }
}
