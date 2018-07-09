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
import com.liye.mycontacts.utils.CallLogInfo;
import com.liye.mycontacts.utils.ContactInfo;
import com.liye.mycontacts.utils.ContactsMsgUtils;
import com.liye.mycontacts.utils.ContactsUtil;

import java.util.HashMap;
import java.util.List;
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

    private static List<CallLogInfo> infos;
    private static ContactsMsgUtils contactsMsgUtils;
    private static ContactsUtil mContactsUtil;
    private static List<ContactInfo> contacts;

    public static final int ADD_CONTACT_START = 100;
    public static final int ADD_CONTACT_END = 101;
    public static final int DELETE_CONTACT_START = 103;
    public static final int DELETE_CONTACT_END = 104;
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
        contactsMsgUtils = new ContactsMsgUtils(this);
        mContactsUtil = new ContactsUtil(this);
    }

    public static void inital(){
        infos = contactsMsgUtils.select();
        contacts = mContactsUtil.select();
    }

    public static List<CallLogInfo> getInfos() {
        return infos;
    }

    public static List<ContactInfo> getContacts() {
        return contacts;
    }
    public static void addContacts(ContactInfo newContact) {
        contacts.add(newContact);
    }
    public static void deleteContact(ContactInfo newContact) {
        for (int i = 0; i < contacts.size();i++) {
            if(contacts.get(i).getContactId() == newContact.getContactId()) {
                contacts.remove(i);
            }
        }
    }
    public static String correctName(String name) {
        String result = name;
        //去除空格
        result.trim();
        /*for(int i = 0; i < name.length(); i++) {
            if(name.indexOf(i) >= 'A' && name.indexOf(i) <= 'z') {
                result = name.substring(i);
            }
        }*/
        return result;
    }
    public static Context getContext(){
        return context;
    }

    public static void setLoginInfo(String info){
        SharedPreferences.Editor editor = db.edit();
        editor.putString("phone",info);
        editor.commit();
    }
    public static ContactsUtil getmContactsUtil() {
        return mContactsUtil;
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
