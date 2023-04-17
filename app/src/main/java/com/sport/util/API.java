package com.sport.util;

import com.sport.entity.LoginUser;
import com.sport.entity.SysUser;
import com.sport.entity.system.ResultData;
import retrofit2.Call;
import retrofit2.http.*;

public interface API {

    String BASE_URL = "http://81.69.249.102:8083/";

    @POST("/user/login")
    Call<ResultData> login(@Body LoginUser loginUser);

    @POST("/user/register")
    Call<ResultData> register(@Body SysUser sysUser);
}