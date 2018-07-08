package com.liye.onlineVoice;

import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;
import android.media.AudioManager;
import android.media.SoundPool;
import android.os.Handler;
import android.os.Message;
import android.widget.Toast;

import com.liye.mycontacts.R;

import java.util.HashMap;
import java.util.Map;
/**
 * Created by zhanh on 2018/7/7.
 */

public class GlobalApplication extends Application {
    private static Context context;
    public static boolean isLogin;
    private static SharedPreferences db;
    private static SoundPool soundPool;
    private static int streamId;
    private static Handler handler ;
    public   static boolean isInComing;
    @Override
    public void onCreate() {
        isInComing = false;
        db = getSharedPreferences("login",MODE_PRIVATE);
        isLogin=false;
        context = getApplicationContext();
        soundPool = new SoundPool(1, AudioManager.STREAM_MUSIC,5);
        soundPool.load(context,R.raw.incomingring,1);
        streamId = -1;
        handler = new Handler(){
            @Override
            public void handleMessage(Message msg) {
                Toast.makeText(context,"提示:"+msg.obj,Toast.LENGTH_SHORT).show();
            }
        };
    }
    public static Context getContext(){
        return context;
    }

    public static void setLoginInfo(String info){
        SharedPreferences.Editor editor = db.edit();
        editor.putString("phone",info);
        editor.commit();
    }
    public static String getLoginInfo(){
        String res =db.getString("phone","");
        return res;
    }
    public static void showMessage( String msg){
        Message m = handler.obtainMessage();
        m.arg1=1;
        m.what=3;
        m.obj = msg;
        handler.sendMessage(m);
    }
    public  static void playRing(){
        streamId =  soundPool.play(1,1,1,0,-1,1);
    }
    public static void stopRing(){
        if(streamId!=-1)
        {
            soundPool.stop(streamId);
            streamId=-1;
        }
    }

    private Map<String, Integer>sta1 = new HashMap<String, Integer>();
    private Map<String, Long>sta2 = new HashMap<String, Long>();
    public Map<String, Integer> getsta1() {
        return sta1;
    }
    public Map<String, Long> getsta2() {
        return sta2;
    }
}
