package com.example.yan.coursedesign.service;

import com.example.yan.coursedesign.bean.Result;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

public interface LoginService {
    @FormUrlEncoded
    @POST("/login")
    Call<Result<Integer >> login(@Field("username")String username,@Field("password")String password);
}
