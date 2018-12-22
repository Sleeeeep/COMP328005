package com.example.test.moappteam;

public class SpeakListItem {
    private int speakNum;
    private String title;
    private String user;
    private String time;
    private String classify;
    private String text;
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
    public void setText(String text) {
        this.text = text;
    }
    public void setSpeakNum(int speakNum) {
        this.speakNum = speakNum;
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
    public String getText() {
        return text;
    }
    public int getSpeakNum() {
        return speakNum;
    }
}