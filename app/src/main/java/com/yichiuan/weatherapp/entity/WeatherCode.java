package com.yichiuan.weatherapp.entity;


public enum WeatherCode {
    TORNADO(0),
    TROPICAL_STORM(1),
    HURRICANE(2),
    SEVERE_THUNDERSTORMS(3),
    THUNDERSTORMS(4),
    MIXED_RAIN_SNOW(5),
    MIXED_RAIN_SLEET(6),
    MIXED_SNOW_SLEET(7),
    FREEZING_DRIZZLE(8),
    DRIZZLE(9),
    FREEZING_RAIN(10),
    SHOWERS(11),
    HEAVY_SHOWERS(12),
    SNOW_FLURRIES(13),
    LIGHT_SNOW_SHOWERS(14),
    BLOWING_SNOW(15),
    SNOW(16),
    HAIL(17),
    SLEET(18),
    DUST(19),
    FOGGY(20),
    HAZE(21),
    SMOKY(22),
    BLUSTERY(23),
    WINDY(24),
    COLD(25),
    CLOUDY(26),
    MOSTLY_CLOUDY_NIGHT(27),
    MOSTLY_CLOUDY_DAY(28),
    PARTLY_CLOUDY_NIGHT(29),
    PARTLY_CLOUDY_DAY(30),
    CLEAR_NIGHT(31),
    SUNNY(32),
    FAIR_NIGHT(33),
    FAIR_DAY(34),
    MIXED_RAIN_AND_HAIL(35),
    HOT(36),
    ISOLATED_THUNDERSTORMS(37),
    SCATTERED_THUNDERSTORMS(38),
    SCATTERED_THUNDERSTORMS_1(39),
    SCATTERED_SHOWERS(40),
    HEAVY_SNOW(41),
    SCATTERED_SNOW_SHOWERS(42),
    HEAVY_SNOW_1(43),
    PARTLY_CLOUD(44),
    THUNDERSHOWERS(45),
    SNOW_SHOWERS(46),
    ISOLATED_THUDERSHOWERS(47),
    LIGHTNING(48),
    SPRINKLE(49),
    RAIN(50),
    MIST(51),
    VOLCANIC_ASH(52),
    SQUALLS(53),
    CLEAR_SKY(54),

    // Wind
    CALM(200),
    BREEZE(201),
    GALE(202),
    STORM(203),

    NOT_AVAILABLE(10000);

    private final int code;

    WeatherCode(int code) {
        this.code = code;
    }

    public int getCode() {
        return this.code;
    }
}
