package com.yichiuan.weatherapp.presentation.weather;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.tbruyelle.rxpermissions.RxPermissions;
import com.yichiuan.weatherapp.Injection;
import com.yichiuan.weatherapp.R;
import com.yichiuan.weatherapp.event.PermissionEvent;

import org.greenrobot.eventbus.EventBus;

import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;


public class WeatherActivity extends AppCompatActivity {

    final String LOCATION_PERMISSION = Manifest.permission.ACCESS_FINE_LOCATION;
    private static final int REQUEST_PERMISSION_LOCATION = 1;

    private WeatherPresenter presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_STABLE |
                                                         View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN);

        setContentView(R.layout.activity_weather);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("Android Exercise");
        setSupportActionBar(toolbar);

        ContentFragment fragment =
                (ContentFragment)getSupportFragmentManager().findFragmentById(R.id.content_frame);
        if (fragment == null) {
            fragment = ContentFragment.newInstance();
            getSupportFragmentManager().beginTransaction()
                                       .replace(R.id.content_frame, fragment)
                                       .commit();
        }

        // Create the presenter
        presenter = new WeatherPresenter(
                fragment,
                Injection.provideWeatherRepository(Injection.provideWeatherPrefs(getApplicationContext())),
                Schedulers.io(),
                AndroidSchedulers.mainThread());

        // Load previously saved state, if available.
        if (savedInstanceState != null) {

        } else {
            RxPermissions rxPermissions = new RxPermissions(this);

            rxPermissions.requestEach(Manifest.permission.ACCESS_FINE_LOCATION)
                    .subscribe(permission -> {
                        if (permission.granted) {
                            EventBus.getDefault().postSticky(new PermissionEvent(LOCATION_PERMISSION,
                                    PackageManager.PERMISSION_GRANTED));
                        } else if (permission.shouldShowRequestPermissionRationale) {
                            Snackbar.make(findViewById(R.id.root_layout), R.string.open_location,
                                    Snackbar.LENGTH_LONG).show();
                        }
                        else {
                            Snackbar.make(findViewById(R.id.root_layout), "permission denied",
                                    Snackbar.LENGTH_LONG).show();
                        }
                    });
        }

    }
}