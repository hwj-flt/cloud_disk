package com.dgut.cloud_disk.mapper;


import com.dgut.cloud_disk.pojo.DirectoryFileMyFile;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DirectoryFileMyFileMapper {

    List<DirectoryFileMyFile> queryFileVoByDirectoryID(String id);
}
