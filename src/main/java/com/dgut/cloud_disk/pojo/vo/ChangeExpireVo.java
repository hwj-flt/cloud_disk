package com.dgut.cloud_disk.pojo.vo;

public class ChangeExpireVo {
    private  String token;
    private  String shareID;
    private  Integer shareTime;		//分享有效期   表示3600s

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getShareID() {
        return shareID;
    }

    public void setShareID(String shareID) {
        this.shareID = shareID;
    }

    public Integer getShareTime() {
        return shareTime;
    }

    public void setShareTime(Integer shareTime) {
        this.shareTime = shareTime;
    }
}
