package com.dgut.cloud_disk.controller;

import com.alibaba.fastjson.JSONObject;
import com.dgut.cloud_disk.pojo.CdstorageUser;
import com.dgut.cloud_disk.service.CdstorageUserService;
import com.dgut.cloud_disk.util.JSONResult;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

import java.util.UUID;

@RestController
@RequestMapping("/cloud")
public class CdstorageUserController {
    @Autowired
    private CdstorageUserService userService;
    @Autowired
    private JedisPool jedisPool;
    @Value("${redis.defaultTokenValidTime}")
    private Integer valid_time;

    /**
     *用户登录
     * @param userName  登录用户名
     * @param userPassword  登录密码
     * @return 成功返回 code状态码 ,firstLogin首次登录, token 在redis存储的键值 , permission用户权限
     * @return 失败返回 code状态码 ,
     * @throws JsonProcessingException
     */
    @RequestMapping("/login")
    public JSONResult loginCdstorageUser(String userName,String userPassword) throws JsonProcessingException {
        //判断用户名和密码是否一致，一致为首次登录
        int firstLogin = 0;
        if(userName.equals(userPassword)){
            firstLogin = 1;
        }
        //根据用户名查询数据库
        CdstorageUser user = userService.queryByUserName(userName);
        if(user == null){
            return JSONResult.errorMsg("该用户不存在");
        }
        //检查密码是否正确
        if(!user.getUserPassword().equals(userPassword)) {
            return JSONResult.errorMsg("密码错误");
        }
        //创建所需对象
        Jedis jedis = jedisPool.getResource();
        JSONObject jsonObject = new JSONObject();
        ObjectMapper mapper = new ObjectMapper();
        //将查出的user转为字符串
        String jsonRst = mapper.writeValueAsString(user);
        //用UUID生成token
        String token = UUID.randomUUID().toString().replace("-", "");
        //以token为键，user字符串为值写入redis并设置有效时间
        jedis.set(token,jsonRst);
        jedis.expire(token,valid_time);
        //将前端所需数据存入json
        jsonObject.put("firstLogin",firstLogin);
        jsonObject.put("token",token);
        jsonObject.put("permission",user.getUserPermission());
        jedis.close();
        //返回前端json
        return new JSONResult(200,"",jsonObject);
    }
}
