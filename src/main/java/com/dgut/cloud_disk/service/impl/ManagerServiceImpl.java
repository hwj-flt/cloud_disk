package com.dgut.cloud_disk.service.impl;

import com.dgut.cloud_disk.mapper.DepartmentUserMapper;
import com.dgut.cloud_disk.pojo.DepartmentUser;
import com.dgut.cloud_disk.service.ManagerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.entity.Example;

import java.util.List;
@Service
public class ManagerServiceImpl implements ManagerService {
    @Autowired(required = false)
    private DepartmentUserMapper departmentUserMapper;
    @Override
    public List<DepartmentUser> delDepartUser(String departId, List<String> userIds) {
        Example example = new Example(DepartmentUser.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.andEqualTo("duDepartId",departId);
//        criteria.andEqualTo("duUserId",userId);
        criteria.andIn("duUserId",userIds);
        return departmentUserMapper.selectByExample(example);
    }
}
