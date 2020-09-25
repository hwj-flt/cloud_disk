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
     * 11111111-文档管理员（上传、下载、删除、创建文件夹、分享、复制、移动、重命名）
            10000000-群组成员权限1（上传）
            每一权限位对应相应的功能
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
     * 获取11111111-文档管理员（上传、下载、删除、创建文件夹、分享、复制、移动、重命名）
            10000000-群组成员权限1（上传）
            每一权限位对应相应的功能
     *
     * @return DU_PERMISSION - 11111111-文档管理员（上传、下载、删除、创建文件夹、分享、复制、移动、重命名）
            10000000-群组成员权限1（上传）
            每一权限位对应相应的功能
     */
    public Integer getDuPermission() {
        return duPermission;
    }

    /**
     * 设置11111111-文档管理员（上传、下载、删除、创建文件夹、分享、复制、移动、重命名）
            10000000-群组成员权限1（上传）
            每一权限位对应相应的功能
     *
     * @param duPermission 11111111-文档管理员（上传、下载、删除、创建文件夹、分享、复制、移动、重命名）
            10000000-群组成员权限1（上传）
            每一权限位对应相应的功能
     */
    public void setDuPermission(Integer duPermission) {
        this.duPermission = duPermission;
    }
}