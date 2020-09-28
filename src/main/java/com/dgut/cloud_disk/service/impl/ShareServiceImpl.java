package com.dgut.cloud_disk.service.impl;

import com.dgut.cloud_disk.mapper.*;
import com.dgut.cloud_disk.pojo.*;
import com.dgut.cloud_disk.pojo.bo.BeShareBo;
import com.dgut.cloud_disk.service.DirectoryFileService;
import com.dgut.cloud_disk.service.ShareService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ShareServiceImpl implements ShareService {

    @Autowired(required = false)
    private SelectbesharefordirectoryMapper selectbesharefordirectoryMapper;

    @Autowired(required = false)
    private SelectbeshareforfileMapper selectbeshareforfileMapper;

    @Autowired(required = false)
    private DirectoryFileService directoryFileService;

    @Autowired(required = false)
    private MyfileMapper myfileMapper;

    @Autowired(required = false)
    private ToshareMapper toshareMapper;

    private DirectoryMapper directoryMapper;


    @Override
    public List<BeShareBo> showBeShare(String beshareID) {
        List<BeShareBo> beShareBos = new ArrayList<BeShareBo>();
        Selectbesharefordirectory selectbesharefordirectory = new Selectbesharefordirectory();
        selectbesharefordirectory.setShareToUserId(beshareID);
        List<Selectbesharefordirectory> sd = selectbesharefordirectoryMapper.select(selectbesharefordirectory);
        for(Selectbesharefordirectory s:sd){
            //判断分享是否过期
            if((s.getShareExpire().compareTo(s.getShareTime()))>0){
                BeShareBo beShareBo = new BeShareBo();
                beShareBo.setDirectName(s.getDirectName());
                beShareBo.setShareID(s.getShareId());
                beShareBo.setShareTime(s.getShareTime().toString());
                beShareBo.setShareUserName(s.getUserName());
                beShareBos.add(beShareBo);
            }
        }

        Selectbeshareforfile selectbeshareforfile = new Selectbeshareforfile();
        selectbeshareforfile.setShareToUserId(beshareID);
        List<Selectbeshareforfile> sf = selectbeshareforfileMapper.select(selectbeshareforfile);
        for(Selectbeshareforfile s:sf){
            //判断分享是否过期
            if((s.getShareExpire().compareTo(s.getShareTime()))>0) {
                BeShareBo beShareBo = new BeShareBo();
                beShareBo.setFileName(s.getDfFileName());
                beShareBo.setShareID(s.getShareId());
                beShareBo.setShareTime(s.getShareTime().toString());
                beShareBo.setShareUserName(s.getUserName());
                beShareBos.add(beShareBo);
            }
        }
        return beShareBos;
    }

    /**
     * 返回下载链接
     *
     * @param shareID 分享表ID
     * @param userID  被分享用户ID
     * @return 下载链接
     */
    @Override
    public String downloadFileByShare(String shareID, String userID) throws Exception {
        Selectbeshareforfile selectbeshareforfile = new Selectbeshareforfile();
        selectbeshareforfile.setShareId(shareID);
        List<Selectbeshareforfile> sf = selectbeshareforfileMapper.select(selectbeshareforfile);
        String link = null;
        if (sf.size()<=0){
            throw  new Exception("查无分享");
        }
        for(Selectbeshareforfile s:sf){
            if((s.getShareExpire().compareTo(s.getShareTime()))>0){
                throw  new Exception("分享已过期");
            }else if(s.getShareId().equals(userID)){
                throw  new Exception("被分享人不匹配");
            }
            Myfile myfile = new Myfile();
            myfile.setFileId(s.getDfFileId());
            Myfile myfile1 = myfileMapper.selectOne(myfile);
            if(myfile1==null){
                throw  new Exception("无此文件");
            }
            link = myfile1.getFileLink();
        }
        return  directoryFileService.fileDownload(link,300L);
    }

    /**
     * 转存文件
     *
     * @param shareID  分析表ID
     * @param directID 要被转存的文件夹
     * @param userID   操作的用户ID
     */
    @Override
    public void storeFileByShare(String shareID, String directID, String userID) throws Exception {
        //从分享表中获取到分享的映射表ID
        String refectID = null;
        Toshare toshare = new Toshare();
        toshare.setShareId(shareID);
        List<Toshare> toshares = toshareMapper.select(toshare);
        if (toshares.size()<=0){
            throw  new Exception("查无分享");
        }
        refectID = toshares.get(0).getShareFileId();
        //查看被转存的文件夹是否属于该用户
        if(refectID!=null){
            Directory directory = new Directory();
            directory.setDirectId(directID);
            List<Directory> directories = directoryMapper.select(directory);
            if (directories.size()<=0){
                throw  new Exception("查无文件夹");
            }
        }
        //填加映射表的数据，表示文件复制到该文件夹

        //文件引用加一
    }
}
