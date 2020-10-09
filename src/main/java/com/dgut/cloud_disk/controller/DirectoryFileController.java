package com.dgut.cloud_disk.controller;

import com.alibaba.fastjson.JSONObject;
import com.dgut.cloud_disk.mapper.DirectoryFileMapper;
import com.dgut.cloud_disk.mapper.DirectoryMapper;
import com.dgut.cloud_disk.mapper.ToshareMapper;
import com.dgut.cloud_disk.pojo.CdstorageUser;
import com.dgut.cloud_disk.pojo.Directory;
import com.dgut.cloud_disk.pojo.DirectoryFile;
import com.dgut.cloud_disk.pojo.Toshare;
import com.dgut.cloud_disk.service.DepartmentUserService;
import com.dgut.cloud_disk.service.DirectoryFileService;
import com.dgut.cloud_disk.service.DirectoryService;
import com.dgut.cloud_disk.util.DateUtil;
import com.dgut.cloud_disk.util.JSONResult;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import tk.mybatis.mapper.entity.Example;

import javax.annotation.Resource;
import javax.persistence.Column;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/cloud")
public class DirectoryFileController {
    @Resource
    private DirectoryFileService DFService;
    @Resource
    private DirectoryMapper Dmapper;
    @Resource
    private DirectoryFileMapper DFmapper;
    @Resource
    private ToshareMapper TSmapper;
    @Resource
    private DepartmentUserService DUService;
    @Autowired
    private JedisPool jedisPool;
    @RequestMapping("/df")
    public List<DirectoryFile> allFile(){
        System.out.println("+++"+DFService.allFile());
        return (DFService.allFile());
    }


    @RequestMapping("/user/deleteFile")
	public JSONResult deleteDirectoryFile(@RequestBody JSONObject jsonObject) throws JsonProcessingException {
        String token = jsonObject.getString("token");
        //从token中获取分享人id
        Jedis jedis = jedisPool.getResource();
        String tokenValue = jedis.get(token);
        ObjectMapper objectMapper=new ObjectMapper();
        CdstorageUser cdstorageUser = objectMapper.readValue(tokenValue, CdstorageUser.class);
        jedis.close();
        String directID=jsonObject.getString("directID");
        String fileID=jsonObject.getString("fileID");
        if(DFService.deleteFile(directID,fileID,cdstorageUser.getUserId())){
            return new JSONResult(200,"删除成功！","");
        }else {
            return new JSONResult(500,"删除失败！","");
        }
    }

    @RequestMapping("/user/deleteDirectory")
    public JSONResult deleteDirectory(@RequestBody JSONObject jsonObject) throws JsonProcessingException {
        String token = jsonObject.getString("token");
        //从token中获取分享人id
        Jedis jedis = jedisPool.getResource();
        String tokenValue = jedis.get(token);
        ObjectMapper objectMapper=new ObjectMapper();
        CdstorageUser cdstorageUser = objectMapper.readValue(tokenValue, CdstorageUser.class);
        String directID=jsonObject.getString("directID");
        if(DFService.deleteDirectory(directID)){
            return new JSONResult(200,"删除成功！","");
        }else {
            return new JSONResult(500,"删除失败！","");
        }

    }


    @RequestMapping("/user/deleteDorDF")//彻底删除(群组中的删除)
    public JSONResult deleteDirectoryFileOrDirectory(@RequestBody JSONObject jsonObject) throws JsonProcessingException {
        //1-文件夹，2-文件
        String token = jsonObject.getString("token");
        int type =jsonObject.getInteger("type");
        String id =jsonObject.getString("id");
        String directId = jsonObject.getString("directID");

        Directory directory=new Directory();
        if(type==1){
            directory=Dmapper.selectByPrimaryKey(id);
        }else if(type==2){
            Example example = new Example(DirectoryFile.class);
            Example.Criteria criteria= example.createCriteria();
            criteria.andEqualTo("dfDirectId",directId);
            criteria.andEqualTo("dfFileId",id);
            DirectoryFile directoryFile=DFmapper.selectOneByExample(example);
            id = directoryFile.getDirectFileId();
        }else{
            return new JSONResult(500,"类型错误！","");
        }

        Jedis jedis = jedisPool.getResource();
        String tokenValue = jedis.get(token);
        ObjectMapper objectMapper=new ObjectMapper();
        CdstorageUser cdstorageUser = objectMapper.readValue(tokenValue, CdstorageUser.class);
        jedis.close();

        if(cdstorageUser.getUserPermission()==0) {
            String dePermission = DUService.selectdepartPermissionByid(directory.getDirectBelongDepart());
            String p = dePermission.substring(2, 3);//截取第三位（即删除文件夹的权限）

            if (p.equals("1")) {//判断为1说明有该权限
                if (DFService.deleteDorDF(type, id,cdstorageUser)) {
                    return new JSONResult(200, "删除成功！", "");
                } else {
                    return new JSONResult(500, "删除失败！", "");
                }
            } else {
                return new JSONResult(500, "权限不足！", "");
            }
        }else{
            if (DFService.deleteDorDF(type, id,cdstorageUser)) {
                return new JSONResult(200, "删除成功！", "");
            } else {
                return new JSONResult(500, "删除失败！", "");
            }
        }
    }
    @RequestMapping("/user/privateShare")//私密分享
    public JSONResult privateShare(@RequestBody JSONObject jsonObject) throws JsonProcessingException{
        //shareTime表示为秒
        //从jsonObject中获取数据
        //System.out.println(jsonObject);
        String token = jsonObject.getString("token");
        String Did = jsonObject.getString("directID");
        String Fid = jsonObject.getString("fileID");
        Integer shareTime =jsonObject.getInteger("shareTime");
        String [] userIDs = jsonObject.getString("users").replace("[","").replace("]","").split(",");
        System.out.println(token+Did+Fid+shareTime);
        //从token中获取分享人id
        Jedis jedis = jedisPool.getResource();
        String tokenValue = jedis.get(token);
        ObjectMapper objectMapper=new ObjectMapper();
        CdstorageUser cdstorageUser = objectMapper.readValue(tokenValue, CdstorageUser.class);
        jedis.close();
        //System.out.println(cdstorageUser.getUserId()+Did+Fid+shareTime+"---"+userIDs[0]+userIDs[1]);
        for (int i=0;i<userIDs.length;i++){
        Toshare toshare=new Toshare();
        toshare.setShareId(UUID.randomUUID().toString().replace("-", ""));
        toshare.setShareUserId(cdstorageUser.getUserId());
        toshare.setShareToUserId(userIDs[i].trim());
        Date date=new Date();
        toshare.setShareTime(date);
        toshare.setShareExpire(new Date(date.getTime() + shareTime * 1000L));

        if(Fid!=null&&Did!=null){//当两个id都非空时 分享文件
            //DFService.getDFidByFidAndDid(Fid,Did);
            toshare.setShareType((byte) 1);//1-私密分享文件 2-私密分享文件夹 3-外链分享文件 4-外链分享文件夹
            toshare.setShareFileId(DFService.getDFidByFidAndDid(Fid,Did));//只设置ShareFileId
        }else if(Fid==null&&Did!=null){//当Fid为空，Did非空时 分享文件夹
            toshare.setShareType((byte) 2);
            toshare.setShareDirectId(Did);//只设置ShareDirectID;
        }else{
            return new JSONResult(500,"分享失败！","");
        }
        DFService.insertShare(toshare);
        }
        return new JSONResult(200,"分享成功！","");
    }

    @RequestMapping("/user/publicShare")//外链分享
    public JSONResult publicShare(@RequestBody JSONObject jsonObject) throws JsonProcessingException {
        String token = jsonObject.getString("token");
        String Did = jsonObject.getString("newDirectID");
        String Fid = jsonObject.getString("fileID");
        Integer shareTime = jsonObject.getInteger("shareTime");
        String Code = jsonObject.getString("code");
        //System.out.println(token+Did+Fid+shareTime+"---"+Code);
        //从token中获取分享人id
        Jedis jedis = jedisPool.getResource();
        String tokenValue = jedis.get(token);
        ObjectMapper objectMapper = new ObjectMapper();
        CdstorageUser cdstorageUser = objectMapper.readValue(tokenValue, CdstorageUser.class);
        jedis.close();
        Toshare toshare=new Toshare();
        toshare.setShareId(UUID.randomUUID().toString().replace("-", ""));
        toshare.setShareUserId(cdstorageUser.getUserId());
        toshare.setShareToUserId("0");//被分享人id为0，表示不指定
        Date date = new Date();
        toshare.setShareTime(date);
        toshare.setShareExpire(new Date(date.getTime() + shareTime * 1000L));
        toshare.setShareCode(Code);
        if(Fid!=null&&Did!=null){
            //3-外链分享文件 ！！外链分享只分享文件
            toshare.setShareType((byte) 3);
            toshare.setShareFileId(DFService.getDFidByFidAndDid(Fid,Did));//只设置ShareFileId
        }else{
            return new JSONResult(500,"分享失败！","");
        }
        DFService.insertShare(toshare);

        JSONObject obj=new JSONObject();
        obj.put("shareID",toshare.getShareId());
        return new JSONResult(200,"分享成功！",obj);
    }
    @RequestMapping("/getShareInfo")
    public JSONResult getShareInfo(@RequestBody JSONObject jsonObject){
        String shareID=jsonObject.getString("shareID");
        Toshare toshare=TSmapper.selectByPrimaryKey(shareID);

        String code=toshare.getShareCode();
        String shareName=DFService.getShareName(toshare.getShareUserId());

        JSONObject obj=new JSONObject();
        obj.put("shareName",shareName);
        obj.put("code",code);
        return new JSONResult(200,"",obj);

    }
        //验证密码
    @RequestMapping("/publicVerifyCode")
    public JSONResult publicVerifyCode(@RequestBody JSONObject jsonObject) {
        String shareID=jsonObject.getString("shareID");
        String code=jsonObject.getString("code");
        int i =DFService.VerifyCode(shareID,code);
        //System.out.println(shareID+code);
        //无密码或密码正确时i=1，有密码但没有输入密码时i=-1，有密码但输入密码错误时i=-2
        if(i==1){
            //需返回fileName，shareTime，downloadURL
            String fileName=DFService.getFileNameByID(shareID);
            Date shareTime=DFService.getShareTimeByID(shareID);
            //System.out.println(fileName+shareTime);
            String downloadUrl=DFService.fileDownload(DFService.getFileLinkByID(shareID),300L);

            JSONObject obj=new JSONObject();
            obj.put("fileName",fileName);
            obj.put("shareTime", DateUtil.transfromDate(shareTime));
            obj.put("downloadURL",downloadUrl);

            return new JSONResult(200,"可以访问！",obj);
        }else if(i==-1){
            return new JSONResult(606,"需要密码！","");
        }else {
            return new JSONResult(500,"密码错误！","");
        }

    }

}

