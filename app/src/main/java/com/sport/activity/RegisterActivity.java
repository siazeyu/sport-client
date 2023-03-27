package com.sport.activity;

import android.os.Bundle;
import android.widget.Button;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import com.sport.R;

public class RegisterActivity extends AppCompatActivity {

    private Button reg;
    private Button quit;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);
        quit = findViewById(R.id.btn_ret);
        quit.setOnClickListener((v) ->{
            this.finish();
        });
    }
}
