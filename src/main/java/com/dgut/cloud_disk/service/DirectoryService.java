package com.dgut.cloud_disk.service;

import com.dgut.cloud_disk.pojo.Directory;


public interface DirectoryService {
    Directory selectDirectoryByID(String directID);
    int updateDirectoryByID(Directory directory, String directID);
    int insertDirectory(Directory directory);
}


