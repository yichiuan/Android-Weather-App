package com.yichiuan.weatherapp;

import android.app.Application;
import android.os.StrictMode;
import android.util.Log;

import com.squareup.leakcanary.LeakCanary;
import com.yichiuan.weatherapp.util.StethoHelper;

import org.greenrobot.eventbus.EventBus;

import timber.log.Timber;


public class WeatherApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();

        // StrictMode setup
        if (BuildConfig.DEBUG) {
            StrictMode.setThreadPolicy(new StrictMode.ThreadPolicy.Builder()
                    .detectCustomSlowCalls()
                    .detectDiskReads()
                    .detectDiskWrites()
                    .detectNetwork()
                    .penaltyLog()
                    .build());
            StrictMode.setVmPolicy(new StrictMode.VmPolicy.Builder()
                    .detectLeakedSqlLiteObjects()
                    .detectLeakedClosableObjects()
                    .penaltyLog()
                    .build());
        }

        // LeakCanary init
        if (LeakCanary.isInAnalyzerProcess(this)) {
            return;
        }
        LeakCanary.install(this);

        // Stetho init
        if (BuildConfig.DEBUG) {
            StethoHelper.init(this);
        }

        // Timber init
        if (BuildConfig.DEBUG) {
            Timber.plant(new Timber.DebugTree());
        } else {
            Timber.plant(new FirebaseCrashReportTree());
        }

        EventBus.builder().addIndex(new EventBusIndex()).installDefaultEventBus();
    }

    private static class FirebaseCrashReportTree extends Timber.Tree {
        @Override
        protected void log(int priority, String tag, String message, Throwable throwable) {
            if (priority == Log.VERBOSE || priority == Log.DEBUG) {
                return;
            }
//  Need Firebase Sdk
//            Throwable t = throwable != null ? throwable : new Exception(message);
//            FirebaseCrash.log(message);
//
//            if (t != null) {
//                FirebaseCrash.report(t);
//            }
//        }

        }
    }
}
