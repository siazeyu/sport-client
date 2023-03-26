package com.sport.handler;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import com.sport.MainActivity;
import com.sport.activity.IndexActivity;
import com.sport.entity.LoginUser;
import com.sport.entity.SysUser;
import com.sport.entity.system.ResultData;
import com.sport.util.API;
import com.sport.util.database.SPUtil;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import java.io.IOException;

public class LoginHandler {

    public static void login(Context context, Button btn, EditText username, EditText password){

        btn.setOnClickListener(new View.OnClickListener() {

            private Retrofit retrofit = new Retrofit.Builder().baseUrl(API.BASE_URL).addConverterFactory(GsonConverterFactory.create()).build();
            private API api = retrofit.create(API.class);

            @Override
            public void onClick(View view) {

                Call<ResultData> result = api.getJsonData(new LoginUser(username.getText().toString(), password.getText().toString()));
                result.enqueue(new Callback<ResultData>() {
                    @Override
                    public void onResponse(Call<ResultData> call, Response<ResultData> response) {
                        if (response.isSuccessful()){
                            ResultData body = response.body();
                            Log.i("login retrofit", body.toString());
                            SysUser data = body.getData(SysUser.class);
                            SPUtil.putString(context,"username", data.getName());
                            SPUtil.putFloat(context, "user_sex", data.getSex());
                            SPUtil.putFloat(context, "user_height", data.getHeight());
                            SPUtil.putString(context, "user_token", data.getToken());
                            SPUtil.putLong(context, "login_time", System.currentTimeMillis());
                            Toast.makeText(context, "登录成功！", Toast.LENGTH_LONG).show();
                            Intent intent = new Intent(context, IndexActivity.class);
                            context.startActivity(intent);
                        }else{
                            Toast.makeText(context, "登录失败，请检查账号密码！", Toast.LENGTH_LONG).show();
                            try {
                                Log.d("login retrofit", response.errorBody().string());
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        }

                    }

                    @Override
                    public void onFailure(Call<ResultData> call, Throwable t) {
                        Toast.makeText(context, "网络错误", Toast.LENGTH_LONG).show();
                        Log.e("login retrofit", "onFailure: ", t);
                    }
                });
                result.request();
            }
        });
    }
}
