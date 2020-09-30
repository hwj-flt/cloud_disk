package com.dgut.cloud_disk.pojo;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import javax.persistence.*;

@Table(name = "`CDSTORAGEUSERDEPARTMENTUSER`")
public class Cdstorageuserdepartmentuser implements Serializable {
    /**
     * 映射ID
     */
    @Column(name = "`DEPART_USER_ID`")
    private String departUserId;

    /**
     * 群组ID
     */
    @Column(name = "`DU_DEPART_ID`")
    private String duDepartId;

    /**
     * 用户ID
     */
    @Column(name = "`DU_USER_ID_`")
    private String duUserId;

    /**
     * 用户ID
     */
    @Column(name = "`USER_ID`")
    private String userId;

    /**
     * 工号
     */
    @Column(name = "`USER_WORK_ID`")
    private Long userWorkId;

    /**
     * 用户名
     */
    @Column(name = "`USER_NAME`")
    private String userName;

    /**
     * 用户性别
     */
    @Column(name = "`USER_SEX`")
    private String userSex;

    /**
     * 用户创建时间
     */
    @Column(name = "`USER_TIME`")
    private Date userTime;

    /**
     * 密码
     */
    @Column(name = "`USER_PASSWORD`")
    private String userPassword;

    /**
     * 手机号码
     */
    @Column(name = "`USER_MOBIE`")
    private String userMobie;

    /**
     * 邮箱地址
     */
    @Column(name = "`USER_EMAIL`")
    private String userEmail;

    /**
     * 用户根目录ID
     */
    @Column(name = "`USER_ROOT_ID`")
    private String userRootId;

    /**
     * 回收站ID
     */
    @Column(name = "`USER_GARBAGE`")
    private String userGarbage;

    /**
     * 存储空间大小使用MB作为单位。最大可以表示1TB，最小可以表示1位
     */
    @Column(name = "`USER_SIZE`")
    private BigDecimal userSize;

    /**
     * 存储空间大小使用MB作为单位。最大可以表示1TB，最小可以表示1位
     */
    @Column(name = "`USER_USED`")
    private BigDecimal userUsed;

    /**
     * 1-表示用户正常 0-表示用户注销 其它状态可以继续添加
     */
    @Column(name = "`USER_STATUS`")
    private Byte userStatus;

    /**
     * 0-表示普通用户权限 2-表示文档管理员权限 3-表示超级管理员  其它权限可以继续添加
     */
    @Column(name = "`USER_PERMISSION`")
    private Integer userPermission;

    /**
     * 群组ID
     */
    @Column(name = "`DEPART_ID`")
    private String departId;

    /**
     * 群组名称
     */
    @Column(name = "`DEPART_NAME`")
    private String departName;

    /**
     * 群组文件夹根目录ID
     */
    @Column(name = "`DEPART_ROOT`")
    private String departRoot;

    /**
     * 群组创建时间
     */
    @Column(name = "`DEPART_TIME`")
    private Date departTime;

    /**
     * 11111111-文档管理员（上传、下载、删除、创建文件夹、分享、复制、移动、重命名）
     */
    @Column(name = "`DEPART_PERMISSION`")
    private String departPermission;

    private static final long serialVersionUID = 1L;

    /**
     * 获取映射ID
     *
     * @return DEPART_USER_ID - 映射ID
     */
    public String getDepartUserId() {
        return departUserId;
    }

    /**
     * 设置映射ID
     *
     * @param departUserId 映射ID
     */
    public void setDepartUserId(String departUserId) {
        this.departUserId = departUserId;
    }

    /**
     * 获取群组ID
     *
     * @return DU_DEPART_ID - 群组ID
     */
    public String getDuDepartId() {
        return duDepartId;
    }

    /**
     * 设置群组ID
     *
     * @param duDepartId 群组ID
     */
    public void setDuDepartId(String duDepartId) {
        this.duDepartId = duDepartId;
    }

    /**
     * 获取用户ID
     *
     * @return DU_USER_ID_ - 用户ID
     */
    public String getDuUserId() {
        return duUserId;
    }

    /**
     * 设置用户ID
     *
     * @param duUserId 用户ID
     */
    public void setDuUserId(String duUserId) {
        this.duUserId = duUserId;
    }

    /**
     * 获取用户ID
     *
     * @return USER_ID - 用户ID
     */
    public String getUserId() {
        return userId;
    }

    /**
     * 设置用户ID
     *
     * @param userId 用户ID
     */
    public void setUserId(String userId) {
        this.userId = userId;
    }

    /**
     * 获取工号
     *
     * @return USER_WORK_ID - 工号
     */
    public Long getUserWorkId() {
        return userWorkId;
    }

    /**
     * 设置工号
     *
     * @param userWorkId 工号
     */
    public void setUserWorkId(Long userWorkId) {
        this.userWorkId = userWorkId;
    }

    /**
     * 获取用户名
     *
     * @return USER_NAME - 用户名
     */
    public String getUserName() {
        return userName;
    }

    /**
     * 设置用户名
     *
     * @param userName 用户名
     */
    public void setUserName(String userName) {
        this.userName = userName;
    }

    /**
     * 获取用户性别
     *
     * @return USER_SEX - 用户性别
     */
    public String getUserSex() {
        return userSex;
    }

    /**
     * 设置用户性别
     *
     * @param userSex 用户性别
     */
    public void setUserSex(String userSex) {
        this.userSex = userSex;
    }

    /**
     * 获取用户创建时间
     *
     * @return USER_TIME - 用户创建时间
     */
    public Date getUserTime() {
        return userTime;
    }

    /**
     * 设置用户创建时间
     *
     * @param userTime 用户创建时间
     */
    public void setUserTime(Date userTime) {
        this.userTime = userTime;
    }

    /**
     * 获取密码
     *
     * @return USER_PASSWORD - 密码
     */
    public String getUserPassword() {
        return userPassword;
    }

    /**
     * 设置密码
     *
     * @param userPassword 密码
     */
    public void setUserPassword(String userPassword) {
        this.userPassword = userPassword;
    }

    /**
     * 获取手机号码
     *
     * @return USER_MOBIE - 手机号码
     */
    public String getUserMobie() {
        return userMobie;
    }

    /**
     * 设置手机号码
     *
     * @param userMobie 手机号码
     */
    public void setUserMobie(String userMobie) {
        this.userMobie = userMobie;
    }

    /**
     * 获取邮箱地址
     *
     * @return USER_EMAIL - 邮箱地址
     */
    public String getUserEmail() {
        return userEmail;
    }

    /**
     * 设置邮箱地址
     *
     * @param userEmail 邮箱地址
     */
    public void setUserEmail(String userEmail) {
        this.userEmail = userEmail;
    }

    /**
     * 获取用户根目录ID
     *
     * @return USER_ROOT_ID - 用户根目录ID
     */
    public String getUserRootId() {
        return userRootId;
    }

    /**
     * 设置用户根目录ID
     *
     * @param userRootId 用户根目录ID
     */
    public void setUserRootId(String userRootId) {
        this.userRootId = userRootId;
    }

    /**
     * 获取回收站ID
     *
     * @return USER_GARBAGE - 回收站ID
     */
    public String getUserGarbage() {
        return userGarbage;
    }

    /**
     * 设置回收站ID
     *
     * @param userGarbage 回收站ID
     */
    public void setUserGarbage(String userGarbage) {
        this.userGarbage = userGarbage;
    }

    /**
     * 获取存储空间大小使用MB作为单位。最大可以表示1TB，最小可以表示1位
     *
     * @return USER_SIZE - 存储空间大小使用MB作为单位。最大可以表示1TB，最小可以表示1位
     */
    public BigDecimal getUserSize() {
        return userSize;
    }

    /**
     * 设置存储空间大小使用MB作为单位。最大可以表示1TB，最小可以表示1位
     *
     * @param userSize 存储空间大小使用MB作为单位。最大可以表示1TB，最小可以表示1位
     */
    public void setUserSize(BigDecimal userSize) {
        this.userSize = userSize;
    }

    /**
     * 获取存储空间大小使用MB作为单位。最大可以表示1TB，最小可以表示1位
     *
     * @return USER_USED - 存储空间大小使用MB作为单位。最大可以表示1TB，最小可以表示1位
     */
    public BigDecimal getUserUsed() {
        return userUsed;
    }

    /**
     * 设置存储空间大小使用MB作为单位。最大可以表示1TB，最小可以表示1位
     *
     * @param userUsed 存储空间大小使用MB作为单位。最大可以表示1TB，最小可以表示1位
     */
    public void setUserUsed(BigDecimal userUsed) {
        this.userUsed = userUsed;
    }

    /**
     * 获取1-表示用户正常 0-表示用户注销 其它状态可以继续添加
     *
     * @return USER_STATUS - 1-表示用户正常 0-表示用户注销 其它状态可以继续添加
     */
    public Byte getUserStatus() {
        return userStatus;
    }

    /**
     * 设置1-表示用户正常 0-表示用户注销 其它状态可以继续添加
     *
     * @param userStatus 1-表示用户正常 0-表示用户注销 其它状态可以继续添加
     */
    public void setUserStatus(Byte userStatus) {
        this.userStatus = userStatus;
    }

    /**
     * 获取0-表示普通用户权限 2-表示文档管理员权限 3-表示超级管理员  其它权限可以继续添加
     *
     * @return USER_PERMISSION - 0-表示普通用户权限 2-表示文档管理员权限 3-表示超级管理员  其它权限可以继续添加
     */
    public Integer getUserPermission() {
        return userPermission;
    }

    /**
     * 设置0-表示普通用户权限 2-表示文档管理员权限 3-表示超级管理员  其它权限可以继续添加
     *
     * @param userPermission 0-表示普通用户权限 2-表示文档管理员权限 3-表示超级管理员  其它权限可以继续添加
     */
    public void setUserPermission(Integer userPermission) {
        this.userPermission = userPermission;
    }

    /**
     * 获取群组ID
     *
     * @return DEPART_ID - 群组ID
     */
    public String getDepartId() {
        return departId;
    }

    /**
     * 设置群组ID
     *
     * @param departId 群组ID
     */
    public void setDepartId(String departId) {
        this.departId = departId;
    }

    /**
     * 获取群组名称
     *
     * @return DEPART_NAME - 群组名称
     */
    public String getDepartName() {
        return departName;
    }

    /**
     * 设置群组名称
     *
     * @param departName 群组名称
     */
    public void setDepartName(String departName) {
        this.departName = departName;
    }

    /**
     * 获取群组文件夹根目录ID
     *
     * @return DEPART_ROOT - 群组文件夹根目录ID
     */
    public String getDepartRoot() {
        return departRoot;
    }

    /**
     * 设置群组文件夹根目录ID
     *
     * @param departRoot 群组文件夹根目录ID
     */
    public void setDepartRoot(String departRoot) {
        this.departRoot = departRoot;
    }

    /**
     * 获取群组创建时间
     *
     * @return DEPART_TIME - 群组创建时间
     */
    public Date getDepartTime() {
        return departTime;
    }

    /**
     * 设置群组创建时间
     *
     * @param departTime 群组创建时间
     */
    public void setDepartTime(Date departTime) {
        this.departTime = departTime;
    }

    /**
     * 获取11111111-文档管理员（上传、下载、删除、创建文件夹、分享、复制、移动、重命名）
     *
     * @return DEPART_PERMISSION - 11111111-文档管理员（上传、下载、删除、创建文件夹、分享、复制、移动、重命名）
     */
    public String getDepartPermission() {
        return departPermission;
    }

    /**
     * 设置11111111-文档管理员（上传、下载、删除、创建文件夹、分享、复制、移动、重命名）
     *
     * @param departPermission 11111111-文档管理员（上传、下载、删除、创建文件夹、分享、复制、移动、重命名）
     */
    public void setDepartPermission(String departPermission) {
        this.departPermission = departPermission;
    }
}