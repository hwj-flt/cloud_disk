package com.dgut.cloud_disk.service.impl;

import com.dgut.cloud_disk.mapper.SelectbesharefordirectoryMapper;
import com.dgut.cloud_disk.mapper.SelectbeshareforfileMapper;
import com.dgut.cloud_disk.pojo.Selectbesharefordirectory;
import com.dgut.cloud_disk.pojo.Selectbeshareforfile;
import com.dgut.cloud_disk.pojo.bo.BeShareBo;
import com.dgut.cloud_disk.service.ShareService;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.List;

public class ShareServiceImpl implements ShareService {

    @Autowired(required = false)
    private SelectbesharefordirectoryMapper selectbesharefordirectoryMapper;

    @Autowired(required = false)
    private SelectbeshareforfileMapper selectbeshareforfileMapper;


    @Override
    public List<BeShareBo> showBeShare(String beshareID) {
        List<BeShareBo> beShareBos = new ArrayList<BeShareBo>();
        Selectbesharefordirectory selectbesharefordirectory = new Selectbesharefordirectory();
        selectbesharefordirectory.setShareToUserId(beshareID);
        List<Selectbesharefordirectory> sd = selectbesharefordirectoryMapper.select(selectbesharefordirectory);
        for(Selectbesharefordirectory s:sd){
            BeShareBo beShareBo = new BeShareBo();
            beShareBo.setDirectName(s.getDirectName());
            beShareBo.setShareID(s.getShareId());
            beShareBo.setShareTime(s.getShareTime().toString());
            beShareBo.setShareUserName(s.getUserName());
            beShareBos.add(beShareBo);
        }

        Selectbeshareforfile selectbeshareforfile = new Selectbeshareforfile();
        selectbeshareforfile.setShareToUserId(beshareID);
        List<Selectbeshareforfile> sf = selectbeshareforfileMapper.select(selectbeshareforfile);
        for(Selectbeshareforfile s:sf){
            BeShareBo beShareBo = new BeShareBo();
            beShareBo.setFileName(s.getDfFileName());
            beShareBo.setShareID(s.getShareId());
            beShareBo.setShareTime(s.getShareTime().toString());
            beShareBo.setShareUserName(s.getUserName());
            beShareBos.add(beShareBo);
        }
        return beShareBos;
    }
}
