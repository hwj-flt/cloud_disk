package com.dgut.cloud_disk.service.impl;

import com.dgut.cloud_disk.mapper.DepartmentUserMapper;
import com.dgut.cloud_disk.pojo.DepartmentUser;
import com.dgut.cloud_disk.service.DepartmentUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DepartmentUserServiceImpl implements DepartmentUserService {
    @Autowired
    private DepartmentUserMapper departmentUserMapper;
    @Override
    public List<DepartmentUser> allUser() {
        List<DepartmentUser>  users= departmentUserMapper.selectAll();
        System.out.println(users);
        return users;
    }
}
