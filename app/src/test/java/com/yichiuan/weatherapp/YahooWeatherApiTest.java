package com.yichiuan.weatherapp;

import com.google.gson.Gson;
import com.yichiuan.weatherapp.data.yahooweather.model.Atmosphere;
import com.yichiuan.weatherapp.data.yahooweather.model.Channel;
import com.yichiuan.weatherapp.data.yahooweather.model.Condition;
import com.yichiuan.weatherapp.data.yahooweather.model.Forecast;
import com.yichiuan.weatherapp.data.yahooweather.model.Item;
import com.yichiuan.weatherapp.data.yahooweather.model.Wind;
import com.yichiuan.weatherapp.data.yahooweather.model.YahooWeatherResponse;


import org.junit.Test;

import java.net.URLEncoder;
import java.util.ArrayList;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

import static org.hamcrest.CoreMatchers.*;
import static org.junit.Assert.*;


public class YahooWeatherApiTest {

    OkHttpClient client = new OkHttpClient();
    Gson gson = new Gson();

    String yqlTemplate = "select * from weather.forecast where woeid in (select woeid from geo.places(1) where text=\"%s\")";
    String apiTemplate = "https://query.yahooapis.com/v1/public/yql?q=%s&format=json";

    @Test
    public void testYahooWeatherData() throws Exception {

        // the location of Taipei 101
        String location = "(25.033968, 121.564793)";
        String YQL = String.format(yqlTemplate, location);
        String endpoint = String.format(apiTemplate, URLEncoder.encode(YQL, "utf-8"));

        Request request = new Request.Builder()
                .url(endpoint)
                .build();

        Response response = client.newCall(request).execute();

        YahooWeatherResponse weatherResponse = gson.fromJson(response.body().string(),
                YahooWeatherResponse.class);

        Channel channel = weatherResponse.query().results().channel();
        assertThat(channel, notNullValue());

        Wind wind = channel.wind();
        assertThat(wind, notNullValue());

        Atmosphere atmosphere = channel.atmosphere();
        assertThat(atmosphere, notNullValue());

        Item item = channel.item();
        assertThat(item, notNullValue());

        Condition condition = item.condition();
        assertThat(condition, notNullValue());

        ArrayList<Forecast> forecasts = item.forecast();
        assertThat(forecasts, notNullValue());
    }
}