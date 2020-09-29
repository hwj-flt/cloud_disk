package com.dgut.cloud_disk.pojo.bo;

import java.util.List;

public class ShareBo {
    private List<BeShareBo> beShares;

    private List<ToShareBo> toShares;

    public List<BeShareBo> getBeShares() {
        return beShares;
    }

    public void setBeShares(List<BeShareBo> beShares) {
        this.beShares = beShares;
    }

    public List<ToShareBo> getToShares() {
        return toShares;
    }

    public void setToShares(List<ToShareBo> toShares) {
        this.toShares = toShares;
    }
}
