package com.yichiuan.weatherapp.ui.weather;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;

import org.greenrobot.eventbus.EventBus;

import com.yichiuan.weatherapp.R;
import com.yichiuan.weatherapp.event.PermissionEvent;

import timber.log.Timber;


public class WeatherActivity extends AppCompatActivity {

    final String LOCATION_PERMISSION = Manifest.permission.ACCESS_FINE_LOCATION;
    private static final int REQUEST_PERMISSION_LOCATION = 1;

    private WeatherPresenter presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_weather);

        ContentFragment fragment =
                (ContentFragment)getSupportFragmentManager().findFragmentById(R.id.content_frame);
        if (fragment == null) {
            fragment = ContentFragment.newInstance();
            getSupportFragmentManager().beginTransaction()
                                       .replace(R.id.content_frame, fragment)
                                       .commit();
        }

        // Create the presenter
        presenter = new WeatherPresenter(fragment);

        // Load previously saved state, if available.
        if (savedInstanceState != null) {

        }

        requestLocationPermission();
    }

    private void requestLocationPermission() {

        if (ContextCompat.checkSelfPermission(this, LOCATION_PERMISSION)
                != PackageManager.PERMISSION_GRANTED) {
            if (ActivityCompat.shouldShowRequestPermissionRationale(this, LOCATION_PERMISSION)) {
                Snackbar.make(findViewById(R.id.root_layout), R.string.open_location, Snackbar.LENGTH_LONG)
                        .setAction(R.string.open, v ->
                            ActivityCompat.requestPermissions(WeatherActivity.this,
                                    new String[]{LOCATION_PERMISSION}, REQUEST_PERMISSION_LOCATION)
                        )
                        .show();
            } else {
                ActivityCompat.requestPermissions(this, new String[]{LOCATION_PERMISSION},
                        REQUEST_PERMISSION_LOCATION);
            }
        } else {
            EventBus.getDefault().postSticky(new PermissionEvent(LOCATION_PERMISSION, PackageManager.PERMISSION_GRANTED));
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == REQUEST_PERMISSION_LOCATION) {
            int grantResult = grantResults[0];
            Timber.tag("WeatherActivity")
                  .i("onRequestPermissionsResult granted = %b", (grantResult == PackageManager.PERMISSION_GRANTED));
            EventBus.getDefault().postSticky(new PermissionEvent(LOCATION_PERMISSION, grantResult));
        }
    }
}