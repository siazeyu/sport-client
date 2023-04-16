package com.sport.view;

import android.view.View;
import android.widget.TextView;

public class RecordListViewModel {

    private String time;
    private String km;
    private String cal;

    public RecordListViewModel(String time, String km, String cal, View.OnClickListener onClickListener) {
        this.time = time;
        this.km = km;
        this.cal = cal;
        this.onClickListener = onClickListener;
    }


    private View.OnClickListener onClickListener;


    public View.OnClickListener getOnClickListener() {
        return onClickListener;
    }

    public void setOnClickListener(View.OnClickListener onClickListener) {
        this.onClickListener = onClickListener;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getKm() {
        return km;
    }

    public void setKm(String km) {
        this.km = km;
    }

    public String getCal() {
        return cal;
    }

    public void setCal(String cal) {
        this.cal = cal;
    }
}