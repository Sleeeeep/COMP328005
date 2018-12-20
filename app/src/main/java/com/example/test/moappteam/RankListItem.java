package com.example.test.moappteam;

public class RankListItem {
    private String user;
    private int rank;
    private int replyNum;
    private int likeNum;

    public void setUser(String user) {
        this.user = user;
    }
    public void setRank(int rank){this.rank = rank; }
    public void setReplyNum(int replyNum) {
        this.replyNum = replyNum;
    }
    public void setLikeNum(int likeNum) {
        this.likeNum = likeNum;
    }

    public String getUser() {
        return user;
    }
    public int getRank(){return rank;}
    public int getReplyNum() {
        return replyNum;
    }
    public int getLikeNum() {
        return likeNum;
    }
}
