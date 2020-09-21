package com.dgut.cloud_disk.service;

import com.dgut.cloud_disk.pojo.CdstorageUser;
import com.dgut.cloud_disk.pojo.DepartmentUser;

import java.util.List;

public interface CdstorageUserService {
    /**
     * 查询所有用户
     * @return 用户列表
     */
    public List<CdstorageUser> allUser();

    /**
     * 用户注销（禁用）
     * @param cdstorageUser 用户
     * @return
     */
    public int updateUserStatus(CdstorageUser cdstorageUser);

    /**
     * 用户解除注销（解禁）
     * @param cdstorageUser
     * @return 数据库影响条数
     */
    public int updateUserStatus1(CdstorageUser cdstorageUser);
    /**
     * 更新用户（邮箱和手机号）
     * @param cdstorageUser
     * @return 数据库影响条数
     */
    public int updateUser(CdstorageUser cdstorageUser);
}
