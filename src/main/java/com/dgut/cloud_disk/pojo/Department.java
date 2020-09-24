package com.dgut.cloud_disk.pojo;

import java.io.Serializable;
import javax.persistence.*;

@Table(name = "`department`")
public class Department implements Serializable {
    /**
     * 部门ID
     */
    @Id
    @Column(name = "`DEPART_ID`")
    private String departId;

    /**
     * 部门名称
     */
    @Column(name = "`DEPART_NAME`")
    private String departName;

    /**
     * 部门文件夹根目录ID
     */
    @Column(name = "`DEPART_ROOT`")
    private String departRoot;

    /**
     * 部门垃圾箱文件夹ID
     */
    @Column(name = "`DEPART_GARBAGE`")
    private String departGarbage;

    private static final long serialVersionUID = 1L;

    /**
     * 获取部门ID
     *
     * @return DEPART_ID - 部门ID
     */
    public String getDepartId() {
        return departId;
    }

    /**
     * 设置部门ID
     *
     * @param departId 部门ID
     */
    public void setDepartId(String departId) {
        this.departId = departId;
    }

    /**
     * 获取部门名称
     *
     * @return DEPART_NAME - 部门名称
     */
    public String getDepartName() {
        return departName;
    }

    /**
     * 设置部门名称
     *
     * @param departName 部门名称
     */
    public void setDepartName(String departName) {
        this.departName = departName;
    }

    /**
     * 获取部门文件夹根目录ID
     *
     * @return DEPART_ROOT - 部门文件夹根目录ID
     */
    public String getDepartRoot() {
        return departRoot;
    }

    /**
     * 设置部门文件夹根目录ID
     *
     * @param departRoot 部门文件夹根目录ID
     */
    public void setDepartRoot(String departRoot) {
        this.departRoot = departRoot;
    }

    /**
     * 获取部门垃圾箱文件夹ID
     *
     * @return DEPART_GARBAGE - 部门垃圾箱文件夹ID
     */
    public String getDepartGarbage() {
        return departGarbage;
    }

    /**
     * 设置部门垃圾箱文件夹ID
     *
     * @param departGarbage 部门垃圾箱文件夹ID
     */
    public void setDepartGarbage(String departGarbage) {
        this.departGarbage = departGarbage;
    }
}