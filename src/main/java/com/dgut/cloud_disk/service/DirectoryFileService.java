package com.dgut.cloud_disk.service;

import com.dgut.cloud_disk.pojo.DirectoryFile;
import com.dgut.cloud_disk.pojo.Toshare;

import java.util.List;

public interface DirectoryFileService {
    public List<DirectoryFile> allFile();

    public Boolean deleteFile(String directID,String fileID);

    public Boolean deleteDorDF(int type,String id);

    public Boolean insertShare(Toshare toshare);

    public Integer VerifyCode(String sid,String code);
    public String fileDownload(String objectname,long expire);
    DirectoryFile selectFileById(String directID,String fileID);
    int ReFilename(DirectoryFile directoryFile,String directID, String fileID);
    int copyToDirect(DirectoryFile directoryFile);
    DirectoryFile selectFileById(String fileID);
    List<DirectoryFile> selectFileByDirectId(String directID);
    String getUploadUrl(String objectName);
    Boolean insertDirectoryFile(DirectoryFile directoryFile);
}
