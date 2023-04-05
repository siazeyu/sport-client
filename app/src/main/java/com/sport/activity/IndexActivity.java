package com.sport.activity;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import com.sport.R;
import com.sport.handler.StepService;
import com.sport.util.database.SPUtil;

public class IndexActivity extends AppCompatActivity{

    private String[] permissions={Manifest.permission.ACTIVITY_RECOGNITION, Manifest.permission. ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION, Manifest.permission.ACCESS_BACKGROUND_LOCATION};
//    private AlertDialog dialog;

    private SensorManager mSensorManager;
    public int mDetector;
//    public float mCounter;
    private static final int sensorTypeD = Sensor.TYPE_STEP_DETECTOR;
    private static final int sensorTypeC = Sensor.TYPE_STEP_COUNTER;
    private TextView tv;
    private Button sport;
    private ImageView person_btn;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.sport);
        shouquan();

        startService(new Intent(this, StepService.class));
        mSensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);
        tv = findViewById(R.id.user_step);
        person_btn = findViewById(R.id.btn_person);
        person_btn.setOnClickListener((v) ->{
            startActivity(new Intent(this, PersonalActivity.class));
        });
        sport = findViewById(R.id.button5);
        sport.setOnClickListener((a) ->{
            Intent intent = new Intent(this, SportActivity.class);
            startActivity(intent);
        });
        mSensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
// 获取我们需要的传感器类型
        Sensor stepCounter = mSensorManager.getDefaultSensor(sensorTypeC);
        Sensor stepDetector = mSensorManager.getDefaultSensor(sensorTypeD);
// 注册监听器
        mSensorManager.registerListener(sensorEventListener, stepCounter, SensorManager.SENSOR_DELAY_FASTEST);
        mSensorManager.registerListener(sensorEventListener, stepDetector, SensorManager.SENSOR_DELAY_FASTEST);

        Log.d("TAG" , "onCreate: " + SPUtil.getString(this, "username" , "null"));
    }


    SensorEventListener sensorEventListener = new SensorEventListener() {
        @Override
        public void onSensorChanged(SensorEvent event) {

//            if (event.sensor.getType() == sensorTypeC) {
//                mCounter = event.values[0];
//                tv.setText(mCounter + "步");
//
//            }
            if (event.sensor.getType() == sensorTypeD) {
                if (event.values[0] == 1.0) {
                    mDetector++;
                    tv.setText(mDetector + "步");
                }
            }

        }

        @Override
        public void onAccuracyChanged(Sensor sensor, int i) {

        }
    };


    private void shouquan(){
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {

            for (String permission : permissions) {
                int get = ContextCompat.checkSelfPermission(this, permission);
                // 权限是否已经 授权 GRANTED---授权  DINIED---拒绝
                if (get != PackageManager.PERMISSION_GRANTED) {
                    // 如果没有授予该权限，就去提示用户请求自动开启权限
                    ActivityCompat.requestPermissions(this, permissions, 321);
                }
            }

        }
    }


}