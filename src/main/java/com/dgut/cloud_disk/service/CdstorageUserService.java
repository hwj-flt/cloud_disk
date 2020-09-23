package com.dgut.cloud_disk.service;

import com.dgut.cloud_disk.pojo.CdstorageUser;
<<<<<<< HEAD
import com.dgut.cloud_disk.pojo.DepartmentUser;

import java.util.List;

public interface CdstorageUserService {
    public List<CdstorageUser> allUser();
=======

public interface CdstorageUserService {
    CdstorageUser queryByUserMobie(String userName);
    boolean updateUserPassword(String userPhone,String userPassword);
    boolean updateUser(CdstorageUser user);
>>>>>>> origin/zjl
}
