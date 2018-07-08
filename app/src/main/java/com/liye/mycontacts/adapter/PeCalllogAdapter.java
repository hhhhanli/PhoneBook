package com.liye.mycontacts.adapter;

import android.content.Context;
import android.graphics.Color;
import android.provider.CallLog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.liye.mycontacts.R;
import com.liye.mycontacts.utils.CallLogInfo;

import java.text.SimpleDateFormat;
import java.util.List;

/**
 * Created by Mr.Zhao on 2018/7/8.
 */

public class PeCalllogAdapter extends BaseAdapter {
    public List<CallLogInfo> infos;
    Context mContext;

    public PeCalllogAdapter(Context context, List<CallLogInfo> infos) {
        super();
        this.infos = infos;
        this.mContext = context;
    }

    @Override
    public int getCount() {
        return infos.size();
    }

    @Override
    public Object getItem(int position) {

        return infos.get(position);
    }

    @Override
    public long getItemId(int position) {

        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.item_pecalllog, null);
        TextView tv_duration = (TextView) view.findViewById(R.id.ptv_duration);
        TextView tv_date = (TextView) view.findViewById(R.id.ptv_date);
        TextView tv_type = (TextView) view.findViewById(R.id.ptv_type);
        CallLogInfo info = infos.get(position);
        tv_duration.setText(String.valueOf(info.calltime));
        SimpleDateFormat format = new SimpleDateFormat(
                "yyyy-MM-dd hh:mm:ss");
        String dateStr = format.format(info.date);
        tv_date.setText(dateStr);
        String typeStr = null;
        int color = 0;
        switch (info.type) {
            case CallLog.Calls.INCOMING_TYPE:
                typeStr = "来电";
                color = Color.BLUE;
                break;
            case CallLog.Calls.OUTGOING_TYPE:
                typeStr = "去电";
                color = Color.GREEN;
                break;
            case CallLog.Calls.MISSED_TYPE:
                typeStr = "未接";
                color = Color.RED;
                break;

            default:
                break;
        }
        if("未接".equals(typeStr)) {
            tv_date.setTextColor(color);
        }
        tv_type.setText(typeStr);
        tv_type.setTextColor(color);
        return view;
    }
}
