package com.yichiuan.weatherapp.event;


public class PermissionEvent {

    public String permission;
    public int grantResult;

    public PermissionEvent(String permission, int grantResult) {
        this.permission = permission;
        this.grantResult = grantResult;
    }
}
