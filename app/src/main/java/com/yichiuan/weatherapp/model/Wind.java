package com.yichiuan.weatherapp.model;

public class Wind {
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
