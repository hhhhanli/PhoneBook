package com.liye.mycontacts.myContacts;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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
        PeCalllogAdapter adapter1og;
        ContactsMsgUtils contactsMsgUtils;
        contactsMsgUtils = new ContactsMsgUtils(mContext);
        contactsMsgUtils.setName(contactInfo.getName());
        lv = (ListView) view.findViewById(R.id.pe_lv);
        infos = contactsMsgUtils.pe_select();
        adapter1og = new PeCalllogAdapter(mContext, infos);
        lv.setAdapter(adapter1og);
        return view;
    }
}
