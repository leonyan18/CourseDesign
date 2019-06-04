package com.example.yan.coursedesign.bean;

import android.graphics.Bitmap;

/**
 * Created by ACM-Yan on 2018/3/15.
 */

public class Friend {
    private String name;
    private int id;
    private String slabel;
    private String image;

    public Friend(String name, int id,String slabel, String image) {
        this.name = name;
        this.slabel = slabel;
        this.image = image;
        this.id=id;
    }
    public Friend(){};

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSlabel() {
        return slabel;
    }

    public void setSlabel(String slabel) {
        this.slabel = slabel;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    @Override
    public String toString() {
        return "Friend{" +
                "name='" + name + '\'' +
                ", id=" + id +
                ", slabel='" + slabel + '\'' +
                ", image='" + image + '\'' +
                '}';
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
