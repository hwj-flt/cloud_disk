package com.dgut.cloud_disk.service;

import com.dgut.cloud_disk.pojo.DepartmentUser;

import java.util.List;

public interface DepartmentUserService {
    public String selectdepartPermissionByid(String dDepartId);

    public String getDepartIDByid(String Uid);
}
