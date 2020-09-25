package com.dgut.cloud_disk.controller;

import com.alibaba.fastjson.JSONObject;
import com.dgut.cloud_disk.pojo.*;
import com.dgut.cloud_disk.service.*;
import com.dgut.cloud_disk.util.JSONResult;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

import java.util.Date;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/cloud/user")
public class FileController {

    private final Logger loggqer = LoggerFactory.getLogger(FileController.class);

    @Autowired(required = false)
    private DirectoryFileService directoryFileService;
    @Autowired
    private DirectoryService directoryService;
    @Autowired
    private JedisPool jedisPool;
    @Autowired
    private MyFileService myFileService;
    @Autowired
    private ToshareService toshareService;
    @Autowired
    private DepartmentUserService departmentUserService;


    @RequestMapping("/download")
    public JSONResult fileDownload(@RequestBody JSONObject jsonObject) throws JsonProcessingException {
        String directID = jsonObject.getString("directID");
        String fileID = jsonObject.getString("fileID");
        String token = jsonObject.getString("token");

        //判断文件是否存在
        DirectoryFile file = directoryFileService.selectFileById(directID, fileID);
        Jedis jedis = jedisPool.getResource();
        String user = jedis.get(token);
        ObjectMapper mapper = new ObjectMapper();
        CdstorageUser cdstorageUser = mapper.readValue(user, CdstorageUser.class);
        Directory directory = directoryService.selectDirectoryByID(directID);
        if(file==null || file.getDfGarbage()==2 || directory.getDirectBelongUser()==cdstorageUser.getUserId()){
            return JSONResult.errorMsg("不能操作此文件");
        }
        //根据文件链接下载文件
        Myfile myfile = myFileService.selectFileById(fileID);
        String url = directoryFileService.fileDownload(myfile.getFileLink());
        jsonObject.put("data",url);
        return new JSONResult(200,"",jsonObject);
    }

    /**
     * 文件重命名
     * @param jsonObject
     * @return
     */
    @RequestMapping("/refilename")
    public JSONResult reFileName(@RequestBody JSONObject jsonObject){
        loggqer.info(jsonObject.toJSONString());
        String newName = jsonObject.getString("newName");
        String directID = jsonObject.getString("directID");
        String fileID = jsonObject.getString("fileID");
        //根据文件夹id和文件id对所选的文件进行重命名
        DirectoryFile directoryFile = new DirectoryFile();
        directoryFile.setDfFileName(newName);
        directoryFileService.ReFilename(directoryFile,directID,fileID);
        //重命名后对分享表记录进行删除
        toshareService.deleteRecord(fileID,directID);
       return new JSONResult(200,"",null);
    }
    @RequestMapping("/redefilename")
    public JSONResult reDeFileName(@RequestBody JSONObject jsonObject){
        loggqer.info(jsonObject.toJSONString());
        //新文件夹名
        String newName = jsonObject.getString("newName");
        //文件夹id
        String directID = jsonObject.getString("directID");
        //根据文件夹id对文件夹表的文件夹名字段进行修改
        Directory directory = new Directory();
        directory.setDirectName(newName);
        directoryService.updateDirectoryByID(directory,directID);
        //重命名后对分享表记录进行删除
        toshareService.deleteDirectRecord(directID);
        return new JSONResult(200,"",null);
    }

    @RequestMapping("/copyfile")
    public JSONResult copyToDiretory(@RequestBody JSONObject jsonObject) throws JsonProcessingException {
        String newDirectID = jsonObject.getString("newDirectID");
        String token = jsonObject.getString("token");
        String fileID = jsonObject.getString("fileID");
        //查群组id
        Directory directory = directoryService.selectDirectoryByID(newDirectID);
        //创建redis对象
        Jedis jedis = jedisPool.getResource();
        //接收token查找用户对象
        String user = jedis.get(token);
        ObjectMapper mapper = new ObjectMapper();
        CdstorageUser cdstorageUser = mapper.readValue(user, CdstorageUser.class);
        //查用户权限
        DepartmentUser departmentUser = departmentUserService.selectduPermissionByid(cdstorageUser.getUserId(), directory.getDirectBelongDepart());
        //判断权限
        if(departmentUser.getDuPermission()!=1){
            return JSONResult.errorMsg("");
        }
        //文件复制
        DirectoryFile directoryFile = directoryFileService.selectFileById(fileID);
        String uuid = UUID.randomUUID().toString().replace("-", "");
        directoryFile.setDirectFileId(uuid);
        directoryFile.setDfDirectId(newDirectID);
        directoryFileService.copyToDirect(directoryFile);
        return new JSONResult(200,"",null);
    }
    @RequestMapping("/copydilect")
    public JSONResult copyDilectFile(@RequestBody JSONObject jsonObject) throws JsonProcessingException {
        String newDirectID = jsonObject.getString("newDirectID");
        String token = jsonObject.getString("token");
        String directID = jsonObject.getString("directID");
        //查群组id
        Directory directory = directoryService.selectDirectoryByID(newDirectID);
        //创建redis对象
        Jedis jedis = jedisPool.getResource();
        //接收token查找用户对象
        String user = jedis.get(token);
        ObjectMapper mapper = new ObjectMapper();
        CdstorageUser cdstorageUser = mapper.readValue(user, CdstorageUser.class);
        //查用户权限
        DepartmentUser departmentUser = departmentUserService.selectduPermissionByid(cdstorageUser.getUserId(), directory.getDirectBelongDepart());
        //判断权限
        if(departmentUser.getDuPermission()!=1){
            return JSONResult.errorMsg("");
        }
        //文件夹复制
        directory = directoryService.selectDirectoryByID(directID);
        directory.setParentDirectId(newDirectID);
        String uuid = UUID.randomUUID().toString().replace("-", "");
        String directFileId = UUID.randomUUID().toString().replace("-", "");
        directory.setDirectId(uuid);
        directory.setDirectCreateTime(new Date());
        //directory.setDirectCreateId(cdstorageUser.getUserId());
        //directory.setDirectBelongUser(cdstorageUser.getUserId());
        directoryService.insertDirectory(directory);
        List<DirectoryFile> list = directoryFileService.selectFileByDirectId(directID);

        for (DirectoryFile directoryFile : list){
            directoryFile.setDirectFileId(directFileId);
            directoryFile.setDfDirectId(uuid);
            directoryFileService.copyToDirect(directoryFile);
        }
        return new JSONResult(200,"",null);
    }
}
