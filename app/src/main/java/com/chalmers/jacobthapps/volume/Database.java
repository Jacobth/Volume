package com.chalmers.jacobthapps.volume;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by jacobth on 2016-05-01.
 */
public class Database extends SQLiteOpenHelper{

    public static final String DATABASE_NAME = "volumes.db";
    public static final String TABLE_NAME = "data_table";
    public static final String COL_1 = "ID";
    public static final String COL_2 = "NAME";
    public static final String COL_3 = "DATETIME";
    public static final String COL_4 = "VOLUME";
    public static final String COL_5 = "AREA";
    public static final String COL_6 = "WEIGHT";

    public Database(Context context) {
        super(context, DATABASE_NAME, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE " + TABLE_NAME + " (ID INTEGER PRIMARY KEY AUTOINCREMENT,NAME TEXT,DATETIME TEXT,VOLUME TEXT,AREA TEXT,WEIGHT TEXT)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }

    public boolean insertData(String name, String date, String volume, String area, String weight) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues =  new ContentValues();
        contentValues.put(COL_2, name);
        contentValues.put(COL_3, date);
        contentValues.put(COL_4, volume);
        contentValues.put(COL_5, area);
        contentValues.put(COL_6, weight);

        long result = db.insert(TABLE_NAME, null, contentValues);

        return result >= 0;
    }

    public Cursor getAllData() {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM " + TABLE_NAME, null);
        return cursor;
    }

    public Cursor getSaveById(int id) {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM " + TABLE_NAME + " WHERE ID=" + id, null);
        return cursor;
    }

    public void deleteSave(int id) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("DELETE FROM " + TABLE_NAME + " WHERE ID =" + id);
    }
}