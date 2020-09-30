package com.dgut.cloud_disk.service;

import com.dgut.cloud_disk.pojo.Directory;
import com.dgut.cloud_disk.pojo.bo.DirectoryBo;
import com.dgut.cloud_disk.pojo.bo.FileBo;

import java.util.List;

public interface PersonalCatalogueService {

    /**
     * 使用父文件夹Id获取下面的所有文件夹
     * @param parentId 父文件夹ID
     * @return  文件夹数组
     */
    List<Directory> getDirectorysByParent(String parentId);

    /**
     * 使用文件夹ID获取下面的所有文件Vo
     * @param directoryId 文件夹ID
     * @return  文件Vo数组
     */
    List<FileBo> getFileVoByDirectory(String directoryId);

    /**
     * 使用根目录获取整个目录结构
     * @param rootId  根目录文件夹Id
     * @return  返回整个目录结构
     */
    DirectoryBo getAllCatalogue(String rootId);
}
