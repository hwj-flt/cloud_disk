package com.dgut.cloud_disk.controller;

import com.dgut.cloud_disk.pojo.CdstorageUser;
import com.dgut.cloud_disk.service.CdstorageUserService;
import com.dgut.cloud_disk.util.JSONResult;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/user")
public class CdstorageUserController {

    @Autowired
    private CdstorageUserService cdstorageUserService;
    @Autowired
    private JedisPool jedisPool;

    @RequestMapping("/all")
    public List<CdstorageUser> allUser(){

        System.out.println("---"+cdstorageUserService.allUser());
        return cdstorageUserService.allUser();
    }

    /*@RequestMapping("/df")
    public List<CdstorageUser> allDF(){
        //System.out.println("+++"+cdstorageUserService.);
    }*/
}
