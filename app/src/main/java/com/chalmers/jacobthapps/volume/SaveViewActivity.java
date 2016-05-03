package com.chalmers.jacobthapps.volume;

import android.content.Intent;
import android.database.Cursor;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

public class SaveViewActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_save_view);

        TextView volume = (TextView)findViewById(R.id.volumeResultSave);
        TextView area = (TextView)findViewById(R.id.areaResultSave);
        TextView weight = (TextView)findViewById(R.id.weightResultSave);

        Intent i = getIntent();
        int id = i.getExtras().getInt("id");

        Database database = new Database(this);

        Cursor cursor = database.getSaveById(id);

        while(cursor.moveToNext()) {
            volume.setText(cursor.getString(3));
            area.setText(cursor.getString(4));
            weight.setText(cursor.getString(5));
        }
    }
}
