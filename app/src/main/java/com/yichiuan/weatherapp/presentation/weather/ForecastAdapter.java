package com.yichiuan.weatherapp.presentation.weather;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.yichiuan.weatherapp.R;
import com.yichiuan.weatherapp.WeatherHelper;
import com.yichiuan.weatherapp.entity.Forecast;
import com.yichiuan.weatherapp.presentation.view.CustomFontTextView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;


public class ForecastAdapter extends RecyclerView.Adapter<ForecastAdapter.ViewHolder> {

    private List<Forecast> forecasts;
    private LayoutInflater inflater;

    public ForecastAdapter(Context context, List<Forecast> forecasts) {

        if (forecasts == null) forecasts = new ArrayList<>(0);

        this.forecasts = forecasts;
        inflater = LayoutInflater.from(context);
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ViewHolder(inflater.inflate(R.layout.item_forecast, parent, false));
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {

        Forecast forecast = forecasts.get(position);

        holder.weatherIcon.setText(WeatherHelper.getWeatherIconWith(forecast.getWeatherCode()));
        holder.temperatureTextView.setText(String.format("%.0f / %.0f", forecast.getHigh(), forecast.getLow()));
    }

    @Override
    public int getItemCount() {
        return forecasts.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.textview_forecast_weathericon)
        CustomFontTextView weatherIcon;

        @BindView(R.id.textview_forecast_temp)
        TextView temperatureTextView;

        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

}
