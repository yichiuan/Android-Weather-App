package com.yichiuan.weatherapp;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

import com.yichiuan.weatherapp.event.ErrorResponseEvent;
import com.yichiuan.weatherapp.event.WeatherInfoEvent;
import com.yichiuan.weatherapp.weatherapi.WeatherService;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

public class WeatherActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_weather);

        WeatherService.getInstance().requestWeather(25.033968, 121.564793);
    }

    @Override
    public void onStart() {
        super.onStart();
        EventBus.getDefault().register(this);
    }

    @Override
    public void onStop() {
        EventBus.getDefault().unregister(this);
        super.onStop();
    }

    @Subscribe
    public void onWeatherInfoEvent(WeatherInfoEvent weatherInfoEvent) {

        String weatherDescription = "The temperature is " +
                weatherInfoEvent.weatherInfo.getTemperature();

        Toast.makeText(this, weatherDescription, Toast.LENGTH_LONG).show();
    }

    @Subscribe
    public void onErrorResponseEvent(ErrorResponseEvent errorEvnet) {
        Toast.makeText(this, errorEvnet.message, Toast.LENGTH_LONG).show();
    }
}
