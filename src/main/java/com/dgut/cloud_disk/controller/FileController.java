package com.dgut.cloud_disk.controller;

import com.alibaba.fastjson.JSONObject;
import com.dgut.cloud_disk.pojo.*;
import com.dgut.cloud_disk.service.*;
import com.dgut.cloud_disk.service.impl.ShareServiceImpl;
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
    @Autowired
    private DepartmentService departmentService;
    /**
     * 判断群组文件权限;有文件夹ID找文件夹表中文件夹有个所属群组ID。用用户ID和群组ID获得群组和用户映射表中权限，查看下载位置是否为1
     * @param directID 文件夹id
     * @return 权限值
     * @throws JsonProcessingException
     */
    public String checkPermission(String directID){
<<<<<<< Updated upstream

=======
>>>>>>> Stashed changes
        Directory directory = directoryService.selectDirectoryByID(directID);
        Department department = departmentService.selDepart(directory.getDirectBelongDepart());
        return department.getDepartPermission();
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
<<<<<<< Updated upstream
        //查群组id
        Directory directory = directoryService.selectDirectoryByID(newDirectID);
        //创建redis对象
        Jedis jedis = jedisPool.getResource();
        //接收token查找用户对象
        String user = jedis.get(token);
        ObjectMapper mapper = new ObjectMapper();
        CdstorageUser cdstorageUser = mapper.readValue(user, CdstorageUser.class);
        //判断权限
        if("00000100".equals(checkPermission(newDirectID))){
            return JSONResult.errorMsg("");
        }
=======

>>>>>>> Stashed changes
        //文件复制
        DirectoryFile directoryFile = directoryFileService.selectFileById(fileID);
        String uuid = UUID.randomUUID().toString().replace("-", "");
        directoryFile.setDirectFileId(uuid);
        directoryFile.setDfDirectId(newDirectID);
        directoryFileService.copyToDirect(directoryFile);
        return new JSONResult(200,"",null);
    }

    /**
     * 个人文件夹复制
     * @param jsonObject
     * @return 前端需要的json数据
     * @throws JsonProcessingException
     */
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
<<<<<<< Updated upstream
        //判断权限
        if("00000100".equals(checkPermission(newDirectID))){
            return JSONResult.errorMsg("");
        }
=======
>>>>>>> Stashed changes
        //文件夹复制
        ShareServiceImpl service = new ShareServiceImpl();
        service.copyDirectory(directID,cdstorageUser.getUserId(),newDirectID);
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
        if ("01000000".equals(checkPermission(directID))){
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
        if ("00000001".equals(checkPermission(directID))){
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
        if("00000001".equals(checkPermission(directID))){
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
        if("00000001".equals(checkPermission(directID))){
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
        if("00000001".equals(checkPermission(directID))){
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
        if("00000100".equals(checkPermission(newDirectID))){
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
    /**
     * 个人文件夹复制
     * @param jsonObject
     * @return 前端需要的json数据
     * @throws JsonProcessingException
     */
    @RequestMapping("/copydepartdilectfile")
    public JSONResult copyDepartDilectFile(@RequestBody JSONObject jsonObject) throws JsonProcessingException {
        String newDirectID = jsonObject.getString("newDirectID");
        String token = jsonObject.getString("token");
        String directID = jsonObject.getString("directID");

        //创建redis对象
        Jedis jedis = jedisPool.getResource();
        //接收token查找用户对象
        String user = jedis.get(token);
        ObjectMapper mapper = new ObjectMapper();
        CdstorageUser cdstorageUser = mapper.readValue(user, CdstorageUser.class);
        //判断权限
        if("00000100".equals(checkPermission(newDirectID))){
            return JSONResult.errorMsg("");
        }
        //文件夹复制
        ShareServiceImpl service = new ShareServiceImpl();
        service.copyDirectory(directID,cdstorageUser.getUserId(),newDirectID);
        return new JSONResult(200,"",null);
    }
}
