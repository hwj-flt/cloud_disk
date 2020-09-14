package com.dgut.cloud_disk.pojo;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.*;

@Table(name = "`toshare`")
public class Toshare implements Serializable {
    /**
     * 分享表ID
     */
    @Id
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
     * 分享时
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
    private Integer shareType;

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
     * 获取分享时
     *
     * @return SHARE_TIME - 分享时
     */
    public Date getShareTime() {
        return shareTime;
    }

    /**
     * 设置分享时
     *
     * @param shareTime 分享时
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
    public Integer getShareType() {
        return shareType;
    }

    /**
     * 设置1-私密分享文件 2-私密分享文件夹 3-外链分享文件 4-外链分享文件夹
     *
     * @param shareType 1-私密分享文件 2-私密分享文件夹 3-外链分享文件 4-外链分享文件夹
     */
    public void setShareType(Integer shareType) {
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
}