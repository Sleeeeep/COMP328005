package com.example.test.moappteam;

import android.graphics.drawable.Drawable;

public class SpeakListItem {
    private String title;
    private String user;
    private String time;
    private String classify;
    private int replyNum;
    private int likeNum;

    public void setTitle(String title) {
        this.title = title;
    }
    public void setUser(String user) {
        this.user = user;
    }
    public void setTime(String time) {
        this.time = time;
    }
    public void setClassify(String classify) {
        this.classify = classify;
    }
    public void setReplyNum(int replyNum) {
        this.replyNum = replyNum;
    }
    public void setLikeNum(int likeNum) {
        this.likeNum = likeNum;
    }

    public String getTitle() {
        return title;
    }
    public String getUser() {
        return user;
    }
    public String getTime() {
        return time;
    }
    public String getClassify() {
        return classify;
    }
    public int getReplyNum() {
        return replyNum;
    }
    public int getLikeNum() {
        return likeNum;
    }
}