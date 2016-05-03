package com.chalmers.jacobthapps.volume;

/**
 * Created by jacobth on 2016-04-20.
 */
public class Coordinate {
    double lat;
    double lng;

    public Coordinate(double lat, double lng) {
        this.lat = lat;
        this.lng = lng;
    }
    public double getLat() {
        return lat;
    }
    public double getLng() {
        return lng;
    }
}
