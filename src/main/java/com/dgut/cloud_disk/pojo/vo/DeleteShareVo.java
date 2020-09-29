package com.dgut.cloud_disk.pojo.vo;

import java.util.List;

public class DeleteShareVo {
        private  String token;
        private List<String> shareIDs;

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public List<String> getShareIDs() {
        return shareIDs;
    }

    public void setShareIDs(List<String> shareIDs) {
        this.shareIDs = shareIDs;
    }
}
