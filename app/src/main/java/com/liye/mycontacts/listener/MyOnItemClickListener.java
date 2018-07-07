package com.liye.mycontacts.listener;

import android.support.v4.app.FragmentTransaction;
import android.view.View;
import android.widget.AdapterView;

import com.liye.mycontacts.menu.TelephoneActivity;
import com.liye.mycontacts.myContacts.FirstFragment;

/**
 * Created by MK on 2016/4/8.
 */
/*
侧滑菜单子条目的监听事件
 */
public class MyOnItemClickListener implements AdapterView.OnItemClickListener {
    private FirstFragment fragment;
    public MyOnItemClickListener(FirstFragment fragment) {
        this.fragment = fragment;
    }
    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
       // String text = activity.contentItems[position];
       // Toast.makeText(TelephoneActivity.this, text, Toast.LENGTH_SHORT).show();
        //FragmentTransaction tran1 = activity.getSupportFragmentManager().beginTransaction();

    }
    }


