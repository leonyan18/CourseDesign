package com.example.yan.coursedesign.activity;

import android.content.Context;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.example.yan.coursedesign.R;
import com.example.yan.coursedesign.util.GildeHelp;
import com.example.yan.coursedesign.util.MyImageloader;
import com.lzy.ninegrid.ImageInfo;
import com.lzy.ninegrid.NineGridView;
import com.lzy.ninegrid.NineGridViewAdapter;
import com.lzy.ninegrid.preview.NineGridViewClickAdapter;

import java.util.ArrayList;

public class TestActivity extends AppCompatActivity {
    NineGridView nineGridView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.trend_item);
        ArrayList<ImageInfo> imageInfos = new ArrayList<>();
        ImageInfo imageInfo1=new ImageInfo();
        imageInfo1.setBigImageUrl("http://www.lancedai.cn/iu1.jpg");
        imageInfo1.setThumbnailUrl("http://www.lancedai.cn/iu1.jpg");
        imageInfos.add(imageInfo1);
        NineGridView.setImageLoader(new MyImageloader());
        nineGridView=findViewById(R.id.nineGrid);
        nineGridView.setAdapter(new NineGridViewClickAdapter(this,imageInfos));
    }
}
