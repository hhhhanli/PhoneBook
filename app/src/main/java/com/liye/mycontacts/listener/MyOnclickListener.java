package com.liye.mycontacts.listener;

import android.content.Intent;
import android.support.v4.app.FragmentTransaction;
import android.view.Gravity;
import android.view.View;
import android.widget.Toast;

import com.liye.QrCode.zxing.activity.CaptureActivity;
import com.liye.QrCode.zxing.utils.CommonUtil;
import com.liye.mycontacts.R;
import com.liye.mycontacts.menu.TelephoneActivity;
import com.liye.mycontacts.myContacts.AddPeopleActivity;
import com.liye.mycontacts.myContacts.CallPhoneActivity;
import com.liye.mycontacts.myContacts.FirstFragment;
import com.liye.mycontacts.myContacts.MainActivity;

/**
 * Created by MK on 2016/4/8.
 */
/*
点击事件
 */
public class MyOnclickListener  implements View.OnClickListener{

    private FirstFragment fragment;
    public MyOnclickListener(FirstFragment fragment) {
        this.fragment = fragment;
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        switch (id) {

            //添加联系人的按钮
            case R.id.txt_add_contact:
                Intent intent = new Intent(this.fragment.getCon(),
                        AddPeopleActivity.class);
                this.fragment.getCon().startActivity(intent);
                break;
            //拨打电话的按钮
            case R.id.txt_call_phone:
                Intent intent2 = new Intent(this.fragment.getCon(),
                        CallPhoneActivity.class);
                this.fragment.getCon().startActivity(intent2);
                break;
            //控制是否显示左侧菜单的按钮
            case R.id.menu1:
                // 按钮按下，将抽屉打开
                this.fragment.getDra().openDrawer(Gravity.LEFT);
                break;

            /////////////begin
            case R.id.txt_scan_QrCode:
                if(CommonUtil.isCameraCanUse()){
                    Intent intent_qr = new Intent(this.fragment.getCon(), CaptureActivity.class);
                    //this.fragment.getAct().startActivityForResult(intent_qr, this.fragment.GET_CODE);
                    this.fragment.startActivityForResult(intent_qr, this.fragment.GET_CODE);
                }else{
                    Toast.makeText(this.fragment.getCon(),"请打开此应用的摄像头权限",Toast.LENGTH_SHORT).show();
                }
                break;
            ///////////////////end


        }
    }
}
