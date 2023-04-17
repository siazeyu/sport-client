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
import android.widget.Toast;
import androidx.annotation.Nullable;
import com.sport.handler.binder.StepBinder;
import com.sport.util.database.SPUtil;

import java.util.*;
import java.util.concurrent.TimeUnit;

public class StepService extends Service {

    private static final int sensorTypeD = Sensor.TYPE_STEP_DETECTOR;

    private SensorManager mSensorManager;
    private static int mDetector;
    private static boolean flag = false;
    private List<StepFunction> functions;


    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        if (!flag) {
            functions = Collections.synchronizedList(new ArrayList<>());
            mDetector = SPUtil.getStep(this);
            Toast.makeText(this, "计步启动", Toast.LENGTH_LONG).show();
            mSensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);
            mSensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
// 获取我们需要的传感器类型
            Sensor stepDetector = mSensorManager.getDefaultSensor(sensorTypeD);
// 注册监听器
            mSensorManager.registerListener(sensorEventListener, stepDetector, SensorManager.SENSOR_DELAY_FASTEST);
            startDBTask();

        }
        return new StepBinder(this);
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {

        return super.onStartCommand(intent, flags, startId);
    }


    SensorEventListener sensorEventListener = new SensorEventListener() {
        @Override
        public void onSensorChanged(SensorEvent event) {

            if (event.sensor.getType() == sensorTypeD) {

                if (event.values[0] == 1.0) {
                    mDetector++;
                    Log.d("TAG", mDetector + "步");
                    Log.d("TAG", "stepService: functions大小：" + functions.size());
                    Iterator<StepFunction> iterator = functions.iterator();
                    while (iterator.hasNext()){
                        StepFunction next = iterator.next();
                        if (next.isActivityValid()){
                            functions.remove(next);
                        }else {
                            next.onCharge(mDetector);
                        }
                    }
                }
            }

        }

        @Override
        public void onAccuracyChanged(Sensor sensor, int i) {

        }
    };


    private void startDBTask(){
        new Thread(() -> {
            while(true){
                try {
                    TimeUnit.SECONDS.sleep(30);
                    SPUtil.putStep(StepService.this, mDetector);
                    Log.i("StepService", "run: 数据保存成功。" + SPUtil.getStep(StepService.this) + " : " + mDetector);
                } catch (InterruptedException e) {
                    Log.e("StepService", "run: 数据保存错误。" + e.getMessage());
                }
            }
        }).start();
    }

    public void addStepHandle(StepFunction stepFunction){
        functions.add(stepFunction);
    }

}
