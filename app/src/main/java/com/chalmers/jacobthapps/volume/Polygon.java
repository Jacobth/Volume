package com.chalmers.jacobthapps.volume;

import java.util.ArrayList;

/**
 * Created by jacobth on 2016-04-20.
 */
public class Polygon {

   /* public static void main(String [] args) {
        ArrayList<Point> list = new ArrayList<>();
        list.add(new Point(4,10));
        list.add(new Point(9,7));
        list.add(new Point(11,2));
        list.add(new Point(2,2));

        double area = getArea(list);
        System.out.println(area);
    }
*/
    public static float getArea(ArrayList<Point> list) {
        float area = 0;
        int j = list.size() -1;

        for(int i = 0; i < list.size(); i++) {
            area = area + (list.get(j).getX()+list.get(i).getX())*(list.get(j).getY()-list.get(i).getY());
            j = i;
        }
        return Math.abs(area/2);
    }
}
