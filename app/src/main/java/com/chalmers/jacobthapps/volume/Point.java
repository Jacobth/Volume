package com.chalmers.jacobthapps.volume;

import java.io.Serializable;

/**
 * Created by jacobth on 2016-04-20.
 */
public class Point implements Serializable{
    float x;
    float y;

    public Point(float x, float y) {
        this.x = x;
        this.y = y;
    }

    public float getX() {
        return x;
    }

    public float getY() {
        return y;
    }
}
