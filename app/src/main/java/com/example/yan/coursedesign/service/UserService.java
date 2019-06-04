package com.example.yan.coursedesign.service;

import com.example.yan.coursedesign.bean.Result;
import com.example.yan.coursedesign.bean.Friend;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface UserService {
    @GET("/user/{id}/friends")
    Call<Result<List<Friend>>> getPeopleList(@Path("id")int userId);
    @GET("/home")
    Call<Result<String>> test();
}
