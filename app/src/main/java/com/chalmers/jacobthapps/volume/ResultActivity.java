package com.chalmers.jacobthapps.volume;

import android.app.Activity;
import android.os.Bundle;
import android.widget.TextView;


public class ResultActivity extends Activity{

    private TextView volumeText;
    private TextView weightText;
    private TextView areaText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.result);

        volumeText = (TextView)findViewById(R.id.volumeText);
        weightText = (TextView)findViewById(R.id.weightText);
        areaText = (TextView)findViewById(R.id.areaText);
    }
}
