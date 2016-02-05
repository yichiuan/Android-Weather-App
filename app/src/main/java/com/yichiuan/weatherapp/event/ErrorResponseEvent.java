package com.yichiuan.weatherapp.event;

public class ErrorResponseEvent {
    public final String message;

    public ErrorResponseEvent(String message) {
        this.message = message;
    }
}
