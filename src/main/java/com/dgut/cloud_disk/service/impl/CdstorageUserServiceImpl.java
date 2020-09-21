package com.dgut.cloud_disk.service.impl;

import com.dgut.cloud_disk.mapper.CdstorageUserMapper;
import com.dgut.cloud_disk.pojo.CdstorageUser;
import com.dgut.cloud_disk.service.CdstorageUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.entity.Example;

import java.util.List;

@Service
public class CdstorageUserServiceImpl implements CdstorageUserService {
    @Autowired(required = false)
    private CdstorageUserMapper cdstorageUserMapper;
    @Override
    public List<CdstorageUser> allUser() {
        List<CdstorageUser>  users= cdstorageUserMapper.selectAll();
//        Example example = new Example(CdstorageUser.class);
//        Example.Criteria criteria = example.createCriteria();
        return users;
    }

    @Override
    public int updateUserStatus(CdstorageUser cdstorageUser) {
        cdstorageUser.setUserStatus((byte) 0);
        int num = cdstorageUserMapper.updateByPrimaryKey(cdstorageUser);
        return num;
    }

    @Override
    public int updateUserStatus1(CdstorageUser cdstorageUser) {
        cdstorageUser.setUserStatus((byte) 1);
        int num = cdstorageUserMapper.updateByPrimaryKey(cdstorageUser);
        return num;
    }

    @Override
    public int updateUser(CdstorageUser cdstorageUser) {
        CdstorageUser cdstorageUser1= (CdstorageUser) cdstorageUserMapper.selectOne(cdstorageUser);
        cdstorageUser1.setUserEmail(cdstorageUser.getUserEmail());
        cdstorageUser1.setUserMobie(cdstorageUser.getUserMobie());
        int num = cdstorageUserMapper.updateByPrimaryKey(cdstorageUser1);
        return num;
    }
}
