package com.dgut.cloud_disk.service;

import com.dgut.cloud_disk.pojo.CdstorageUser;

public interface CdstorageUserService {
    CdstorageUser queryByUserMobie(String userName);
    boolean updateUserPassword(String userPhone,String userPassword);
    boolean updateUser(CdstorageUser user);
}
