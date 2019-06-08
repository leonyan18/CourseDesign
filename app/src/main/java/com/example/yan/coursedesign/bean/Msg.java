package com.example.yan.coursedesign.bean;

import org.litepal.crud.DataSupport;

public class Msg extends DataSupport {

    public static final int TYPE_RECEIVED = 0;

    public static final int TYPE_SENT = 1;
    private int from;
    private int to;
    private String content;

    private int type;

    public Msg() {
    }

    public void setContent(String content) {
        this.content = content;
    }

    public void setType(int type) {
        this.type = type;
    }

    public Msg(String content, int type) {
        this.content = content;
        this.type = type;
    }

    public String getContent() {
        return content;
    }

    public int getType() {
        return type;
    }

    public int getFrom() {
        return from;
    }

    public void setFrom(int from) {
        this.from = from;
    }

    public int getTo() {
        return to;
    }

    public void setTo(int to) {
        this.to = to;
    }
}
