package com.example.yan.coursedesign.service;

import com.example.yan.coursedesign.bean.Result;
import com.example.yan.coursedesign.bean.Token;

import retrofit2.Call;
import retrofit2.http.GET;

public interface ImgService {
    @GET("/img/token")
    Call<Result<Token>> getToken();
}
