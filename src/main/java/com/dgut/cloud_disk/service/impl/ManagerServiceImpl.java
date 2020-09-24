package com.dgut.cloud_disk.service.impl;

import com.dgut.cloud_disk.mapper.DepartmentUserMapper;
import com.dgut.cloud_disk.pojo.CdstorageUser;
import com.dgut.cloud_disk.pojo.DepartmentUser;
import com.dgut.cloud_disk.service.ManagerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.entity.Example;

import java.util.Arrays;
import java.util.List;
@Service
public class ManagerServiceImpl implements ManagerService {
    @Autowired(required = false)
    private DepartmentUserMapper departmentUserMapper;
    @Override
    public int delDepartUser(String [] userIds,String departId) {
        Example example = new Example(DepartmentUser.class);
        Example.Criteria criteria = example.createCriteria();
        List ids = Arrays.asList(userIds);
        criteria.andEqualTo("duDepartId",departId);
        criteria.andIn("duUserId",ids);
        return departmentUserMapper.deleteByExample(example);
    }
}
