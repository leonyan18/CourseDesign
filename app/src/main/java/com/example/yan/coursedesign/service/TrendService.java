package com.example.yan.coursedesign.service;

import com.example.yan.coursedesign.bean.Result;
import com.example.yan.coursedesign.bean.Trend;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface TrendService {
    @FormUrlEncoded
    @POST("/trend/upload")
    Call<Result<String >> uploadTrend(@Field("pids") String pids, @Field("uid") int uid);
    @GET("/trend/{id}/trends")
    Call<Result<List<Trend>>> getTrends(@Path("id")int id);
}
