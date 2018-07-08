package com.liye.mycontacts.utils;

import android.content.ContentResolver;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.provider.CallLog;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Mr.Zhao on 2018/7/7.
 */

public class ContactsMsgUtils {
    Context mContext;
    ContentResolver mContentResolver;

    public ContactsMsgUtils(Context context) {
        this.mContext = context;
        mContentResolver = mContext.getContentResolver();

    }
    /*
     * 查询通话记录
     */
    public List<CallLogInfo> select() {
        List<CallLogInfo> infos = new ArrayList<CallLogInfo>();
        Uri uri = CallLog.Calls.CONTENT_URI;
        String[] projection = new String[]{CallLog.Calls.NUMBER, CallLog.Calls.DATE,
                CallLog.Calls.TYPE, CallLog.Calls.CACHED_NAME, CallLog.Calls.DURATION };
        Cursor cursor = mContentResolver.query(uri, projection, null, null, null);
        while (cursor.moveToNext()) {
            String number = cursor.getString(0);
            long date = cursor.getLong(1);
            int type = cursor.getInt(2);
            String name = cursor.getString(3);
            long calltime = cursor.getLong(4);
            infos.add(new CallLogInfo(number, date, type, name, calltime));
            System.out.println(number + " " + date + " " + type + " " + name + " " + calltime);
        }
        cursor.close();
        return infos;
    }
}
