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

/**
 * Created by MK on 2016/4/8.
 */
/*
点击事件
 */
public class MyOnclickListener  implements View.OnClickListener{

    private TelephoneActivity activity;
    public MyOnclickListener(TelephoneActivity activity) {
        this.activity = activity;
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        switch (id) {

            //添加联系人的按钮
            case R.id.txt_add_contact:
                Intent intent = new Intent(activity,
                        AddPeopleActivity.class);
                activity.startActivity(intent);
                break;
            //拨打电话的按钮
            case R.id.txt_call_phone:
                Intent intent2 = new Intent(activity,
                        CallPhoneActivity.class);
                activity.startActivity(intent2);
                break;
            //控制是否显示左侧菜单的按钮
            case R.id.menu1:
                // 按钮按下，将抽屉打开
                activity.mDrawerLayout.openDrawer(Gravity.LEFT);
                break;

            /////////////begin
            case R.id.txt_scan_QrCode:
                if(CommonUtil.isCameraCanUse()){
                    Intent intent_qr = new Intent(this.activity, CaptureActivity.class);
                    this.activity.startActivityForResult(intent_qr, this.activity.GET_CODE);
                }else{
                    Toast.makeText(this.activity,"请打开此应用的摄像头权限",Toast.LENGTH_SHORT).show();
                }
                break;
            ///////////////////end


        }
    }
}
