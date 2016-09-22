package com.yichiuan.weatherapp;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.yichiuan.weatherapp.event.ErrorResponseEvent;
import com.yichiuan.weatherapp.event.WeatherInfoEvent;
import com.yichiuan.weatherapp.weatherapi.WeatherService;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.io.IOException;
import java.util.List;

public class WeatherActivity extends AppCompatActivity {

    private static final int REQUEST_PERMISSION_LOCATION = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_weather);
    }

    @Override
    public void onStart() {
        super.onStart();
        EventBus.getDefault().register(this);

        requestLocationPermission();
    }

    // TODO: requestLocationPermission 包含取得權限及取得定位資訊，函式行為非單一且流程非線性
    //       方法1: 改名為 requestLocationPermissionAndLocation
    //       方法2: 取得權限及取得定位資訊拆開
    private void requestLocationPermission() {

        final String LOCATION_PERMISSION = Manifest.permission.ACCESS_FINE_LOCATION;

        if (ContextCompat.checkSelfPermission(this, LOCATION_PERMISSION)
                    != PackageManager.PERMISSION_GRANTED) {
                if (ActivityCompat.shouldShowRequestPermissionRationale(this, LOCATION_PERMISSION)) {
                    Snackbar.make(findViewById(R.id.root_layout), R.string.open_location, Snackbar.LENGTH_LONG)
                            .setAction(R.string.open, new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    ActivityCompat.requestPermissions(WeatherActivity.this,
                                            new String[]{LOCATION_PERMISSION}, REQUEST_PERMISSION_LOCATION);
                                }
                            })
                            .show();
                } else {
                    ActivityCompat.requestPermissions(this,
                            new String[]{LOCATION_PERMISSION},
                            REQUEST_PERMISSION_LOCATION);
                }
        } else {
            receiveLocation();
        }
    }

    private void receiveLocation() {
        LocationManager locationManager = (LocationManager)getSystemService(Context.LOCATION_SERVICE);

        List<String> allprovides = locationManager.getAllProviders();
        for(String allprovide : allprovides){
            Log.i("WeatherActivity", allprovide);
        }
        Location location = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
        Log.i("WeatherActivity", "Lat:"+location.getLatitude()+" Lon:" + location.getLongitude());

        WeatherService.getInstance().requestWeather(location.getLatitude(), location.getLongitude());

        Geocoder geocoder = new Geocoder(this);
        List<Address> addresses = null;

        try {
            addresses = geocoder.getFromLocation(location.getLatitude(), location.getLongitude(), 1); //放入座標
        } catch (IOException e) {
            e.printStackTrace();
        }

        if (addresses != null && addresses.size() > 0) {
            Address address = addresses.get(0);
            String addressText = String.format("%s-%s%s%s%s",
                    address.getCountryName(), //國家
                    address.getAdminArea(), //城市
                    address.getLocality(), //區
                    address.getThoroughfare(), //路
                    address.getSubThoroughfare() //巷號
            );

            Log.i("WeatherActivity", addressText);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == REQUEST_PERMISSION_LOCATION) {
            int grantResult = grantResults[0];
            boolean granted = grantResult == PackageManager.PERMISSION_GRANTED;
            Log.i("WeatherActivity", "onRequestPermissionsResult granted = " + granted);
            if (granted) {
                receiveLocation();
            }
        }
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
