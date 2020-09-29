package com.dgut.cloud_disk.pojo;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.*;

@Table(name = "`department`")
public class Department implements Serializable {
    /**
     * 群组ID
     */
    @Id
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