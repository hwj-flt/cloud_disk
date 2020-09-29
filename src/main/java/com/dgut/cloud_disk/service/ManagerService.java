package com.dgut.cloud_disk.service;

import com.dgut.cloud_disk.pojo.CdstorageUser;
import com.dgut.cloud_disk.pojo.Department;
import com.dgut.cloud_disk.pojo.DepartmentUser;

import java.util.List;

public interface ManagerService {
    /**
     * 将用户移出群组
     * @param departId
     * @param userIds
     * @return
     */
    public Boolean delDepartUser(String departId, List<String> userIds);

    /**
     * 新增部门及部门根目录文件夹
     * @param department 部门
     * @return 是否成功添加
     */
    public Boolean addDepartment(Department department);

    /**
     * 设置部门权限
     * @param duDepartId
     * @param duPermission
     * @return
     */
    public Boolean setDepPerm(String duDepartId , String duPermission);

    /**
     * 添加用户到部门
     * @param departId 部门id
     * @param userIds 用户id列表
     * @return
     */
    public Boolean addUserToDepart(String departId, List<String> userIds);

    /**
     * 所有部门
     * @return
     */
    public List<Department> allDepart();

    /**
     * 查询部门内用户
     * @param departId
     * @return
     */
    public List<CdstorageUser> selUserAtDep(String departId);
}
