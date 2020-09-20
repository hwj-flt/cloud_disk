package com.dgut.cloud_disk.service.impl;

import com.dgut.cloud_disk.mapper.CdstorageUserMapper;
import com.dgut.cloud_disk.pojo.CdstorageUser;
import com.dgut.cloud_disk.service.CdstorageUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.entity.Example;
@Service
public class CdstorageUserServiceImpl implements CdstorageUserService {
    @Autowired(required = false)
    CdstorageUserMapper userMapper;

    /**
     *根据用户名查询用户
     * @param userName
     * @return user 查询结果
     */
    @Override
    public CdstorageUser queryByUserName(String userName) {
        Example example = new Example(CdstorageUser.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.andEqualTo("userName",userName);
        CdstorageUser user = userMapper.selectOneByExample(example);
        return user;
    }
}
