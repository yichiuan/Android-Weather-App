package com.yichiuan.weatherapp.presentation.weather;

import android.app.Dialog;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.FragmentActivity;
import android.support.v7.app.AlertDialog;

import com.yichiuan.weatherapp.Injection;
import com.yichiuan.weatherapp.R;
import com.yichiuan.weatherapp.entity.WeatherApiSourceType;
import com.yichiuan.weatherapp.event.WeatherApiChangeEvent;

import org.greenrobot.eventbus.EventBus;


public class WeatherApiDialogFragment extends DialogFragment {

    public static void showDialog(@Nullable FragmentActivity activity) {
        final WeatherApiDialogFragment dialogFragment = new WeatherApiDialogFragment();
        dialogFragment.show(activity.getSupportFragmentManager(), null);
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        final int currentType = Injection.provideWeatherPrefs(getActivity().getApplicationContext())
                .getWeatherApiSourceType();

        final String[] weatherApis = getResources().getStringArray(R.array.weather_api_source);

        final AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setSingleChoiceItems(weatherApis, currentType, null)
                .setPositiveButton(android.R.string.ok, (dialog, which) -> {
                    int position = ((AlertDialog) dialog).getListView().getCheckedItemPosition();

                    if (currentType == position) return;

                    @WeatherApiSourceType int type = WeatherApiSourceType.YAHOO;
                    switch (position) {
                        case 0: type = WeatherApiSourceType.YAHOO; break;
                        case 1: type = WeatherApiSourceType.OPENWEATHERMAP; break;
                        default: type = WeatherApiSourceType.YAHOO; break;
                    }

                    EventBus.getDefault().post(new WeatherApiChangeEvent(type));
                })
                .setNegativeButton(android.R.string.cancel, (dialog, which) -> dialog.dismiss());
        return builder.create();
    }
}
