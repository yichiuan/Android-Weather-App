package com.yichiuan.weatherapp.weatherapi.yahooweather;


public class YahooWeatherResponse {
    private Query query;

    public Channel getChannel() {
        return query.getResults().getChannel();
    }

    static class Query {
        private Result results;

        public Result getResults() {
            return results;
        }
    }

    static class Result {
        private Channel channel;

        public Channel getChannel() {
            return channel;
        }
    }
}
