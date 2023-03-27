package com.sport.activity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import com.sport.R;

public class PersonalActivity extends AppCompatActivity {

    private Button history_btn;
    private Button information_btn;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.tongji);
        history_btn = findViewById(R.id.his_btn);
        information_btn = findViewById(R.id.info_btn);

        history_btn.setOnClickListener((v)->{
            startActivity(new Intent(this, HistoryActivity.class));
        });

        information_btn.setOnClickListener((v) ->{
            startActivity(new Intent(this, InformationActivity.class));
        });
    }
}
