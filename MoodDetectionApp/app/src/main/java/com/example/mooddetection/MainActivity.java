package com.example.mooddetection;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v4.content.FileProvider;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.content.Context;
import android.content.DialogInterface;
import com.bumptech.glide.Glide;
import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import android.app.Dialog;
import javax.inject.Provider;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;


public class MainActivity extends AppCompatActivity {
    private static final String TAG = "MainActivity";
    private static final int PERMISSIONS_REQUEST_CODE = 1;
    private static final int CAMERA_REQUEST_CODE = 2;
    File mTmpFile;
    Uri imageUri;
    Bitmap photo = null;
    Provider<FaceppService> faceppService;

    boolean hasFace=false;
    int num[] = new int[7];
    private Dialog mDialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Button btn_photo = (Button) findViewById(R.id.button);
        btn_photo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                for(int i=0;i<7;i++)
                    num[i] = 0;
                takePhoto();
            }
        });
        Button btn_analyze = (Button) findViewById(R.id.button2);
        btn_analyze.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                hasFace=false;
                for(int i=0;i<7;i++)
                    num[i] = 0;
                if (photo != null) {
                    ConnectivityManager connectivityManager = (ConnectivityManager) getApplicationContext().getSystemService(Context.CONNECTIVITY_SERVICE);
                    NetworkInfo info = connectivityManager.getActiveNetworkInfo();
                    if (info != null && info.isAvailable()) {
                        getDetectResultFromServer(photo);
                    }
                    else {
                        Toast.makeText(MainActivity.this, "Internet is not connected!", Toast.LENGTH_LONG).show();
                    }
                }
                else {
                    dialog();
                }
            }
        });
    }

    //take photo function
    private void takePhoto() {
        if (!Utils.checkAndRequestPermission(this, PERMISSIONS_REQUEST_CODE)) {
            return;
        }
        Intent intent = new Intent();
        intent.setAction(MediaStore.ACTION_IMAGE_CAPTURE);
        String path = Environment.getExternalStorageDirectory().getAbsolutePath() + "/img";
        if (new File(path).exists()) {
            try {
                new File(path).createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        @SuppressLint("SimpleDateFormat")
        String filename = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        mTmpFile = new File(path, filename + ".jpg");
        mTmpFile.getParentFile().mkdirs();
        String authority = getPackageName() + ".provider";
        imageUri = FileProvider.getUriForFile(this, authority, mTmpFile);
        intent.putExtra(MediaStore.EXTRA_OUTPUT, imageUri);
        startActivityForResult(intent, CAMERA_REQUEST_CODE);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == PERMISSIONS_REQUEST_CODE) {
            if (grantResults.length > 0) {
                for (int grantResult : grantResults) {
                    if (grantResult == PackageManager.PERMISSION_DENIED) {
                        return;
                    }
                }
                takePhoto();
            }
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == RESULT_OK) {
            switch (requestCode) {
                case CAMERA_REQUEST_CODE:
                    BitmapFactory.Options options = new BitmapFactory.Options();
                    options.inSampleSize = 2;
                    photo = BitmapFactory.decodeFile(mTmpFile.getAbsolutePath(), options);
                    int bitmapDegree = Utils.getBitmapDegree(mTmpFile.getAbsolutePath());
                    if (bitmapDegree != 0) {
                            photo = Utils.rotateBitmapByDegree(this.photo, bitmapDegree);
                    }
                    displayPhoto(this.photo);
                    break;
                default:
                    break;
            }
        }
    }

    // @Override
    public void displayPhoto(Bitmap photo) {
        ImageView imageView = (ImageView) findViewById(R.id.imageView);
        Glide.with(this).load(photo).into(imageView);
    }
    protected void dialog() {
        AlertDialog.Builder builder = new Builder(MainActivity.this);
        builder.setMessage("Please take a photo!" );
        builder.setTitle("Warning");
        builder.setPositiveButton("OK",
                new android.content.DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });
        builder.setNegativeButton("Cancel",

                new android.content.DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        finish();
                    }

                });
        builder.create().show();
    }
    //call API function
    public void getDetectResultFromServer(final Bitmap photo) {
        String s = Utils.base64(photo);
        FaceppServiceModule face = new FaceppServiceModule();
        FaceppService faceService = face.providesFacepp();//faceppService.get();
        faceService.getFaceInfo("RDoshSS_CwhjdQ7ccjSfC4VDp64VKuLe", "W59veeVJzdjBtonn8GV3A1jWayCsfdrj", s, 1, "emotion")
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<FaceppBean>() {

                    public void onSubscribe(Disposable d) {
                       /* if(process.getVisibility()==View.GONE)
                            process.setVisibility(View.VISIBLE);*/
                        mDialog = new AlertDialog.Builder(MainActivity.this).create();
                        //显示进度条
                        mDialog.show();
                        // 注意此处要放在show之后 否则会报异常
                        mDialog.setContentView(R.layout.loading_process_dialog_color);
                        mDialog.setCancelable(false);
                    }

                    @Override
                    public void onNext(FaceppBean faceppBean) {
                        handleDetectResult(faceppBean);
                    }

                    @Override
                    public void onError(Throwable e) {
                        mDialog.dismiss();
                        Toast.makeText(MainActivity.this,"Unknown error:maybe photo/internet is bad",Toast.LENGTH_LONG).show();
                    }

                    @Override
                    public void onComplete() {
                        /*if(process.getVisibility()==View.VISIBLE)
                            process.setVisibility(View.GONE);*/
                        mDialog.dismiss();
                        if(hasFace==true)
                        {
                            Intent intent = new Intent(MainActivity.this, Detection_Activity.class);
                            intent.putExtra("emotion", num);
                            startActivity(intent);
                        }
                    }

                });
    }
    //process return result
    private void handleDetectResult(FaceppBean faceppBean) {
        List<FaceppBean.FacesBean> faces = faceppBean.getFaces();
        if (faces == null || faces.size() == 0) {
            Toast.makeText(this, "No Face Detected!", Toast.LENGTH_LONG).show();
        } else {
            hasFace=true;
            Bitmap pro= markFacesInThePhoto(photo,faces);
            ImageView imageView = (ImageView) findViewById(R.id.imageView);
            Glide.with(this).load(pro).into(imageView);
            getEmotionInfo(faces);
        }
    }
  //get emotion information
    void getEmotionInfo(List<FaceppBean.FacesBean> faces) {
        double rate[]=new double[7];
        for (FaceppBean.FacesBean face : faces) {
            int Max=0;
            try{
            FaceppBean.FacesBean.AttributesBean attributes = face.getAttributes();
            FaceppBean.FacesBean.AttributesBean.EmotionBean emotion = attributes.getEmotion();
                rate[0]=emotion.getAnger();
                rate[1]=emotion.getDisgust();
                rate[2]=emotion.getFear();
                rate[3]=emotion.getHappiness();
                rate[4]=emotion.getNeutral();
                rate[5]=emotion.getSadness();
                rate[6]=emotion.getSurprise();
            }
            catch(java.lang.NullPointerException e){
                Toast.makeText(MainActivity.this,"Too many people in photo!",Toast.LENGTH_LONG).show();
            }
            for(int i=0;i<7;i++) {
                if(rate[i]>rate[Max])
                    Max=i;
            }
            num[Max]++;
        }
    }


    private Bitmap markFacesInThePhoto(Bitmap bitmap, List<FaceppBean.FacesBean> faces) {
        Bitmap tempBitmap = bitmap.copy(Bitmap.Config.ARGB_8888, true);
        Canvas canvas = new Canvas(tempBitmap);
        Paint paint = new Paint();
        paint.setColor(Color.RED);
        paint.setStyle(Paint.Style.STROKE);
        paint.setStrokeWidth(10);
        for (FaceppBean.FacesBean face : faces) {
            FaceppBean.FacesBean.FaceRectangleBean faceRectangle = face.getFace_rectangle();
            int top = faceRectangle.getTop();
            int left = faceRectangle.getLeft();
            int height = faceRectangle.getHeight();
            int width = faceRectangle.getWidth();
            canvas.drawRect(left, top, left + width, top + height, paint);
        }
        return tempBitmap;
    }
}