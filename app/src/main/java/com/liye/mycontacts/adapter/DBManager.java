package com.liye.mycontacts.adapter;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.liye.mycontacts.utils.FestivalInfo;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by tiancz on 2015/3/8.
 */
public class DBManager {
    private DBHelper helper;
    private SQLiteDatabase db;

    public DBManager(Context context){
        helper = new DBHelper(context);
        db = helper.getWritableDatabase();
    }

    public void add(List<FestivalInfo> persons){
        db.beginTransaction();
        try{
            for (FestivalInfo p:persons){
                db.execSQL("INSERT INTO person VALUES(null,?,?,?,?)",
                        new Object[]{p.getMonth(),p.getDay(),p.getKind(),p.getName()});
            }
            db.setTransactionSuccessful();
        }catch(Exception e){
            e.printStackTrace();
        }finally {
            db.endTransaction();
        }
    }

    public void delete(){
        //db.execSQL("delete from person where name='tom'");
    }


    public List<FestivalInfo> findAllPerson(){
        ArrayList<FestivalInfo> persons = new ArrayList<FestivalInfo>();
        Cursor c = db.rawQuery("SELECT * FROM person", null);
        while(c.moveToNext()){
            FestivalInfo p = new FestivalInfo();
            p.set_id(c.getInt(c.getColumnIndex("_id")));
            p.setMonth(c.getInt(c.getColumnIndex("month")));
            p.setDay(c.getInt(c.getColumnIndex("day")));
            p.setKind(c.getInt(c.getColumnIndex("kind")));
            p.setName(c.getString(c.getColumnIndex("name")));
            persons.add(p);
        }
        c.close();
        return persons;
    }

//    public void dropTable(){
//        db.execSQL("drop table person");
//    }

    public void closeDB(){
        db.close();
    }

    public int numDB() {
        Cursor c = db.rawQuery("SELECT * FROM person", null);
        int num = 0;
        if (c.moveToNext()) {
            num += 1;
        }
        c.close();
        return num;
    }


}
