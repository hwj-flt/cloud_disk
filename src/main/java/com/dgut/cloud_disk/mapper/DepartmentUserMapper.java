package com.dgut.cloud_disk.mapper;

import com.dgut.cloud_disk.pojo.DepartmentUser;

import java.util.List;

/**
* 通用 Mapper 代码生成器
*
* @author mapper-generator
*/
public interface DepartmentUserMapper extends tk.mybatis.mapper.common.Mapper<DepartmentUser> {
    int deleteByIds( String departId,List<String> userIds);
}




