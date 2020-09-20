package com.dgut.cloud_disk.service;

import com.dgut.cloud_disk.pojo.CdstorageUser;

public interface CdstorageUserService {
    CdstorageUser queryByUserName(String userName);
}
