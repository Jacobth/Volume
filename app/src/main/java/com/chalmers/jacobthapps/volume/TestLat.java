package com.chalmers.jacobthapps.volume;

import net.sf.geographiclib.Geodesic;
import net.sf.geographiclib.PolygonArea;
import net.sf.geographiclib.PolygonResult;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by jacobth on 2016-04-20.
 */
public class TestLat {
    static ArrayList<Coordinate> coordinates;
    static ArrayList<Point> list;

    private final static float QUARTERPI = (float)(Math.PI / 4.0);

    public static void main(String[] args) {
        coordinates = new ArrayList<>();

        coordinates.add(new Coordinate(57.795610, 14.279015));
        coordinates.add(new Coordinate(57.795645, 14.279162));
        coordinates.add(new Coordinate(57.795567, 14.279226));
        coordinates.add(new Coordinate(57.795536, 14.279089));
        list = convert(coordinates);

        double[]lat = new double[4];
        double[]lon = new double[4];
        double r = 6371000;

        PolygonArea p = new PolygonArea(Geodesic.WGS84, false);


        for(int i = 0; i < coordinates.size(); i++) {
            p.AddPoint(coordinates.get(i).getLat(), coordinates.get(i).getLng());
        }

        PolygonResult pr = p.Compute();

        System.out.println("Area:" + Math.abs(pr.area));
    }

    private static ArrayList<Point> convert(List<Coordinate> coordinates) {
        ArrayList<Point> xys = new ArrayList<>();

        return xys;
    }

    private static double haverSine( double x )
    {
        return ( 1.0 - Math.cos( x ) ) / 2.0;
    }

    public static double getArea( double[ ] lat , double[ ] lon , double r )
    {
        double lam1 = 0, lam2 = 0, beta1 =0, beta2 = 0, cosB1 =0, cosB2 = 0;
        double hav = 0;
        double sum = 0;

        for( int j = 0 ; j < lat.length ; j++ )
        {
            int k = j + 1;
            if( j == 0 )
            {
                lam1 = lon[j];
                beta1 = lat[j];
                lam2 = lon[j + 1];
                beta2 = lat[j + 1];
                cosB1 = Math.cos( beta1 );
                cosB2 = Math.cos( beta2 );
            }
            else
            {
                k = ( j + 1 ) % lat.length;
                lam1 = lam2;
                beta1 = beta2;
                lam2 = lon[k];
                beta2 = lat[k];
                cosB1 = cosB2;
                cosB2 = Math.cos( beta2 );
            }
            if( lam1 != lam2 )
            {
                hav = haverSine( beta2 - beta1 ) +
                        cosB1 * cosB2 * haverSine( lam2 - lam1 );
                double a = 2 * Math.asin( Math.sqrt( hav ) );
                double b = Math.PI / 2 - beta2;
                double c = Math.PI / 2 - beta1;
                double s = 0.5 * ( a + b + c );
                double t = Math.tan( s / 2 ) * Math.tan( ( s - a ) / 2 ) *
                        Math.tan( ( s - b ) / 2 ) * Math.tan( ( s - c ) / 2 );

                double excess = Math.abs( 4 * Math.atan( Math.sqrt(
                        Math.abs( t ) ) ) );

                if( lam2 < lam1 )
                {
                    excess = -excess;
                }

                sum += excess;
            }
        }
        return Math.abs( sum )*r*r;
    }

    public static double getArea2( double[ ] lat , double[ ] lon , double r ) {
        double area = 0;
            for (int i = 0; i < lat.length-1; i++) {
                area += toRadians(lon[i+1] - lon[i]) * (2 + Math.sin(toRadians(lat[i])) + Math.sin(toRadians(lat[i+1])));
                area = area / 2;
            }
        return Math.abs(area)*r*r;
    }

    public static double toRadians(double degrees) {
        return degrees * (Math.PI / 180);
    }

    private static double CalculatePolygonArea(ArrayList<Coordinate> coordinates)
    {
        double area = 0;

        if (coordinates.size() > 2)
        {
            for (int i = 0; i < coordinates.size() - 1; i++)
            {
                Coordinate p1 = coordinates.get(i);
                Coordinate p2 = coordinates.get(i+1);
                area += (p2.getLng() - p1.getLng()) * (2 + Math.sin(ConvertToRadian(p1.getLat())) + Math.sin(ConvertToRadian(p2.getLat())));
            }
        }
        area = area * 6378137 * 6378137 / 2;

        return Math.abs(area);
    }

    private static double ConvertToRadian(double input)
    {
        return input * Math.PI / 180;
    }
}
