package com.liye.mycontacts.myContacts;

/**
 * Created by dell-pc on 2018.7.7.
 */

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.AlertDialog;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.TextView;

import com.liye.mycontacts.R;
import com.liye.mycontacts.adapter.DBManager;
import com.liye.mycontacts.utils.FestivalInfo;
import com.liye.onlineVoice.GlobalApplication;

public class MainActivity extends ActionBarActivity implements OnClickListener {

    private TextView tv_item_one;
    private TextView tv_item_two;
    private TextView tv_item_three;
    private ViewPager myViewPager;
    private FirstFragment oneFragment;
    private SecondFragment twoFragment;
    private ThirdFragment threeFragment;
    private List<Fragment> list;
    private TabFragmentPagerAdapter adapter;
    private AlertDialog.Builder dialogBuilder = null;
    private AlertDialog alertDialog = null;
    private Context mContext;
    private DBManager dm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        InitView();

    //设置提醒事项有关的数据
    mContext = this;
    dm = new DBManager(this);
    if (GlobalApplication.getMain_num() == 1) {
        remind();
        GlobalApplication.setMain_num();
    }

// 设置菜单栏的点击事件
        tv_item_one.setOnClickListener(this);
        tv_item_two.setOnClickListener(this);
        tv_item_three.setOnClickListener(this);
        myViewPager.setOnPageChangeListener(new MyPagerChangeListener());

//把Fragment添加到List集合里面
        list = new ArrayList<>();
        oneFragment = new FirstFragment();
        twoFragment = new SecondFragment();
        threeFragment = new ThirdFragment();
        oneFragment.SetContext(this);
        oneFragment.setAct(this);
        twoFragment.SetContext(this);
        threeFragment.SetContext(this);
        list.add(oneFragment);
        list.add(twoFragment);
        list.add(threeFragment);
        adapter = new TabFragmentPagerAdapter(getSupportFragmentManager(), list);
        myViewPager.setAdapter(adapter);
        myViewPager.setCurrentItem(0);  //初始化显示第一个页面
        tv_item_one.setBackgroundColor(Color.WHITE);
        tv_item_one.setTextColor(Color.rgb(128,128,128));
    }

    /**
     * 初始化控件
     */
    private void InitView() {
        tv_item_one = (TextView) findViewById(R.id.tv_item_one);
        tv_item_two = (TextView) findViewById(R.id.tv_item_two);
        tv_item_three = (TextView) findViewById(R.id.tv_item_three);
        myViewPager = (ViewPager) findViewById(R.id.myViewPager);
    }

    /**
     * 点击事件
     */
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_item_one:
                myViewPager.setCurrentItem(0);
                tv_item_one.setBackgroundColor(Color.WHITE);
                tv_item_two.setBackgroundColor(Color.rgb(44,162,192));
                tv_item_three.setBackgroundColor(Color.rgb(44,162,192));
                tv_item_one.setTextColor(Color.rgb(128,128,128));
                tv_item_two.setTextColor(Color.WHITE);
                tv_item_three.setTextColor(Color.WHITE);
                break;
            case R.id.tv_item_two:
                myViewPager.setCurrentItem(1);
                tv_item_one.setBackgroundColor(Color.rgb(44,162,192));
                tv_item_two.setBackgroundColor(Color.WHITE);
                tv_item_three.setBackgroundColor(Color.rgb(44,162,192));
                tv_item_two.setTextColor(Color.rgb(128,128,128));
                tv_item_one.setTextColor(Color.WHITE);
                tv_item_three.setTextColor(Color.WHITE);
                break;
            case R.id.tv_item_three:
                myViewPager.setCurrentItem(2);
                tv_item_one.setBackgroundColor(Color.rgb(44,162,192));
                tv_item_two.setBackgroundColor(Color.rgb(44,162,192));
                tv_item_three.setBackgroundColor(Color.WHITE);
                tv_item_three.setTextColor(Color.rgb(128,128,128));
                tv_item_one.setTextColor(Color.WHITE);
                tv_item_two.setTextColor(Color.WHITE);
                break;
        }
    }

    /**
     * 设置一个ViewPager的侦听事件，当左右滑动ViewPager时菜单栏被选中状态跟着改变
     *
     */
    public class MyPagerChangeListener implements OnPageChangeListener {

        @Override
        public void onPageScrollStateChanged(int arg0) {
        }

        @Override
        public void onPageScrolled(int arg0, float arg1, int arg2) {
        }

        @Override
        public void onPageSelected(int arg0) {
            switch (arg0) {
                case 0:
                    tv_item_one.setBackgroundColor(Color.WHITE);
                    tv_item_two.setBackgroundColor(Color.rgb(44,162,192));
                    tv_item_three.setBackgroundColor(Color.rgb(44,162,192));
                    tv_item_one.setTextColor(Color.rgb(128,128,128));
                    tv_item_two.setTextColor(Color.WHITE);
                    tv_item_three.setTextColor(Color.WHITE);
                    break;
                case 1:
                    tv_item_one.setBackgroundColor(Color.rgb(44,162,192));
                    tv_item_two.setBackgroundColor(Color.WHITE);
                    tv_item_three.setBackgroundColor(Color.rgb(44,162,192));
                    tv_item_two.setTextColor(Color.rgb(128,128,128));
                    tv_item_one.setTextColor(Color.WHITE);
                    tv_item_three.setTextColor(Color.WHITE);
                    break;
                case 2:
                    tv_item_one.setBackgroundColor(Color.rgb(44,162,192));
                    tv_item_two.setBackgroundColor(Color.rgb(44,162,192));
                    tv_item_three.setBackgroundColor(Color.WHITE);
                    tv_item_three.setTextColor(Color.rgb(128,128,128));
                    tv_item_one.setTextColor(Color.WHITE);
                    tv_item_two.setTextColor(Color.WHITE);
                    break;
            }
        }
    }
    public void remind() {
        dm = new DBManager(mContext);
        if (dm.numDB() == 0) {
            List<FestivalInfo> persons = new ArrayList<>();
            FestivalInfo p1 = new FestivalInfo(1,1,0,"元旦节");
            FestivalInfo p2 = new FestivalInfo(10,1,0,"国庆节");
            FestivalInfo p3 = new FestivalInfo(7,9,0,"DDL");
            FestivalInfo p4 = new FestivalInfo(7,10,1,"张帅帅");

            persons.add(p1);
            persons.add(p2);
            persons.add(p3);
            persons.add(p4);

            dm.add(persons);
        }
        List<FestivalInfo> festivals    = dm.findAllPerson();
        Calendar calendar = Calendar.getInstance();
        int month = calendar.get(Calendar.MONTH)+1;
        int day = calendar.get(Calendar.DAY_OF_MONTH);
        for(FestivalInfo ff:festivals) {
            if (ff.getMonth() == month && ff.getDay() == day) {
                remindDialog(month, day, ff.getKind(), ff.getName());
            }
        }
    }

    public void remindDialog(int month, int day, int kind, String name) {
        String msg = "";
        msg = month + "月" + day + "日是";
        if (kind == 0) {
            msg += name + "哦 \n给大家发个祝福吧";
        } else if (kind == 1){
            msg += name + "的生日哦\n给他发个祝福吧";
        }


        dialogBuilder = new AlertDialog.Builder(mContext);
        alertDialog = dialogBuilder.setTitle("温馨提醒：")
                .setMessage(msg)
                .setNegativeButton("取消", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        //Toast. makeText ( mContext, " 你点击了取消按钮", Toast. LENGTH_SHORT ).show();
                    }
                })
                .setPositiveButton("确定 ", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        //Toast. makeText ( mContext, "你点击了确定按钮", Toast. LENGTH_SHORT ).show();
                    }
                }).create(); // 创建AlertDialog对象
        alertDialog.show();

    }

}