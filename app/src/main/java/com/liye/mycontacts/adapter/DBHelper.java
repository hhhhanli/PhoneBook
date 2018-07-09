package com.liye.mycontacts.adapter;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by Mr.Zhao on 2018/7/9.
 */

public class DBHelper extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "festival.db";
    private static final int DATABASE_VERSION = 1;
    public DBHelper(Context context){
        super(context,DATABASE_NAME,null,DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db){
        db.execSQL("CREATE TABLE IF NOT EXISTS person(_id INTEGER PRIMARY KEY AUTOINCREMENT," +
                " month INTEGER,day INTEGER, kind INTEGER, name TEXT)");

    }

    @Override
    public void onUpgrade(SQLiteDatabase db,int oldVersion,int newVersion){
        db.execSQL("ALTER TABLE person COLUMN other STRING");
    }
}