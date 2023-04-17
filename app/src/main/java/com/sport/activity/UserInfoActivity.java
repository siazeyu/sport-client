package com.sport.activity;

import android.os.Bundle;
import android.text.Editable;
import android.widget.Button;
import android.widget.EditText;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import com.sport.R;
import com.sport.util.database.SPUtil;

public class UserInfoActivity extends AppCompatActivity {

    private EditText name;
    private EditText pwd;
    private EditText s_pwd;
    private EditText phone;

    private Button profile;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.userinfo);

        name = findViewById(R.id.editTextTextPassword13);
        pwd = findViewById(R.id.editTextTextPassword14);
        s_pwd = findViewById(R.id.editTextTextPassword12);
        phone = findViewById(R.id.editText);
        profile = findViewById(R.id.button20);

        String username = SPUtil.getString(this, "username", "");
        String user_phone = SPUtil.getString(this, "user_phone", "");

        name.setText(username);
        phone.setText(user_phone);

        profile.setOnClickListener(view -> {

        });
    }
}
