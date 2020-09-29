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

import java.math.BigDecimal;
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
    @Autowired
    private CdstorageUserService cdstorageUserService;

    /**
     * 获取上传的链接
     * @param jsonObject 接收前端数据
     * @return uploadUrl
     * @throws JsonProcessingException json字符转对象异常
     */
    @RequestMapping("/getUploadUrl")
    public JSONResult getUploadUrl(@RequestBody JSONObject jsonObject) throws JsonProcessingException {
        String fileName = jsonObject.getString("fileName");
        String fileSize = jsonObject.getString("fileSize");
        String token = jsonObject.getString("token");
        Jedis jedis = jedisPool.getResource();
        String tokenValue = jedis.get(token);
        ObjectMapper mapper = new ObjectMapper();
        CdstorageUser user = mapper.readValue(tokenValue, CdstorageUser.class);
        if(user.getUserUsed().add(new BigDecimal(fileSize)).compareTo(user.getUserSize()) > 0){
            return JSONResult.errorMsg("存储空间不足，无法上传");
        }
        //objectName为工号加文件名
        String objectName = user.getUserWorkId() + "/" + fileName;
        String url = directoryFileService.getUploadUrl(objectName);
        JSONObject res = new JSONObject();
        res.put("uploadUrl",url);
        return JSONResult.build(200,"上传链接",res);
    }

    /**
     * 前端返回上传成功后操作数据库
     * @param jsonObject 接收前端数据
     * @return 操作结果
     * @throws JsonProcessingException json字符转对象异常
     */
    @RequestMapping("/upload")
    public JSONResult upload(@RequestBody JSONObject jsonObject) throws JsonProcessingException {
        String fileName = jsonObject.getString("fileName");
        String directID = jsonObject.getString("directID");
        String fileSize = jsonObject.getString("fileSize");
        String fileType = jsonObject.getString("fileType");
        String token = jsonObject.getString("token");
        //将用户对象从redis中读出
        Jedis jedis = jedisPool.getResource();
        String tokenValue = jedis.get(token);
        ObjectMapper mapper = new ObjectMapper();
        CdstorageUser user = mapper.readValue(tokenValue, CdstorageUser.class);
        //在文件表中插入一条数据:文件ID为UUID 文件链接就是objectName 上传时间为当前时间 引用文件夹个数为1
        Myfile newFile = new Myfile();
        newFile.setFileId(UUID.randomUUID().toString().replace("-",""));
        newFile.setFileLink(user.getUserWorkId() + "/" + fileName);
        newFile.setFileUploadTime(new Date());
        newFile.setFileRefere((byte)1);
        newFile.setFileSize(new BigDecimal(fileSize));
        newFile.setFileType(fileType);
        newFile.setFileUploadId(user.getUserId());
        if(!myFileService.insertFile(newFile)) {
            return JSONResult.errorMsg("插入文件表失败");
        }
        //在映射表中插入一条数据:映射ID由UUID生成 文件夹ID是前端传入的 文件ID为前面的文件ID 文件名为前端传入 Garbage未被删除 其它为空
        DirectoryFile directoryFile = new DirectoryFile();
        directoryFile.setDirectFileId(UUID.randomUUID().toString().replace("-",""));
        directoryFile.setDfFileId(newFile.getFileId());
        directoryFile.setDfFileName(fileName);
        directoryFile.setDfGarbage((byte)1);
        directoryFile.setDfDirectId(directID);
        if(!directoryFileService.insertDirectoryFile(directoryFile)) {
            return JSONResult.errorMsg("插入映射表失败");
        }
        //修改用户表的用户使用空间，更新redis中用户信息
        user.setUserUsed(user.getUserUsed().add(new BigDecimal(fileSize)));
        if(!cdstorageUserService.updateUser(user)) {
            return JSONResult.errorMsg("更新用户表失败");
        }
        jedis.set(token,mapper.writeValueAsString(user));
        return JSONResult.build(200,"上传成功",null);
    }

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
        String url = directoryFileService.fileDownload(myfile.getFileLink(),300L);
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
//        //判断权限
//        if(departmentUser.getDuPermission()!=1){
//            return JSONResult.errorMsg("");
//        }
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
//        //判断权限
//        if(departmentUser.getDuPermission()!=1){
//            return JSONResult.errorMsg("");
//        }
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
