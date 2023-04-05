package com.sport.activity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.widget.Button;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import com.amap.api.location.AMapLocationClient;
import com.amap.api.location.AMapLocationClientOption;
import com.amap.api.maps2d.AMap;
import com.amap.api.maps2d.CameraUpdateFactory;
import com.amap.api.maps2d.MapView;
import com.amap.api.maps2d.model.*;
import com.sport.R;
import com.sport.util.database.DBOpenHelper;
import com.sport.util.database.DBTable;
import com.sport.util.database.entity.Point;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;


public class SportActivity extends AppCompatActivity {

    MapView mMapView = null;
    AMap aMap;

    public AMapLocationClient mLocationClient = null;//声明AMapLocationClient类对象
    public AMapLocationClientOption mLocationOption = null;
    private DBTable dbTable = DBTable.asDBTable(Point.class);
    private DBOpenHelper sport = DBOpenHelper.createDBHelper(this, "sport", dbTable, 1);
    private Button start;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.guiji);

        initView();

        AMapLocationClient.updatePrivacyAgree(this, true);
        AMapLocationClient.updatePrivacyShow(this, true, true);

        startService(new Intent(this, com.sport.handler.SportService.class));

        mMapView = findViewById(R.id.gaode_map);
        mMapView.onCreate(savedInstanceState);


        if (aMap == null) {
            aMap = mMapView.getMap();
        }

        MyLocationStyle myLocationStyle;
        myLocationStyle = new MyLocationStyle();//初始化定位蓝点样式类myLocationStyle.myLocationType(MyLocationStyle.LOCATION_TYPE_LOCATION_ROTATE);//连续定位、且将视角移动到地图中心点，定位点依照设备方向旋转，并且会跟随设备移动。（1秒1次定位）如果不设置myLocationType，默认也会执行此种模式。
        myLocationStyle.interval(2000); //设置连续定位模式下的定位间隔，只在连续定位模式下生效，单次定位模式下不会生效。单位为毫秒。
        myLocationStyle.myLocationType(MyLocationStyle.LOCATION_TYPE_FOLLOW_NO_CENTER);
        aMap.setMyLocationStyle(myLocationStyle);//设置定位蓝点的Style
        aMap.getUiSettings().setMyLocationButtonEnabled(true);//设置默认定位按钮是否显示，非必需设置。
        aMap.moveCamera(CameraUpdateFactory.zoomTo(100));
        aMap.setMyLocationEnabled(true);// 设置为true表示启动显示定位蓝点，false表示隐藏定位蓝点并不进行定位，默认是false。
        guiji();

    }

    private void initView() {
        start = findViewById(R.id.start_btn);

    }

    // 加载轨迹
    private void guiji() {
        LinkedList<Point> all = sport.getAll(Point.class);
        List<LatLng> latLngs = new ArrayList<>();
        for (Point point : all) {
            latLngs.add(new LatLng(point.getLongitude(), point.getLatitude()));
        }
        aMap.addPolyline(new PolylineOptions().
                addAll(latLngs).width(10).color(Color.BLUE)).setDottedLine(false);

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
