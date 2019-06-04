package com.example.yan.coursedesign.service;

import com.example.yan.coursedesign.bean.Result;

import retrofit2.Call;
import retrofit2.http.POST;

public interface LoginService {
    @POST("/login")
    Call<Result<String >> login();
}
