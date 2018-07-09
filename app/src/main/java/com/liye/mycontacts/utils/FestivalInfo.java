package com.liye.mycontacts.utils;

public class FestivalInfo {
    private int _id;
    private int month;
    private int day;
    private int kind;//0节假日，1生日
    private String name;//假日表示假日名，生日表示人名
    public FestivalInfo(){}
    public FestivalInfo(int m, int d, int k, String n) {
        month = m;
        day = d;
        kind = k;
        name = n;
    }

    public int get_id() {
        return _id;
    }

    public void set_id(int _id) {
        this._id = _id;
    }

    public int getMonth(){
        return month;
    }
    public void setMonth(int m){
        month = m;
    }

    public int getDay() {
        return day;
    }

    public void setDay(int day) {
        this.day = day;
    }

    public int getKind() {
        return kind;
    }

    public void setKind(int kind) {
        this.kind = kind;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
