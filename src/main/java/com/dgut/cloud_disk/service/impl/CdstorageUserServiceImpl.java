package com.dgut.cloud_disk.service.impl;

import com.dgut.cloud_disk.mapper.CdstorageUserMapper;
import com.dgut.cloud_disk.pojo.CdstorageUser;
import com.dgut.cloud_disk.pojo.bo.ShareUserBo;
import com.dgut.cloud_disk.pojo.bo.UserBo;
import com.dgut.cloud_disk.pojo.vo.CdstorageUserVo;
import com.dgut.cloud_disk.service.CdstorageUserService;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.apache.catalina.User;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.entity.Example;

import java.util.ArrayList;
import java.util.List;

@Service
public class CdstorageUserServiceImpl implements CdstorageUserService {
    @Autowired(required = false)
    private CdstorageUserMapper cdstorageUserMapper;

    @Override
    public List<CdstorageUser> allUser() {
        Example example = new Example(CdstorageUser.class);
        Example.Criteria criteria = example.createCriteria();
        List<Integer> lists = new ArrayList<>();
        lists.add(3);
        criteria.andNotIn("userPermission",lists);
        List<CdstorageUser> users= cdstorageUserMapper.selectByExample(example);
        return users;
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
    public int updateUser1(CdstorageUserVo cdstorageUserVo) {
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
        if(StringUtils.isNotBlank(cdstorageUserVo.getUserMobie())){
            cdstorageUser.setUserMobie(cdstorageUserVo.getUserMobie());
        }
        if(StringUtils.isNotBlank(cdstorageUserVo.getUserSex())){
            cdstorageUser.setUserSex(cdstorageUserVo.getUserSex());
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
    public CdstorageUser selByUserId(String userId) {
        return cdstorageUserMapper.selectByPrimaryKey(userId);
    }

    @Override
    public List<CdstorageUser> selByUserPhone(String token, String userMobie) {
        Example example = new Example(CdstorageUser.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.andLike("userMobie","%"+userMobie+"%");
        List<CdstorageUser> users = cdstorageUserMapper.selectByExample(example);
        return users;
    }

    @Override
    public CdstorageUser selByUserMobie(String userMobie) {
        Example example = new Example(CdstorageUser.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.andEqualTo("userMobie",userMobie);
        CdstorageUser user = cdstorageUserMapper.selectOneByExample(example);
        return user;
    }

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
        CdstorageUser user = cdstorageUserMapper.selectOneByExample(example);
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
        if(cdstorageUserMapper.updateByExampleSelective(user, example) == 1) {
            return true;
        }
        return false;
    }

    @Override
    public boolean updateUser(CdstorageUser user) {
        Example example = new Example(CdstorageUser.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.andEqualTo("userWorkId",user.getUserWorkId());
        if(cdstorageUserMapper.updateByExampleSelective(user, example) == 1) {
            return true;
        }
        return false;
    }
    @Override
    public List<CdstorageUser> allManages() {
        Example example = new Example(CdstorageUser.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.andEqualTo("userPermission", 2);
        return cdstorageUserMapper.selectByExample(example);
    }

    @Override
    public boolean addManage(String userId) {
        Example example = new Example(CdstorageUser.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.andEqualTo("userId", userId);
        CdstorageUser cdstorageUser = cdstorageUserMapper.selectOneByExample(example);
        if (cdstorageUser == null) {
            return false;
        } else {
            cdstorageUser.setUserPermission(2);
            if (cdstorageUserMapper.updateByPrimaryKey(cdstorageUser) == 1) {
                return true;
            } else {
                return false;
            }
        }
    }

    @Override
    public boolean delManage(String userId) {
        Example example = new Example(CdstorageUser.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.andEqualTo("userId", userId);
        CdstorageUser cdstorageUser = cdstorageUserMapper.selectOneByExample(example);
        if (cdstorageUser == null) {
            return false;
        } else {
            cdstorageUser.setUserPermission(0);
            if (cdstorageUserMapper.updateByPrimaryKey(cdstorageUser) == 1) {
                return true;
            } else {
                return false;
            }
        }
    }

    @Override
    public List<ShareUserBo> simpleUserExcludeManager() {
        List<ShareUserBo> shareUserBos = new ArrayList<ShareUserBo>();
        Example example = new Example(CdstorageUser.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.andEqualTo("userPermission",0);
        List<CdstorageUser> cdstorageUsers= cdstorageUserMapper.selectByExample(example);
        for(CdstorageUser u: cdstorageUsers){
            ShareUserBo shareUserBo = new ShareUserBo();
            shareUserBo.setUserID(u.getUserId());
            shareUserBo.setUserName(u.getUserName());
            shareUserBo.setWorkID(u.getUserWorkId().toString());
            shareUserBos.add(shareUserBo);
        }
        return shareUserBos;
    }
}
