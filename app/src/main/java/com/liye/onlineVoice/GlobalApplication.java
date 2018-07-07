package com.liye.onlineVoice;

import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Handler;
import android.os.Message;
import android.widget.Toast;

/**
 * Created by zhanh on 2018/7/7.
 */

public class GlobalApplication extends Application {
    private static Context context;
    public static boolean isLogin;
    private static SharedPreferences db;

    private static Handler handler ;
    @Override
    public void onCreate() {
        db = getSharedPreferences("login",MODE_PRIVATE);
        isLogin=false;
        context = getApplicationContext();
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


}
