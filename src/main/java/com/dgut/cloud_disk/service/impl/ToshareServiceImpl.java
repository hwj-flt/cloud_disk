package com.dgut.cloud_disk.service.impl;

import com.dgut.cloud_disk.mapper.ToshareMapper;
import com.dgut.cloud_disk.pojo.Toshare;
import com.dgut.cloud_disk.service.ToshareService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.entity.Example;
@Service
public class ToshareServiceImpl implements ToshareService {
    @Autowired(required = false)
    ToshareMapper toshareMapper;
    /**
     * 删除分享表记录
     * @param shareFileId 文件id
     * @param shareDirectId 文件夹id
     * @return 1.成功，0.失败
     */
    @Override
    public int deleteRecord(String shareFileId, String shareDirectId) {
        Example example = new Example(Toshare.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.andEqualTo("shareFileId", shareFileId);
        criteria.andEqualTo("shareDirectId",shareDirectId);
        int i = toshareMapper.deleteByExample(example);
        return i;
    }
    /**
     * 删除分享表记录
     * @param shareDirectId 文件夹id
     * @return 1.成功，0.失败
     */
    @Override
    public int deleteDirectRecord(String shareDirectId) {
        Example example = new Example(Toshare.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.andEqualTo("shareDirectId",shareDirectId);
        int i = toshareMapper.deleteByExample(example);
        return i;
    }
}
