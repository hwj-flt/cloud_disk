package com.dgut.cloud_disk.service.impl;

import com.dgut.cloud_disk.config.ObsConfig;
import com.dgut.cloud_disk.mapper.DirectoryFileMapper;
import com.dgut.cloud_disk.mapper.DirectoryMapper;
import com.dgut.cloud_disk.mapper.ToshareMapper;
import com.dgut.cloud_disk.pojo.DirectoryFile;
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
import java.util.List;
@Service
public class DirectoryFileServiceImpl implements DirectoryFileService {

    @Resource
    private DirectoryFileMapper DFmapper;
    @Resource
    private DirectoryMapper Dmapper;
    @Resource
    private ToshareMapper toshareMapper;
    @Autowired
    private ObsConfig obsConfig;
    @Override
    public List<DirectoryFile> allFile() {
        List<DirectoryFile> file=DFmapper.selectAll();
        return file;
    }



    @Override
    public Boolean deleteFile(String directID, String fileID) {
        //修改DF_GARBAGE为1，并截取时间戳
       DirectoryFile df= DFmapper.selectByPrimaryKey(directID);
       df.setDfGarbage((byte) 2);
       Date date=new Date();
       df.setDfDeleteTime(date);
       int i=DFmapper.updateByPrimaryKeySelective(df);
       if(i>0){
           return true;
       }else {
           System.out.println(i);
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
        System.out.println(i);
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
    @Autowired(required = false)
    DirectoryFileMapper directoryFileMapper;
    /**
     * 下载文件
     * @param objectname 文件名
     * @return
     */
    @Override
    public String fileDownload(String objectname) {
        String ak = obsConfig.getAccessKeyId();
        String sk = obsConfig.getSecretAccessKey();
        String endPoint = obsConfig.getEndpoint();

        // 创建ObsClient实例
        ObsClient obsClient = new ObsClient(ak, sk, endPoint);
        // URL有效期，3600秒
        long expireSeconds = 3600L;
        TemporarySignatureRequest request = new TemporarySignatureRequest(HttpMethodEnum.GET, expireSeconds);
        request.setBucketName(obsConfig.getBucketName());
        request.setObjectKey(objectname);
        TemporarySignatureResponse response = obsClient.createTemporarySignature(request);
        return response.getSignedUrl();
    }

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
        DirectoryFile directoryFile = directoryFileMapper.selectOneByExample(example);
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
        int i = directoryFileMapper.updateByExampleSelective(directoryFile, example);
        return i;
    }

    /**
     * 文件复制到指定文件夹里
     * @param directoryFile 文件夹
     * @return 1：成功  0：失败
     */
    @Override
    public int copyToDirect(DirectoryFile directoryFile) {
        int i = directoryFileMapper.insertSelective(directoryFile);
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
        DirectoryFile directoryFile = directoryFileMapper.selectOneByExample(example);
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
        List<DirectoryFile> list = directoryFileMapper.selectByExample(example);
        return list;
    }
}
