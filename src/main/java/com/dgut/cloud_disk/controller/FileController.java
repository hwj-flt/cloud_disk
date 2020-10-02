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
import org.springframework.beans.factory.annotation.Value;
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
    private DepartmentService departmentService;
    @Autowired
    private CdstorageUserService cdstorageUserService;
    @Value("${redis.defaultTokenValidTime}")
    private Integer tokenValidTime;
    /**
     * 判断群组文件权限;有文件夹ID找文件夹表中文件夹有个所属群组ID。用用户ID和群组ID获得群组和用户映射表中权限，查看下载位置是否为1
     * @param directID 文件夹id
     * @param token 登录的用户
     * @return 权限值
     * @throws JsonProcessingException
     */
    public String checkPermission(String directID,String token) throws JsonProcessingException {
        Directory directory = directoryService.selectDirectoryByID(directID);
        Department department = departmentService.selDepart(directory.getDirectBelongDepart());
        return department.getDepartPermission();
    }
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
        jedis.close();
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
        jedis.expire(token,tokenValidTime);
        jedis.close();
        return JSONResult.build(200,"上传成功",null);
    }
    /**
     * 文件下载
     * @param jsonObject
     * @return 文件的下载链接
     * @throws JsonProcessingException
     */
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

        String url = directoryFileService.fileDownload(myfile.getFileLink(),3600L);

        jsonObject.put("data",url);
        return new JSONResult(200,"",jsonObject);
    }

    /**
     * 个人文件重命名
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
    /**
     * 个人文件夹重命名
     * @param jsonObject
     * @return 前端需要的json数据
     */
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
    /**
     * 个人文件复制
     * @param jsonObject
     * @return
     * @throws JsonProcessingException
     */
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
        //DepartmentUser departmentUser = departmentUserService.selectduPermissionByid(cdstorageUser.getUserId(), directory.getDirectBelongDepart());


        //判断权限
        if("00000100".equals(checkPermission(newDirectID,token))){
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
    @RequestMapping("/copydilectfile")
    public JSONResult copyDilectFile(@RequestBody JSONObject jsonObject) throws JsonProcessingException {
        String newDirectID = jsonObject.getString("newDirectID");
        String token = jsonObject.getString("token");
        String directID = jsonObject.getString("directID");

        //创建redis对象
        Jedis jedis = jedisPool.getResource();
        //接收token查找用户对象
        String user = jedis.get(token);
        ObjectMapper mapper = new ObjectMapper();
        CdstorageUser cdstorageUser = mapper.readValue(user, CdstorageUser.class);

        //查用户权限
       // DepartmentUser departmentUser = departmentUserService.selectduPermissionByid(cdstorageUser.getUserId(), directory.getDirectBelongDepart());


//        //判断权限
//        if(departmentUser.getDuPermission()!=1){
//            return JSONResult.errorMsg("");
//        }


        //判断权限
        if("00000100".equals(checkPermission(newDirectID,token))){
            return JSONResult.errorMsg("");
        }

        //文件夹复制
        Directory directory = directoryService.selectDirectoryByID(directID);
        directory.setParentDirectId(newDirectID);
        String uuid = UUID.randomUUID().toString().replace("-", "");
        String directFileId = UUID.randomUUID().toString().replace("-", "");
        directory.setDirectId(uuid);
        directory.setDirectCreateTime(new Date());
        directory.setDirectCreateId(cdstorageUser.getUserId());
        directory.setDirectBelongUser(cdstorageUser.getUserId());
        directoryService.insertDirectory(directory);
        List<DirectoryFile> list = directoryFileService.selectFileByDirectId(directID);

        for (DirectoryFile directoryFile : list){
            directoryFile.setDirectFileId(directFileId);
            directoryFile.setDfDirectId(uuid);
            directoryFileService.copyToDirect(directoryFile);
        }
        return new JSONResult(200,"",null);
    }
    /**
     * 移动文件到指定目录
     * @param jsonObject
     * @return 需要的json值
     */
    @RequestMapping("/movefile")
    public JSONResult moveFile(@RequestBody JSONObject jsonObject){
        String token = jsonObject.getString("token");
        //新文件夹ID
        String newDirectID = jsonObject.getString("newDirectID");
        //文件ID
        String fileID = jsonObject.getString("fileID");
        //原文件夹id
        String directID = jsonObject.getString("directID");
        DirectoryFile directoryFile = directoryFileService.selectFileById(directID,fileID);
        directoryFile.setDfDirectId(newDirectID);
        directoryFileService.updateDirectFileById(directoryFile,fileID);
        return new JSONResult(200,"",null);
    }

    /**
     * 移动文件夹到指定目录
     * @param jsonObject
     * @return 需要的json值
     */
    @RequestMapping("/movedirectfile")
    public JSONResult moveDirectFile(@RequestBody JSONObject jsonObject){
        String token = jsonObject.getString("token");
        //新文件夹ID
        String newDirectID = jsonObject.getString("newDirectID");
        //文件夹ID
        String directID = jsonObject.getString("directID");
        Directory directory = directoryService.selectDirectoryByID(directID);
        directory.setParentDirectId(newDirectID);
        directoryService.updateDirectoryByID(directory,directID);
        return new JSONResult(200,"",null);
    }
    /**
     * 群组文件下载
     * @param jsonObject json接收对象
     * @return
     * @throws JsonProcessingException
     */
    @RequestMapping("/departdownload")
    public JSONResult departFileDownload(@RequestBody JSONObject jsonObject) throws JsonProcessingException {
        String directID = jsonObject.getString("directID");//文件夹id
        String fileID = jsonObject.getString("fileID");//文件id
        String token = jsonObject.getString("token");//登录token
        if ("01000000".equals(checkPermission(directID,token))){
            return JSONResult.errorMsg("无权限操作此文件");
        }
        //根据文件链接下载文件
        Myfile myfile = myFileService.selectFileById(fileID);
        String url = directoryFileService.fileDownload(myfile.getFileLink(),3600L);
        jsonObject.put("data",url);
        return new JSONResult(200,"",jsonObject);
    }

    /**
     * 群组文件重命名
     * @param jsonObject
     * @return
     */
    @RequestMapping("/redepartfilename")
    public JSONResult reDepartFileName(@RequestBody JSONObject jsonObject) throws JsonProcessingException {
        String newName = jsonObject.getString("newName");//新文件名
        String directID = jsonObject.getString("directID");//文件夹id
        String fileID = jsonObject.getString("fileID");//文件id
        String token = jsonObject.getString("token");//用户
        //判断权限
        if ("00000001".equals(checkPermission(directID,token))){
            return JSONResult.errorMsg("无权限操作此文件");
        }
        //根据文件夹id和文件id对所选的文件进行重命名
        DirectoryFile directoryFile = new DirectoryFile();
        directoryFile.setDfFileName(newName);
        directoryFileService.ReFilename(directoryFile,directID,fileID);
        //重命名后对分享表记录进行删除
        toshareService.deleteRecord(fileID,directID);
        return new JSONResult(200,"",null);
    }
    /**
     * 群组文件夹重命名
     * @param jsonObject
     * @return
     */
    @RequestMapping("/redepatdefilename")
    public JSONResult reDepatDeFileName(@RequestBody JSONObject jsonObject) throws JsonProcessingException {
        //新文件夹名
        String newName = jsonObject.getString("newName");
        //文件夹id
        String directID = jsonObject.getString("directID");
        //用户
        String token = jsonObject.getString("token");
        //判断权限
        if("00000001".equals(checkPermission(directID,token))){
            return JSONResult.errorMsg("无权限操作此文件");
        }
        //根据文件夹id对文件夹表的文件夹名字段进行修改
        Directory directory = new Directory();
        directory.setDirectName(newName);
        directoryService.updateDirectoryByID(directory,directID);
        //重命名后对分享表记录进行删除
        toshareService.deleteDirectRecord(directID);
        return new JSONResult(200,"",null);
    }
    /**
     * 移动群组文件到指定目录
     * @param jsonObject
     * @return 需要的json值
     */
    @RequestMapping("/movedepartfile")
    public JSONResult moveDepartFile(@RequestBody JSONObject jsonObject) throws JsonProcessingException {
        String token = jsonObject.getString("token");
        //新文件夹ID
        String newDirectID = jsonObject.getString("newDirectID");
        //文件ID
        String fileID = jsonObject.getString("fileID");
        //原文件夹id
        String directID = jsonObject.getString("directID");
        //判断权限
        if("00000001".equals(checkPermission(directID,token))){
            return JSONResult.errorMsg("无权限操作此文件");
        }
        //根据文件id查询映射表
        DirectoryFile directoryFile = directoryFileService.selectFileById(directID,fileID);
        //修改文件夹id
        directoryFile.setDfDirectId(newDirectID);
        directoryFileService.updateDirectFileById(directoryFile,fileID);
        return new JSONResult(200,"",null);
    }
    /**
     * 移动群组文件夹到指定目录
     * @param jsonObject
     * @return 需要的json值
     */
    @RequestMapping("/movedepartdirectfile")
    public JSONResult moveDepartDirectFile(@RequestBody JSONObject jsonObject) throws JsonProcessingException {
        String token = jsonObject.getString("token");
        //新文件夹ID
        String newDirectID = jsonObject.getString("newDirectID");
        //文件夹ID
        String directID = jsonObject.getString("directID");
        //判断权限
        if("00000001".equals(checkPermission(directID,token))){
            return JSONResult.errorMsg("无权限操作此文件");
        }
        //根据文件夹id查询文件夹表
        Directory directory = directoryService.selectDirectoryByID(directID);
        //修改父文件夹id
        directory.setParentDirectId(newDirectID);
        directoryService.updateDirectoryByID(directory,directID);
        return new JSONResult(200,"",null);
    }

    /**
     * 复制群组文件
     * @param jsonObject
     * @return
     * @throws JsonProcessingException
     */
    @RequestMapping("/copydepartfile")
    public JSONResult copyDepartFile(@RequestBody JSONObject jsonObject) throws JsonProcessingException {
        String newDirectID = jsonObject.getString("newDirectID");
        String token = jsonObject.getString("token");
        String fileID = jsonObject.getString("fileID");
        //创建redis对象
        Jedis jedis = jedisPool.getResource();
        //接收token查找用户对象
        String user = jedis.get(token);
        ObjectMapper mapper = new ObjectMapper();
        CdstorageUser cdstorageUser = mapper.readValue(user, CdstorageUser.class);
        //判断权限
        if("00000100".equals(checkPermission(newDirectID,token))){
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
}
