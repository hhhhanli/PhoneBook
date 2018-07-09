package com.liye.QrCode.zxing.profile;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.zxing.WriterException;
import com.liye.QrCode.zxing.decoding.Intents;
import com.liye.QrCode.zxing.encoding.EncodingHandler;
import com.liye.mycontacts.R;
import com.liye.mycontacts.utils.ContactInfo;
import com.liye.onlineVoice.GlobalApplication;

import android.view.View.OnClickListener;

/**
 * Created by zhanh on 2018/7/5.
 */

public class ShowQrCodeActivity extends Activity implements OnClickListener{
    ImageView mQrCode,mQrProfile;
    TextView mQrName;
    ContactInfo mContactInfo;
    Button comback ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.qr_code_img);
        Intent intent = getIntent();
        mContactInfo = intent.getParcelableExtra("contact");
        initData();
    }
    public void initData(){
        mQrCode = (ImageView) this.findViewById(R.id.QrCode);
        mQrProfile = (ImageView) this.findViewById(R.id.QR_code_profile);
        mQrName = (TextView) this.findViewById(R.id.QR_code_name);
        comback = (Button)findViewById(R.id.QR_comeback);
        comback.setOnClickListener(this);
        Bitmap qr = null;
        try {
            qr = EncodingHandler.createQRCode(mContactInfo.getJson(),600,600,mContactInfo.getIcon());
//            Log.d("yesssss" ,mContactInfo.getJson());
            mQrCode.setImageBitmap(qr);
            mQrProfile.setImageBitmap(mContactInfo.getIcon());
            mQrName.setText(mContactInfo.getName());
        } catch (WriterException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.QR_comeback:
                finish();
                break;
        }
    }
}
