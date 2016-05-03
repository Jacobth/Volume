package com.chalmers.jacobthapps.volume;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Path;
import android.view.View;

import java.util.ArrayList;

/**
 * Created by jacobth on 2016-04-20.
 */
public class PolygonDraw extends View{

    public ArrayList<Point> list;

    public PolygonDraw(Context context, ArrayList<Point> list) {
        super(context);

        this.list = list;
      /*  list.add(new Point(4,10));
        list.add(new Point(2,2));
        list.add(new Point(11,2));
        list.add(new Point(9,7));*/
    }

    @Override
    protected void onDraw(Canvas canvas) {
        drawPoly(canvas, list);
    }

    private void drawPoly(Canvas canvas, ArrayList<Point> list) {
        Paint paint = new Paint();
        paint.setColor(Color.GRAY);
        paint.setStyle(Paint.Style.FILL);

        Path path = new Path();
        path.reset(); // only needed when reusing this path for a new build
        path.moveTo(list.get(0).getX(), list.get(0).getY()); // used for first point

        for(int i = 1; i < list.size(); i++) {
            path.lineTo(list.get(i).getX(), list.get(i).getY());
        }
        path.close();
        
        Matrix matrix = new Matrix();
        matrix.setScale(100f, 100f);
        path.transform(matrix);

        canvas.drawPath(path, paint);
    }
}
