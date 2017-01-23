package com.yichiuan.weatherapp.presentation.weather;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.graphics.Typeface;
import android.location.Address;
import android.location.Criteria;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.constraint.ConstraintLayout;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.yichiuan.weatherapp.R;
import com.yichiuan.weatherapp.WeatherHelper;
import com.yichiuan.weatherapp.entity.Weather;
import com.yichiuan.weatherapp.event.PermissionEvent;
import com.yichiuan.weatherapp.event.WeatherApiChangeEvent;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.io.IOException;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import timber.log.Timber;


public class WeatherFragment extends Fragment implements WeatherContract.View {

    private static final String TAG = "WeatherFragment";

    private static final String LOCATION_PERMISSION = Manifest.permission.ACCESS_FINE_LOCATION;

    private static final long TWO_MINUTES = 1000 * 60 * 5;

    private WeatherContract.Presenter presenter;

    private Location currentLocation;

    private LocationManager locationManager;
    private String locationProvider;

    @BindView(R.id.refreshlayout_weather)
    SwipeRefreshLayout refreshLayout;

    @BindView(R.id.textview_weather_temperature)
    TextView temperatureView;

    @BindView(R.id.constraint_layout)
    ConstraintLayout constraintLayout;

    @BindView(R.id.textview_weather_description)
    TextView descriptionView;

    @BindView(R.id.textview_weather_weathericon)
    TextView weatherIconView;

    @BindView(R.id.textview_weather_humidity)
    TextView humidityTextview;

    @BindView(R.id.textview_weather_wind)
    TextView windTextview;

    public static WeatherFragment newInstance() {
        return new WeatherFragment();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);

        locationManager =
                (LocationManager) getActivity().getSystemService(Context.LOCATION_SERVICE);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.content_frag, container, false);
        ButterKnife.bind(this, view);

        refreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                refreshWeather(true);
            }
        });

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
        presenter.stop();
        EventBus.getDefault().unregister(this);
        super.onStop();
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.fragment_weather, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.weather_api:
                WeatherApiDialogFragment.showDialog(getActivity());
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void refreshWeather(boolean forceUpdateLocaton) {

        if (!checkLocationAvailableFromDevice()) {
            ((AppCompatActivity) getActivity()).getSupportActionBar().setTitle("");
            return;
        }

        setRefreshing(true);

        // first time
        if (currentLocation == null) {
            if (receiveLocation(false)) {
                requestWeather();
            }
        } else {
            if (forceUpdateLocaton || isTooOld(currentLocation.getTime())) {
                receiveLocation(true);
            } else {
                requestWeather();
            }
        }
    }

    private boolean checkLocationAvailableFromDevice() {
        if (ContextCompat.checkSelfPermission(getContext(), LOCATION_PERMISSION)
                == PackageManager.PERMISSION_GRANTED) {
            Criteria criteria = new Criteria();
            criteria.setPowerRequirement(Criteria.POWER_LOW);

            locationProvider= locationManager.getBestProvider(criteria, true);
            if (locationProvider == null || locationProvider.equals(LocationManager.PASSIVE_PROVIDER)) {
                Snackbar.make(constraintLayout, "Please enable android location.", Snackbar.LENGTH_LONG)
                        .show();
                return false;
            }

            Timber.tag(TAG).i("bestProvider = " + locationProvider);
            return true;

        } else {
            Snackbar.make(constraintLayout, "No Location Permission", Snackbar.LENGTH_LONG)
                    .show();
            return false;
        }
    }

    /**
     * Receive the location from GPS or Network
     *
     * @return boolean Return true if location is immediately updated.
     */
    @SuppressWarnings("MissingPermission")
    private boolean receiveLocation(boolean forceUpdate) {
        Location location = locationManager.getLastKnownLocation(locationProvider);
        if (location != null) {
            if (forceUpdate || isTooOld(location.getTime())) {
                locationManager.requestSingleUpdate(locationProvider, locationListener, null);
                return false;
            } else {
                currentLocation = location;
                return true;
            }

        } else {
            Timber.tag(TAG).e("LastKnownLocation is null.");
            locationManager.requestSingleUpdate(locationProvider, locationListener, null);
            return false;
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
            currentLocation = location;
            requestWeather();
        }
    };

    private boolean isTooOld(long thisTime) {
        long timeDelta = System.currentTimeMillis() - thisTime;
        return timeDelta > TWO_MINUTES;
    }

    private void showRegion(double latitude, double longitude) {

        StringBuilder region = new StringBuilder();

        if (Geocoder.isPresent()) {
            Geocoder geocoder = new Geocoder(getContext());

            List<Address> addresses = null;
            try {
                addresses = geocoder.getFromLocation(latitude, longitude, 1);
            } catch (IOException e) {
                e.printStackTrace();
            }

            if (addresses != null && addresses.size() > 0) {
                Address address = addresses.get(0);

                String adminArea = address.getAdminArea();
                if (adminArea == null) adminArea = address.getSubAdminArea();

                String locality = address.getLocality();
                if (locality == null) locality = address.getSubLocality();

                if (adminArea == null && locality == null) {
                    region.append(address.getCountryName());
                } else {
                    if (adminArea != null) region.append(adminArea);
                    if (locality != null) region.append(locality);
                }

            } else {
                Timber.e("No address be found.");
            }
        } else {
            Timber.e("No Geocoder");
        }

        ((AppCompatActivity) getActivity()).getSupportActionBar()
                .setTitle(region.toString());
    }

    @Subscribe(sticky = true)
    public void onPermissionEvent(PermissionEvent permissionEvent) {
        if (permissionEvent.grantResult == PackageManager.PERMISSION_GRANTED) {
            refreshWeather(false);
        }
    }

    private void requestWeather() {
        if (currentLocation != null) {
            presenter.requestWeather(currentLocation.getLatitude(), currentLocation.getLongitude());
            showRegion(currentLocation.getLatitude(), currentLocation.getLongitude());
        }
    }

    @Subscribe
    public void onWeatherApiChange(WeatherApiChangeEvent event) {
        presenter.changeWeatherApi(event.type);
        refreshWeather(false);
    }

    @Override
    public void showWeather(Weather weather) {

        if (constraintLayout.getVisibility() != View.VISIBLE) {
            constraintLayout.setVisibility(View.VISIBLE);
        }

        int temperature = Math.round(weather.getTemperature());
        temperatureView.setText(String.valueOf(temperature) + "°");
        descriptionView.setText(weather.getDescription());
        weatherIconView.setText(WeatherHelper.getWeatherIconWith(weather.getWeatherCode()));

        humidityTextview.setText(String.valueOf(weather.getHumidity()) + "%");

        windTextview.setText(String.format("%.1f", weather.getWind().getSpeed()) + " m/s");
    }

    @Override
    public void showErrorMessage(String message) {
        Toast.makeText(getActivity().getApplicationContext(), message, Toast.LENGTH_LONG).show();
    }

    @Override
    public void setRefreshing(boolean enable) {
        refreshLayout.setRefreshing(enable);
    }

    @Override
    public void setPresenter(WeatherContract.Presenter presenter) {
        this.presenter = presenter;
    }
}
