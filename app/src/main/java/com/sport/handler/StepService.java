package com.sport.handler;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.IBinder;
import android.util.Log;
import androidx.annotation.Nullable;

public class StepService extends Service {

    private static final int sensorTypeD = Sensor.TYPE_STEP_DETECTOR;
    private static final int sensorTypeC = Sensor.TYPE_STEP_COUNTER;

    private SensorManager mSensorManager;
    public int mDetector;

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        mSensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);
        mSensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
// 获取我们需要的传感器类型
        Sensor stepCounter = mSensorManager.getDefaultSensor(sensorTypeC);
        Sensor stepDetector = mSensorManager.getDefaultSensor(sensorTypeD);
// 注册监听器
        mSensorManager.registerListener(sensorEventListener, stepCounter, SensorManager.SENSOR_DELAY_FASTEST);
        mSensorManager.registerListener(sensorEventListener, stepDetector, SensorManager.SENSOR_DELAY_FASTEST);

        return super.onStartCommand(intent, flags, startId);
    }


    SensorEventListener sensorEventListener = new SensorEventListener() {
        @Override
        public void onSensorChanged(SensorEvent event) {

            if (event.sensor.getType() == sensorTypeD) {
                if (event.values[0] == 1.0) {
                    mDetector++;
                    Log.d("TAG", mDetector + "步");
                }
            }

        }

        @Override
        public void onAccuracyChanged(Sensor sensor, int i) {

        }
    };


}
