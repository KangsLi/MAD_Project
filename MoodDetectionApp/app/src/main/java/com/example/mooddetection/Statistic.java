package com.example.mooddetection;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import com.github.mikephil.charting.components.AxisBase;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.formatter.IAxisValueFormatter;
import com.github.mikephil.charting.utils.ColorTemplate;
import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;

import java.text.NumberFormat;
import java.util.ArrayList;

public class Statistic extends AppCompatActivity {
    public BarChart barChart;
    //data set
    public ArrayList<BarEntry> entries = new ArrayList<BarEntry>();
    public BarDataSet dataset;
    int data[];
    int num=0;
    String rate[]=new String[7];
    NumberFormat nf = NumberFormat.getPercentInstance();
    TextView description;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_statistic);
        barChart = (BarChart) findViewById(R.id.bar_chart);
        description=(TextView)findViewById(R.id.description);
        Intent intent = getIntent();
        if (intent != null) {
            data= intent.getIntArrayExtra("result");
        }
        //add data
        initEntriesData();
        //set bar chart

        show();
        if(rate!=null)
            description.setText("There are(is) "+String.valueOf(num)+" people:"+" anger:"+rate[0]+"; disgust:"+rate[1]+"; fear:"+
                rate[2]+"; happy:"+rate[3]+"; neutral:"+rate[4]+"; sad:"+rate[5]+"; surprise:"+rate[6]);
    }
    public void initEntriesData() {
        if(data!=null){
            for(int i=0;i<7;i++)
                num+=data[i];
            for(int i=0;i<7;i++)
            {
                float ratio=0;
                if(num!=0)
                    ratio=(float)data[i]/(float)num;
                entries.add(new BarEntry(i+1,ratio));
                nf.setMaximumFractionDigits(1);
                rate[i]=String.valueOf(nf.format(ratio));
            }
        }
    }

    public void show() {

        //put data
        dataset = new BarDataSet(entries, "Mood rate");
        dataset.setColors(ColorTemplate.COLORFUL_COLORS);
        BarData data = new BarData(dataset);

        barChart.getAxisRight().setEnabled(false);

        barChart.getXAxis().setPosition(XAxis.XAxisPosition.BOTTOM);
        barChart.setData(data);
        barChart.animateY(2000);
        barChart.getDescription().setEnabled(false);
        final String[] values ={"Anger","Disgust","Fear","Happiness","Neutral","Sadness","Surprise"};


        IAxisValueFormatter formatter = new IAxisValueFormatter() {
            @Override
            public String getFormattedValue(float value, AxisBase axis) {
                return values[(int) value-1];
            }

        };
        XAxis xAxis = barChart.getXAxis();
        xAxis.setDrawAxisLine(false);
        xAxis.setDrawGridLines(false);
        xAxis.setLabelCount(7);
        xAxis.setValueFormatter(formatter);

        Legend mLegend = barChart.getLegend();
        mLegend.setEnabled(false);


    }
}
