package com.sport.activity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import com.sport.MainActivity;
import com.sport.R;
import com.sport.util.database.SPUtil;

public class PersonalActivity extends AppCompatActivity {

    private Button history_btn;
    private Button information_btn;
    private ImageView exit;
    private Button changeInfo;
    private Button logout;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.tongji);
        history_btn = findViewById(R.id.his_btn);
        information_btn = findViewById(R.id.info_btn);
        changeInfo = findViewById(R.id.button22);
        exit = findViewById(R.id.btn_person);
        exit.setOnClickListener(view -> finish());
        logout = findViewById(R.id.button2);
        logout.setOnClickListener(view -> {
            SPUtil.remove(this, "login_time");
            startActivity(new Intent(this, MainActivity.class));
            finish();
        });

        history_btn.setOnClickListener((v)->{
            startActivity(new Intent(this, HistoryActivity.class));
        });

        information_btn.setOnClickListener((v) ->{
            startActivity(new Intent(this, InformationActivity.class));
        });
        changeInfo.setOnClickListener(view -> startActivity(new Intent(this, UserInfoActivity.class)));
    }
}
