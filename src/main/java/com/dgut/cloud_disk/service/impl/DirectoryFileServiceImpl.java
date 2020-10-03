package com.dgut.cloud_disk.service.impl;

import com.dgut.cloud_disk.config.ObsConfig;
import com.dgut.cloud_disk.mapper.DirectoryFileMapper;
import com.dgut.cloud_disk.mapper.DirectoryMapper;
import com.dgut.cloud_disk.mapper.MyfileMapper;
import com.dgut.cloud_disk.mapper.ToshareMapper;
import com.dgut.cloud_disk.pojo.Directory;
import com.dgut.cloud_disk.pojo.DirectoryFile;
import com.dgut.cloud_disk.pojo.Myfile;
import com.dgut.cloud_disk.pojo.Toshare;
import com.dgut.cloud_disk.service.DirectoryFileService;
import com.obs.services.ObsClient;
import com.obs.services.model.HttpMethodEnum;
import com.obs.services.model.TemporarySignatureRequest;
import com.obs.services.model.TemporarySignatureResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.entity.Example;

import javax.annotation.Resource;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class DirectoryFileServiceImpl implements DirectoryFileService {

    @Resource
    private DirectoryFileMapper DFmapper;
    @Resource
    private DirectoryMapper Dmapper;
    @Autowired(required = false)
    private ToshareMapper toshareMapper;

    @Resource
    private MyfileMapper myfileMapper;

    @Autowired
    private ObsConfig obsConfig;

    @Override
    public List<DirectoryFile> allFile() {
        List<DirectoryFile> file=DFmapper.selectAll();
        return file;
    }



    @Override
        public Boolean deleteFile(String directID, String fileID,String DeleteID) {
        //修改DF_GARBAGE为1，并截取时间戳
        Example example = new Example(DirectoryFile.class);
        Example.Criteria criteria=example.createCriteria();
        criteria.andEqualTo("dfDirectId",directID).andEqualTo("dfFileId",fileID);

       DirectoryFile df= DFmapper.selectOneByExample(example);
        //System.out.println(df);
       df.setDfGarbage((byte) 2);
       Date date=new Date();
       df.setDfDeleteTime(date);
       df.setDfDeleteId(DeleteID);
       int i=DFmapper.updateByPrimaryKeySelective(df);
       if(i>0){
           return true;
       }else {
           return false;
       }
    }

    @Override
    public Boolean deleteDirectory(String directID) {
        Example example = new Example(Directory.class);
        Example.Criteria criteria=example.createCriteria();
        criteria.andEqualTo("directId",directID);
        Directory directory=Dmapper.selectOneByExample(example);
        directory.setDirectDelete((byte) 2);
        Date date=new Date();
        directory.setDirectDeleteTime(date);
        int i = Dmapper.updateByPrimaryKeySelective(directory);
        if(i>0){
            return true;
        }else {
            return false;
        }
    }


    @Override
    public Boolean deleteDorDF(int type, String id) {
        int i=0;
        if(type==1){
            //删除文件夹需要删除所有子文件夹及其子文件夹
            List<String> list =Dmapper.selectIdByPid(id);
            Dmapper.deleteByPrimaryKey(id);
            Dmapper.deleteDirectoryByPId(id);
                for(int n=0;n<list.size();n++){
                    //System.out.println(list.get(n));
                    i=Dmapper.deleteDirectoryByPId(list.get(n));
                }
        }else if(type==2){
            i=DFmapper.deleteByPrimaryKey(id);
        }
        //System.out.println(i);
            if(i>0){
                return true;
            }else {
                return false;
            }
        }

    @Override
    public Boolean insertShare(Toshare toshare) {
        int i =toshareMapper.insert(toshare);
        if(i>0){
            return true;
        }else {
            return false;
        }
    }

    @Override
    public Integer VerifyCode(String sid, String code) {
        Toshare toshare = toshareMapper.selectByPrimaryKey(sid);
        if(toshare.getShareCode()==null) {
            return 1;//表示外链无密码，可直接访问
        }else if(code==""){
            return -1;//表示外链有密码，但是用户没有输入密码
        }else if(toshare.getShareCode().equals(code)){
            return 1;//表示外链有密码，用户输入正确密码，可正常访问
        }else {
            return -2;//表示外链有密码，但用户输入了错误密码
        }
    }



    @Override

    public Date getShareTimeByID(String id) {
        Toshare toshare=toshareMapper.selectByPrimaryKey(id);
        return toshare.getShareTime();
    }

    @Override
    public String getFileNameByID(String id) {

        Toshare toshare=toshareMapper.selectByPrimaryKey(id);
        /*String Did=toshare.getShareDirectId();
        String Fid=toshare.getShareFileId();*/

        Example example = new Example(DirectoryFile.class);
        Example.Criteria criteria=example.createCriteria();
        criteria.andEqualTo("directFileId",toshare.getShareFileId());
        DirectoryFile directoryFile=DFmapper.selectOneByExample(example);

        return directoryFile.getDfFileName();
    }

    @Override
    public String getFileLinkByID(String id) {
        Toshare toshare = toshareMapper.selectByPrimaryKey(id);
        //toshare.getShareFileId()
        DirectoryFile df = DFmapper.selectByPrimaryKey(toshare.getShareFileId());
        Myfile myfile = myfileMapper.selectByPrimaryKey(df.getDfFileId());
        String FileLink = myfile.getFileLink();
        return FileLink;
    }
    @Override
    public String getUploadUrl(String objectName){
        String ak = obsConfig.getAccessKeyId();
        String sk = obsConfig.getSecretAccessKey();
        String endPoint = obsConfig.getEndpoint();
        // 创建ObsClient实例
        ObsClient obsClient = new ObsClient(ak, sk, endPoint);
        // URL有效期，3600秒
        long expireSeconds = 3600L;
        Map<String, String> headers = new HashMap<String, String>();
        String contentType = "multipart/form-data";
        headers.put("Content-Type", contentType);

        TemporarySignatureRequest request = new TemporarySignatureRequest(HttpMethodEnum.PUT, expireSeconds);
        request.setBucketName(obsConfig.getBucketName());
        request.setObjectKey(objectName);
        request.setHeaders(headers);
//        System.out.println(request);
        TemporarySignatureResponse response = obsClient.createTemporarySignature(request);
//        System.out.println(response.getSignedUrl());
        return response.getSignedUrl();
    }

    @Override
    public Boolean insertDirectoryFile(DirectoryFile directoryFile) {
        return directoryFileMapper.insertSelective(directoryFile)>0;

    }


    /**
     * 文件下载
     * @param文件链接
     * @return 下载文件的地址链接
     */
    @Override

    public String getDFidByFidAndDid(String Fid, String Did) {
        Example example = new Example(DirectoryFile.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.andEqualTo("dfDirectId", Did);
        criteria.andEqualTo("dfFileId",Fid);
        DirectoryFile directoryFile = DFmapper.selectOneByExample(example);
        return directoryFile.getDirectFileId();
    }



    @Override
    public String fileDownload(String objectname, long expire) {
        String ak = obsConfig.getAccessKeyId();
        String sk = obsConfig.getSecretAccessKey();
        String endPoint = obsConfig.getEndpoint();


        // 创建ObsClient实例
        ObsClient obsClient = new ObsClient(ak, sk, endPoint);
        // URL有效期，3600秒
        long expireSeconds = expire;
        TemporarySignatureRequest request = new TemporarySignatureRequest(HttpMethodEnum.GET, expireSeconds);
        request.setBucketName(obsConfig.getBucketName());
        request.setObjectKey(objectname);
        Map map = new HashMap<String,Object>();
        map.put("response-content-disposition","attachment");
        request.setQueryParams(map);
        TemporarySignatureResponse response = obsClient.createTemporarySignature(request);
        return response.getSignedUrl();

    }
/*
    @Override
    public String fileDownload(String objectname) {
        String ak = "VSTWKTJ92NZAI2VJ14PJ";
        String sk = "5tpC64qnXaOFpw5zwKV0vnZoEQAVCjpE0s6BomQg";
        String endPoint = "obs.cn-north-4.myhuaweicloud.com";

        // 创建ObsClient实例
        ObsClient obsClient = new ObsClient(ak, sk, endPoint);
        // URL有效期，3600秒
        long expireSeconds = 3600L;
        TemporarySignatureRequest request = new TemporarySignatureRequest(HttpMethodEnum.GET, expireSeconds);
        request.setBucketName("obs-dgut-lh");
        request.setObjectKey(objectname);
        TemporarySignatureResponse response = obsClient.createTemporarySignature(request);
        return response.getSignedUrl();
    }*/

    /**
     * 根据文件夹id和文件id进行数据库查询
     * @param directID 文件夹id
     * @param fileID 文件id
     * @return
     */
    @Override
    public DirectoryFile selectFileById(String directID, String fileID) {

        Example example = new Example(DirectoryFile.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.andEqualTo("dfDirectId", directID);
        criteria.andEqualTo("dfFileId",fileID);
        DirectoryFile directoryFile = DFmapper.selectOneByExample(example);
        return directoryFile;

    }

    /**
     * 文件重命名
     * @param directoryFile 文件和文件夹映射表对象
     * @param directID 文件夹id
     * @param fileID 文件id
     * @return 1.重命名成功，0.重命名失败
     */
    @Override
    public int ReFilename(DirectoryFile directoryFile,String directID, String fileID) {
        //修改文件映射表的文件命名
        Example example = new Example(DirectoryFile.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.andEqualTo("dfDirectId", directID);
        criteria.andEqualTo("dfFileId",fileID);
        int i = DFmapper.updateByExampleSelective(directoryFile, example);
        return i;
    }

    /**
     * 文件复制到指定文件夹里
     * @param directoryFile 文件夹
     * @return 1：成功  0：失败
     */
    @Override
    public int copyToDirect(DirectoryFile directoryFile) {
        int i = DFmapper.insertSelective(directoryFile);
        return i;
    }

    /**
     *根据文件id查询
     * @param fileID 文件id
     * @return 映射表对象
     */
    @Override
    public DirectoryFile selectFileById(String fileID) {
        Example example = new Example(DirectoryFile.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.andEqualTo("dfFileId",fileID);
        DirectoryFile directoryFile = DFmapper.selectOneByExample(example);
        return directoryFile;
    }

    /**
     * 根据文件夹id遍历映射表
     * @param directID 文件夹id
     * @return 遍历集合
     */
    @Override
    public List<DirectoryFile> selectFileByDirectId(String directID) {
        Example example = new Example(DirectoryFile.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.andEqualTo("dfDirectId",directID);
        List<DirectoryFile> list = DFmapper.selectByExample(example);
        return list;
    }

    //获取被删除至回收站的文件
    @Override
    public List<DirectoryFile> getDeletedFileByID(String id) {
        Example example = new Example(DirectoryFile.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.andEqualTo("dfDeleteId",id);
        List<DirectoryFile> list=DFmapper.selectByExample(example);

        return list;
    }
    //获取被删除至回收站的文件夹
    @Override
    public List<Directory> getDeletedDirectoryByID(String id) {
        Example example = new Example(Directory.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.andEqualTo("directBelongUser",id).andIsNotNull("directDeleteTime");//删除时间非空
        List<Directory> list=Dmapper.selectByExample(example);
        return list;
    }

    @Override
    public Boolean restoreDirectory(String id) {//还原文件夹
        Directory d=Dmapper.selectByPrimaryKey(id);
        Directory dParent=Dmapper.selectByPrimaryKey(d.getParentDirectId());
        if(dParent.getDirectDelete()==1){//判断父文件夹是否被删除,否的话进行还原，是的话报错
            d.setDirectDelete((byte) 1);
            d.setDirectDeleteTime(null);
            int i=Dmapper.updateByPrimaryKey(d);
            if(i>0){
                return true;
            }else {
                System.out.println("还原失败！");
                return false;
            }
        }else{//除了DirectDelete==1，其他情况视为父文件夹不存在，直接报错
            return false;
        }

    }

    @Override
    public Boolean restoreFile(String id) {//还原文件
        DirectoryFile df=DFmapper.selectByPrimaryKey(id);
        Directory dParent=Dmapper.selectByPrimaryKey(df.getDfDirectId());
        if(dParent.getDirectDelete()==1){//判断父文件夹是否被删除,否的话进行还原，是的话报错
            df.setDfGarbage((byte) 1);
            df.setDfDeleteTime(null);
            df.setDfDeleteId(null);
            //System.out.println(df.getDfDeleteTime()+"---"+df.getDfDeleteId());
            int i =DFmapper.updateByPrimaryKey(df);
            if(i>0){
                return true;
            }else {
                System.out.println("还原失败！");
                return false;
            }
        }else{//除了DFGarbage==1，其他情况视为父文件夹不存在，直接报错
            return false;
        }
    }

    @Override
    public Boolean restoreDirectorytoNew(String id, String Did) {
        Directory d=Dmapper.selectByPrimaryKey(id);
        Directory dNew=Dmapper.selectByPrimaryKey(Did);
        if(dNew.getDirectDelete()==1){//判断新文件夹是否被删除,否的话进行还原，是的话报错
            d.setDirectDelete((byte) 1);
            d.setDirectDeleteTime(null);
            d.setParentDirectId(Did);//设置新文件夹为父文件夹
            int i=Dmapper.updateByPrimaryKey(d);
            if(i>0){
                return true;
            }else {
                System.out.println("还原失败！");
                return false;
            }
        }else{//除了DirectDelete==1，其他情况视为新文件夹不存在，直接报错
            return false;
        }

    }

    @Override
    public Boolean restoreFiletoNew(String id, String Did) {
        DirectoryFile df=DFmapper.selectByPrimaryKey(id);
        Directory dNew=Dmapper.selectByPrimaryKey(Did);
        if(dNew.getDirectDelete()==1) {//判断新文件夹是否被删除,否的话进行还原，是的话报错
            df.setDfGarbage((byte) 1);
            df.setDfDeleteTime(null);
            df.setDfDeleteId(null);
            df.setDfDirectId(Did);//设置新文件夹为父文件夹
            int i = DFmapper.updateByPrimaryKey(df);
            if(i>0){
                return true;
            }else {
                System.out.println("还原失败！");
                return false;
            }
        }else{//除了DirectDelete==1，其他情况视为新文件夹不存在，直接报错
            return false;
        }

    }



    @Override
    public int updateDirectFileById(DirectoryFile directoryFile, String dfFileId) {
        //修改文件映射表
        Example example = new Example(DirectoryFile.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.andEqualTo("dfFileId",dfFileId);
        int i = DFmapper.updateByExampleSelective(directoryFile, example);
        return i;
    }
}
