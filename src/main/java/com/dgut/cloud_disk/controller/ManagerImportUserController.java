package com.dgut.cloud_disk.controller;

import com.dgut.cloud_disk.exception.ParameterException;
import com.dgut.cloud_disk.pojo.CdstorageUser;
import com.dgut.cloud_disk.pojo.vo.ImportUserVo;
import com.dgut.cloud_disk.pojo.vo.TokenVo;
import com.dgut.cloud_disk.service.ManagerImportUserService;
import com.dgut.cloud_disk.service.impl.ManagerImportUserServiceImpl;
import com.dgut.cloud_disk.util.JSONResult;
import com.dgut.cloud_disk.util.ReadExcel;
import com.dgut.cloud_disk.util.Verify;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpRequest;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

import javax.servlet.http.HttpServletRequest;
import java.io.*;
import java.util.List;

@RestController
@RequestMapping("/cloud/user/manage/")
public class ManagerImportUserController {

    @Autowired(required = false)
    private HttpServletRequest request;

    @Autowired
    private ManagerImportUserService managerImportUserService;

    @Autowired
    private JedisPool jedisPool;

    //指定上传的位置为 d:/upload/
    @Value("${upload.path}")
    private String path;


    @RequestMapping("/importsingle")
    public JSONResult ImportSingleUser(@RequestBody ImportUserVo userVo) throws Exception{
        //验证数据是否为空
        if(userVo.getUser().getUserName()==null||"".equals(userVo.getUser().getUserName())){
            throw new ParameterException("参数为空");
        }
        if(userVo.getUser().getUserEmail()==null||"".equals(userVo.getUser().getUserEmail())){
            throw new ParameterException("参数为空");
        }
        if(userVo.getUser().getUserPhone()==null||"".equals(userVo.getUser().getUserPhone())){
            throw new ParameterException("参数为空");
        }
        if(userVo.getUser().getUserSex()==null||"".equals(userVo.getUser().getUserSex())){
            throw new ParameterException("参数为空");
        }
        //验证邮箱格式
        if(!Verify.verifyEmail(userVo.getUser().getUserEmail())) {
            throw new ParameterException("邮箱错误");
        }//验证手机格式
        else if(!Verify.verifyPhone(userVo.getUser().getUserPhone())){
            throw new ParameterException("电话错误");
        }//验证性别
        else if(!userVo.getUser().getUserSex().equals("男")&&!userVo.getUser().getUserSex().equals("女")){
            throw new ParameterException("性别参数错误");
        }
        //将数据插入数据库
        managerImportUserService.insertUser(userVo.getUser());
        //返回前端json
        return new JSONResult(200,"",null);
    }


    @RequestMapping("/importservals")
    public JSONResult ImportUsers(@RequestParam("file") MultipartFile file) throws Exception{
        String token = request.getHeader("token");
        if (file.isEmpty()) {
            throw new ParameterException("参数为空");
        }
        managerImportUserService.storageFile(file,token);

        jedisPool.getResource();
        Jedis jedis = jedisPool.getResource();
        String tokenValue = jedis.get(token);
        jedis.close();
        ObjectMapper mapper = new ObjectMapper();
        CdstorageUser user = mapper.readValue(tokenValue, CdstorageUser.class);
        String workID = user.getUserWorkId().toString();
        File file1 = new File(path + workID+".xls");
        if(!file1.exists()){
            throw new ParameterException("找不到上传的xls");
        }
        List<ImportUserVo.UserVo> list = ReadExcel.readExcelToUser(file1);
        managerImportUserService.insertUsers(list);
        //返回前端json
        return new JSONResult(200,"",null);
    }
}
