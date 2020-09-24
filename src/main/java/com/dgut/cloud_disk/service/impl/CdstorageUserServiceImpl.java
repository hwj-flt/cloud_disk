package com.dgut.cloud_disk.service.impl;

import com.dgut.cloud_disk.mapper.CdstorageUserMapper;
import com.dgut.cloud_disk.pojo.CdstorageUser;
import com.dgut.cloud_disk.service.CdstorageUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CdstorageUserServiceImpl implements CdstorageUserService {
    @Autowired(required = false)
    private CdstorageUserMapper cdstorageUserMapper;
    @Override
    public List<CdstorageUser> allUser() {
        List<CdstorageUser>  users= cdstorageUserMapper.selectAll();

        return users;
    }
}
