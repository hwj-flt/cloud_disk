package com.dgut.cloud_disk.service.impl;


import com.dgut.cloud_disk.mapper.*;

import com.dgut.cloud_disk.pojo.Directory;
import com.dgut.cloud_disk.pojo.DirectoryFile;
import com.dgut.cloud_disk.pojo.DirectoryFileMyFile;
import com.dgut.cloud_disk.pojo.Myfile;
import com.dgut.cloud_disk.service.DirectoryFileService;
import com.dgut.cloud_disk.service.DirectoryService;
import com.dgut.cloud_disk.service.PersonalCatalogueService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.entity.Example;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

@Service
public class DirectoryServiceImpl implements DirectoryService {

    @Autowired(required = false)
    DirectoryMapper directoryMapper;

    @Autowired(required = false)
    private MyfileMapper myfileMapper;

    @Autowired(required = false)
    private DirectoryFileMapper directoryFileMapper;

    @Autowired(required = false)
    private DirectoryFileMyFileMapper directoryFileMyFileMapper;

    @Autowired(required = false)
    private PersonalCatalogueService personalCatalogueService;

    /**
     * 根据文件夹id查询文件夹
     * @param directID 文件夹id
     * @return Directory对象
     */
    @Override
    public Directory selectDirectoryByID(String directID) {
        Example example = new Example(Directory.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.andEqualTo("directId",directID);
        Directory directory = directoryMapper.selectOneByExample(example);
        return directory;
    }

    /**
     * 根据文件夹id修改文件夹
     * @param directory 文件夹对象
     * @param directID 文件夹id
     * @return 1：成功  0：失败
     */
    @Override
    public int updateDirectoryByID(Directory directory,String directID) {
        Example example = new Example(Directory.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.andEqualTo("directId", directID);
        int i = directoryMapper.updateByExampleSelective(directory, example);
        return i;
    }

    @Override
    public Boolean insertDirectory(Directory directory) {
        int i = directoryMapper.insertSelective(directory);
        if(i > 0){
            return true;
        }else {
            return false;
        }
    }

    @Override
    public void copyFileToNew(String directoryId, String newDirectoryId){
        List<DirectoryFileMyFile> list=directoryFileMyFileMapper.queryFileVoByDirectoryID(directoryId);
        for (DirectoryFileMyFile d:list){
            DirectoryFile directoryFile = new DirectoryFile();
            String uuid = UUID.randomUUID().toString().replaceAll("-","");
            directoryFile.setDirectFileId(uuid);
            directoryFile.setDfDirectId(newDirectoryId);
            directoryFile.setDfFileId(d.getFileId());
            directoryFile.setDfFileName(d.getDfFileName());
            directoryFile.setDfGarbage((byte)1);
            directoryFileMapper.insert(directoryFile);
            Myfile myfile = new Myfile();
            myfile.setFileId(d.getFileId());
            Myfile myfile1 = myfileMapper.selectOne(myfile);
            myfile1.setFileRefere((byte) (d.getFileRefere()+1));
            myfileMapper.updateByPrimaryKey(myfile1);
        }
    }

    @Override
    public void copyDirectory(String directID, String userID, String dID) {
        //复制文件夹
        //复制当前文件夹
        Directory directory1 = new Directory();
        directory1.setDirectId(dID);
        Directory directory2 = directoryMapper.selectOne(directory1);
        Directory directory = new Directory();
        String uuid = UUID.randomUUID().toString().replaceAll("-","");
        directory.setDirectId(uuid);
        directory.setParentDirectId(directID);
        directory.setDirectDelete((byte)1);
        directory.setDirectBelongUser(userID);
        directory.setDirectCreateId(userID);
        directory.setDirectName(directory2.getDirectName());
        //directory.setDirectSize();
        directory.setDirectType((byte)3);
        directory.setDirectSize(directory2.getDirectSize());
        directory.setDirectCreateTime(new Date());
        directoryMapper.insert(directory);
        //复制文件夹下的文件到新的文件夹
        copyFileToNew(dID,uuid);
        //复制下面的文件夹
        List<Directory> directories = personalCatalogueService.getDirectorysByParent(dID);
        for (Directory d:directories){
            copyDirectory(uuid,userID,d.getDirectId());
        }
    }

}
