package com.sport.util;

import com.sport.entity.LoginUser;
import com.sport.entity.system.ResultData;
import retrofit2.Call;
import retrofit2.http.*;

public interface API {

    public final static String BASE_URL = "http://172.220.19.47:8083/";
    @POST("/user/login")
    public Call<ResultData> getJsonData(@Body LoginUser loginUser);
}