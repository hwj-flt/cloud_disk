package com.dgut.cloud_disk.service;

import com.dgut.cloud_disk.pojo.DirectoryFile;
import com.dgut.cloud_disk.pojo.Toshare;

import java.util.Date;
import java.util.List;

public interface DirectoryFileService {
    public List<DirectoryFile> allFile();

    public Boolean deleteFile(String directID,String fileID);

    public Boolean deleteDorDF(int type,String id);

    public Boolean insertShare(Toshare toshare);

    public Integer VerifyCode(String sid,String code);
    String fileDownload(String objectname);
    DirectoryFile selectFileById(String directID,String fileID);
    int ReFilename(DirectoryFile directoryFile,String directID, String fileID);
    int copyToDirect(DirectoryFile directoryFile);
    DirectoryFile selectFileById(String fileID);
    List<DirectoryFile> selectFileByDirectId(String directID);
    public Date getShareTimeByID(String id);

    public String getFileNameByID(String id);

    public String getFileLinkByID(String id);
}
