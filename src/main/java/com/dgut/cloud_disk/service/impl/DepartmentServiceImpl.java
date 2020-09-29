package com.dgut.cloud_disk.service.impl;

import com.dgut.cloud_disk.mapper.DepartmentMapper;
import com.dgut.cloud_disk.pojo.Department;
import com.dgut.cloud_disk.service.DepartmentService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
@Service
public class DepartmentServiceImpl implements DepartmentService {
    @Resource
    DepartmentMapper departmentMapper;
    @Override
    public Department selDepart(String departId) {
        Department department = departmentMapper.selectByPrimaryKey(departId);
        return department;
    }
}
