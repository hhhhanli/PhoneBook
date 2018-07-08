package com.liye.mycontacts.utils;

/**
 * Created by Administrator on 2018/7/8 0008.
 */

import android.app.Activity;
import android.graphics.Color;
import android.graphics.Interpolator;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.liye.mycontacts.R;
import com.liye.onlineVoice.GlobalApplication;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import lecho.lib.hellocharts.gesture.ContainerScrollType;
import lecho.lib.hellocharts.gesture.ZoomType;
import lecho.lib.hellocharts.model.Axis;
import lecho.lib.hellocharts.model.AxisValue;
import lecho.lib.hellocharts.model.Line;
import lecho.lib.hellocharts.model.LineChartData;
import lecho.lib.hellocharts.model.PointValue;
import lecho.lib.hellocharts.model.ValueShape;
import lecho.lib.hellocharts.model.Viewport;
import lecho.lib.hellocharts.view.LineChartView;

/**
 * Created by Administrator on 2018/7/5 0005.
 */

public class Myline extends Activity {

    List<String>date = new ArrayList<String>();//X轴的标注
    List<Integer>score = new ArrayList<Integer>();
    private ArrayList<PointValue> mPointValues = new ArrayList<PointValue>();
    private ArrayList<AxisValue> mAxisXValues = new ArrayList<AxisValue>();
    Map<String, Integer> myinfo = new HashMap<String, Integer>();



    LineChartView lineChart;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.line);

        GlobalApplication data = (GlobalApplication)getApplication();
        myinfo = data.getsta1();
        List<Map.Entry<String, Integer>> list = new ArrayList<Map.Entry<String, Integer>>(myinfo.entrySet());

        Collections.sort(list, new Comparator<Map.Entry<String, Integer>>() {
            @Override
            public int compare(Map.Entry<String, Integer> o1, Map.Entry<String, Integer> o2) {

                return o1.getKey().compareTo(o2.getKey());
            }
        });

        for (Map.Entry<String, Integer> key: list) {
            date.add(key.getKey());
            score.add(key.getValue());
        }

        lineChart = (LineChartView) findViewById(R.id.line_chart);
        getAxisXLables();
        getAxisPoints();
        initLineChart();
    }
    private void getAxisXLables(){
        for (int i = 0; i < date.size(); i++) {
            mAxisXValues.add(new AxisValue(i).setLabel(date.get(i)));
        }
    }

    private void getAxisPoints() {
        for (int i = 0; i < score.size(); i++) {
            mPointValues.add(new PointValue(i, score.get(i)));
        }
    }
    private void initLineChart(){
        Line line = new Line(mPointValues).setColor(Color.parseColor("#87CEFA"));
        ArrayList<Line> lines = new ArrayList<Line>();
        line.setShape(ValueShape.CIRCLE);
        line.setCubic(false);
        line.setFilled(false);
        line.setHasLabels(true);
        line.setHasLines(true);
        line.setHasPoints(true);
        lines.add(line);
        LineChartData data = new LineChartData();
        data.setLines(lines);

        Axis axisX = new Axis();
        axisX.setHasTiltedLabels(false);
        axisX.setTextColor(Color.LTGRAY);
        axisX.setName("日期");
        axisX.setTextSize(10);
        axisX.setMaxLabelChars(7);
        axisX.setValues(mAxisXValues);
        data.setAxisXBottom(axisX);
        axisX.setHasLines(true);

        Axis axisY = new Axis();
        axisY.setName("次数");
        axisY.setTextSize(10);
        data.setAxisYLeft(axisY);


        lineChart.setInteractive(true);
        lineChart.setZoomType(ZoomType.HORIZONTAL);
        lineChart.setMaxZoom((float) 4);//最大方法比例
        lineChart.setContainerScrollEnabled(true, ContainerScrollType.HORIZONTAL);
        lineChart.setLineChartData(data);
        lineChart.setVisibility(View.VISIBLE);

        Viewport v = new Viewport(lineChart.getMaximumViewport());
        v.left = 0;
        v.top = 100;
        v.bottom = 0;
        v.right= date.size();
        lineChart.setMaximumViewport(v);
        lineChart.setCurrentViewport(v);
    }

}
