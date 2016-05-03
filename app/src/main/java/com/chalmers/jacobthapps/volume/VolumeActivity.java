package com.chalmers.jacobthapps.volume;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class VolumeActivity extends AppCompatActivity {

    private Button button;
    private Button savedButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_volume);

        button = (Button) findViewById(R.id.button);
        button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent i = new Intent(VolumeActivity.this, LocationActivity.class);
                startActivity(i);
            }
        });

        savedButton = (Button)findViewById(R.id.savedButtonData);
        savedButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(VolumeActivity.this, SaveActivity.class);
                startActivity(i);
            }
        });
    }
}

//http://math.stackexchange.com/questions/903272/is-it-possible-to-accurately-calculate-an-irregularly-shaped-frustums-volume
