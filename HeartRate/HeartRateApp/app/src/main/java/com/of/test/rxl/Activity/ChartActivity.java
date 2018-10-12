package com.of.test.rxl.Activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import com.of.test.rxl.R;
import com.of.test.rxl.chartview.ChartEntity;
import com.of.test.rxl.chartview.LineChartView;

import java.util.ArrayList;

public class ChartActivity extends AppCompatActivity {
    //数据的最大最小值
    float minData;
    float maxData;
    float sumData;
    float averageData;

    private LineChartView lineChartView;
    private TextView Mymax;
    private TextView Mymin;
    private TextView Myaverage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.chart_layout);

        lineChartView= (LineChartView) findViewById(R.id.lineChartView);
        Mymax=(TextView)findViewById(R.id.max);
        Mymin=(TextView)findViewById(R.id.min);
        Myaverage=(TextView)findViewById(R.id.average);

        ArrayList<ChartEntity> data = new ArrayList<>();
        ChartEntity entity1 = new ChartEntity();
        entity1.setValue(100);
        entity1.setText("10/22");
        ChartEntity entity2 = new ChartEntity();
        entity2.setValue(103);
        entity2.setText("10/23");
        ChartEntity entity3 = new ChartEntity();
        entity3.setValue(99);
        entity3.setText("10/24");
        ChartEntity entity4 = new ChartEntity();
        entity4.setValue(150);
        entity4.setText("10/25");
        ChartEntity entity5 = new ChartEntity();
        entity5.setValue(132);
        entity5.setText("10/26");
        ChartEntity entity6 = new ChartEntity();
        entity6.setValue(205);
        entity6.setText("10/27");
        ChartEntity entity7 = new ChartEntity();
        entity7.setValue(180);
        entity7.setText("10/28");
        ChartEntity entity8 = new ChartEntity();
        entity8.setValue(186);
        entity8.setText("10/29");
        ChartEntity entity9 = new ChartEntity();
        entity9.setValue(175);
        entity9.setText("10/30");
        ChartEntity entity10 = new ChartEntity();
        entity10.setValue(181);
        entity10.setText("10/31");
        ChartEntity entity11 = new ChartEntity();
        entity11.setValue(155);
        entity11.setText("11/1");
        ChartEntity entity12 = new ChartEntity();
        entity12.setValue(142);
        entity12.setText("11/2");
        ChartEntity entity13 = new ChartEntity();
        entity13.setValue(165);
        entity13.setText("11/3");
        ChartEntity entity14 = new ChartEntity();
        entity14.setValue(141);
        entity14.setText("11/4");
        ChartEntity entity15 = new ChartEntity();
        entity15.setValue(178);
        entity15.setText("11/5");
        ChartEntity entity16 = new ChartEntity();
        entity16.setValue(135);
        entity16.setText("11/6");
        ChartEntity entity17 = new ChartEntity();
        entity17.setValue(148);
        entity17.setText("11/7");
        ChartEntity entity18 = new ChartEntity();
        entity18.setValue(150);
        entity18.setText("11/8");
        ChartEntity entity19 = new ChartEntity();
        entity19.setValue(125);
        entity19.setText("11/9");
        ChartEntity entity20 = new ChartEntity();
        entity20.setValue(123);
        entity20.setText("11/10");
        data.add(entity1);
        data.add(entity2);
        data.add(entity3);
        data.add(entity4);
        data.add(entity5);
        data.add(entity6);
        data.add(entity7);
        data.add(entity8);
        data.add(entity9);
        data.add(entity10);
        data.add(entity11);
        data.add(entity12);
        data.add(entity13);
        data.add(entity14);
        data.add(entity15);
        data.add(entity16);
        data.add(entity17);
        data.add(entity18);
        data.add(entity19);
        data.add(entity20);
        lineChartView.setShadow(true);
        lineChartView.setUnitText("g");
        lineChartView.setDataChart(data);
        float max=getMaxValue(data);
        float min=getMinValues(data);
        float average=getAverageValues(data);
        Mymax.setText(String.valueOf(max));
        Mymin.setText(String.valueOf(min));
        Myaverage.setText(String.valueOf(average));
    }

    private float getMaxValue(ArrayList<ChartEntity> values) {
        if (values != null && values.size() > 0) {
            maxData = values.get(0).getValue();
            for (int y = 0; y < values.size(); y++) {
                if (values.get(y).getValue() > maxData) {
                    maxData = values.get(y).getValue();
                }
            }
        }
        return maxData;
    }
    private float getMinValues(ArrayList<ChartEntity> values) {
        if (values != null && values.size() > 0) {
            minData = values.get(0).getValue();
            for (int y = 0; y < values.size(); y++) {
                if (values.get(y).getValue() < minData) {
                    minData = values.get(y).getValue();
                }
            }
        }
        return minData;
    }
    private float getAverageValues(ArrayList<ChartEntity> values){
        if (values != null && values.size() > 0) {
            sumData = 0;
            for (int y = 0; y < values.size(); y++) {
                sumData+=values.get(y).getValue();
            }
        }
        averageData=sumData/values.size();
        return averageData;

    }


}
