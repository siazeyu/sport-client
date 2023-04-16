package com.sport.activity;

import android.app.Service;
import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.graphics.Color;
import android.location.Location;
import android.os.Bundle;
import android.os.IBinder;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import com.amap.api.location.AMapLocationClient;
import com.amap.api.maps2d.AMap;
import com.amap.api.maps2d.CameraUpdate;
import com.amap.api.maps2d.CameraUpdateFactory;
import com.amap.api.maps2d.MapView;
import com.amap.api.maps2d.model.CameraPosition;
import com.amap.api.maps2d.model.LatLng;
import com.amap.api.maps2d.model.MyLocationStyle;
import com.amap.api.maps2d.model.PolylineOptions;
import com.autonavi.amap.mapcore2d.Inner_3dMap_location;
import com.sport.R;
import com.sport.handler.StepFunction;
import com.sport.handler.StepService;
import com.sport.handler.binder.StepBinder;
import com.sport.util.database.DBOpenHelper;
import com.sport.util.database.DBTable;
import com.sport.util.database.SPUtil;
import com.sport.util.database.entity.Point;
import com.sport.util.database.entity.Record;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;


public class HistoryInfoActivity extends AppCompatActivity {

    private TextView step;
    private TextView km;
    private TextView kll;
    private DBTable dbTable;
    private DBTable record_table;
    private DBOpenHelper record;
    private DBOpenHelper sport;
    private Intent intent;
    private Button start;
    MapView mMapView = null;
    AMap aMap;
    private boolean flag = true;
    LinkedList<Point> all ;
    PolylineOptions options;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.guiji);

        mMapView = findViewById(R.id.gaode_map);
        mMapView.onCreate(savedInstanceState);
        if (aMap == null) {
            aMap = mMapView.getMap();
        }
        initView();
        AMapLocationClient.updatePrivacyAgree(this, true);
        AMapLocationClient.updatePrivacyShow(this, true, true);


        aMap.moveCamera(CameraUpdateFactory.zoomTo(100));
        aMap.setMyLocationEnabled(true);// 设置为true表示启动显示定位蓝点，false表示隐藏定位蓝点并不进行定位，默认是false。

    }



    private void initView() {
        intent = getIntent();
        record_table = DBTable.asDBTable(Record.class);
        dbTable = DBTable.asDBTable(Point.class);
        record = DBOpenHelper.createDBHelper(this, "record", record_table, 1);
        sport = DBOpenHelper.createDBHelper(this, "sport", dbTable, 1);
        step = findViewById(R.id.step_text);
        km = findViewById(R.id.km_text);
        kll = findViewById(R.id.kll_text);
        start = findViewById(R.id.start_btn);
        start.setVisibility(View.INVISIBLE);
        options = new PolylineOptions();
        loadStep();
        guiji();
    }

    private void loadStep() {
        this.step.setText(intent.getIntExtra("step", 0) + "步");
        km.setText(String.format("%.4f km", intent.getDoubleExtra("km", 0d)));
        kll.setText(String.format("%.4f cal", intent.getDoubleExtra("kll", 0f) ));
    }

    // 加载轨迹
    private void guiji() {
        all = sport.getByAny(Point.class, "groupby", String.valueOf(intent.getLongExtra("id", 0l)));
        List<LatLng> latLngs = new ArrayList<>();
        for (Point point : all) {
            latLngs.add(new LatLng(point.getLatitude(), point.getLongitude()));
        }
        aMap.addPolyline(options.
                addAll(latLngs).width(10).color(Color.BLUE)).setDottedLine(false);
        if (latLngs.size() > 0) {
            aMap.moveCamera(CameraUpdateFactory.changeLatLng(latLngs.get(0)));
        }

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
