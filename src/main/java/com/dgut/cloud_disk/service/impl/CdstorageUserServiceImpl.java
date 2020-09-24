package com.dgut.cloud_disk.service.impl;

import com.dgut.cloud_disk.mapper.CdstorageUserMapper;
import com.dgut.cloud_disk.pojo.CdstorageUser;
import com.dgut.cloud_disk.pojo.vo.CdstorageUserVo;
import com.dgut.cloud_disk.service.CdstorageUserService;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.entity.Example;

import java.util.List;

@Service
public class CdstorageUserServiceImpl implements CdstorageUserService {
    @Autowired(required = false)
    private CdstorageUserMapper cdstorageUserMapper;

    @Override
    public List<CdstorageUser> allUser(Integer pageNum,Integer pageSize,Integer showDisableUser) {
        PageHelper.startPage(pageNum,pageSize);
        List<CdstorageUser>  users = null;
        if(showDisableUser==1){
            users= cdstorageUserMapper.selectAll();
        }else {
            Example example = new Example(CdstorageUser.class);
            Example.Criteria criteria = example.createCriteria();
            criteria.andEqualTo("userStatus",1);
            users= cdstorageUserMapper.selectByExample(example);
        }
        PageInfo<CdstorageUser> page = new PageInfo<CdstorageUser>(users);
        System.out.println(page.getTotal());
        System.out.println(page.getPages());
        List<CdstorageUser>  resUsers = page.getList();
        return resUsers;
    }

    @Override
    public int updateUserStatus(String token,String userID) {
        Example example = new Example(CdstorageUser.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.andEqualTo("userId",userID);
        CdstorageUser cdstorageUser= (CdstorageUser) cdstorageUserMapper.selectOneByExample(example);
        cdstorageUser.setUserStatus((byte) 0);
        int num = cdstorageUserMapper.updateByPrimaryKey(cdstorageUser);
        return num;
    }

    @Override
    public int updateUserStatus1(String token,String userID) {
        Example example = new Example(CdstorageUser.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.andEqualTo("userId",userID);
        CdstorageUser cdstorageUser= (CdstorageUser) cdstorageUserMapper.selectOneByExample(example);
        cdstorageUser.setUserStatus((byte) 1);
        int num = cdstorageUserMapper.updateByPrimaryKey(cdstorageUser);
        return num;
    }

    @Override
    public int updateUser(CdstorageUserVo cdstorageUserVo) {
        Example example = new Example(CdstorageUser.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.andEqualTo("userId",cdstorageUserVo.getUserId());
        CdstorageUser cdstorageUser= (CdstorageUser) cdstorageUserMapper.selectOneByExample(example);
        if(StringUtils.isNotBlank(cdstorageUserVo.getUserName())){
            cdstorageUser.setUserName(cdstorageUserVo.getUserName());
        }
        if(StringUtils.isNotBlank(cdstorageUserVo.getUserEmail())){
            cdstorageUser.setUserEmail(cdstorageUserVo.getUserEmail());
        }
        if(StringUtils.isNotBlank(cdstorageUserVo.getUserPassword())){
            cdstorageUser.setUserPassword(cdstorageUserVo.getUserPassword());
        }
        if(StringUtils.isNotBlank(cdstorageUserVo.getUserMobie())){
            cdstorageUser.setUserMobie(cdstorageUserVo.getUserMobie());
        }
        if(cdstorageUserVo.getUserSize()!=null){
            cdstorageUser.setUserSize(cdstorageUserVo.getUserSize());
        }
        int num = cdstorageUserMapper.updateByPrimaryKey(cdstorageUser);
        return num;
    }

    @Override
    public List<CdstorageUser> selByUserName(String token, String userName) {
        Example example = new Example(CdstorageUser.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.andLike("userName","%"+userName+"%");
        List<CdstorageUser> users = cdstorageUserMapper.selectByExample(example);
        return users;
    }

    @Override
    public List<CdstorageUser> selByWorkId(String token, String userWorkId) {
        Example example = new Example(CdstorageUser.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.andLike("userWorkId","%"+userWorkId+"%");
        List<CdstorageUser> users = cdstorageUserMapper.selectByExample(example);
        return users;
    }

    @Override
    public List<CdstorageUser> selByUserPhone(String token, String userMobie) {
        Example example = new Example(CdstorageUser.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.andLike("userMobie","%"+userMobie+"%");
        List<CdstorageUser> users = cdstorageUserMapper.selectByExample(example);
        return users;
    }
}
