package com.sport.view;

import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.ListView;
import com.sport.R;

import java.util.List;

public class RecordListView extends LinearLayout {

    private ListView listView;

    private List<RecordListViewModel> modelList;

    private RecordListViewAdapter myListViewAdapter;

    public RecordListView(Context context,
                          @Nullable AttributeSet attrs) {
        super(context, attrs);
        LayoutInflater.from(context).inflate(R.layout.record_layout, this);
        myListViewAdapter = new RecordListViewAdapter(context, R.layout.record_item);
        listView = findViewById(R.id.listview);
        listView.setAdapter(myListViewAdapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                RecordListViewModel model = modelList.get(i);
                model.getOnClickListener().onClick(view);
            }
        });
    }

    public void setModelList(List<RecordListViewModel> modelList) {
        this.modelList = modelList;
        myListViewAdapter.setModelList(modelList);
    }

    public List<RecordListViewModel> getModelList() {
        return modelList;
    }

    public void notifyDataSetChanged() {
        myListViewAdapter.notifyDataSetChanged();
    }
}
