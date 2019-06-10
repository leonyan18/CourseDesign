package com.example.yan.coursedesign.util;

import android.util.Log;

import com.example.yan.coursedesign.bean.Result;
import com.example.yan.coursedesign.bean.Token;
import com.example.yan.coursedesign.service.ApiService;
import com.example.yan.coursedesign.service.ImgService;
import com.qiniu.android.common.FixedZone;
import com.qiniu.android.storage.Configuration;
import com.qiniu.android.storage.UploadManager;

import java.io.File;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class QiniuUtil {
    private static final String TAG = "QiniuUtil";
    private static Token token = null;
    private static ImgService imgService = ApiService.retrofit.create(ImgService.class);
    private static UploadManager uploadManager;

    static {
        Configuration config = new Configuration.Builder()
                .chunkSize(512 * 1024)        // 分片上传时，每片的大小。 默认256K
                .putThreshhold(1024 * 1024)   // 启用分片上传阀值。默认512K
                .connectTimeout(10)           // 链接超时。默认10秒
                .useHttps(false)               // 是否使用https上传域名
                .responseTimeout(60)          // 服务器响应超时。默认60秒
                .zone(FixedZone.zone0)        // 设置区域，指定不同区域的上传域名、备用域名、备用IP。
                .build();
        // 重用uploadManager。一般地，只需要创建一个uploadManager对象
        uploadManager = new UploadManager(config);
    }

    public static void upload(File data, String key) {
        uploadManager.put(data, key, token.getAns(),
                (key1, info, res) -> {
                    //res包含hash、key等信息，具体字段取决于上传策略的设置
                    if (info.isOK()) {
                        Log.i("qiniu", "Upload Success");
                    } else {
                        Log.i("qiniu", "Upload Fail");
                    }
                    Log.i("qiniu", key1 + ",\r\n " + info + ",\r\n " + res);
                }, null);
    }

    public static void initToken() {
        Call<Result<Token>> call = imgService.getToken();
        call.enqueue(new Callback<Result<Token>>() {
            @Override
            public void onResponse(Call<Result<Token>> call, Response<Result<Token>> response) {
                token = response.body().getData();
                Log.d(TAG, "getToken: token.getCreateTime" + token.getCreateTime().getTime());
                Log.d(TAG, "getToken: System.currentTimeMillis" + System.currentTimeMillis());
                Log.d(TAG, "onResponse: " + token);
            }

            @Override
            public void onFailure(Call<Result<Token>> call, Throwable t) {
                t.printStackTrace();
            }
        });
    }

    public static void uploadTrend(File file, String pid) {
        if (token == null || token.getCreateTime().getTime() + 60*1000*55 > System.currentTimeMillis()) {
            Call<Result<Token>> call = imgService.getToken();
            call.enqueue(new Callback<Result<Token>>() {
                @Override
                public void onResponse(Call<Result<Token>> call, Response<Result<Token>> response) {
                    upload(file, pid);
                    Log.d(TAG, "onResponse: " + token);
                }

                @Override
                public void onFailure(Call<Result<Token>> call, Throwable t) {
                    t.printStackTrace();
                }
            });
        }
    }
}
