package com.liye.mycontacts.myContacts;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;

import com.liye.QrCode.zxing.profile.ShowQrCodeActivity;
import com.liye.mycontacts.R;
import com.liye.mycontacts.menu.TelephoneActivity;
import com.liye.mycontacts.utils.ContactInfo;
import com.liye.mycontacts.utils.ContactsUtil;
import com.liye.onlineVoice.GlobalApplication;
import com.liye.onlineVoice.OnlineVoiceManager;

import java.util.List;

/**
 * Created by Mr.Zhao on 2018/7/8.
 */

public class XiangxiFirst extends android.support.v4.app.Fragment implements View.OnClickListener{
    Context mContext;
    TextView mPhone, mEmail, mAddress, mCallPhone,mOnlineVoice;
    ContactsUtil mContactsUtil;
    ////////////////begin
    TextView mEditContact, mDeleteContact, mShowQrCode;
    //////////////end
    ContactInfo contactInfo;
    public XiangxiFirst() {
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
        View view = inflater.inflate(R.layout.xiangxione, null);

        mPhone = (TextView) view.findViewById(R.id.txt_show_phone3);
        if(contactInfo.getPhone() == null) mPhone.setText("");
        else mPhone.setText(contactInfo.getPhone());
        mEmail = (TextView) view.findViewById(R.id.txt_show_email3);
        if(contactInfo.getEmail() == null) mEmail.setText("");
        else mEmail.setText(contactInfo.getEmail());

        mAddress = (TextView) view.findViewById(R.id.txt_show_address3);
        if(contactInfo.getAddress() == null) mAddress.setText("");
        else mAddress.setText(contactInfo.getAddress());


        mEditContact = (TextView) view.findViewById(R.id.edit_contact3);
        mEditContact.setOnClickListener(this);
        mDeleteContact = (TextView) view.findViewById(R.id.delete_contact3);
        mDeleteContact.setOnClickListener(this);

        ///////////////begin
        mShowQrCode = (TextView) view.findViewById(R.id.show_QR_code);
        mShowQrCode.setOnClickListener(this);
        /////////////end

        mOnlineVoice = (TextView)view.findViewById(R.id.online_vioce);
		mOnlineVoice.setOnClickListener(this);

        mCallPhone = (TextView) view.findViewById(R.id.callPhone);
        mCallPhone.setOnClickListener(this);

        return view;
    }
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.edit_contact3:
                Intent editContact = new Intent(mContext,
                        EditContactActivity.class);
                editContact.putExtra("contact", contactInfo);
                startActivity(editContact);
                break;
            case R.id.delete_contact3:
                AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
                builder.setTitle("删除");
                builder.setMessage("确定要删除联系人吗?");

                builder.setPositiveButton("删除",
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {

                                mContactsUtil.delete(contactInfo.getContactId());
                                GlobalApplication.deleteContact(contactInfo);
                                Intent delete = new Intent(mContext,MainActivity.class);
                                //Intent intent = new Intent();
                                startActivity(delete);
                            }
                        }

                ).show();

                builder.setNegativeButton("取消",
                        new DialogInterface.OnClickListener() {

                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                // dialog.dismiss();
                            }
                        }).show();

                break;
            case R.id.show_QR_code:

                Intent show_qr = new Intent(mContext,
                        ShowQrCodeActivity.class);
                show_qr.putExtra("contact", contactInfo);
                startActivity(show_qr);
                break;

            case R.id.online_vioce:
                if(GlobalApplication.isLogin){
                    if(! OnlineVoiceManager.getInstance().call_up(contactInfo) ){
                        Toast.makeText(mContext,"对方未上线",Toast.LENGTH_SHORT).show();
                    }
                }else{
                    Toast.makeText(mContext,"请先打开网络电话功能",Toast.LENGTH_SHORT).show();;
                }

                break;
            case R.id.callPhone:
                // 打电话的意图
                Intent intent = new Intent();
                // Intent.ACTION_CALL打电话的动作
                intent.setAction(Intent.ACTION_CALL);
                // uri统一资源标示符
                intent.setData(Uri.parse("tel:" + contactInfo.getPhone()));
                // 开启一个新的界面
                startActivity(intent);
                break;

        }

    }
}
