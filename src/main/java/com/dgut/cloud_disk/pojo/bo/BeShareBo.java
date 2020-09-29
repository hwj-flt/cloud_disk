package com.dgut.cloud_disk.pojo.bo;

public class BeShareBo {
    private  String shareID;
    private  String fileName;					//如果分享的是文件就填文件名
    private  String directName;				//如果分享的是文件夹就填文件夹名
    private  String shareUserName;
    private  String shareTime;

    public String getShareID() {
        return shareID;
    }

    public void setShareID(String shareID) {
        this.shareID = shareID;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public String getDirectName() {
        return directName;
    }

    public void setDirectName(String directName) {
        this.directName = directName;
    }

    public String getShareUserName() {
        return shareUserName;
    }

    public void setShareUserName(String shareUserName) {
        this.shareUserName = shareUserName;
    }

    public String getShareTime() {
        return shareTime;
    }

    public void setShareTime(String shareTime) {
        this.shareTime = shareTime;
    }
}
