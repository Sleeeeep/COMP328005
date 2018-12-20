package com.example.test.moappteam;

public class ReplyListItem {
    private String user;
    private String time;
    private String text;
    private int likeNum;

    public void setUser(String user) {
        this.user = user;
    }
    public void setTime(String time) {
        this.time = time;
    }
    public void setLikeNum(int likeNum) {
        this.likeNum = likeNum;
    }
    public void setText(String text) {
        this.text = text;
    }

    public String getUser() {
        return user;
    }
    public String getTime() {
        return time;
    }
    public int getLikeNum() {
        return likeNum;
    }
    public String getText() {
        return text;
    }
}