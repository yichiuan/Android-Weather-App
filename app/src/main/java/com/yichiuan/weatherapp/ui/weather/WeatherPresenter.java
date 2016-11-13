package com.yichiuan.weatherapp.ui.weather;


import com.yichiuan.weatherapp.entity.Weather;
import com.yichiuan.weatherapp.data.WeatherRepository;

import rx.Observer;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;
import timber.log.Timber;

public class WeatherPresenter implements WeatherContract.Presenter {

    private final WeatherContract.View weatherView;

    public WeatherPresenter(WeatherContract.View weatherView) {
        this.weatherView = weatherView;

        weatherView.setPresenter(this);
    }

    @Override
    public void requestWeather(double latitude, double longitude) {

        WeatherRepository.getInstance().getWeather(latitude, longitude)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<Weather>() {
                    @Override
                    public void onCompleted() {
                        Timber.i("requestWeather onCompleted");
                    }

                    @Override
                    public void onError(Throwable e) {
                        Timber.e(e);
                        weatherView.showErrorMessage(e.getMessage());
                    }

                    @Override
                    public void onNext(Weather weather) {
                        Timber.i("requestWeather onNext");
                        weatherView.showWeather(weather);
                    }
                });
    }

    @Override
    public void start() {

    }

    @Override
    public void exit() {

    }
}
