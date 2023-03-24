package com.sport;

import android.util.Log;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import com.sport.entity.LoginUser;
import com.sport.entity.system.ResultData;
import com.sport.util.API;
import retrofit2.*;
import retrofit2.converter.gson.GsonConverterFactory;

import java.io.IOException;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Retrofit retrofit = new Retrofit.Builder().baseUrl(API.BASE_URL).addConverterFactory(GsonConverterFactory.create()).build();
        API api = retrofit.create(API.class);
        Call<ResultData> result = api.getJsonData(new LoginUser("1", "123456"));
        result.enqueue(new Callback<ResultData>() {
            @Override
            public void onResponse(Call<ResultData> call, Response<ResultData> response) {
               if (response.isSuccessful()){
                   Log.d("retrofit", response.body().toString());
               }else{
                   try {
                       Log.d("retrofit", response.errorBody().string());
                   } catch (IOException e) {
                       e.printStackTrace();
                   }
               }

            }

            @Override
            public void onFailure(Call<ResultData> call, Throwable t) {
                Log.e("retrofit", "onFailure: ", t);
            }
        });
        result.request();
    }
}