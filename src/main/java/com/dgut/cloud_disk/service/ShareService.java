package com.dgut.cloud_disk.service;

import com.dgut.cloud_disk.pojo.bo.BeShareBo;

import java.util.List;

public interface ShareService {
    List<BeShareBo> showBeShare(String beshareID);
}
