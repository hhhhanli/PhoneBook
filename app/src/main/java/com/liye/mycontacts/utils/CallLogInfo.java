package com.liye.mycontacts.utils;

/**
 * Created by Mr.Zhao on 2018/7/7.
 */

public class CallLogInfo {
    public String name;
    public String number;
    public long date;
    public int type;
    public long calltime;

    public CallLogInfo(String number, long date, int type, String name, long calltime) {
        super();
        this.number = number;
        this.date = date;
        this.type = type;
        this.name = name;
        this.calltime = calltime;
    }

    public long getDate() {
        return date;
    }

    public CallLogInfo() {
        super();
    }
}
