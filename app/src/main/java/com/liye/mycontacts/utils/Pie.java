package com.liye.mycontacts.utils;

/**
 * Created by Administrator on 2018/7/8 0008.
 */

import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

import com.liye.mycontacts.R;
import com.liye.onlineVoice.GlobalApplication;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Map;

import lecho.lib.hellocharts.listener.PieChartOnValueSelectListener;
import lecho.lib.hellocharts.model.PieChartData;
import lecho.lib.hellocharts.model.SliceValue;
import lecho.lib.hellocharts.view.PieChartView;

import static com.liye.mycontacts.R.layout.sector;

/**
 * Created by Administrator on 2018/7/5 0005.
 */

public class Pie extends Activity {

    PieChartView pie_chart;
    //数据
    PieChartData pieChardata;
    List<SliceValue> values = new ArrayList<SliceValue>();
    //定义数据，实际情况肯定不是这样写固定值的

    private int[] colorData = {Color.parseColor("#ec063d"),
            Color.parseColor("#f1c704"),
            Color.parseColor("#0000FF"),
            Color.parseColor("#2bc208"),
            Color.parseColor("#333333")};


    List<Long>data = new ArrayList<Long>();
    List<String>stateChar = new ArrayList<String>();
    Map<String, Long> myinfo;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(sector);

        GlobalApplication data2 = (GlobalApplication)getApplication();
        myinfo = data2.getsta2();
        List<Map.Entry<String, Long>> list = new ArrayList<Map.Entry<String, Long>>(myinfo.entrySet());
        Collections.sort(list, new Comparator<Map.Entry<String, Long>>() {
            @Override
            public int compare(Map.Entry<String, Long> mapping1, Map.Entry<String, Long> mapping2) {
                if (mapping1.getValue() < mapping2.getValue()) {
                    return 1;
                }
                else if (mapping1.getValue() > mapping2.getValue()) {
                    return -1;
                }
                else {
                    return 0;
                }
            }
        });
        for (Map.Entry<String, Long> key: list) {
            stateChar.add(key.getKey());
            data.add(key.getValue());
        }
        pie_chart = (PieChartView) findViewById(R.id.pie_chart);
        pie_chart.setOnValueTouchListener(selectListener);//设置点击事件监听
        setPieChartData();
        initPieChart();
    }
    /**
     * 获取数据
     */
    private void setPieChartData() {

        for (int i = 0; i < data.size(); ++i) {
            SliceValue sliceValue;
            if (i < 5) {
                sliceValue = new SliceValue((float) data.get(i), colorData[i]);
            }
            else {
                sliceValue = new SliceValue((float) data.get(i));
            }
            values.add(sliceValue);
        }
    }
    /**
     * 初始化
     */
    private void initPieChart() {
        pieChardata = new PieChartData();
        pieChardata.setHasLabels(true);
        pieChardata.setHasLabelsOnlyForSelected(false);
        pieChardata.setHasLabelsOutside(false);
        pieChardata.setHasCenterCircle(true);
        pieChardata.setValues(values);
        pieChardata.setCenterCircleColor(Color.WHITE);
        pieChardata.setCenterCircleScale(0.5f);
        pie_chart.setPieChartData(pieChardata);
        pie_chart.setValueSelectionEnabled(true);
        pie_chart.setCircleFillRatio(1f);

        pie_chart.setViewportCalculationEnabled(true);
    }
    /**
     * 监听事件
     */
    private PieChartOnValueSelectListener selectListener = new PieChartOnValueSelectListener() {

        @Override
        public void onValueDeselected() {
            // TODO Auto-generated method stub

        }
        @Override
        public void onValueSelected(int arg0, SliceValue value) {
            //选择对应图形后，在中间部分显示相应信息
            pieChardata.setCenterText1(stateChar.get(arg0));
            pieChardata.setCenterText1FontSize(10);
            pieChardata.setCenterText2(value.getValue()/60 + "min（" + calPercent(arg0) + ")");
            pieChardata.setCenterText2FontSize(12);
//            Toast.makeText(sector.this, stateChar.get(arg0) + ":" + value.getValue(), Toast.LENGTH_SHORT).show();
        }
    };
    private String calPercent(int i) {
        String result = "";
        int sum = 0;
        for (int i1 = 0; i1 < data.size(); i1++) {
            sum += data.get(i1);
        }
        result = String.format("%.2f", (float) data.get(i) * 100 / sum) + "%";
        return result;
    }
}
