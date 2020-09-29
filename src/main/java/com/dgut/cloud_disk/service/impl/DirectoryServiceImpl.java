package com.dgut.cloud_disk.service.impl;


import com.dgut.cloud_disk.mapper.DirectoryMapper;

import com.dgut.cloud_disk.pojo.Directory;
import com.dgut.cloud_disk.pojo.DirectoryFile;
import com.dgut.cloud_disk.pojo.Myfile;
import com.dgut.cloud_disk.service.DirectoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.entity.Example;

@Service
public class DirectoryServiceImpl implements DirectoryService {

    @Autowired(required = false)
    DirectoryMapper directoryMapper;

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
}
