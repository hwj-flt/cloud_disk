package com.dgut.cloud_disk.service;

import com.dgut.cloud_disk.pojo.Myfile;

public interface MyFileService {

    Myfile selectFileById(String fileId);
}
