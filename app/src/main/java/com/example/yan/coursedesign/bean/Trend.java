package com.example.yan.coursedesign.bean;

import android.graphics.Bitmap;


import java.util.List;

/**
 * Created by ACM-Yan on 2018/3/15.
 */

public class Trend {
    private String name;
    private String headPic;
    private List<String> imgContent;

    public Trend(String name, String headPic, List<String> imgContent) {
        this.name = name;
        this.headPic = headPic;
        this.imgContent = imgContent;
    }

    public Trend() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getHeadPic() {
        return headPic;
    }

    public void setHeadPic(String headPic) {
        this.headPic = headPic;
    }

    public List<String> getImgContent() {
        return imgContent;
    }

    public void setImgContent(List<String> imgContent) {
        this.imgContent = imgContent;
    }

    @Override
    public String toString() {
        return "Trend{" +
                "name='" + name + '\'' +
                ", headPic='" + headPic + '\'' +
                ", imgContent=" + imgContent +
                '}';
    }
}
