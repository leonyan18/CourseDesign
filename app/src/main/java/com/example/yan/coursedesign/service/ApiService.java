package com.example.yan.coursedesign.service;

import com.example.yan.coursedesign.util.Retrofit2ConverterFactory;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ApiService {
    public static String BASE_URL="http://192.168.0.115:8080/";
    public static Retrofit retrofit = new Retrofit.Builder()
            .baseUrl(ApiService.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .addConverterFactory(Retrofit2ConverterFactory.create())
            .build();
}
