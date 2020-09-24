package com.dgut.cloud_disk.pojo;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import javax.persistence.*;

@Table(name = "`directory`")
public class Directory implements Serializable {
    /**
     * 文件夹ID
     */
    @Id
    @Column(name = "`DIRECT_ID`")
    private String directId;

    /**
     * 父文件夹ID
     */
    @Column(name = "`PARENT_DIRECT_ID`")
    private String parentDirectId;

    /**
     * 文件夹名
     */
    @Column(name = "`DIRECT_NAME`")
    private String directName;

    /**
     * 创建时间
     */
    @Column(name = "`DIRECT_CREATE_TIME`")
    private Date directCreateTime;

    /**
     * 创建用户ID
     */
    @Column(name = "`DIRECT_CREATE_ID`")
    private String directCreateId;

    /**
     * 1-个人根目录 2-部门根目录 3-个人文件夹 4-部门文件夹 5-个人回收站
     */
    @Column(name = "`DIRECT_TYPE`")
    private Byte directType;

    /**
     * 所属用户ID
     */
    @Column(name = "`DIRECT_BELONG_USER`")
    private String directBelongUser;

    /**
     * 所属群组ID
     */
    @Column(name = "`DIRECT_BELONG_DEPART`")
    private String directBelongDepart;

    /**
     * 1-不在回收站 2-在回收站
     */
    @Column(name = "`DIRECT_DELETE`")
    private Byte directDelete;

    /**
     * 文件夹大小
     */
    @Column(name = "`DIRECT_SIZE`")
    private BigDecimal directSize;

    /**
     * 删除文件的时间
     */
    @Column(name = "`DIRECT_DELETE_TIME`")
    private Date directDeleteTime;

    private static final long serialVersionUID = 1L;

    /**
     * 获取文件夹ID
     *
     * @return DIRECT_ID - 文件夹ID
     */
    public String getDirectId() {
        return directId;
    }

    /**
     * 设置文件夹ID
     *
     * @param directId 文件夹ID
     */
    public void setDirectId(String directId) {
        this.directId = directId;
    }

    /**
     * 获取父文件夹ID
     *
     * @return PARENT_DIRECT_ID - 父文件夹ID
     */
    public String getParentDirectId() {
        return parentDirectId;
    }

    /**
     * 设置父文件夹ID
     *
     * @param parentDirectId 父文件夹ID
     */
    public void setParentDirectId(String parentDirectId) {
        this.parentDirectId = parentDirectId;
    }

    /**
     * 获取文件夹名
     *
     * @return DIRECT_NAME - 文件夹名
     */
    public String getDirectName() {
        return directName;
    }

    /**
     * 设置文件夹名
     *
     * @param directName 文件夹名
     */
    public void setDirectName(String directName) {
        this.directName = directName;
    }

    /**
     * 获取创建时间
     *
     * @return DIRECT_CREATE_TIME - 创建时间
     */
    public Date getDirectCreateTime() {
        return directCreateTime;
    }

    /**
     * 设置创建时间
     *
     * @param directCreateTime 创建时间
     */
    public void setDirectCreateTime(Date directCreateTime) {
        this.directCreateTime = directCreateTime;
    }

    /**
     * 获取创建用户ID
     *
     * @return DIRECT_CREATE_ID - 创建用户ID
     */
    public String getDirectCreateId() {
        return directCreateId;
    }

    /**
     * 设置创建用户ID
     *
     * @param directCreateId 创建用户ID
     */
    public void setDirectCreateId(String directCreateId) {
        this.directCreateId = directCreateId;
    }

    /**
     * 获取1-个人根目录 2-部门根目录 3-个人文件夹 4-部门文件夹 5-个人回收站
     *
     * @return DIRECT_TYPE - 1-个人根目录 2-部门根目录 3-个人文件夹 4-部门文件夹 5-个人回收站
     */
    public Byte getDirectType() {
        return directType;
    }

    /**
     * 设置1-个人根目录 2-部门根目录 3-个人文件夹 4-部门文件夹 5-个人回收站
     *
     * @param directType 1-个人根目录 2-部门根目录 3-个人文件夹 4-部门文件夹 5-个人回收站
     */
    public void setDirectType(Byte directType) {
        this.directType = directType;
    }

    /**
     * 获取所属用户ID
     *
     * @return DIRECT_BELONG_USER - 所属用户ID
     */
    public String getDirectBelongUser() {
        return directBelongUser;
    }

    /**
     * 设置所属用户ID
     *
     * @param directBelongUser 所属用户ID
     */
    public void setDirectBelongUser(String directBelongUser) {
        this.directBelongUser = directBelongUser;
    }

    /**
     * 获取所属群组ID
     *
     * @return DIRECT_BELONG_DEPART - 所属群组ID
     */
    public String getDirectBelongDepart() {
        return directBelongDepart;
    }

    /**
     * 设置所属群组ID
     *
     * @param directBelongDepart 所属群组ID
     */
    public void setDirectBelongDepart(String directBelongDepart) {
        this.directBelongDepart = directBelongDepart;
    }

    /**
     * 获取1-不在回收站 2-在回收站
     *
     * @return DIRECT_DELETE - 1-不在回收站 2-在回收站
     */
    public Byte getDirectDelete() {
        return directDelete;
    }

    /**
     * 设置1-不在回收站 2-在回收站
     *
     * @param directDelete 1-不在回收站 2-在回收站
     */
    public void setDirectDelete(Byte directDelete) {
        this.directDelete = directDelete;
    }

    /**
     * 获取文件夹大小
     *
     * @return DIRECT_SIZE - 文件夹大小
     */
    public BigDecimal getDirectSize() {
        return directSize;
    }

    /**
     * 设置文件夹大小
     *
     * @param directSize 文件夹大小
     */
    public void setDirectSize(BigDecimal directSize) {
        this.directSize = directSize;
    }

    /**
     * 获取删除文件的时间
     *
     * @return DIRECT_DELETE_TIME - 删除文件的时间
     */
    public Date getDirectDeleteTime() {
        return directDeleteTime;
    }

    /**
     * 设置删除文件的时间
     *
     * @param directDeleteTime 删除文件的时间
     */
    public void setDirectDeleteTime(Date directDeleteTime) {
        this.directDeleteTime = directDeleteTime;
    }
}