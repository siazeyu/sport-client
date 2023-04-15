package com.sport.activity;

import android.Manifest;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.location.Location;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import com.amap.api.location.AMapLocation;
import com.amap.api.location.AMapLocationClient;
import com.amap.api.location.AMapLocationClientOption;
import com.amap.api.location.AMapLocationListener;
import com.amap.api.maps2d.AMap;
import com.amap.api.maps2d.CameraUpdateFactory;
import com.amap.api.maps2d.MapView;
import com.amap.api.maps2d.model.*;
import com.autonavi.amap.mapcore2d.Inner_3dMap_location;
import com.sport.R;
import com.sport.handler.SportService;
import com.sport.util.database.DBOpenHelper;
import com.sport.util.database.DBTable;
import com.sport.util.database.SPUtil;
import com.sport.util.database.entity.Point;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;


public class SportActivity extends AppCompatActivity {

    private static final int sensorTypeD = Sensor.TYPE_STEP_DETECTOR;
    private static final int sensorTypeC = Sensor.TYPE_STEP_COUNTER;

    private TextView step;
    private TextView km;
    private TextView kll;
    public int mDetector;
    private String[] permissions={Manifest.permission.ACTIVITY_RECOGNITION, Manifest.permission. ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION, Manifest.permission.ACCESS_BACKGROUND_LOCATION};
    private SensorManager mSensorManager;
    SensorEventListener sensorEventListener = new SensorEventListener() {
        @Override
        public void onSensorChanged(SensorEvent event) {
            if (event.sensor.getType() == sensorTypeD && !flag) {
                if (event.values[0] == 1.0) {
                    mDetector++;
                    step.setText(mDetector + "步");
                    float user_height = SPUtil.getFloat(SportActivity.this, "user_height", 0f);
                    float user_weight = SPUtil.getFloat(SportActivity.this, "user_weight", 0);
                    float stepLength = user_height / 300 / 1000;
                    km.setText(String.format("%.4f km", stepLength * mDetector));
                    kll.setText(String.format("%.4f cal", user_weight *  stepLength * mDetector * 1.036 ));
                }
            }

        }

        @Override
        public void onAccuracyChanged(Sensor sensor, int i) {

        }
    };

    MapView mMapView = null;
    AMap aMap;
    private boolean flag = true;
    LinkedList<Point> all ;
    PolylineOptions options = new PolylineOptions();

    private Long group;

    public AMapLocationClient mLocationClient = null;//声明AMapLocationClient类对象
    public AMapLocationClientOption mLocationOption = null;
    private DBTable dbTable = DBTable.asDBTable(Point.class);
    private DBOpenHelper sport = DBOpenHelper.createDBHelper(this, "sport", dbTable, 1);
    private Button start;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.guiji);
        group = System.currentTimeMillis();

        initView();

        AMapLocationClient.updatePrivacyAgree(this, true);
        AMapLocationClient.updatePrivacyShow(this, true, true);



        mMapView = findViewById(R.id.gaode_map);
        mMapView.onCreate(savedInstanceState);


        if (aMap == null) {
            aMap = mMapView.getMap();
        }

        MyLocationStyle myLocationStyle;
        myLocationStyle = new MyLocationStyle();//初始化定位蓝点样式类myLocationStyle.myLocationType(MyLocationStyle.LOCATION_TYPE_LOCATION_ROTATE);//连续定位、且将视角移动到地图中心点，定位点依照设备方向旋转，并且会跟随设备移动。（1秒1次定位）如果不设置myLocationType，默认也会执行此种模式。
        myLocationStyle.interval(8000); //设置连续定位模式下的定位间隔，只在连续定位模式下生效，单次定位模式下不会生效。单位为毫秒。
        myLocationStyle.myLocationType(MyLocationStyle.LOCATION_TYPE_FOLLOW_NO_CENTER);


        aMap.setOnMyLocationChangeListener(new AMap.OnMyLocationChangeListener() {
            @Override
            public void onMyLocationChange(Location l) {
                StringBuffer sb = new StringBuffer();
                Inner_3dMap_location location = null;
                if (l instanceof Inner_3dMap_location){
                    location = (Inner_3dMap_location) l;
                }
                if (null != location) {
                    //errCode等于0代表定位成功，其他的为定位失败，具体的可以参照官网定位错误码说明
                    sb.append("定位成功" + "\n");
                    sb.append("经    度    : " + location.getLongitude() + "\n");
                    sb.append("纬    度    : " + location.getLatitude() + "\n");
                    sb.append("精    度    : " + location.getAccuracy() + "米" + "\n");
                    sb.append("提供者    : " + location.getProvider() + "\n");

                    sb.append("速    度    : " + location.getSpeed() + "米/秒" + "\n");
                    sb.append("角    度    : " + location.getBearing() + "\n");
                    // 获取当前提供定位服务的卫星个数
                    sb.append("星    数    : " + location.getSatellites() + "\n");
                    sb.append("国    家    : " + location.getCountry() + "\n");
                    sb.append("省            : " + location.getProvince() + "\n");
                    sb.append("市            : " + location.getCity() + "\n");
                    sb.append("城市编码 : " + location.getCityCode() + "\n");
                    sb.append("区            : " + location.getDistrict() + "\n");
                    sb.append("区域 码   : " + location.getAdCode() + "\n");
                    sb.append("地    址    : " + location.getAddress() + "\n");
                    sb.append("兴趣点    : " + location.getPoiName() + "\n");
                    if (!flag){
                        Point point = new Point(null, location.getLongitude(), location.getLatitude(), location.getAddress(), group, System.currentTimeMillis());
                        guiji(point);
                        sport.add(point);
                    }
                    aMap.moveCamera(CameraUpdateFactory.zoomTo(100));
                }
                //解析定位结果，
                String result = sb.toString();
                Log.i("TAG_定位", "是否记录: " + !flag);
                Log.i("TAG_定位", result);
            }
        });
        aMap.setMyLocationStyle(myLocationStyle);//设置定位蓝点的Style
        aMap.moveCamera(CameraUpdateFactory.zoomTo(100));
        aMap.setMyLocationEnabled(true);// 设置为true表示启动显示定位蓝点，false表示隐藏定位蓝点并不进行定位，默认是false。


        mSensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
// 获取我们需要的传感器类型
        Sensor stepCounter = mSensorManager.getDefaultSensor(sensorTypeC);
        Sensor stepDetector = mSensorManager.getDefaultSensor(sensorTypeD);
// 注册监听器
        mSensorManager.registerListener(sensorEventListener, stepCounter, SensorManager.SENSOR_DELAY_FASTEST);
        mSensorManager.registerListener(sensorEventListener, stepDetector, SensorManager.SENSOR_DELAY_FASTEST);

    }



    private void initView() {
        start = findViewById(R.id.start_btn);
        start.setOnClickListener(v ->{
            if (flag){
                start.setText("停止");
//                startService(new Intent(this, SportService.class));
                flag = false;
            }else {
                start.setText("开始");
                mDetector = 0;
//                stopService(new Intent(this, SportService.class));
                flag = true;
            }
        });

        step = findViewById(R.id.step_text);
        km = findViewById(R.id.km_text);
        kll = findViewById(R.id.kll_text);

    }

    // 加载轨迹
    private void guiji() {
        all = sport.getNowDay(Point.class);
        List<LatLng> latLngs = new ArrayList<>();
        for (Point point : all) {
//            if (point.getId() < 108){
                latLngs.add(new LatLng(point.getLatitude(), point.getLongitude()));
//            }
        }
        aMap.addPolyline(options.
                addAll(latLngs).width(10).color(Color.BLUE)).setDottedLine(false);

    }
    private void guiji(Point point) {

        options.add(new LatLng(point.getLatitude(), point.getLongitude()));
        aMap.addPolyline(options);
    }


    private void test() {
        DBTable dbTable = DBTable.asDBTable(Point.class);
        DBOpenHelper sport = DBOpenHelper.createDBHelper(this, "sport", dbTable, 1);
        LinkedList<Point> all = sport.getAll(Point.class);
        Point point = sport.get(1, Point.class);

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        //在activity执行onDestroy时执行mMapView.onDestroy()，销毁地图
        mMapView.onDestroy();
    }

    @Override
    protected void onResume() {
        super.onResume();
        //在activity执行onResume时执行mMapView.onResume ()，重新绘制加载地图
        mMapView.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
        //在activity执行onPause时执行mMapView.onPause ()，暂停地图的绘制
        mMapView.onPause();
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        //在activity执行onSaveInstanceState时执行mMapView.onSaveInstanceState (outState)，保存地图当前的状态
        mMapView.onSaveInstanceState(outState);
    }


}
