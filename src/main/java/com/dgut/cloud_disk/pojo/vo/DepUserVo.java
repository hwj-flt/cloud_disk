package com.dgut.cloud_disk.pojo.vo;

import java.util.List;

public class DepUserVo {
    private String token;
    private String departId;
    private List<String> userIds;

    @Override
    public String toString() {
        return "DepUserVo{" +
                "token='" + token + '\'' +
                ", departId='" + departId + '\'' +
                ", userIds=" + userIds +
                '}';
    }

    public DepUserVo(String token, String departId, List<String> userIds) {
        this.token = token;
        this.departId = departId;
        this.userIds = userIds;
    }

    public DepUserVo() {
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getDepartId() {
        return departId;
    }

    public void setDepartId(String departId) {
        this.departId = departId;
    }

    public List<String> getUserIds() {
        return userIds;
    }

    public void setUserIds(List<String> userIds) {
        this.userIds = userIds;
    }
}
