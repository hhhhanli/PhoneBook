package com.liye.mycontacts.myContacts;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.DrawerLayout;
import android.text.InputType;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.liye.QrCode.zxing.activity.CaptureActivity;
import com.liye.mycontacts.R;
import com.liye.mycontacts.adapter.ContactAdapter;
import com.liye.mycontacts.adapter.ListViewAdapter;
import com.liye.mycontacts.listener.MListOnItemClickListener;
import com.liye.mycontacts.listener.MyAddTextChangedListener;
import com.liye.mycontacts.listener.MyOnItemClickListener;
import com.liye.mycontacts.listener.MyOnclickListener;
import com.liye.mycontacts.menu.TelephoneActivity;
import com.liye.mycontacts.utils.CommonUtil;
import com.liye.mycontacts.utils.ContactInfo;
import com.liye.mycontacts.utils.ContactsUtil;
import com.liye.mycontacts.utils.PinyinComparator;
import com.liye.onlineVoice.GlobalApplication;
import com.liye.onlineVoice.OnlineVoiceManager;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

public class FirstFragment extends android.support.v4.app.Fragment {
    Context mContext;
    /*************zjt:sidebar*************/
    private SideBar sideBar;
    private TextView dialog;
    public static final int ADD_CONTACT = 100;
    /*************zjt:sidebar*************/
    //侧滑布局对象，用于通过手指滑动将左侧的菜单布局进行显示或隐藏。
    private ImageView ImageView_menu;
    ListView mList;
    //侧滑中展示其它功能的
    List<ContactInfo> filterContacts;
    ImageView mHead;
    ImageView mExit;
    TextView mAddText;
    TextView mCallphone;
    MainActivity activity;

    //////////////////////begin
    TextView mScanQrCode;
    public static final int GET_CODE = 0;
    Button onlineVoiceSwitch;
    ////////////////////end

    List<ContactInfo> contacts;

    public DrawerLayout mDrawerLayout;
    public ContactAdapter adapter;

    SearchEditText mSearchEditText;
    public FirstFragment() {
        // Required empty public constructor
    }

    public void SetContext(Context context) {
        // Required empty public constructor
        this.mContext = context;
    }

    public Context getCon() {
        return this.mContext;
    }

    public void setAct(MainActivity activity) {
        this.activity = activity;
    }

    public MainActivity getAct() {
        return this.activity;
    }

    public DrawerLayout getDra() {
        return this.mDrawerLayout;
    }

    @Override
    public View onCreateView(LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.fragment_one, null);
        contacts = GlobalApplication.getContacts();
        Collections.sort(contacts, new PinyinComparator());
        adapter = new ContactAdapter(this.mContext, contacts);
        mList = (ListView) view.findViewById(R.id.lst_show_contact);
        mList.setAdapter(adapter);

        mDrawerLayout=(DrawerLayout) view.findViewById(R.id.drawerlayout);
        mHead = (ImageView) view.findViewById(R.id.imagebtn);

        getCircleHead();
        mExit = (ImageView) view.findViewById(R.id.imagebtn1);

        mExit.setOnClickListener(new MyOnclickListener(this));

        ImageView_menu = (ImageView) view.findViewById(R.id.menu1);
        ImageView_menu.setOnClickListener(new MyOnclickListener(this));
        mList.setOnItemClickListener(new MListOnItemClickListener(this));
        mAddText = (TextView) view.findViewById(R.id.txt_add_contact);
        mAddText.setOnClickListener(new MyOnclickListener(this));
        mCallphone = (TextView) view.findViewById(R.id.txt_call_phone);
        mCallphone.setOnClickListener(new MyOnclickListener(this));

        ////////////begin/////
        mScanQrCode = (TextView) view.findViewById(R.id.txt_scan_QrCode);
        mScanQrCode.setOnClickListener(new MyOnclickListener(this));
        //////////////end///////////

        onlineVoiceSwitch = (Button) view.findViewById(R.id.online_voice_switch);
        onlineVoiceSwitch.setBackground(getResources().getDrawable(R.drawable.fou));
        onlineVoiceSwitch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View view) {

                final EditText et = new EditText(view.getContext());
                et.setInputType(InputType.TYPE_CLASS_PHONE);
                et.setText(GlobalApplication.getLoginInfo());
                new AlertDialog.Builder(view.getContext()).setTitle("请输入本机号码")
                        .setView(et)
                        .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                String local_phone = et.getText().toString();
                                if( !OnlineVoiceManager.getInstance().initialize(local_phone) ){
                                    Toast.makeText(view.getContext(),"网络错误",Toast.LENGTH_SHORT).show();
                                    onlineVoiceSwitch.setBackground(getResources().getDrawable(R.drawable.fou));
                                }else {
                                    GlobalApplication.setLoginInfo(local_phone);
                                    GlobalApplication.isLogin=true;
                                    onlineVoiceSwitch.setBackground(getResources().getDrawable(R.drawable.shi));
                                }

                            }
                        }).setNegativeButton("取消", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        if(GlobalApplication.isLogin){
                            onlineVoiceSwitch.setBackground(getResources().getDrawable(R.drawable.fou));
                            OnlineVoiceManager.getInstance().uninitialize();
                        }
                    }
                }).show();
            }
        });

        mSearchEditText = (SearchEditText) view.findViewById(R.id.edt_search);
        // 添加一个文本改变的监听事件
        mSearchEditText.addTextChangedListener(new MyAddTextChangedListener(this));


        /***********************************zjt:sidebar*****************/
        sideBar = (SideBar) view.findViewById(R.id.mysidebar);
        dialog = (TextView) view.findViewById(R.id.dialog);
        sideBar.setTextView(dialog);

        // 设置右侧触摸监听
        sideBar.setOnTouchingLetterChangedListener(new SideBar.OnTouchingLetterChangedListener() {

            @Override
            public void onTouchingLetterChanged(String s) {
                // 该字母首次出现的位置
                int position = adapter.getFirstPosition(s.charAt(0));
                if(position != -1) {
                    mList.setSelection(position);
                }

            }
        });
        return view;
    }

    public void filterContact(String input) {
        filterContacts = new ArrayList<ContactInfo>();
        // 如果输入是空的
        if (TextUtils.isEmpty(input)) {
            filterContacts = contacts;
            adapter.reflash(filterContacts);
        } else {
            // 过滤
            for (int i = 0; i < contacts.size(); i++) {
                // 姓名
                String name = contacts.get(i).getName();
                if (name.contains(input) || name.startsWith(input)) {
                    filterContacts.add(contacts.get(i));
                }
            }
            // 拿到过滤联系人的信息filterContacts
            // 刷新适配器
            adapter.reflash(filterContacts);
        }
    }

    //图片圆形化
    public void getCircleHead() {
        Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.t1);
        Bitmap smallBitmap = Bitmap.createScaledBitmap(bitmap, 100, 80, true);
        Bitmap circleBitmap = CommonUtil.createCircleImage(smallBitmap);
        mHead.setImageBitmap(circleBitmap);

    }

    //////////////begin
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        //扫描结果回调
        if (requestCode == GET_CODE && resultCode == CaptureActivity.RESULT_CODE_QR_SCAN) {

            Bundle bundle = data.getExtras();
            String scanResult = bundle.getString(CaptureActivity.INTENT_EXTRA_KEY_QR_SCAN);
            //将扫描出的信息显示出来

            try {
                JSONObject jsonObject = new JSONObject(scanResult);
                String invalid = "";
                String name = jsonObject.optString("name",invalid),phone = jsonObject.optString("phone",invalid),
                        email = jsonObject.optString("email",invalid),address = jsonObject.optString("address",invalid);
                if(name.equals(invalid) || phone.equals(invalid)  ){
                    Toast.makeText(mContext,"二维码名片格式错误",Toast.LENGTH_SHORT).show();

                    return ;
                }else{
                    /////////////////////////////////////////////////////////////////////////////////////////////////////
                    Intent intent = new Intent(mContext,
                            AddPeopleActivity.class);
                    intent.putExtra("contactInfo",scanResult);
                    startActivityForResult(intent, GlobalApplication.ADD_CONTACT_START);

                }
            } catch (JSONException e) {
                e.printStackTrace();
                Toast.makeText(mContext,"二维码名片格式错误",Toast.LENGTH_SHORT).show();
            }
        }
        else if(requestCode == GlobalApplication.ADD_CONTACT_START && resultCode == GlobalApplication.ADD_CONTACT_END) {

            Toast.makeText(mContext,"添加成功",Toast.LENGTH_SHORT).show();
//            Toast.makeText(mContext,"add返回mainactivity",Toast.LENGTH_LONG).show();

            //contacts.add(newContact);
            Collections.sort(contacts, new PinyinComparator());

            //adapter.addContact(newContact);
            adapter.notifyDataSetChanged();
            mList.setAdapter(adapter);
        }
        else if (requestCode == GlobalApplication.DELETE_CONTACT_START && resultCode == GlobalApplication.DELETE_CONTACT_END) {
            Toast.makeText(mContext,"删除成功",Toast.LENGTH_SHORT).show();
//            Toast.makeText(mContext,"delete返回mainactivity",Toast.LENGTH_LONG).show();
            contacts = GlobalApplication.getContacts();
            adapter.notifyDataSetChanged();
            mList.setAdapter(adapter);
        }
    }
}
