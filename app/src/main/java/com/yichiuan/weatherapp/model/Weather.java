package com.yichiuan.weatherapp.model;


public class Weather {
    private WeatherCode weatherCode;
    private short temperature; // °C
    private String description;
    private byte humidity;
    private Wind wind;

    public Weather(WeatherCode weatherCode, short temperature, String description, byte humidity, Wind wind) {
        this.weatherCode = weatherCode;
        this.temperature = temperature;
        this.description = description;
        this.humidity = humidity;
        this.wind = wind;
    }

    public WeatherCode getWeatherCode() {
        return weatherCode;
    }

    public void setWeatherCode(WeatherCode weatherCode) {
        this.weatherCode = weatherCode;
    }

    public short getTemperature() {
        return temperature;
    }

    public void setTemperature(short temperature) {
        this.temperature = temperature;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public byte getHumidity() {
        return humidity;
    }

    public void setHumidity(byte humidity) {
        this.humidity = humidity;
    }

    public Wind getWind() {
        return wind;
    }

    public void setWind(Wind wind) {
        this.wind = wind;
    }

    static public class Wind {
        private float speed; // mph
        private float direction;

        public Wind(float speed, float direction) {
            this.speed = speed;
            this.direction = direction;
        }

        public float getSpeed() {
            return speed;
        }

        public void setSpeed(float speed) {
            this.speed = speed;
        }

        public float getDirection() {
            return direction;
        }

        public void setDirection(float direction) {
            this.direction = direction;
        }

    }
}
