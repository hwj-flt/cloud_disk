package com.dgut.cloud_disk.service;

import com.dgut.cloud_disk.pojo.Myfile;

public interface MyFileService {
    boolean insertFile(Myfile myfile);
    Myfile selectFileById(String fileId);
}
