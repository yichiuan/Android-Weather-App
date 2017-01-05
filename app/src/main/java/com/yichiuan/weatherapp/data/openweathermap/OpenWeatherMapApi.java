package com.yichiuan.weatherapp.data.openweathermap;

import android.support.annotation.NonNull;
import android.support.annotation.VisibleForTesting;

import com.yichiuan.weatherapp.BuildConfig;
import com.yichiuan.weatherapp.data.openweathermap.model.Main;
import com.yichiuan.weatherapp.data.openweathermap.model.Response;
import com.yichiuan.weatherapp.data.openweathermap.model.WeatherItem;
import com.yichiuan.weatherapp.data.openweathermap.model.Wind;
import com.yichiuan.weatherapp.entity.Weather;
import com.yichiuan.weatherapp.entity.WeatherCode;

import retrofit2.Retrofit;
import rx.Observable;


public class OpenWeatherMapApi {

    private static final String key = BuildConfig.OPENWEATHERMAP_API_KEY;

    private OpenWeatherMapService openWeatherMapService;

    public OpenWeatherMapApi(Retrofit retrofit) {
        openWeatherMapService = retrofit.create(OpenWeatherMapService.class);
    }

    public Observable<Weather> getWeather(double latitude, double longitude) {
        return openWeatherMapService.getWeather(latitude, longitude, key)
                .map(response -> {return processWeather(response);});
    }

    @VisibleForTesting
    @NonNull
    Weather processWeather(Response response) {
        WeatherItem weatherItem = response.weathers().get(0);
        Main main = response.main();
        Wind responseWind = response.wind();
        com.yichiuan.weatherapp.entity.Wind wind =
                new com.yichiuan.weatherapp.entity.Wind(responseWind.speed(),
                        responseWind.deg());

        return new Weather(getWeatherCodeFromConditionCode(weatherItem.conditionCode()),
                           main.temp(),
                           weatherItem.description(),
                           main.humidity(),
                           wind);
    }

    private WeatherCode getWeatherCodeFromConditionCode(int conditionCode) {
        switch (conditionCode) {
            // Group 2xx: Thunderstorm
            case 200:
            case 201:
            case 202:
                return WeatherCode.THUNDERSTORMS;
            case 210:
            case 211:
            case 212:
            case 221:
                return WeatherCode.LIGHTNING;
            case 230:
            case 231:
            case 232:
                return WeatherCode.THUNDERSTORMS;

            // Group 3xx: Drizzle
            case 300:
            case 301:
            case 302:
            case 310:
            case 311:
            case 312:
            case 313:
            case 314:
            case 321:
                return WeatherCode.DRIZZLE;

            // Group 5xx: Rain
            case 500:
            case 501:
            case 502:
            case 503:
            case 504:
                return WeatherCode.RAIN;
            case 511:
                return WeatherCode.FREEZING_RAIN;
            case 520:
            case 521:
            case 522:
            case 531:
                return WeatherCode.SHOWERS;

            // Group 6xx: Snow
            case 600:
            case 601:
                return WeatherCode.SNOW;
            case 602:
                return WeatherCode.HEAVY_SNOW;
            case 611:
                return WeatherCode.SLEET;
            case 612:
                return WeatherCode.MIXED_RAIN_SLEET;
            case 615:
            case 616:
            case 620:
            case 621:
            case 622:
                return WeatherCode.MIXED_RAIN_SNOW;

            // Group 7xx: Atmosphere
            case 701:
                return WeatherCode.SNOW;
            case 711:
                return WeatherCode.SMOKY;
            case 721:
                return WeatherCode.HAZE;
            case 731:
            case 751:
            case 761:
                return WeatherCode.DUST;
            case 741:
                return WeatherCode.FOGGY;
            case 762:
                return WeatherCode.VOLCANIC_ASH;
            case 771:
                return WeatherCode.SQUALLS;
            case 781:
                return WeatherCode.TORNADO;
            // Group 800: Clear
            case 800:
                return WeatherCode.CLEAR_SKY;

            // Group 80x: Clouds
            case 801:
                return WeatherCode.PARTLY_CLOUD;
            case 802:
            case 803:
            case 804:
                return WeatherCode.CLOUDY;

            // Group 90x: Extreme
            case 900:
                return WeatherCode.TORNADO;
            case 901:
                return WeatherCode.TROPICAL_STORM;
            case 902:
                return WeatherCode.HURRICANE;
            case 903:
                return WeatherCode.COLD;
            case 904:
                return WeatherCode.HOT;
            case 905:
                return WeatherCode.WINDY;
            case 906:
                return WeatherCode.HAIL;

            // Group 9xx: Additional
            case 951:
                return WeatherCode.CALM;
            case 952:
            case 953:
            case 954:
            case 955:
            case 956:
                return WeatherCode.BREEZE;
            case 957:
            case 958:
            case 959:
                return WeatherCode.GALE;
            case 960:
            case 961:
                return WeatherCode.STORM;
            case 962:
                return WeatherCode.HURRICANE;

            default: return WeatherCode.NOT_AVAILABLE;
        }
    }
}
