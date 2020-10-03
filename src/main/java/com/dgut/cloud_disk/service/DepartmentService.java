package com.dgut.cloud_disk.service;

import com.dgut.cloud_disk.pojo.Department;
import com.dgut.cloud_disk.pojo.bo.DepartmentBo;

import java.util.List;

public interface DepartmentService {

    public Department selDepart(String departId);

    /**
     * 查询群组
     * @param userId 用户ID
     * @return  群组Bo
     */
    public List<DepartmentBo> showDepart(String userId);

}
