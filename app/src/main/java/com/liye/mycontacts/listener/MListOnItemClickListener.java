package com.liye.mycontacts.listener;

import android.content.Intent;
import android.view.View;
import android.widget.AdapterView;

import com.liye.mycontacts.menu.TelephoneActivity;
import com.liye.mycontacts.myContacts.FirstFragment;
import com.liye.mycontacts.myContacts.MainActivity;
import com.liye.mycontacts.myContacts.XiangxiActivity;
import com.liye.mycontacts.utils.ContactInfo;
import com.liye.onlineVoice.GlobalApplication;

/**
 * Created by MK on 2016/4/8.
 */
/*
电话薄列表的监听事件
 */
public class MListOnItemClickListener  implements AdapterView.OnItemClickListener{
    private FirstFragment fragment;
    public MListOnItemClickListener(FirstFragment fragment) {
        this.fragment = fragment;
    }


    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        ContactInfo contact = this.fragment.adapter.getItem(position);
        //  Log.e(this + "", contact.getAddress() + " contact=" + contact);
        Intent intent = new Intent(this.fragment.getCon(), XiangxiActivity.class);
        intent.putExtra("contact", contact);
        //this.fragment.getCon().startActivity(intent);
        this.fragment.startActivityForResult(intent, GlobalApplication.DELETE_CONTACT_START);
    }
}
