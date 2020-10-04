package com.dgut.cloud_disk.service.impl;

import com.dgut.cloud_disk.mapper.CdstorageuserdepartmentuserMapper;
import com.dgut.cloud_disk.mapper.DepartmentMapper;
import com.dgut.cloud_disk.pojo.Cdstorageuserdepartmentuser;
import com.dgut.cloud_disk.pojo.Department;
import com.dgut.cloud_disk.pojo.bo.DepartmentBo;
import com.dgut.cloud_disk.service.DepartmentService;
import com.dgut.cloud_disk.util.DateUtil;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

@Service
public class DepartmentServiceImpl implements DepartmentService {
    @Resource
    DepartmentMapper departmentMapper;

    @Resource
    private CdstorageuserdepartmentuserMapper cdstorageuserdepartmentuserMapper;

    @Override
    public Department selDepart(String departId) {
        Department department = departmentMapper.selectByPrimaryKey(departId);
        return department;
    }

    /**
     * 查询群组
     *
     * @param userId 用户ID
     * @return 群组Bo
     */
    @Override
    public List<DepartmentBo> showDepart(String userId,Integer i) {
        List<DepartmentBo> departmentBos = new ArrayList<DepartmentBo>();
        List<Cdstorageuserdepartmentuser> cdstorageuserdepartmentusers=null;
        if(i!=1){

            Cdstorageuserdepartmentuser cdstorageuserdepartmentuser = new Cdstorageuserdepartmentuser();
            cdstorageuserdepartmentuser.setDuUserId(userId);
            cdstorageuserdepartmentusers = cdstorageuserdepartmentuserMapper.select(cdstorageuserdepartmentuser);
        }else{
            cdstorageuserdepartmentusers = cdstorageuserdepartmentuserMapper.selectAll();
        }

        for (Cdstorageuserdepartmentuser c: cdstorageuserdepartmentusers){
            DepartmentBo departmentBo = new DepartmentBo();
            departmentBo.setDepartID(c.getDepartId());
            departmentBo.setDepartName(c.getDepartName());
            departmentBo.setDepartTime(DateUtil.transfromDate(c.getDepartTime()));
            departmentBo.setPermission(c.getDepartPermission());
            departmentBos.add(departmentBo);
        }
        return departmentBos;
    }
}
