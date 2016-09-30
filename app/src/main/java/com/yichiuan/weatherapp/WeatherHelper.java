package com.yichiuan.weatherapp;

import com.yichiuan.weatherapp.model.WeatherCode;

public class WeatherHelper {
    public static int getWeatherIconWith(WeatherCode code) {
        int resID = R.string.wi_na;
        switch (code) {
            case TORNADO:
                resID = R.string.wi_tornado; break;
            case TROPICAL_STORM:
            case HURRICANE:
                resID = R.string.wi_hurricane; break;
            case SEVERE_THUNDERSTORMS:
            case THUNDERSTORMS:
                resID = R.string.wi_thunderstorm; break;
            case MIXED_RAIN_SNOW:
            case MIXED_RAIN_SLEET:
            case MIXED_SNOW_SLEET:
                resID = R.string.wi_rain_mix; break;
            case FREEZING_DRIZZLE:
                resID = R.string.wi_hail; break;
            case DRIZZLE:
                resID = R.string.wi_showers; break;
            case FREEZING_RAIN:
                resID = R.string.wi_hail; break;
            case SHOWERS:
            case HEAVY_SHOWERS:
                resID = R.string.wi_showers; break;
            case SNOW_FLURRIES:
                resID = R.string.wi_snow; break;
            case LIGHT_SNOW_SHOWERS:
                resID = R.string.wi_day_snow; break;
            case BLOWING_SNOW:
                resID = R.string.wi_snow_wind; break;
            case SNOW:
                resID = R.string.wi_snow; break;
            case HAIL:
                resID = R.string.wi_hail; break;
            case SLEET:
                resID = R.string.wi_sleet; break;
            case DUST:
                resID = R.string.wi_dust; break;
            case FOGGY:
                resID = R.string.wi_fog; break;
            case HAZE:
                resID = R.string.wi_day_haze; break;
            case SMOKY:
                resID = R.string.wi_smoke; break;
            case BLUSTERY:
                resID = R.string.wi_strong_wind; break;
            case WINDY:
                resID = R.string.wi_windy; break;
            case COLD:
                resID = R.string.wi_snowflake_cold; break;
            case CLOUDY:
                resID = R.string.wi_cloudy; break;
            case MOSTLY_CLOUDY_NIGHT:
                resID = R.string.wi_night_cloudy; break;
            case MOSTLY_CLOUDY_DAY:
                resID = R.string.wi_day_cloudy; break;
            case PARTLY_CLOUDY_NIGHT:
                resID = R.string.wi_night_partly_cloudy; break;
            case PARTLY_CLOUDY_DAY:
                resID = R.string.wi_day_sunny_overcast; break;
            case CLEAR_NIGHT:
                resID = R.string.wi_night_clear; break;
            case SUNNY:
                resID = R.string.wi_day_sunny; break;
            case FAIR_NIGHT:
                resID = R.string.wi_night_partly_cloudy; break;
            case FAIR_DAY:
                resID = R.string.wi_day_sunny_overcast; break;
            case MIXED_RAIN_AND_HAIL:
                resID = R.string.wi_rain_mix; break;
            case HOT:
                resID = R.string.wi_hot; break;
            case ISOLATED_THUNDERSTORMS:
            case SCATTERED_THUNDERSTORMS:
            case SCATTERED_THUNDERSTORMS_1:
                resID = R.string.wi_thunderstorm; break;
            case SCATTERED_SHOWERS:
                resID = R.string.wi_showers; break;
            case HEAVY_SNOW:
                resID = R.string.wi_snow_wind; break;
            case SCATTERED_SNOW_SHOWERS:
                resID = R.string.wi_snow; break;
            case PARTLY_CLOUD:
                resID = R.string.wi_day_sunny_overcast; break;
            case THUNDERSHOWERS:
                resID = R.string.wi_day_storm_showers; break;
            case SNOW_SHOWERS:
                resID = R.string.wi_snow; break;
            case ISOLATED_THUDERSHOWERS:
                resID = R.string.wi_day_storm_showers; break;
            default:
                resID = R.string.wi_na; break;
        }
        return resID;
    }
}
