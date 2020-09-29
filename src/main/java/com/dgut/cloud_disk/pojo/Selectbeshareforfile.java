package com.dgut.cloud_disk.pojo;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import javax.persistence.*;

@Table(name = "`SELECTBESHAREFORFILE`")
public class Selectbeshareforfile implements Serializable {
    /**
     * 分享表ID
     */
    @Column(name = "`SHARE_ID`")
    private String shareId;

    /**
     * 分享人ID
     */
    @Column(name = "`SHARE_USER_ID`")
    private String shareUserId;

    /**
     * 被分享人ID
     */
    @Column(name = "`SHARE_TO_USER_ID`")
    private String shareToUserId;

    /**
     * 分享时间
     */
    @Column(name = "`SHARE_TIME`")
    private Date shareTime;

    /**
     * 分享有效期
     */
    @Column(name = "`SHARE_EXPIRE`")
    private Date shareExpire;

    /**
     * 1-私密分享文件 2-私密分享文件夹 3-外链分享文件 4-外链分享文件夹
     */
    @Column(name = "`SHARE_TYPE`")
    private Byte shareType;

    /**
     * 分享文件ID
     */
    @Column(name = "`SHARE_FILE_ID`")
    private String shareFileId;

    /**
     * 分享文件夹ID
     */
    @Column(name = "`SHARE_DIRECT_ID`")
    private String shareDirectId;

    /**
     * 分享密码
     */
    @Column(name = "`SHARE_CODE`")
    private String shareCode;

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
     * 映射ID
     */
    @Column(name = "`DIRECT_FILE_ID`")
    private String directFileId;

    /**
     * 文件夹ID
     */
    @Column(name = "`DF_DIRECT_ID`")
    private String dfDirectId;

    /**
     * 文件ID
     */
    @Column(name = "`DF_FILE_ID`")
    private String dfFileId;

    /**
     * 文件名
     */
    @Column(name = "`DF_FILE_NAME`")
    private String dfFileName;

    /**
     * 1-未被删除 2被删除
     */
    @Column(name = "`DF_GARBAGE`")
    private Byte dfGarbage;

    /**
     * 删除时间
     */
    @Column(name = "`DF_DELETE_TIME`")
    private Date dfDeleteTime;

    /**
     * 删除用户ID
     */
    @Column(name = "`DF_DELETE_ID`")
    private String dfDeleteId;

    private static final long serialVersionUID = 1L;

    /**
     * 获取分享表ID
     *
     * @return SHARE_ID - 分享表ID
     */
    public String getShareId() {
        return shareId;
    }

    /**
     * 设置分享表ID
     *
     * @param shareId 分享表ID
     */
    public void setShareId(String shareId) {
        this.shareId = shareId;
    }

    /**
     * 获取分享人ID
     *
     * @return SHARE_USER_ID - 分享人ID
     */
    public String getShareUserId() {
        return shareUserId;
    }

    /**
     * 设置分享人ID
     *
     * @param shareUserId 分享人ID
     */
    public void setShareUserId(String shareUserId) {
        this.shareUserId = shareUserId;
    }

    /**
     * 获取被分享人ID
     *
     * @return SHARE_TO_USER_ID - 被分享人ID
     */
    public String getShareToUserId() {
        return shareToUserId;
    }

    /**
     * 设置被分享人ID
     *
     * @param shareToUserId 被分享人ID
     */
    public void setShareToUserId(String shareToUserId) {
        this.shareToUserId = shareToUserId;
    }

    /**
     * 获取分享时间
     *
     * @return SHARE_TIME - 分享时间
     */
    public Date getShareTime() {
        return shareTime;
    }

    /**
     * 设置分享时间
     *
     * @param shareTime 分享时间
     */
    public void setShareTime(Date shareTime) {
        this.shareTime = shareTime;
    }

    /**
     * 获取分享有效期
     *
     * @return SHARE_EXPIRE - 分享有效期
     */
    public Date getShareExpire() {
        return shareExpire;
    }

    /**
     * 设置分享有效期
     *
     * @param shareExpire 分享有效期
     */
    public void setShareExpire(Date shareExpire) {
        this.shareExpire = shareExpire;
    }

    /**
     * 获取1-私密分享文件 2-私密分享文件夹 3-外链分享文件 4-外链分享文件夹
     *
     * @return SHARE_TYPE - 1-私密分享文件 2-私密分享文件夹 3-外链分享文件 4-外链分享文件夹
     */
    public Byte getShareType() {
        return shareType;
    }

    /**
     * 设置1-私密分享文件 2-私密分享文件夹 3-外链分享文件 4-外链分享文件夹
     *
     * @param shareType 1-私密分享文件 2-私密分享文件夹 3-外链分享文件 4-外链分享文件夹
     */
    public void setShareType(Byte shareType) {
        this.shareType = shareType;
    }

    /**
     * 获取分享文件ID
     *
     * @return SHARE_FILE_ID - 分享文件ID
     */
    public String getShareFileId() {
        return shareFileId;
    }

    /**
     * 设置分享文件ID
     *
     * @param shareFileId 分享文件ID
     */
    public void setShareFileId(String shareFileId) {
        this.shareFileId = shareFileId;
    }

    /**
     * 获取分享文件夹ID
     *
     * @return SHARE_DIRECT_ID - 分享文件夹ID
     */
    public String getShareDirectId() {
        return shareDirectId;
    }

    /**
     * 设置分享文件夹ID
     *
     * @param shareDirectId 分享文件夹ID
     */
    public void setShareDirectId(String shareDirectId) {
        this.shareDirectId = shareDirectId;
    }

    /**
     * 获取分享密码
     *
     * @return SHARE_CODE - 分享密码
     */
    public String getShareCode() {
        return shareCode;
    }

    /**
     * 设置分享密码
     *
     * @param shareCode 分享密码
     */
    public void setShareCode(String shareCode) {
        this.shareCode = shareCode;
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
     * 获取映射ID
     *
     * @return DIRECT_FILE_ID - 映射ID
     */
    public String getDirectFileId() {
        return directFileId;
    }

    /**
     * 设置映射ID
     *
     * @param directFileId 映射ID
     */
    public void setDirectFileId(String directFileId) {
        this.directFileId = directFileId;
    }

    /**
     * 获取文件夹ID
     *
     * @return DF_DIRECT_ID - 文件夹ID
     */
    public String getDfDirectId() {
        return dfDirectId;
    }

    /**
     * 设置文件夹ID
     *
     * @param dfDirectId 文件夹ID
     */
    public void setDfDirectId(String dfDirectId) {
        this.dfDirectId = dfDirectId;
    }

    /**
     * 获取文件ID
     *
     * @return DF_FILE_ID - 文件ID
     */
    public String getDfFileId() {
        return dfFileId;
    }

    /**
     * 设置文件ID
     *
     * @param dfFileId 文件ID
     */
    public void setDfFileId(String dfFileId) {
        this.dfFileId = dfFileId;
    }

    /**
     * 获取文件名
     *
     * @return DF_FILE_NAME - 文件名
     */
    public String getDfFileName() {
        return dfFileName;
    }

    /**
     * 设置文件名
     *
     * @param dfFileName 文件名
     */
    public void setDfFileName(String dfFileName) {
        this.dfFileName = dfFileName;
    }

    /**
     * 获取1-未被删除 2被删除
     *
     * @return DF_GARBAGE - 1-未被删除 2被删除
     */
    public Byte getDfGarbage() {
        return dfGarbage;
    }

    /**
     * 设置1-未被删除 2被删除
     *
     * @param dfGarbage 1-未被删除 2被删除
     */
    public void setDfGarbage(Byte dfGarbage) {
        this.dfGarbage = dfGarbage;
    }

    /**
     * 获取删除时间
     *
     * @return DF_DELETE_TIME - 删除时间
     */
    public Date getDfDeleteTime() {
        return dfDeleteTime;
    }

    /**
     * 设置删除时间
     *
     * @param dfDeleteTime 删除时间
     */
    public void setDfDeleteTime(Date dfDeleteTime) {
        this.dfDeleteTime = dfDeleteTime;
    }

    /**
     * 获取删除用户ID
     *
     * @return DF_DELETE_ID - 删除用户ID
     */
    public String getDfDeleteId() {
        return dfDeleteId;
    }

    /**
     * 设置删除用户ID
     *
     * @param dfDeleteId 删除用户ID
     */
    public void setDfDeleteId(String dfDeleteId) {
        this.dfDeleteId = dfDeleteId;
    }
}