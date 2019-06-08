package com.example.yan.coursedesign.service;

import com.example.yan.coursedesign.bean.Result;
import com.example.yan.coursedesign.bean.Friend;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.DELETE;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Part;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface UserService {
    @GET("/user/{id}/friends")
    Call<Result<List<Friend>>> getFriendList(@Path("id")int userId);
    @GET("/home")
    Call<Result<String>> test();
    @GET("/user/{id}/friendList")
    Call<Result<List<Friend>>> getPeopleList(@Path("id")int userId);
    @GET("/user/{id}/friendList")
    Call<Result<List<Friend>>> searchPeopleList(@Path("id")int userId,@Query("keyword") String keyword);
    @FormUrlEncoded
    @PUT("/user/addFriend")
    Call<Result<String >> addFriend(@Field("uid1") int uid1, @Field("uid2") int uid2);
    @DELETE("/user/deleteFriend")
    Call<Result<String>> deleteFriend(@Query("uid1") int uid1, @Query("uid2") int uid2);
}
