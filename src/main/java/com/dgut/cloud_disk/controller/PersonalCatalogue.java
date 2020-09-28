package com.dgut.cloud_disk.controller;

import com.dgut.cloud_disk.pojo.CdstorageUser;
import com.dgut.cloud_disk.pojo.bo.DirectoryVo;
import com.dgut.cloud_disk.pojo.vo.TokenVo;
import com.dgut.cloud_disk.service.PersonalCatalogueService;
import com.dgut.cloud_disk.util.JSONResult;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

@RestController
@RequestMapping("/cloud/user/")
public class PersonalCatalogue {

    @Autowired(required = false)
    private PersonalCatalogueService personalCatalogueService;

    @Autowired
    private JedisPool jedisPool;

    @RequestMapping("/userCatalogue")
    public JSONResult userCatalogue(@RequestBody TokenVo tokenVo) throws Exception {

        //获取用户的根目录ID
        jedisPool.getResource();
        Jedis jedis = jedisPool.getResource();
        String tokenValue = jedis.get(tokenVo.getToken());
        System.out.println(tokenVo.getToken());
        System.out.println(tokenValue);
        if (tokenValue==null){
            throw  new Exception("请登录");
        }
        jedis.close();
        ObjectMapper mapper = new ObjectMapper();
        CdstorageUser user = mapper.readValue(tokenValue, CdstorageUser.class);
        String directoryRootId = user.getUserRootId();
        //使用根目录ID获取文件结构目录
        DirectoryVo directoryVo = personalCatalogueService.getAllCatalogue(directoryRootId);
        return  JSONResult.ok(directoryVo);
    }
}
