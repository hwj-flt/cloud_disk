package com.dgut.cloud_disk.mapper;

import com.dgut.cloud_disk.pojo.Directory;

import java.util.List;

/**
* 通用 Mapper 代码生成器
*
* @author mapper-generator
*/
public interface DirectoryMapper extends tk.mybatis.mapper.common.Mapper<Directory> {
    public int deleteDirectoryByPId(String Pid);//根据父id删除文件夹

    public List<String> selectIdByPid(String Pid);//根据父id查找id

}




