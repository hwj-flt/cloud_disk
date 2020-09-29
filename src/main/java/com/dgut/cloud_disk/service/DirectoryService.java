package com.dgut.cloud_disk.service;

import com.dgut.cloud_disk.pojo.Directory;


public interface DirectoryService {
    Directory selectDirectoryByID(String directID);
    int updateDirectoryByID(Directory directory, String directID);

    /**
     * 新建文件夹
     * @param directory
     * @return
     */
    Boolean insertDirectory(Directory directory);
}


