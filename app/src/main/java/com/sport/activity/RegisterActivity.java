package com.sport.activity;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import com.sport.R;
import com.sport.entity.SysUser;
import com.sport.handler.UserHandler;

public class RegisterActivity extends AppCompatActivity {

    private Button reg;
    private Button quit;

    private EditText sys_name;
    private EditText pwd;
    private EditText s_pwd;
    private EditText phone;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);
        quit = findViewById(R.id.btn_ret);
        sys_name = findViewById(R.id.sys_name);
        pwd = findViewById(R.id.editTextTextPassword);
        s_pwd = findViewById(R.id.editTextTextPassword4);
        phone = findViewById(R.id.editTextTextPassword5);
        quit.setOnClickListener((v) ->{
            this.finish();
        });
        reg = findViewById(R.id.button6);
        reg.setOnClickListener(view -> {
            SysUser regInfo = getRegInfo();
           if (regInfo != null){
               UserHandler.register(this, regInfo);
           }
        });
    }

    private SysUser getRegInfo(){
        SysUser sysUser = new SysUser();
        sysUser.setName(sys_name.getText().toString());
        sysUser.setPassword(pwd.getText().toString());
        sysUser.setPhone(phone.getText().toString());
        if (!pwd.getText().toString().equals(s_pwd.getText().toString())){
            Toast.makeText(this, "密码不一致", Toast.LENGTH_SHORT).show();
            return null;
        }
        if (pwd.getText().toString().length() < 1){
            Toast.makeText(this, "密码不能为空", Toast.LENGTH_SHORT).show();
            return null;
        }

        return sysUser;
    }
}
