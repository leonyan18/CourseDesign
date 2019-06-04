package com.example.yan.coursedesign.bean;

import java.util.Date;

public class Token {
    String ans;
    Date createTime;

    public Token(String ans, Date createTime) {
        this.ans = ans;
        this.createTime = createTime;
    }

    public Token() {
    }

    public String getAns() {
        return ans;
    }

    public void setAns(String ans) {
        this.ans = ans;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    @Override
    public String toString() {
        return "Token{" +
                "ans='" + ans + '\'' +
                ", createTime=" + createTime +
                '}';
    }
}
