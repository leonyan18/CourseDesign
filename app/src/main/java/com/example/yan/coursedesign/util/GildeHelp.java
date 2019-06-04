package com.example.yan.coursedesign.util;

import com.bumptech.glide.request.RequestOptions;
import com.example.yan.coursedesign.R;

public class GildeHelp {
    private static RequestOptions options = new RequestOptions()
            .placeholder(R.drawable.loading)//图片加载出来前，显示的图片
            .fallback( R.drawable.noimg) //url为空的时候,显示的图片
            .error(R.drawable.noimg);//图片加载失败后，显示的图片
    public static RequestOptions getOptions() {
        return options;
    }
}
