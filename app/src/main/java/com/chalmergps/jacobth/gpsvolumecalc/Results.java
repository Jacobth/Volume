package com.chalmergps.jacobth.gpsvolumecalc;

import java.math.BigDecimal;
import java.math.RoundingMode;

/**
 * Created by jacobth on 2016-05-01.
 */
public class Results {

    private double area;
    private double height;
    private double density;
    private double angle;

    public Results(double area, double height, double density, double angle) {
        this.area = area;
        this.height = height;
        this.density = density;
        this.angle = angle;
    }

    private double getTopArea() {
        return area * Math.sin(degreeToRadians(angle));
    }

    public double getArea() {
        return round(area, 3);
    }

    public double getVolume() {
        double vol = (height / 3) * (area + getTopArea() + Math.sqrt(area * getTopArea()));

        return round(vol, 3);
    }

    public double getWeight() {
        return round(getVolume() * density, 3);
    }

    private double degreeToRadians(double angle) {
        return (angle * Math.PI) / 180.0;
    }

    private double round(double value, int places) {
        if (places < 0) throw new IllegalArgumentException();

        BigDecimal bd = new BigDecimal(value);
        bd = bd.setScale(places, RoundingMode.HALF_UP);
        return bd.doubleValue();
    }
}