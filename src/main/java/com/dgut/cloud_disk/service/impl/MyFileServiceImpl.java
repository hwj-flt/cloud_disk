package com.dgut.cloud_disk.service.impl;

import com.dgut.cloud_disk.mapper.MyfileMapper;
import com.dgut.cloud_disk.pojo.Directory;
import com.dgut.cloud_disk.pojo.Myfile;
import com.dgut.cloud_disk.service.MyFileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.entity.Example;

@Service
public class MyFileServiceImpl implements MyFileService {
    @Autowired(required = false)
    MyfileMapper myfileMapper;
    @Override
    public Myfile selectFileById(String fileId) {
        Example example = new Example(Myfile.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.andEqualTo("fileId",fileId);
        Myfile myfile = myfileMapper.selectOneByExample(example);
        return myfile;
    }
}
