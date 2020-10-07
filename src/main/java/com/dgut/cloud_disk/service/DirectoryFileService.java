package com.dgut.cloud_disk.service;

import com.dgut.cloud_disk.pojo.Directory;
import com.dgut.cloud_disk.pojo.DirectoryFile;
import com.dgut.cloud_disk.pojo.Toshare;



import java.util.Date;
import java.util.List;

public interface DirectoryFileService {
    public List<DirectoryFile> allFile();

    public Boolean deleteFile(String directID,String fileID,String DeleteID);

    public Boolean deleteDirectory(String directID);

    public Boolean deleteDorDF(int type,String id);

    public Boolean insertShare(Toshare toshare);

    public Integer VerifyCode(String sid,String code);


    public String fileDownload(String objectname,long expire);


    DirectoryFile selectFileById(String directID,String fileID);
    int ReFilename(DirectoryFile directoryFile,String directID, String fileID);
    int copyToDirect(DirectoryFile directoryFile);
    //DirectoryFile selectFileById(String fileID);
    List<DirectoryFile> selectFileByDirectId(String directID);
    int updateDirectFileById(DirectoryFile directoryFile,String dfFileId);
    DirectoryFile selectFile(String directID, String fileName);
    public Date getShareTimeByID(String id);

    public String getFileNameByID(String id);

    public String getFileLinkByID(String id);


    public String getDFidByFidAndDid(String Fid,String Did);

    //获取被删除至回收站的文件
    public List<DirectoryFile> getDeletedFileByID(String id);
    //获取被删除至回收站的文件夹
    public List<Directory> getDeletedDirectoryByID(String id);
    //还原文件夹
    public Boolean restoreDirectory(String id);
    //还原文件
    public Boolean restoreFile(String id);
    //还原文件夹到新文件夹中
    public Boolean restoreDirectorytoNew(String id,String Did);
    //还原文件到新文件夹中
    public Boolean restoreFiletoNew(String id,String Did);


    String getUploadUrl(String objectName);
    Boolean insertDirectoryFile(DirectoryFile directoryFile);

    public String getShareName(String id);

}
