package com.liye.onlineVoice;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.media.AudioManager;
import android.os.Looper;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.liye.mycontacts.myContacts.XiangxiActivity;
import com.liye.mycontacts.utils.ContactInfo;
import com.liye.mycontacts.utils.ContactsUtil;
import com.pubnub.api.Callback;
import com.pubnub.api.Pubnub;
import com.pubnub.api.PubnubError;
import com.pubnub.api.PubnubException;
import com.sinch.android.rtc.PushPair;
import com.sinch.android.rtc.Sinch;
import com.sinch.android.rtc.SinchClient;
import com.sinch.android.rtc.calling.Call;
import com.sinch.android.rtc.calling.CallClient;
import com.sinch.android.rtc.calling.CallClientListener;
import com.sinch.android.rtc.calling.CallListener;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by zhanh on 2018/7/7.
 */

public class OnlineVoiceManager {

    private static final String
            PUBNUB_PUBLISH_KEY = "pub-c-39071a91-55f7-4a44-b33d-c7d43a0b7ace",
            PUBNUB_SUBSCRIBE_KEY = "sub-c-cdb4d408-80ea-11e8-b789-a225d5f6efd3",
            SINCH_KEY="eb2e29f1-8dee-47e4-9e7a-8b46a2d774cd",
            SINCH_SECRET="hLWvBQqrh02T6KqI2EiNZA==",
            SINCH_HOST="clientapi.sinch.com",
            CHANNEL="calling_channel";


    private Pubnub pubnub;
    private SinchClient sinchClient;
    private Call currentCall;
    private ContactInfo remote_user;

    private Context global_context;
    public VoiceControlActivity voice_control_context;

    private static OnlineVoiceManager instance;

    public static OnlineVoiceManager getInstance(){
        if(instance == null){
            instance = new OnlineVoiceManager();
        }
        return instance;
    }
    private OnlineVoiceManager(){
        global_context = GlobalApplication.getContext();
    }
    public boolean initialize(String username){
        pubnub = new Pubnub(PUBNUB_PUBLISH_KEY, PUBNUB_SUBSCRIBE_KEY);
        pubnub.setUUID(username);
        if(!joinChannel()) return false;
        sinchClient = Sinch.getSinchClientBuilder()
                .context(global_context)
                .userId(username)
                .applicationKey(SINCH_KEY)
                .applicationSecret(SINCH_SECRET)
                .environmentHost(SINCH_HOST)
                .build();        //use the calling feature

        sinchClient.setSupportCalling(true);
        //start listening for incoming calls
        sinchClient.startListeningOnActiveConnection();
        sinchClient.start();
        sinchClient.getCallClient().addCallClientListener(new SinchCallClientListener());
        GlobalApplication.isLogin=true;
        return true;
    }


    //you'll attach an instance of this listener to individual calls
    private class SinchCallListener implements CallListener {
        //when the call ends for any reason
        @Override
        public void onCallEnded(Call call) {
            //no current call
            currentCall = null;
            //volume buttons go back to controlling ringtone volume
            voice_control_context.setVolumeControlStream(AudioManager.USE_DEFAULT_STREAM_TYPE);
            voice_control_context.finish();
            remote_user=null;
        }

        //recipient picks up the call
        @Override
        public void onCallEstablished(Call call) {

            //ringtone volume buttons now control the speaker volume
           voice_control_context.setVolumeControlStream(AudioManager.STREAM_VOICE_CALL);
        }

        //when call is "ringing"
        @Override
        public void onCallProgressing(Call call) {
            voice_control_context.updateStatus(true);
//            hangupButton.setText("Ringing");

        }

        //don't worry about this for now
        @Override
        public void onShouldSendPushNotification(Call call, List<PushPair> pushPairs) {}
    }

    //you'll attach an instance of this to the Sinch client
    private class SinchCallClientListener implements CallClientListener {
        //when there is an incoming call
        @Override
        public void onIncomingCall(CallClient callClient, Call incomingCall) {
            //only react if there is no current call
            if (currentCall == null) {
                currentCall = incomingCall;
                currentCall.addCallListener(new SinchCallListener());

                ContactInfo contact = new ContactsUtil(global_context).findContactInfo(currentCall.getRemoteUserId());
                if(contact != null){
                    remote_user = contact;
                    Intent intent = new Intent(global_context,VoiceControlActivity.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    intent.putExtra("contact",remote_user);
                    intent.putExtra("switch",false);
                    intent.putExtra("status",false);
                    global_context.startActivity(intent);

                }
            }
        }
    }

    private boolean joinChannel(){
        //subscribe to calling_channel
    //empty callback for the sake of simplicity in this tutorial
        try {
            pubnub.subscribe(CHANNEL, new Callback() {

            });
        } catch (PubnubException e) {
            Log.d("PubnubException",e.toString());
            return false;

        }
        return true;
    }

    private void leaveChannel(){
        pubnub.unsubscribe("calling_channel");
    }

    public void huang_up(){
        if(currentCall != null){
            currentCall.hangup();
        }
    }
    public void pick_up(){
        if(currentCall != null) currentCall.answer();
    }
    public boolean call_up(ContactInfo contact){

        if(currentCall == null){
            remote_user =  contact;

            pubnub.whereNow(remote_user.getPhone().replace(" ",""), new Callback() {
                @Override
                public void successCallback(String s, Object o) {
                    try {
                        JSONObject jsonMessage = new JSONObject(o.toString());
                        JSONArray result = jsonMessage.getJSONArray("channels");

                        if(result.length() ==1 && result.getString(0).equals(CHANNEL)){
                            Intent voiceIntent = new Intent(global_context,
                                    VoiceControlActivity.class);
                            voiceIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

                            voiceIntent.putExtra("contact",remote_user);
                            voiceIntent.putExtra("switch",true);
                            voiceIntent.putExtra("status",false);
                            global_context.startActivity(voiceIntent);

                            currentCall = sinchClient.getCallClient().callUser( remote_user.getPhone().replace(" ","") );
                            currentCall.addCallListener(new SinchCallListener());
                        }else{
                            GlobalApplication.showMessage("对方未启用网络电话");
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }

                @Override
                public void errorCallback(String s, PubnubError pubnubError) {
                    super.errorCallback(s, pubnubError);
                    GlobalApplication.showMessage("网络连接失败，请启用网络权限");
                }
            });
            return true;
        }
        return false;
    }

    public void setCurrentVoiceControlContext(Context context){
        voice_control_context = (VoiceControlActivity)context;

    }
    public void uninitialize(){

        leaveChannel();
        GlobalApplication.isLogin=false;
    }

    ///is SIM card valid
    public boolean isSIMValid(){

        TelephonyManager tManager = (TelephonyManager)global_context.getSystemService(Context.TELEPHONY_SERVICE);
        return tManager.getSimState() == TelephonyManager.SIM_STATE_READY;
    }
    public String getPhoneNum(){
        final EditText et = new EditText(global_context);
        new AlertDialog.Builder(global_context).setTitle("请输入手机号码")
                .setView(et)
                .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                }).setNegativeButton("取消", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        }).show();

        TelephonyManager tManager=(TelephonyManager) global_context.getSystemService(Context.TELEPHONY_SERVICE);


        return tManager.getLine1Number();
    }
    ///////////////zhl
}
