package com.dgut.cloud_disk.controller;

import com.alibaba.fastjson.JSONObject;
import com.dgut.cloud_disk.mapper.DirectoryMapper;
import com.dgut.cloud_disk.pojo.CdstorageUser;
import com.dgut.cloud_disk.pojo.Directory;
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

    @RequestMapping("/user/newDirectory")//个人用户新建文件夹
    public JSONResult newDirectory(@RequestBody JSONObject jsonObject) throws JsonProcessingException {
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
        Date date=new Date();
        directory.setDirectCreateTime(date);
        directory.setDirectCreateId(cdstorageUser.getUserId());
        directory.setDirectType((byte) 3);
        directory.setDirectBelongUser(cdstorageUser.getUserId());
        directory.setDirectDelete((byte) 1);
        BigDecimal bigDecimal=new BigDecimal(0);
        directory.setDirectSize(bigDecimal);//暂时初始为0

        int i = DService.insertDirectory(directory);
        if(i>0){
            return new JSONResult(200,"新建成功！","");
        }else {
            return new JSONResult(500,"新建失败！","");
        }
    }
}
