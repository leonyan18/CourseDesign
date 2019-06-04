package com.example.yan.coursedesign.util;

import android.util.Log;
import android.widget.Toast;

import com.qiniu.android.common.FixedZone;
import com.qiniu.android.http.ResponseInfo;
import com.qiniu.android.storage.Configuration;
import com.qiniu.android.storage.UpCompletionHandler;
import com.qiniu.android.storage.UploadManager;

import org.json.JSONObject;

import java.io.File;

import javax.security.auth.AuthPermission;

public class QiniuUtil {

    public static UploadManager uploadManager;
    static{
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

    public static void upload(File data,String key,String token){
        uploadManager.put(data, key, token,
                (key1, info, res) -> {
                    //res包含hash、key等信息，具体字段取决于上传策略的设置
                    if(info.isOK()) {
                        Log.i("qiniu", "Upload Success");
                    } else {
                        Log.i("qiniu", "Upload Fail");
                    }
                    Log.i("qiniu", key1 + ",\r\n " + info + ",\r\n " + res);
                }, null);
    }

}
