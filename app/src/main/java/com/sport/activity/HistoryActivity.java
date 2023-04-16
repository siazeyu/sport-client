package com.sport.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import com.sport.R;
import com.sport.util.DateUtils;
import com.sport.util.database.DBOpenHelper;
import com.sport.util.database.DBTable;
import com.sport.util.database.entity.Point;
import com.sport.util.database.entity.Record;
import com.sport.view.RecordListView;
import com.sport.view.RecordListViewModel;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class HistoryActivity extends AppCompatActivity {

    DBTable record_table;
    DBOpenHelper record;
    DBTable dbTable;
    DBOpenHelper sport;
    LinkedList<Record> all;
    ImageView exit;
    RecordListView recordListView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.lishijjilu);
        initData();
        initModelData();
    }

    private void initData(){

        recordListView = findViewById(R.id.record_list);
        exit = findViewById(R.id.imageView2);
        exit.setOnClickListener(view -> {
            finish();
        });
        record_table = DBTable.asDBTable(Record.class);

        dbTable = DBTable.asDBTable(Point.class);

        record = DBOpenHelper.createDBHelper(this, "record", record_table, 1);

        sport = DBOpenHelper.createDBHelper(this, "sport", dbTable, 1);

        all = record.getAll(Record.class);


    }

    private List<RecordListViewModel> initModelData() {

        List<RecordListViewModel> list = new ArrayList<>();
        for (Record record : all) {

            RecordListViewModel model = new RecordListViewModel( DateUtils.format(record.getStart(), record.getEnd()),
                    String.format("%.4f km", record.getKm()), String.format("%.4f cal", record.getKll()), new View.OnClickListener(){
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(HistoryActivity.this, HistoryInfoActivity.class);
                    Bundle bundle =new Bundle();
                    bundle.putLong("id", record.getStart());
                    bundle.putInt("step",record.getStep());
                    bundle.putDouble("km", record.getKm());
                    bundle.putDouble("kll", record.getKll());
                    intent.putExtras(bundle);
                    startActivity(intent);
                }
            });
            list.add(model);
        }
        recordListView.setModelList(list);
        recordListView.notifyDataSetChanged();
        return list;
    }


}
