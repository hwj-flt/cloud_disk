package com.dgut.cloud_disk.controller;

import com.alibaba.fastjson.JSONObject;
import com.dgut.cloud_disk.mapper.DirectoryMapper;
import com.dgut.cloud_disk.pojo.CdstorageUser;
import com.dgut.cloud_disk.pojo.DepartmentUser;
import com.dgut.cloud_disk.pojo.Directory;
import com.dgut.cloud_disk.service.DepartmentUserService;
import com.dgut.cloud_disk.service.DirectoryService;
import com.dgut.cloud_disk.util.JSONResult;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.Date;
import java.util.UUID;

@RestController
@RequestMapping("/cloud")
public class DirectoryController {
    @Autowired
    private JedisPool jedisPool;
    @Resource
    private DirectoryService DService;
    @Resource
    private DepartmentUserService DUService;

    @RequestMapping("/user/newDirectory")//个人用户新建文件夹
    public JSONResult newDirectory(@RequestBody JSONObject jsonObject) throws JsonProcessingException {
        String token = jsonObject.getString("token");
        String parentDirectID =jsonObject.getString("parentDirectID");
        String directName = jsonObject.getString("directName");
        System.out.println(token+parentDirectID+directName);
        //从token中获取创建人id
        Jedis jedis = jedisPool.getResource();
        String tokenValue = jedis.get(token);
        ObjectMapper objectMapper=new ObjectMapper();
        CdstorageUser cdstorageUser = objectMapper.readValue(tokenValue, CdstorageUser.class);

        Directory directory=new Directory();
        directory.setDirectId(UUID.randomUUID().toString().replace("-", ""));
        directory.setParentDirectId(parentDirectID);
        directory.setDirectName(directName);
        /*Date date=new Date();//数据库会自动打时间戳
        directory.setDirectCreateTime(date);*/
        directory.setDirectCreateId(cdstorageUser.getUserId());
        directory.setDirectType((byte) 3);
        directory.setDirectBelongUser(cdstorageUser.getUserId());
        directory.setDirectDelete((byte) 1);
        BigDecimal bigDecimal=new BigDecimal(0);
        directory.setDirectSize(bigDecimal);//暂时初始为0

        Boolean boo = DService.insertDirectory(directory);
        if(boo){
            return new JSONResult(200,"新建成功！","");
        }else {
            return new JSONResult(500,"新建失败！","");
        }
    }

    @RequestMapping("/user/newDepartDirectory")//群组中新建文件夹
    public JSONResult newDepartDirectory(@RequestBody JSONObject jsonObject) throws JsonProcessingException {
        //从父文件夹获取所属群组id，再用创建用户的id从d——u表中获取权限

        String token = jsonObject.getString("token");
        String parentDirectID =jsonObject.getString("parentDirectID");
        String directName = jsonObject.getString("directName");

        //从token中获取创建人id
        Jedis jedis = jedisPool.getResource();
        String tokenValue = jedis.get(token);
        ObjectMapper objectMapper=new ObjectMapper();
        CdstorageUser cdstorageUser = objectMapper.readValue(tokenValue, CdstorageUser.class);

        Directory directory=new Directory();
        directory.setDirectId(UUID.randomUUID().toString().replace("-", ""));
        directory.setParentDirectId(parentDirectID);
        directory.setDirectName(directName);
       /* Date date=new Date();//数据库会自动打时间戳
        directory.setDirectCreateTime(date);*/
        directory.setDirectCreateId(cdstorageUser.getUserId());
        directory.setDirectType((byte) 4);

        //DUService.selectduPermissionByid();
        directory.setDirectBelongDepart(DUService.getDepartIDByid(cdstorageUser.getUserId()));
        directory.setDirectDelete((byte) 1);
        BigDecimal bigDecimal=new BigDecimal(0);
        directory.setDirectSize(bigDecimal);//暂时初始为0

        DepartmentUser Duser=DUService.selectduPermissionByid(cdstorageUser.getUserId(),DUService.getDepartIDByid(cdstorageUser.getUserId()));
//        String dePermission=Duser.getDuPermission();
//        String p=dePermission.substring(3,4);//截取第四位（即新建文件夹的权限）
        //System.out.println("++++"+p);
        int i=0;
//        if(p.equals("1")){//判断为1说明有该权限
//            i = DService.insertDirectory(directory);
//        }else{
//            System.out.println("无相应权限！");
//        }
        //int i = DService.insertDirectory(directory);
        if(i>0){
            return new JSONResult(200,"新建成功！","");
        }else {
            return new JSONResult(500,"新建失败！","");
        }
    }
}
