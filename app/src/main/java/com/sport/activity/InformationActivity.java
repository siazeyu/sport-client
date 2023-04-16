package com.sport.activity;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import com.google.android.material.textfield.TextInputEditText;
import com.sport.R;
import com.sport.util.database.SPUtil;

public class InformationActivity extends AppCompatActivity {

    private EditText height;
    private ImageView exit;
    private EditText weight;
    private EditText target;
    private Button edit;
    private Button finish;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.gerenziliao);
        height = findViewById(R.id.height_edit);
        exit = findViewById(R.id.imageView);
        exit.setOnClickListener(view -> finish());
        weight = findViewById(R.id.weight_edit);
        target = findViewById(R.id.textView14);
        height.setText(String.valueOf(SPUtil.getFloat(this, "user_height", 0)));
        weight.setText(String.valueOf(SPUtil.getFloat(this, "user_weight", 0)));
        target.setText(String.valueOf(SPUtil.getInt(this, "user_target", 0)));
        edit = findViewById(R.id.button13);
        edit.setOnClickListener(view -> setEditEnable(true));
        finish = findViewById(R.id.button14);
        finish.setOnClickListener(view -> {
            SPUtil.putFloat(this,"user_height", Float.parseFloat(height.getText().toString()));
            SPUtil.putFloat(this,"user_weight", Float.parseFloat(weight.getText().toString()));
            SPUtil.putInt(this,"user_target", Integer.parseInt(target.getText().toString()));
            setEditEnable(false);
        });
        setEditEnable(false);

    }

    public void setEditEnable(boolean flag){
        target.setEnabled(flag);
        height.setEnabled(flag);
        weight.setEnabled(flag);
    }
}
