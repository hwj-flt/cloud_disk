package com.dgut.cloud_disk.service.impl;

import com.dgut.cloud_disk.mapper.DepartmentMapper;
import com.dgut.cloud_disk.mapper.DepartmentUserMapper;
import com.dgut.cloud_disk.pojo.Department;
import com.dgut.cloud_disk.pojo.DepartmentUser;
import com.dgut.cloud_disk.service.DepartmentUserService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.entity.Example;

import java.util.List;

@Service
public class DepartmentUserServiceImpl implements DepartmentUserService {
    @Autowired(required = false)
    private DepartmentUserMapper departmentUserMapper;
    @Autowired(required = false)
    private DepartmentMapper departmentMapper;

 /*   @Override
    public DepartmentUser selectduPermissionByid(String duUserId, String duDepartId) {
        Example example = new Example(DepartmentUser.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.andEqualTo("duUserId", duUserId);
        criteria.andEqualTo("duDepartId",duDepartId);
        DepartmentUser departmentUser = departmentUserMapper.selectOneByExample(example);
        return departmentUser;
    }*/

    @Override
    public String selectdepartPermissionByid(String dDepartId) {
        Department department=departmentMapper.selectByPrimaryKey(dDepartId);
        //department;
        return null;
    }

    @Override
    public String getDepartIDByid(String Uid) {
        Example example =new Example((DepartmentUser.class));
        Example.Criteria criteria=example.createCriteria();
        criteria.andEqualTo("duUserId",Uid);
        DepartmentUser DUser=departmentUserMapper.selectOneByExample(example);
        return DUser.getDuDepartId();
    }
}
