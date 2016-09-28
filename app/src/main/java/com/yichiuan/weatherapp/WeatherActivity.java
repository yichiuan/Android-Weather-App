package com.yichiuan.weatherapp;

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
import com.yichiuan.weatherapp.event.PermissionEvent;


public class WeatherActivity extends AppCompatActivity {

    final String LOCATION_PERMISSION = Manifest.permission.ACCESS_FINE_LOCATION;
    private static final int REQUEST_PERMISSION_LOCATION = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_weather);

        if (getSupportFragmentManager().findFragmentById(R.id.content_frame) == null) {
            getSupportFragmentManager().beginTransaction()
                                       .replace(R.id.content_frame, new ContentFragment())
                                       .commit();
        }

        requestLocationPermission();
    }

    private void requestLocationPermission() {

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
            Log.i("WeatherActivity", "onRequestPermissionsResult granted = " + (grantResult == PackageManager.PERMISSION_GRANTED));
            EventBus.getDefault().postSticky(new PermissionEvent(LOCATION_PERMISSION, grantResult));
        }
    }
}