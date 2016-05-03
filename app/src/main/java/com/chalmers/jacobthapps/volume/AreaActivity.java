package com.chalmers.jacobthapps.volume;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import java.util.ArrayList;

/**
 * Created by jacobth on 2016-04-20.
 */
public class AreaActivity extends Activity{

    ArrayList<Point> list;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        list = (ArrayList<Point>) getIntent().getSerializableExtra("list");
        if(list == null) {
            list = new ArrayList<>();
        }
        setContentView(new PolygonDraw(this, list));
    }
}
