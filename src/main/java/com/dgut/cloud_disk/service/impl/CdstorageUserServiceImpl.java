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
     *根据手机号查询用户
     * @param userMobie
     * @return user 查询结果
     */
    @Override
    public CdstorageUser queryByUserMobie(String userMobie) {
        Example example = new Example(CdstorageUser.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.andEqualTo("userMobie",userMobie);
        CdstorageUser user = userMapper.selectOneByExample(example);
        return user;
    }

    /**
     * 根据手机号修改密码
     * @param userPhone 手机号
     * @param userPassword 新密码
     * @return 修改成功true，失败false
     */
    @Override
    public boolean updateUserPassword(String userPhone, String userPassword) {
        CdstorageUser user = new CdstorageUser();
        user.setUserPassword(userPassword);
        Example example = new Example(CdstorageUser.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.andEqualTo("userMobie",userPhone);
        if(userMapper.updateByExampleSelective(user, example) == 1) {
            return true;
        }
        return false;
    }

    @Override
    public boolean updateUser(CdstorageUser user) {
        Example example = new Example(CdstorageUser.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.andEqualTo("userWorkId",user.getUserWorkId());
        if(userMapper.updateByExampleSelective(user, example) == 1) {
            return true;
        }
        return false;
    }
}
