package com.sport.activity;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import com.sport.R;
import com.sport.util.database.SPUtil;

public class IndexActivity extends AppCompatActivity implements SensorEventListener {

    SensorManager mSensorManager;//管理器实例
    Sensor stepCounter;//传感器
    float mSteps;//截止当天0点时步数/重启手机到当前为止的步数
    TextView steps;//显示步数

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.sport);

        mSensorManager = (SensorManager)getSystemService(SENSOR_SERVICE);
        steps = (TextView)findViewById(R.id.user_step);

        // 获取计步器sensor
        stepCounter = mSensorManager.getDefaultSensor(Sensor.TYPE_STEP_COUNTER);
        if(stepCounter != null){
            // 如果sensor找到，则注册监听器
            mSensorManager.registerListener((SensorEventListener) this,stepCounter,mSensorManager.SENSOR_DELAY_GAME);
        }
        else{
            Log.e("hemeiwolong","no step counter sensor found");
        }

        Log.d("TAG", "onCreate: " + SPUtil.getString(this, "username", "null"));
    }

    //步数变化时
    @Override
    public void onSensorChanged(SensorEvent event) {
        //今天所走步数 = 目前的总步数 - 今天0点为止的总步数，如果今天重启过，只记录重启到现在所走的步数
        float showSteps = event.values[0] - mSteps;
        steps.setText("你已经走了"+ showSteps +"步");

    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }

}