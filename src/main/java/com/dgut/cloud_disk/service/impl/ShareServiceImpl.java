package com.dgut.cloud_disk.service.impl;

import com.dgut.cloud_disk.mapper.*;
import com.dgut.cloud_disk.pojo.*;
import com.dgut.cloud_disk.pojo.bo.BeShareBo;
import com.dgut.cloud_disk.pojo.bo.FileBo;
import com.dgut.cloud_disk.service.DirectoryFileService;
import com.dgut.cloud_disk.service.PersonalCatalogueService;
import com.dgut.cloud_disk.service.ShareService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.entity.Example;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

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

    @Autowired(required = false)
    private DirectoryMapper directoryMapper;

    @Autowired(required = false)
    private DirectoryFileMapper directoryFileMapper;

    @Autowired(required = false)
    private DirectoryFileMyFileMapper directoryFileMyFileMapper;

    @Autowired(required = false)
    private PersonalCatalogueService personalCatalogueService;

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
        //获取文件ID
        String fileID = null;
        String fileName = null;
        DirectoryFile d = new DirectoryFile();
        d.setDirectFileId(refectID);
        List<DirectoryFile> directoryFiles = directoryFileMapper.select(d);
        for (DirectoryFile df:directoryFiles){
            fileID = df.getDfFileId();
            fileName = df.getDfFileName();
        }
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
        DirectoryFile directoryFile = new DirectoryFile();
        String uuid = UUID.randomUUID().toString().replace("-","");
        directoryFile.setDirectFileId(uuid);
        directoryFile.setDfDirectId(directID);
        directoryFile.setDfFileId(fileID);
        directoryFile.setDfFileName(fileName);
        directoryFile.setDfGarbage((byte)1);
        directoryFileMapper.insert(directoryFile);
        //文件引用加一
        Myfile myfile = new Myfile();
        myfile.setFileId(fileID);
        Myfile myfile1 = myfileMapper.selectOne(myfile);
        int i = myfile1.getFileRefere()+1;
        Myfile myfile2 = new Myfile();
        myfile2.setFileId(fileID);
        myfile2.setFileRefere((byte)i);
        myfileMapper.updateByPrimaryKey(myfile2);
    }

    /**
     * 转存文件夹
     *
     * @param shareID  分析表ID
     * @param directID 要转存到的文件夹
     * @param userID   操作的用户ID
     * @throws Exception
     */
    @Override
    public void storeDirectByShare(String shareID, String directID, String userID) throws Exception {
        //获取文件夹ID
        String dID = null;
        Toshare toshare = new Toshare();
        toshare.setShareId(shareID);
        List<Toshare> toshares = toshareMapper.select(toshare);
        if (toshares.size()<=0){
            throw  new Exception("查无分享");
        }
        dID = toshares.get(0).getShareDirectId();
        copyDirectory(directID,userID,dID);
    }

    /**
     * 复制文件夹下的文件到新的文件夹
     * @param directoryId 要被复制的文佳夹
     * @param newDirectoryId 复制的新文件夹
     */
    public void copyFileToNew(String directoryId,String newDirectoryId) {
        List<Myfile> rlist = new ArrayList<Myfile>();
        List<DirectoryFileMyFile> list=directoryFileMyFileMapper.queryFileVoByDirectoryID(directoryId);
        for (DirectoryFileMyFile d:list){
            DirectoryFile directoryFile = new DirectoryFile();
            String uuid = UUID.randomUUID().toString().replace("-","");
            directoryFile.setDirectFileId(uuid);
            directoryFile.setDfDirectId(newDirectoryId);
            directoryFile.setDfFileId(d.getFileId());
            directoryFile.setDfFileName(d.getDfFileName());
            directoryFile.setDfGarbage((byte)1);
            directoryFileMapper.insert(directoryFile);
            Myfile myfile = new Myfile();
            myfile.setFileId(d.getFileId());
            myfile.setFileRefere((byte) (d.getFileRefere()+1));
            myfileMapper.updateByPrimaryKey(myfile);
        }
    }

    /**
     * 转存文件夹
     * @param directID 要转存到的文件夹ID
     * @param userID   操作的用户ID
     * @param dID 被转存的文件夹ID
     */
    public void copyDirectory(String directID, String userID,String dID){
        //复制文件夹
        //复制当前文件夹
        Directory directory1 = new Directory();
        directory1.setDirectId(dID);
        Directory directory2 = directoryMapper.selectOne(directory1);
        Directory directory = new Directory();
        String uuid = UUID.randomUUID().toString().replace("_","");
        directory.setDirectId(uuid);
        directory.setParentDirectId(directID);
        directory.setDirectDelete((byte)1);
        directory.setDirectBelongUser(userID);
        directory.setDirectCreateId(userID);
        directory.setDirectName(directory2.getDirectName());
        //directory.setDirectSize();
        directory.setDirectType((byte)3);
        //复制文件夹下的文件到新的文件夹
        copyFileToNew(dID,uuid);
        //复制下面的文件夹
        List<Directory> directories = personalCatalogueService.getDirectorysByParent(dID);
        for (Directory d:directories){
            copyDirectory(dID,userID,d.getDirectId());
        }
    }
}
