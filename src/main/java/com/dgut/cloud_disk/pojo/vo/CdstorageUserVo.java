package com.dgut.cloud_disk.pojo.vo;

import javax.persistence.Column;
import javax.persistence.Id;
import java.math.BigDecimal;
import java.util.Date;

public class CdstorageUserVo {
    private String token;
    /**
     * 用户ID
     */
    @Id
    @Column(name = "`USER_ID`")
    private String userId;

    /**
     * 工号
     */
    @Column(name = "`USER_WORK_ID`")
    private Integer userWorkId;

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

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public Integer getUserWorkId() {
        return userWorkId;
    }

    public void setUserWorkId(Integer userWorkId) {
        this.userWorkId = userWorkId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserSex() {
        return userSex;
    }

    public void setUserSex(String userSex) {
        this.userSex = userSex;
    }

    public Date getUserTime() {
        return userTime;
    }

    public void setUserTime(Date userTime) {
        this.userTime = userTime;
    }

    public String getUserPassword() {
        return userPassword;
    }

    public void setUserPassword(String userPassword) {
        this.userPassword = userPassword;
    }

    public String getUserMobie() {
        return userMobie;
    }

    public void setUserMobie(String userMobie) {
        this.userMobie = userMobie;
    }

    public String getUserEmail() {
        return userEmail;
    }

    public void setUserEmail(String userEmail) {
        this.userEmail = userEmail;
    }

    public String getUserRootId() {
        return userRootId;
    }

    public void setUserRootId(String userRootId) {
        this.userRootId = userRootId;
    }

    public String getUserGarbage() {
        return userGarbage;
    }

    public void setUserGarbage(String userGarbage) {
        this.userGarbage = userGarbage;
    }

    public BigDecimal getUserSize() {
        return userSize;
    }

    public void setUserSize(BigDecimal userSize) {
        this.userSize = userSize;
    }

    public BigDecimal getUserUsed() {
        return userUsed;
    }

    public void setUserUsed(BigDecimal userUsed) {
        this.userUsed = userUsed;
    }

    public Byte getUserStatus() {
        return userStatus;
    }

    public void setUserStatus(Byte userStatus) {
        this.userStatus = userStatus;
    }

    public Integer getUserPermission() {
        return userPermission;
    }

    public void setUserPermission(Integer userPermission) {
        this.userPermission = userPermission;
    }

    @Override
    public String toString() {
        return "CdstorageUserVo{" +
                "token='" + token + '\'' +
                ", userId='" + userId + '\'' +
                ", userWorkId=" + userWorkId +
                ", userName='" + userName + '\'' +
                ", userSex='" + userSex + '\'' +
                ", userTime=" + userTime +
                ", userPassword='" + userPassword + '\'' +
                ", userMobie='" + userMobie + '\'' +
                ", userEmail='" + userEmail + '\'' +
                ", userRootId='" + userRootId + '\'' +
                ", userGarbage='" + userGarbage + '\'' +
                ", userSize=" + userSize +
                ", userUsed=" + userUsed +
                ", userStatus=" + userStatus +
                ", userPermission=" + userPermission +
                '}';
    }

    public CdstorageUserVo() {
    }

    public CdstorageUserVo(String token, String userId, Integer userWorkId, String userName, String userSex, Date userTime, String userPassword, String userMobie, String userEmail, String userRootId, String userGarbage, BigDecimal userSize, BigDecimal userUsed, Byte userStatus, Integer userPermission) {
        this.token = token;
        this.userId = userId;
        this.userWorkId = userWorkId;
        this.userName = userName;
        this.userSex = userSex;
        this.userTime = userTime;
        this.userPassword = userPassword;
        this.userMobie = userMobie;
        this.userEmail = userEmail;
        this.userRootId = userRootId;
        this.userGarbage = userGarbage;
        this.userSize = userSize;
        this.userUsed = userUsed;
        this.userStatus = userStatus;
        this.userPermission = userPermission;
    }
}
