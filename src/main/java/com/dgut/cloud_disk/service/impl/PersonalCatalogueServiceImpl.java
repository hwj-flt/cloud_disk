package com.dgut.cloud_disk.service.impl;

import com.dgut.cloud_disk.mapper.DirectoryFileMyFileMapper;
import com.dgut.cloud_disk.mapper.DirectoryMapper;
import com.dgut.cloud_disk.pojo.Directory;
import com.dgut.cloud_disk.pojo.DirectoryFileMyFile;
import com.dgut.cloud_disk.pojo.bo.DirectoryBo;
import com.dgut.cloud_disk.pojo.bo.FileBo;
import com.dgut.cloud_disk.service.PersonalCatalogueService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class PersonalCatalogueServiceImpl implements PersonalCatalogueService {

    @Autowired(required = false)
    private DirectoryMapper directoryMapper;

    @Autowired
    private DirectoryFileMyFileMapper directoryFileMyFileMapper;

    @Override
    public List<Directory> getDirectorysByParent(String parentId) {
        Directory directory = new Directory();
        directory.setParentDirectId(parentId);
        List<Directory> list = directoryMapper.select(directory);
        return list;
    }

    @Override
    public List<FileBo> getFileVoByDirectory(String directoryId) {
        List<FileBo> rlist = new ArrayList<FileBo>();
        List<DirectoryFileMyFile> list=directoryFileMyFileMapper.queryFileVoByDirectoryID(directoryId);
        for (DirectoryFileMyFile d:list){
            FileBo f  = new FileBo();
            f.setFileID(d.getFileId());
            f.setModificationDate(d.getFileUploadTime().toString());
            f.setName(d.getDfFileName());
            f.setType(d.getFileType());
            rlist.add(f);
        }
        return rlist;
    }

    /**
     * 使用根目录获取整个目录结构
     *
     * @param rootId 根目录文件夹Id
     * @return 返回整个目录结构
     */
    @Override
    public DirectoryBo getAllCatalogue(String rootId) {
        return getAllCatalogueb(rootId,new DirectoryBo());
    }

    /**
     * 使用根目录获取整个目录结构
     *
     * @param rootId 根目录文件夹Id
     * @return 返回整个目录结构
     */
    public DirectoryBo getAllCatalogueb(String rootId,DirectoryBo dv) {
        //获取当前文件夹信息
        Directory directory = new Directory();
        directory.setDirectId(rootId);
        List<Directory> list = directoryMapper.select(directory);
        if(list.size()>0){
            dv.setDirectID(list.get(0).getDirectId());
            dv.setName(list.get(0).getDirectName());
            dv.setSize(list.get(0).getDirectSize().toString());
            dv.setModificationDate(list.get(0).getDirectCreateTime().toString());
        }
        //获取当前文件夹下的文件
        List<FileBo> fileVoList = getFileVoByDirectory(rootId);
        dv.setIncludeFiles(fileVoList);
        //获取当前文件夹下的文件夹
        List<DirectoryBo> dl = new ArrayList<DirectoryBo>();
        List<Directory> dlist = getDirectorysByParent(rootId);
        for(Directory directory1:dlist){
            DirectoryBo q = getAllCatalogueb(directory1.getDirectId(),new DirectoryBo());
            dl.add(q);
        }
        dv.setIncludeDirects(dl);
        return dv;
    }


}
