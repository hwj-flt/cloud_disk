package com.dgut.cloud_disk.pojo;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import javax.persistence.*;

@Table(name = "`cdstorage_user`")
public class CdstorageUser implements Serializable {
    /**
     * 用户ID
     */
    @Id
    @Column(name = "`USER_ID`")
    private String userId;

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
    private String userUsed;

    /**
     * 1-表示用户正常 0-表示用户注销 其它状态可以继续添加
     */
    @Column(name = "`USER_STATUS`")
    private Integer userStatus;

    /**
     * 0-表示普通用户权限 1-表示管理员权限 其它权限可以继续添加
     */
    @Column(name = "`USER_PERMISSION`")
    private Integer userPermission;

    private static final long serialVersionUID = 1L;

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
    public String getUserUsed() {
        return userUsed;
    }

    /**
     * 设置存储空间大小使用MB作为单位。最大可以表示1TB，最小可以表示1位
     *
     * @param userUsed 存储空间大小使用MB作为单位。最大可以表示1TB，最小可以表示1位
     */
    public void setUserUsed(String userUsed) {
        this.userUsed = userUsed;
    }

    /**
     * 获取1-表示用户正常 0-表示用户注销 其它状态可以继续添加
     *
     * @return USER_STATUS - 1-表示用户正常 0-表示用户注销 其它状态可以继续添加
     */
    public Integer getUserStatus() {
        return userStatus;
    }

    /**
     * 设置1-表示用户正常 0-表示用户注销 其它状态可以继续添加
     *
     * @param userStatus 1-表示用户正常 0-表示用户注销 其它状态可以继续添加
     */
    public void setUserStatus(Integer userStatus) {
        this.userStatus = userStatus;
    }

    /**
     * 获取0-表示普通用户权限 1-表示管理员权限 其它权限可以继续添加
     *
     * @return USER_PERMISSION - 0-表示普通用户权限 1-表示管理员权限 其它权限可以继续添加
     */
    public Integer getUserPermission() {
        return userPermission;
    }

    /**
     * 设置0-表示普通用户权限 1-表示管理员权限 其它权限可以继续添加
     *
     * @param userPermission 0-表示普通用户权限 1-表示管理员权限 其它权限可以继续添加
     */
    public void setUserPermission(Integer userPermission) {
        this.userPermission = userPermission;
    }
}