package com.yichiuan.weatherapp;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.constraint.ConstraintLayout;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.yichiuan.weatherapp.event.ErrorResponseEvent;
import com.yichiuan.weatherapp.event.PermissionEvent;
import com.yichiuan.weatherapp.event.WeatherInfoEvent;
import com.yichiuan.weatherapp.weatherapi.WeatherService;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;


public class ContentFragment extends Fragment {

    private static final String LOCATION_PERMISSION = Manifest.permission.ACCESS_FINE_LOCATION;

    @BindView(R.id.temperature_view)
    TextView temperatureView;
    @BindView(R.id.constraint_layout)
    ConstraintLayout constraintLayout;
    @BindView(R.id.description_view)
    TextView descriptionView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.content_frag, container, false);
        ButterKnife.bind(this, view);
        return view;
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

    private void receiveLocation() {

        if (ContextCompat.checkSelfPermission(getContext(), LOCATION_PERMISSION)
                == PackageManager.PERMISSION_GRANTED) {

            LocationManager locationManager =
                    (LocationManager) getActivity().getSystemService(Context.LOCATION_SERVICE);

            List<String> allprovides = locationManager.getAllProviders();
            for (String allprovide : allprovides) {
                Log.i("WeatherActivity", allprovide);
            }

            String bestProvider = locationManager.getBestProvider(new Criteria(), true);

            if (bestProvider != null) {
                Log.i("WeatherActivity", "bestProvider = " + bestProvider);
                Snackbar.make(constraintLayout, "bestProvider = " + bestProvider, Snackbar.LENGTH_INDEFINITE)
                        .show();
            } else {
                Log.e("WeatherActivity", "bestProvider is null.");
                Snackbar.make(constraintLayout, "bestProvider is null.", Snackbar.LENGTH_INDEFINITE)
                        .show();
            }

            locationManager.requestLocationUpdates(bestProvider, 0, 0, locationListener);

            Location location = locationManager.getLastKnownLocation(bestProvider);

            if (location != null) {
                WeatherService.getInstance().requestWeather(location.getLatitude(), location.getLongitude());
            } else {
                Log.e("WeatherActivity", "LastKnownLocation is null.");
            }

        } else {
            Snackbar.make(constraintLayout, "沒權限", Snackbar.LENGTH_LONG)
                    .show();
        }
    }

    LocationListener locationListener = new LocationListener() {

        @Override
        public void onStatusChanged(String provider, int status, Bundle extras) {

        }
        @Override
        public void onProviderEnabled(String provider) {

        }
        @Override
        public void onProviderDisabled(String provider) {

        }
        @Override
        public void onLocationChanged(Location location) {

            WeatherService.getInstance().requestWeather(location.getLatitude(), location.getLongitude());
            LocationManager locationManager =
                    (LocationManager) getActivity().getSystemService(Context.LOCATION_SERVICE);
            locationManager.removeUpdates(locationListener);
        }
    };

    @Subscribe
    public void onWeatherInfoEvent(WeatherInfoEvent weatherInfoEvent) {
        temperatureView.setText(String.valueOf(weatherInfoEvent.weatherInfo.getTemperature())+"°");
        descriptionView.setText(weatherInfoEvent.weatherInfo.getDescription());
    }

    @Subscribe
    public void onErrorResponseEvent(ErrorResponseEvent errorEvnet) {
        Toast.makeText(getActivity().getApplicationContext(), errorEvnet.message, Toast.LENGTH_LONG).show();
    }

    @Subscribe(sticky = true)
    public void onPermissionEvent(PermissionEvent permissionEvent) {
        if (permissionEvent.grantResult ==PackageManager.PERMISSION_GRANTED) {
            receiveLocation();
        }
    }
}
