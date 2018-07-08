package com.liye.mycontacts.myContacts;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import com.liye.mycontacts.R;
import com.liye.mycontacts.adapter.CalllogAdapter;
import com.liye.mycontacts.adapter.PeCalllogAdapter;
import com.liye.mycontacts.utils.CallLogInfo;
import com.liye.mycontacts.utils.ContactInfo;
import com.liye.mycontacts.utils.ContactsMsgUtils;

import java.util.List;

/**
 * Created by Mr.Zhao on 2018/7/8.
 */

public class XiangxiSecond extends android.support.v4.app.Fragment {
    Context mContext;
    ListView lv;
    ContactInfo contactInfo;
    PeCalllogAdapter adapter1og;

    public XiangxiSecond() {
        // Required empty public constructor
    }

    public void SetContext(Context context) {
        // Required empty public constructor
        this.mContext = context;
    }

    public void setcon(ContactInfo contactInfo) {
        this.contactInfo = contactInfo;
    }

    @Override
    public View onCreateView(LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.xiangxitwo, null);
        List<CallLogInfo> infos;
        ContactsMsgUtils contactsMsgUtils;
        contactsMsgUtils = new ContactsMsgUtils(mContext);
        contactsMsgUtils.setName(contactInfo.getName());
        lv = (ListView) view.findViewById(R.id.pe_lv);
        infos = contactsMsgUtils.pe_select();
        adapter1og = new PeCalllogAdapter(mContext, infos);
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
