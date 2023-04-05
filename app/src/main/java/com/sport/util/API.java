package com.sport.util;

import com.sport.entity.LoginUser;
import com.sport.entity.system.ResultData;
import retrofit2.Call;
import retrofit2.http.*;

public interface API {

    String BASE_URL = "http://192.168.36.135:8083/";

    @POST("/user/login")
    Call<ResultData> getJsonData(@Body LoginUser loginUser);
}