package com.example.mooddetection;

import android.content.Intent;
import android.graphics.Bitmap;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.Toolbar;
import java.text.NumberFormat;
import java.text.ParseException;
import java.util.List;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class Detection_Activity extends AppCompatActivity {

    FaceppService faceppService;
    MyTempView mTempView;
    android.support.v7.widget.Toolbar top;
    int data[];
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);
        mTempView = (MyTempView) findViewById(R.id.mTemp);

        Intent intent = getIntent();
        if (intent != null) {
            data= intent.getIntArrayExtra("emotion");
            if(data!=null)
            {int sum=0;
            for(int i=0;i<7;i++)
                sum+=data[i];
            int good=data[3]+data[4];
            double ratio=0;
            if(sum!=0)
                ratio=good/sum;
            NumberFormat nf = NumberFormat.getPercentInstance();
            nf.setMaximumFractionDigits(1);
            mTempView.setTemp(String.valueOf(nf.format(ratio)),this,ratio);}
            else{finish();}
        }
    }
}
