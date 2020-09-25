package com.dgut.cloud_disk.service;

import com.dgut.cloud_disk.pojo.DepartmentUser;

import java.util.List;

public interface ManagerService {
    List<DepartmentUser> delDepartUser(String departId, List<String> userIds);
}
