package com.techgarden.navi.model;

public class LocationRequest {
    private long device;
    private double longitude;
    private double latitude;

    public LocationRequest() {
    }

    public LocationRequest(long device, double longitude, double latitude) {
        this.device = device;
        this.longitude = longitude;
        this.latitude = latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public long getDevice() {
        return device;
    }

    public void setDevice(long device) {
        this.device = device;
    }
}
