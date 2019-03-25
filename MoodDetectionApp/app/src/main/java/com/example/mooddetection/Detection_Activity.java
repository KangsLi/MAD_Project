package com.example.mooddetection;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import java.text.NumberFormat;

public class Detection_Activity extends AppCompatActivity {

    //FaceppService faceppService;
    MyTempView mTempView;
    android.support.v7.widget.Toolbar top;
    int data[];
    int num;
    Button detail;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);
        mTempView = (MyTempView) findViewById(R.id.mTemp);
        detail=(Button)findViewById(R.id.button3);
        top=(android.support.v7.widget.Toolbar)findViewById(R.id.toolbar);
        top.setNavigationIcon(R.mipmap.back_left_navigation);
        top.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        Intent intent = getIntent();
        if (intent != null) {
            data= intent.getIntArrayExtra("emotion");
            if(data!=null)
            {int sum=0;
            for(int i=0;i<7;i++) {
                sum += data[i];
            }
            int good=data[3]+data[4];
            double ratio=0;
            if(sum!=0)
                ratio=(float)good/(float)sum;
            num=sum;
            NumberFormat nf = NumberFormat.getPercentInstance();
            nf.setMaximumFractionDigits(1);
            mTempView.setTemp(String.valueOf(nf.format(ratio)),this,ratio);
            }
            else{finish();}
        }
        detail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(Detection_Activity.this,Statistic.class);
                if(data!=null){
                    intent.putExtra("result",data);
                    startActivity(intent);
                }

            }
        });
    }
}
