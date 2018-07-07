package com.liye.onlineVoice;

import android.content.Context;
import android.content.Intent;
import android.media.AudioManager;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.liye.mycontacts.R;

import com.liye.mycontacts.utils.ContactInfo;

/**
 * Created by zhanh on 2018/7/7.
 */

public class VoiceControlActivity extends AppCompatActivity implements View.OnClickListener {
    private ContactInfo remote_user;
    private TextView voice_status;
    TextView hangs_free,mute;
    ImageView calling_huang_up,answering_huang_up,answering_pick_up;


    public  boolean calling_or_answering ;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_voice_control);

        remote_user = getIntent().getParcelableExtra("contact");
        ImageView remote_user_profile =  (ImageView)findViewById(R.id.remote_user_profile);
        remote_user_profile.setImageBitmap(remote_user.getIcon());
        TextView remote_user_name = (TextView) findViewById(R.id.remote_user_name);
        remote_user_name.setText(remote_user.getName());
        TextView remote_user_phone_num = (TextView) findViewById(R.id.remote_user_phone_num);
        remote_user_phone_num.setText(remote_user.getPhone());

        voice_status = (TextView)findViewById(R.id.voice_status);

        addCallingItem();
        addAnsweringItem();

        if(getIntent().getBooleanExtra("switch",true) ) {
            switchAnsweringToCalling(true);
        }else{
            switchAnsweringToCalling(false);
        }

        if(getIntent().getBooleanExtra("status",true)){
            updateStatus(false);
        }else{
            updateStatus(true);
        }

        OnlineVoiceManager.getInstance().setCurrentVoiceControlContext(this);

    }

    public void updateStatus(boolean established){
        if(calling_or_answering){
            if(established){
                voice_status.setText("通话中");
            }else{
                voice_status.setText("等待对方接通...");
            }
        }else{
            if(established){
                voice_status.setText("通话中");
            }else{
                voice_status.setText("等待接通或挂断");
            }
        }
    }


    private void addCallingItem(){
        final LayoutInflater inflater = LayoutInflater.from(this);
        // 获取需要被添加控件的布局
        final LinearLayout lin = (LinearLayout) findViewById(R.id.activity_voice_control);
        // 获取需要添加的布局
        LinearLayout layout = (LinearLayout) inflater.inflate(
                R.layout.item_calling, null).findViewById(R.id.item_calling);
        // 将布局加入到当前布局中
        lin.addView(layout);
        hangs_free = (TextView)findViewById(R.id.calling_hands_free);
        hangs_free.setText("免提");
        hangs_free.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AudioManager am = (AudioManager) VoiceControlActivity.this.getSystemService(Context.AUDIO_SERVICE);
                am.setMode(AudioManager.MODE_IN_CALL);
                am.setSpeakerphoneOn(!am.isSpeakerphoneOn());
                if("免提".equals(hangs_free.getText())) hangs_free.setText("听筒");
                else hangs_free.setText("免提");
            }
        });
        mute = (TextView)findViewById(R.id.calling_mute);
        mute.setText("静音");
        mute.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AudioManager am = (AudioManager) VoiceControlActivity.this.getSystemService(Context.AUDIO_SERVICE);
                am.setMode(AudioManager.MODE_IN_CALL);
                am.setMicrophoneMute(!am.isMicrophoneMute());
                if("静音".equals(mute.getText())) mute.setText("关闭静音");
                else mute.setText("静音");
            }
        });

        calling_huang_up = (ImageView)findViewById(R.id.calling_hang_up);
        calling_huang_up.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                OnlineVoiceManager.getInstance().huang_up();
            }
        });
    }

    private void addAnsweringItem(){
        final LayoutInflater inflater = LayoutInflater.from(this);
        // 获取需要被添加控件的布局
        final LinearLayout lin = (LinearLayout) findViewById(R.id.activity_voice_control);
        // 获取需要添加的布局
        LinearLayout layout = (LinearLayout) inflater.inflate(
                R.layout.item_answering, null).findViewById(R.id.item_answering);
        // 将布局加入到当前布局中
        lin.addView(layout);

        answering_huang_up = (ImageView)findViewById(R.id.answering_huang_up);
        answering_huang_up.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                OnlineVoiceManager.getInstance().huang_up();
            }
        });
        answering_pick_up = (ImageView)findViewById(R.id.answering_pick_up);
        answering_pick_up.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                OnlineVoiceManager.getInstance().pick_up();
                switchAnsweringToCalling(true);
                updateStatus(true);
            }
        });
    }
    public void switchAnsweringToCalling( boolean target){
        calling_or_answering = target;
        if(calling_or_answering){
            answering_huang_up.setVisibility(View.GONE);
            answering_pick_up.setVisibility(View.GONE);

            calling_huang_up.setVisibility(View.VISIBLE);
            mute.setVisibility(View.VISIBLE);
            hangs_free.setVisibility(View.VISIBLE);
        }else{
            answering_huang_up.setVisibility(View.VISIBLE);
            answering_pick_up.setVisibility(View.VISIBLE);

            calling_huang_up.setVisibility(View.GONE);
            mute.setVisibility(View.GONE);
            hangs_free.setVisibility(View.GONE);
        }
    }

    @Override
    public void onClick(View v) {


    }

    @Override
    public void onBackPressed() {
    }

}
