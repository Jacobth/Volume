package com.chalmers.jacobthapps.volume;

import android.content.Intent;
import android.database.Cursor;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import java.util.ArrayList;

public class SaveActivity extends AppCompatActivity {

    private CustomSaveAdapter adapter;
    private ListView saveList;
    private ArrayList<String> dateList;
    private ArrayList<String> nameList;
    private ArrayList<Integer> imgList;
    private ArrayList<Integer> idList;
    private Database database;
    private Cursor cursor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_save);

        database = new Database(this);

        dateList = new ArrayList<>();
        nameList = new ArrayList<>();
        imgList = new ArrayList<>();
        idList = new ArrayList<>();

        cursor = database.getAllData();

        setList(cursor);

        adapter= new CustomSaveAdapter(this, nameList, imgList, dateList, idList);
        saveList = (ListView)findViewById(R.id.saveList);
        saveList.setAdapter(adapter);

        saveList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                int saveId = idList.get(position);
                Intent i = new Intent(SaveActivity.this, SaveViewActivity.class);
                i.putExtra("id", saveId);
                startActivity(i);
            }
        });
    }

    private void setList(Cursor cursor) {
        while(cursor.moveToNext()) {
            idList.add(Integer.parseInt(cursor.getString(0)));
            dateList.add(cursor.getString(2));
            nameList.add(cursor.getString(1));
            imgList.add(R.mipmap.saveic);
        }
    }
}
