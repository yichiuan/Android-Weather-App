package com.yichiuan.weatherapp.ui.weather;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.graphics.Typeface;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.constraint.ConstraintLayout;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.yichiuan.weatherapp.R;
import com.yichiuan.weatherapp.WeatherHelper;
import com.yichiuan.weatherapp.entity.Weather;
import com.yichiuan.weatherapp.event.PermissionEvent;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import timber.log.Timber;


public class ContentFragment extends Fragment implements WeatherContract.View {

    private static String TAG = "WeatherActivity";

    private static final String LOCATION_PERMISSION = Manifest.permission.ACCESS_FINE_LOCATION;

    private WeatherContract.Presenter presenter;

    @BindView(R.id.temperature_view)
    TextView temperatureView;
    @BindView(R.id.constraint_layout)
    ConstraintLayout constraintLayout;
    @BindView(R.id.description_view)
    TextView descriptionView;
    @BindView(R.id.weathericon_view)
    TextView weatherIconView;

    public static ContentFragment newInstance() {
        return new ContentFragment();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.content_frag, container, false);
        ButterKnife.bind(this, view);
        weatherIconView.setTypeface(Typeface.createFromAsset(getContext().getAssets(), "fonts/weathericons.ttf"));
        return view;
    }

    @Override
    public void onStart() {
        super.onStart();
        EventBus.getDefault().register(this);
        presenter.start();
    }

    @Override
    public void onStop() {
        presenter.exit();
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
                Timber.tag(TAG).i(allprovide);
            }

            String bestProvider = locationManager.getBestProvider(new Criteria(), true);

            if (bestProvider != null) {
                Timber.tag(TAG).i("bestProvider = " + bestProvider);
                Snackbar.make(constraintLayout, "bestProvider = " + bestProvider, Snackbar.LENGTH_INDEFINITE)
                        .show();
            } else {
                Timber.tag(TAG).e("bestProvider is null.");
                Snackbar.make(constraintLayout, "bestProvider is null.", Snackbar.LENGTH_INDEFINITE)
                        .show();
            }

            locationManager.requestLocationUpdates(bestProvider, 0, 0, locationListener);

            Location location = locationManager.getLastKnownLocation(bestProvider);

            if (location != null) {
                presenter.requestWeather(location.getLatitude(), location.getLongitude());
            } else {
                Timber.tag(TAG).e("LastKnownLocation is null.");
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

            LocationManager locationManager =
                    (LocationManager) getActivity().getSystemService(Context.LOCATION_SERVICE);
            locationManager.removeUpdates(locationListener);

            presenter.requestWeather(location.getLatitude(), location.getLongitude());
        }
    };

    @Subscribe(sticky = true)
    public void onPermissionEvent(PermissionEvent permissionEvent) {
        if (permissionEvent.grantResult == PackageManager.PERMISSION_GRANTED) {
            receiveLocation();
        }
    }

    @Override
    public void showWeather(Weather weather) {
        int temperature = Math.round(Weather.convertToCelsius(weather.getTemperature()));
        temperatureView.setText(String.valueOf(temperature) + "°");
        descriptionView.setText(weather.getDescription());
        weatherIconView.setText(WeatherHelper.getWeatherIconWith(weather.getWeatherCode()));
    }

    @Override
    public void showErrorMessage(String message) {
        Toast.makeText(getActivity().getApplicationContext(), message, Toast.LENGTH_LONG).show();
    }

    @Override
    public void setPresenter(WeatherContract.Presenter presenter) {
        this.presenter = presenter;
    }
}
