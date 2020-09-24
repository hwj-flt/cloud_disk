package com.dgut.cloud_disk.service;

import com.dgut.cloud_disk.pojo.CdstorageUser;
import com.dgut.cloud_disk.pojo.DepartmentUser;

import java.util.List;

public interface CdstorageUserService {
    CdstorageUser queryByUserMobie(String userName);
    boolean updateUserPassword(String userPhone,String userPassword);
    boolean updateUser(CdstorageUser user);
}
