package com.dgut.cloud_disk.service.impl;

import com.dgut.cloud_disk.mapper.CdstorageUserMapper;
import com.dgut.cloud_disk.mapper.DepartmentMapper;
import com.dgut.cloud_disk.mapper.DepartmentUserMapper;
import com.dgut.cloud_disk.mapper.DirectoryMapper;
import com.dgut.cloud_disk.pojo.CdstorageUser;
import com.dgut.cloud_disk.pojo.Department;
import com.dgut.cloud_disk.pojo.DepartmentUser;
import com.dgut.cloud_disk.pojo.Directory;
import com.dgut.cloud_disk.service.ManagerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.entity.Example;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
public class ManagerServiceImpl implements ManagerService {
    @Autowired(required = false)
    private DepartmentUserMapper departmentUserMapper;
    @Autowired(required = false)
    private DepartmentMapper departmentMapper;
    @Autowired(required = false)
    private DirectoryMapper directoryMapper;
    @Autowired(required = false)
    private CdstorageUserMapper cdstorageUserMapper;
    @Override
    public Boolean delDepartUser(String departId, List<String> userIds) {
        Example example = new Example(DepartmentUser.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.andEqualTo("duDepartId",departId);
        criteria.andIn("duUserId",userIds);
        int num = departmentUserMapper.deleteByExample(example);
        if (num>0){
            return true;
        }else {
            return false;
        }
    }

    @Override
    public Boolean addDepartment(Department department) {
        int i = departmentMapper.insertSelective(department);
        if(i > 0){
            return true;
        }else {
            return false;
        }
    }

    @Override
    public Boolean setDepPerm(String departId, String permission) {
        Example example = new Example(Department.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.andEqualTo("departId",departId);
        Department department = departmentMapper.selectOneByExample(example);
        department.setDepartPermission(permission);
        int num = departmentMapper.updateByPrimaryKey(department);
        if (num > 0){
            return true;
        }else {
            return false;
        }
    }

    @Override
    public Boolean addUserToDepart(String departId, List<String> userIds) {
        DepartmentUser departmentUser = new DepartmentUser();
        int count = 0;
        departmentUser.setDuDepartId(departId);
        for (String id : userIds) {
            departmentUser.setDepartUserId(UUID.randomUUID().toString().replaceAll("-",""));
            departmentUser.setDuUserId(id);
            departmentUserMapper.insert(departmentUser);
            count++;
        }
        if (count>0){
            return true;
        }else {
            return false;
        }
    }

    @Override
    public List<Department> allDepart() {
        return departmentMapper.selectAll();
    }

    @Override
    public List<CdstorageUser> selUserAtDep(String departId) {
        Example example = new Example(DepartmentUser.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.andEqualTo("duDepartId",departId);
        List<DepartmentUser> departmentUsers = departmentUserMapper.selectByExample(example);
        Example example1 = new Example(CdstorageUser.class);
        Example.Criteria criteria1 = example1.createCriteria();
        List<String> userIds = new ArrayList<String>();
        for (DepartmentUser d:departmentUsers) {
            userIds.add(d.getDuUserId());
        }
        criteria1.andIn("userId",userIds);
        return cdstorageUserMapper.selectByExample(example1);
    }
}
