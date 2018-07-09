package com.liye.mycontacts.utils;

import java.util.Comparator;

/**
 * Created by Mr.Zhao on 2018/7/9.
 */

public class TimeComparator implements Comparator<CallLogInfo> {
    @Override
    public int compare(CallLogInfo lhs, CallLogInfo rhs) {
        // 返回3个值
        // 1表示前面的大于后面的
        // 0表示相等
        // -1表示前面的小雨后面的
        // return lhs.getName().compareToIgnoreCase(rhs.getName());
        if(lhs.getDate() > rhs.getDate()) {
            return -1;
        }
        else if(lhs.getDate() == rhs.getDate()) {
            return 0;
        }
        else {
            return 1;
        }
    }
}
