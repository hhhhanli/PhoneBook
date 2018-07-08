package com.liye.mycontacts.myContacts;

/**
 * Created by dell-pc on 2018.7.7.
 */


import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import com.liye.mycontacts.R;
import com.liye.mycontacts.adapter.CalllogAdapter;
import com.liye.mycontacts.utils.CallLogInfo;
import com.liye.mycontacts.utils.ContactsMsgUtils;

import java.util.List;

public class SecondFragment extends android.support.v4.app.Fragment {
    Context mContext;
    ListView lv;
    CalllogAdapter adapter1og;

    public SecondFragment() {
        // Required empty public constructor
    }

    public void SetContext(Context context) {
        // Required empty public constructor
        this.mContext = context;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_two, container, false);
        List<CallLogInfo> infos;
        ContactsMsgUtils contactsMsgUtils;
        lv = (ListView) view.findViewById(R.id.lv);
        contactsMsgUtils = new ContactsMsgUtils(mContext);
        infos = contactsMsgUtils.select();
        adapter1og = new CalllogAdapter(mContext, infos);
        lv.setAdapter(adapter1og);
        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
                CallLogInfo info = (CallLogInfo) adapter1og.getItem(arg2);
                final String number = info.number;
                String[] items = new String[]{"复制号码到拨号盘, 拨号, 发送短信 "};
                AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
                builder.setTitle("操作 ");
                if (info.name == null) {
                    builder.setMessage(number);//提示内容
                } else {
                    builder.setMessage(info.name);
                }
                builder.setPositiveButton("复制号码到拨号盘", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        startActivity(new Intent(
                                Intent.ACTION_DIAL, Uri
                                .parse("tel:" + number)));
                    }
                });
                builder.setNeutralButton("拨号", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        startActivity(new Intent(
                                Intent.ACTION_CALL, Uri
                                .parse("tel:" + number)));
                    }
                });
                builder.setNegativeButton("发送短信", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        startActivity(new Intent(
                                Intent.ACTION_SENDTO, Uri
                                .parse("sms:" + number)));
                    }
                });
                AlertDialog dialog = builder.create();
                dialog.show();
            }
        });
        return view;
    }


}