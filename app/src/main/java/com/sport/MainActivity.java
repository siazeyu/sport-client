package com.sport;

import android.content.Intent;
import android.widget.Button;
import android.widget.EditText;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import com.sport.activity.IndexActivity;
import com.sport.activity.RegisterActivity;
import com.sport.handler.UserHandler;
import com.sport.util.database.SPUtil;

public class MainActivity extends AppCompatActivity {

    private EditText username;

    private EditText password;

    private Button login_btn;

    private Button register_btn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
        check();
        UserHandler.login(this, login_btn, username, password);

    }

    private void initView(){
        username = findViewById(R.id.login_username);
        password = findViewById(R.id.login_password);
        login_btn = findViewById(R.id.login_btn);
        register_btn = findViewById(R.id.reg_btn);
        register_btn.setOnClickListener((v) -> {
            startActivity(new Intent(this, RegisterActivity.class));
        });


    }

    private void check(){
        long login_time = SPUtil.getLong(this, "login_time" , 0);
        if (System.currentTimeMillis() - login_time < 7 * 24 * 60 * 60 * 1000 && SPUtil.getString(this, "user_token", null) != null){
            Intent intent = new Intent(this, IndexActivity.class);
            startActivity(intent);
            finish();
        }
    }
}