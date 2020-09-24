package com.dgut.cloud_disk.pojo;

import java.io.Serializable;
import javax.persistence.*;

@Table(name = "`department_user`")
public class DepartmentUser implements Serializable {
    /**
     * 映射ID
     */
    @Id
    @Column(name = "`DEPART_USER_ID`")
    private String departUserId;

    /**
     * 部门ID
     */
    @Column(name = "`DU_DEPART_ID`")
    private String duDepartId;

    /**
     * 用户ID
     */
    @Column(name = "`DU_USER_ID_`")
    private String duUserId;

    /**
     * 1-部门管理员（上传、下载、重命名、移动、删除）2-部门成员（上传、下载、保存到自己的文件夹）
     */
    @Column(name = "`DU_PERMISSION`")
    private Integer duPermission;

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
     * 获取部门ID
     *
     * @return DU_DEPART_ID - 部门ID
     */
    public String getDuDepartId() {
        return duDepartId;
    }

    /**
     * 设置部门ID
     *
     * @param duDepartId 部门ID
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
     * 获取1-部门管理员（上传、下载、重命名、移动、删除）2-部门成员（上传、下载、保存到自己的文件夹）
     *
     * @return DU_PERMISSION - 1-部门管理员（上传、下载、重命名、移动、删除）2-部门成员（上传、下载、保存到自己的文件夹）
     */
    public Integer getDuPermission() {
        return duPermission;
    }

    /**
     * 设置1-部门管理员（上传、下载、重命名、移动、删除）2-部门成员（上传、下载、保存到自己的文件夹）
     *
     * @param duPermission 1-部门管理员（上传、下载、重命名、移动、删除）2-部门成员（上传、下载、保存到自己的文件夹）
     */
    public void setDuPermission(Integer duPermission) {
        this.duPermission = duPermission;
    }
}