package com.sport.activity;

import android.Manifest;
import android.app.Service;
import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.IBinder;
import android.util.Log;
import android.view.KeyEvent;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import com.alibaba.fastjson2.JSON;
import com.sport.R;
import com.sport.handler.StepFunction;
import com.sport.handler.StepService;
import com.sport.handler.binder.StepBinder;
import com.sport.util.database.DBOpenHelper;
import com.sport.util.database.DBTable;
import com.sport.util.database.SPUtil;
import com.sport.util.database.entity.Point;
import com.sport.util.database.entity.Record;

import java.util.LinkedList;

public class IndexActivity extends AppCompatActivity{


    private String[] permissions={Manifest.permission.ACTIVITY_RECOGNITION, Manifest.permission. ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION, Manifest.permission.ACCESS_BACKGROUND_LOCATION};
    private TextView tv;
    private TextView km;
    private TextView kll;
    private Button sport;
    private Button start;
    private ImageView person_btn;
    private ServiceConnection serviceConnection;
    private StepBinder stepBinder;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.sport);
//        startService(new Intent(this, StepService.class));
        shouquan();
        initData();
        bindStepService();
        test();
        Log.d("TAG" , "onCreate: " + SPUtil.getString(this, "username" , "null"));
    }

    private void bindStepService() {

        bindService(new Intent(this, StepService.class) ,serviceConnection, Service.BIND_AUTO_CREATE);

    }

    private void initData() {
        float user_height = SPUtil.getFloat(IndexActivity.this, "user_height", 0f);
        float user_weight = SPUtil.getFloat(IndexActivity.this, "user_weight", 0);
        tv = findViewById(R.id.user_step);
        km = findViewById(R.id.textView6);
        kll = findViewById(R.id.textView7);
        person_btn = findViewById(R.id.btn_person);
        person_btn.setOnClickListener((v) ->{
            startActivity(new Intent(this, PersonalActivity.class));
        });
        sport = findViewById(R.id.button5);
        sport.setOnClickListener((a) ->{
            Intent intent = new Intent(this, SportActivity.class);
            startActivity(intent);
        });
        loadStep(SPUtil.getStep(this), user_height, user_weight);
        serviceConnection = new ServiceConnection() {
            @Override
            public void onServiceConnected(ComponentName componentName, IBinder iBinder) {
                stepBinder = (StepBinder) iBinder;
                StepFunction stepFunction = new StepFunction(IndexActivity.this){

                    @Override
                    public void onCharge(int step) {
                        runOnUiThread(() -> {
                           loadStep(step, user_height, user_weight);
                        });
                    }
                };

                stepBinder.getStepService().addStepHandle(stepFunction);
            }

            @Override
            public void onServiceDisconnected(ComponentName componentName) {
                stepBinder = null;
            }
        };
        start = findViewById(R.id.button1110);
        start.setOnClickListener( view -> {
            Log.d("TAG", "initData: ");
        });
    }

    private void shouquan(){
            for (String permission : permissions) {
                int get = ContextCompat.checkSelfPermission(this, permission);
                // 权限是否已经 授权 GRANTED---授权  DINIED---拒绝
                if (get != PackageManager.PERMISSION_GRANTED) {
                    // 如果没有授予该权限，就去提示用户请求自动开启权限
                    ActivityCompat.requestPermissions(this, permissions, 321);
                }
            }
    }

    private void loadStep(int step,float user_height, float user_weight){
        float stepLength = user_height / 300 / 1000;
        tv.setText(step + "步");
        km.setText(String.format("%.2f km", stepLength * step));
        kll.setText(String.format("%.2f cal", user_weight *  stepLength * step * 1.036 ));
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK){
            moveTaskToBack(true);
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    @Override
    protected void onDestroy() {
        unbindService(serviceConnection);
        super.onDestroy();
    }

    private void test(){

    }
}