package com.liye.mycontacts.myContacts;

/**
 * Created by dell-pc on 2018.7.7.
 */


import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.liye.mycontacts.R;
import com.liye.mycontacts.adapter.CalllogAdapter;
import com.liye.mycontacts.utils.CallLogInfo;
import com.liye.mycontacts.utils.ContactsMsgUtils;

import java.util.List;

public class SecondFragment extends android.support.v4.app.Fragment {
    Context mContext;
    ListView lv;

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
        CalllogAdapter adapter1og;
        ContactsMsgUtils contactsMsgUtils;
        lv = (ListView) view.findViewById(R.id.lv);
        contactsMsgUtils = new ContactsMsgUtils(mContext);
        infos = contactsMsgUtils.select();
        adapter1og = new CalllogAdapter(mContext, infos);
        lv.setAdapter(adapter1og);
        return view;
    }

}