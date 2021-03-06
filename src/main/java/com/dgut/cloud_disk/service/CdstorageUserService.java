package com.dgut.cloud_disk.service;

import com.dgut.cloud_disk.pojo.CdstorageUser;
import com.dgut.cloud_disk.pojo.DepartmentUser;
import com.dgut.cloud_disk.pojo.bo.ShareUserBo;
import com.dgut.cloud_disk.pojo.vo.CdstorageUserVo;

import java.util.List;

public interface CdstorageUserService {
    /**
     * 查询所有用户
     * @return 用户列表
     */
    public List<CdstorageUser> allUser();

    /**
     * 用户注销（禁用）
     * @param token
     * @param userID
     * @return 数据库影响条数
     */
    public int updateUserStatus(String token,String userID);

    /**
     * 用户解除注销（解禁）
     * @param token
     * @param userID
     * @return 数据库影响条数
     */
    public int updateUserStatus1(String token,String userID);
    /**
     * 更新用户
     * @param cdstorageUserVo
     * @return 数据库影响条数
     */
    public int updateUser1(CdstorageUserVo cdstorageUserVo);

    /**
     * 用户名进行查询
     * @param token
     * @param userName
     * @return 用户名查询结果
     */
    public List<CdstorageUser> selByUserName(String token,String userName);
    /**
     * 用工号进行查询
     * @param token
     * @param userWorkId
     * @return 工号查询结果
     */
    public List<CdstorageUser> selByWorkId(String token,String userWorkId);

    /**
     * 用用户id进行查询
     * @param userId
     * @return
     */
    public CdstorageUser selByUserId(String userId);

    /**
     * 用手机号进行查询（模糊）
     * @param token
     * @param userMobie
     * @return 手机号查询结果
     */
    public List<CdstorageUser> selByUserPhone(String token,String userMobie);

    /**
     * 用手机号进行查询
     * @param userMobie
     * @return
     */
    public CdstorageUser selByUserMobie(String userMobie);

    CdstorageUser queryByUserMobie(String userName);
    boolean updateUserPassword(String userPhone,String userPassword);
    boolean updateUser(CdstorageUser user);
    public List<CdstorageUser> allManages();
    public boolean addManage(String userId);
    public boolean delManage(String userId);

    public List<ShareUserBo> simpleUserExcludeManager();

}
