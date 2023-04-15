package com.sport.activity;

import android.os.Bundle;
import android.widget.EditText;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import com.google.android.material.textfield.TextInputEditText;
import com.sport.R;
import com.sport.util.database.SPUtil;

public class InformationActivity extends AppCompatActivity {

    private EditText height;

    private EditText weight;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.gerenziliao);

        height = findViewById(R.id.height_edit);
        weight = findViewById(R.id.weight_edit);
        height.setText(String.valueOf(SPUtil.getFloat(this, "user_height", 0)));
        weight.setText(String.valueOf(SPUtil.getFloat(this, "user_weight", 0)));

        height.setEnabled(false);
        weight.setEnabled(false);
    }
}
