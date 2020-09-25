package com.dgut.cloud_disk.service.impl;

import com.dgut.cloud_disk.mapper.DirectoryFileMapper;
import com.dgut.cloud_disk.mapper.DirectoryMapper;
import com.dgut.cloud_disk.mapper.ToshareMapper;
import com.dgut.cloud_disk.pojo.DirectoryFile;
import com.dgut.cloud_disk.pojo.Toshare;
import com.dgut.cloud_disk.service.DirectoryFileService;
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
        criteria.andEqualTo("dfDirectId",toshare.getShareDirectId()).andEqualTo("dfFileId",toshare.getShareFileId());
        DirectoryFile directoryFile=DFmapper.selectOneByExample(example);

        return directoryFile.getDfFileName();
    }

}
