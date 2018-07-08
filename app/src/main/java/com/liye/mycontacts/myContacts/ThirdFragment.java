package com.liye.mycontacts.myContacts;


/**
 * Created by dell-pc on 2018.7.7.
 */


import android.app.DatePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.InputType;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;

import com.liye.mycontacts.R;
import com.liye.mycontacts.utils.CallLogInfo;
import com.liye.mycontacts.utils.ContactsMsgUtils;
import com.liye.mycontacts.utils.Myline;
import com.liye.mycontacts.utils.Pie;
import com.liye.onlineVoice.GlobalApplication;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ThirdFragment extends android.support.v4.app.Fragment {
    EditText from_time;
    EditText to_time;
    Button line, sector;
    boolean from_s = false;
    boolean to_s = false;
    boolean data_get = false;
    Context mContext;
    TextView count_value, time_value, name1, name2 ,max_value1, max_value2;


    public ThirdFragment() {
        // Required empty public constructor
    }

    public void SetContext(Context context) {
        // Required empty public constructor
        this.mContext = context;
    }


    @Override
    public View onCreateView(LayoutInflater inflater,
                             ViewGroup container,  Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_three, container, false);
        name1 = (TextView)view.findViewById(R.id.name1);
        name2 = (TextView)view.findViewById(R.id.name2);
        max_value1 = (TextView)view.findViewById(R.id.max_value1);
        max_value2 = (TextView)view.findViewById(R.id.max_value2);
        count_value = (TextView)view.findViewById(R.id.count_value);
        time_value = (TextView)view.findViewById(R.id.time_value);
        from_time = (EditText)view.findViewById(R.id.from_time);
        from_time.setInputType(InputType.TYPE_NULL);
        to_time = (EditText)view.findViewById(R.id.to_time);
        to_time.setInputType(InputType.TYPE_NULL);
//        from_time.setOnFocusChangeListener(new View.OnFocusChangeListener() {
//
//            @Override
//            public void onFocusChange(View v, boolean hasFocus) {
//                if(hasFocus){
//                    showDatePickerDialog_from();
//                }
//            }
//        });

        from_time.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                showDatePickerDialog_from();
            }
        });

//        to_time.setOnFocusChangeListener(new View.OnFocusChangeListener() {
//
//            @Override
//            public void onFocusChange(View v, boolean hasFocus) {
//                if(hasFocus){
//                    showDatePickerDialog_to();
//                }
//            }
//        });

        to_time.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                showDatePickerDialog_to();
            }
        });

        //获得通话列表，统计，修改textview

        //按钮监听转换view
        line = (Button)view.findViewById(R.id.zhexian);
        line.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                if (data_get) {
                    Intent intent =new Intent(mContext, Myline.class);
                    startActivity(intent);
                }
            }
        });

        sector = (Button)view.findViewById(R.id.shanxing);
        sector.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                if (data_get) {
                    Intent intent =new Intent(mContext, Pie.class);
                    startActivity(intent);
                }
            }
        });

        from_time.addTextChangedListener(new TextWatcher() {

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            	/*++ 文本每次改变就会跑这个方法 ++*/

            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count,
                                          int after) {
            	/*++这里的count树枝上是和onTextChanged（）里的before一样的
            	 * after树枝上是和onTextChanged（）里的count一样的 ++*/

            }

            @Override
            public void afterTextChanged(Editable s) {
            	/*++这里显示出输入的字符串++*/
                if (from_time.getText().toString().length() != 0) {
                    from_s = true;
                }
                if (to_s && from_s) {
                    statistic();
                }
            }

        });

        to_time.addTextChangedListener(new TextWatcher() {

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            	/*++ 文本每次改变就会跑这个方法 ++*/
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count,
                                          int after) {
            	/*++这里的count树枝上是和onTextChanged（）里的before一样的
            	 * after树枝上是和onTextChanged（）里的count一样的 ++*/

            }

            @Override
            public void afterTextChanged(Editable s) {
            	/*++这里显示出输入的字符串++*/
                if (to_time.getText().toString().length() != 0) {
                    to_s = true;
                }
                if (to_s && from_s) {
                    statistic();
                }
            }

        });
        return view;
    }


    private void showDatePickerDialog_from() {
        Calendar c = Calendar.getInstance();

        DatePickerDialog dialog = new DatePickerDialog(mContext, R.style.ThemeDialog,new DatePickerDialog.OnDateSetListener() {

            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                from_time.setText(year+"."+(monthOfYear+1)+"."+dayOfMonth);
            }
        }, c.get(Calendar.YEAR), c.get(Calendar.MONTH), c.get(Calendar.DAY_OF_MONTH));
        dialog.getDatePicker().setCalendarViewShown(false);
        dialog.show();
    }

    private void showDatePickerDialog_to() {
        Calendar c = Calendar.getInstance();

        DatePickerDialog dialog = new DatePickerDialog(mContext, R.style.ThemeDialog,new DatePickerDialog.OnDateSetListener() {

            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                to_time.setText(year+"."+(monthOfYear+1)+"."+dayOfMonth);
            }
        }, c.get(Calendar.YEAR), c.get(Calendar.MONTH), c.get(Calendar.DAY_OF_MONTH));
        dialog.getDatePicker().setCalendarViewShown(false);
        dialog.show();
    }

    public void statistic() {
        data_get = false;
        String time1 = from_time.getText().toString();
        time1 += " 00:00:00";
        String time2 = to_time.getText().toString();
        time2 += " 23:59:59";
        SimpleDateFormat df = new SimpleDateFormat("yyyy.MM.dd HH:mm:ss");
        SimpleDateFormat df2 = new SimpleDateFormat("yyyy.MM.dd");
        Date date1 = new Date();
        Date date2 = new Date();
        try {
            date1 = df.parse(time1);
            date2 = df.parse(time2);
            if (date1.getTime() - date2.getTime() > 0) {
                return;
            }
        }
        catch (ParseException e) {
            e.printStackTrace();
        }

        //获得时间内的通话记录，统计。
        List<CallLogInfo> infos;
        ContactsMsgUtils contactsMsgUtils;
        contactsMsgUtils = new ContactsMsgUtils(mContext);
        infos = contactsMsgUtils.select();
        if (infos.size() == 0) {
            return;
        }

        Map<String,Integer> sta1 = new HashMap<String, Integer>();
        Map<String,Long>sta2 = new HashMap<String, Long>();
        int all_count = 0;
        long call_length = 0;
        Map<String, Integer>count = new HashMap<String, Integer>();
        Map<String, Long>length = new HashMap<String, Long>();

        GlobalApplication data = (GlobalApplication)getActivity().getApplication();
        data.getsta1().clear();
        data.getsta2().clear();
        for (int i = 0; i < infos.size(); i++) {

            String temp2 = df2.format(infos.get(i).date);

            if (infos.get(i).date - date1.getTime() >= 0 && infos.get(i).date - date2.getTime() <= 0) {
                all_count++;
                call_length += infos.get(i).calltime;

                if (!data.getsta1().containsKey(temp2)) {
                    data.getsta1().put(temp2, 1);
                }
                else {
                    data.getsta1().put(temp2, (int)data.getsta1().get(temp2) + 1);
                }
                if (!data.getsta2().containsKey(infos.get(i).number)) {
                    data.getsta2().put(infos.get(i).number, (Long)infos.get(i).calltime);
                }
                else {
                    data.getsta2().put(infos.get(i).number, data.getsta2().get(infos.get(i).number) + (Long)infos.get(i).calltime);
                }

                if (!count.containsKey(infos.get(i).number)) {
                    count.put(infos.get(i).number, 1);
                }
                else {
                    count.put(infos.get(i).number, count.get(infos.get(i).number) + 1);
                }
                if (!length.containsKey(infos.get(i).number)) {
                    length.put(infos.get(i).number, (Long)infos.get(i).calltime);
                }
                else {
                    length.put(infos.get(i).number, length.get(infos.get(i).number) + (Long)infos.get(i).calltime);
                }
            }
        }
        Integer max_count = 0;
        String max_count_name = "";

        for (String key:count.keySet()) {
            if (count.get(key) > max_count) {
                max_count = count.get(key);
                max_count_name = key;
            }
        }
        Long max_length = new Long(0);
        String max_length_name = "";
        for (String key:length.keySet()) {
            if (length.get(key) > max_length) {
                max_length = length.get(key);
                max_length_name = key;
            }
        }

        call_length = call_length / 60;
        String all_length = String.valueOf(call_length);

        all_length += "min";
        String an1 = String.valueOf(all_count);
        String an2 = String.valueOf(all_length);
        String an3 = String.valueOf(max_count);
        count_value.setText(an1);
        time_value.setText(an2);
        name1.setText(max_count_name);
        name2.setText(max_length_name);
        max_value1.setText(an3);

        String max_length_value = String.valueOf(max_length);
        max_length_value += "s";
        max_value2.setText(max_length_value);

        data_get = true;
    }
}