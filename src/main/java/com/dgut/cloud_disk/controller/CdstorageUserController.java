package com.dgut.cloud_disk.controller;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.dgut.cloud_disk.pojo.CdstorageUser;
import com.dgut.cloud_disk.pojo.bo.UserBo;
import com.dgut.cloud_disk.pojo.vo.CdstorageUserVo;
import com.dgut.cloud_disk.service.CdstorageUserService;
import com.dgut.cloud_disk.util.DateUtil;
import com.dgut.cloud_disk.util.JSONResult;
import com.dgut.cloud_disk.util.SendCodeUtil;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.issCollege.util.MD5;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import com.issCollege.util.RandomChar;
import java.util.UUID;


@RestController
@RequestMapping("/cloud")
public class CdstorageUserController {

    @Autowired
    private CdstorageUserService userService;
	@Autowired
    private JedisPool jedisPool;
    @Autowired
    private SendCodeUtil util;
    @Value("${redis.defaultTokenValidTime}")
    private Integer tokenValidTime;
    @Value("${redis.defaultCodeValidTime}")
    private Integer codeValidTime;

    /**
     *用户登录
     * @return 成功返回 code状态码 ,firstLogin首次登录, token 在redis存储的键 , permission用户权限
     * @return 失败返回 code状态码 ,
     * @throws JsonProcessingException
     */
    @RequestMapping("/login")
    public JSONResult loginCdstorageUser(@RequestBody JSONObject json) throws JsonProcessingException {
        String userName = json.getString("userName");
        String userPassword = json.getString("userPassword");
        //判断用户名和密码是否一致，一致为首次登录
        int firstLogin = 0;
        if(userName.equals(userPassword)){
            firstLogin = 1;
        }
        //根据用户名查询数据库
        CdstorageUser user = userService.queryByUserMobie(userName);
        if(user == null){
            return JSONResult.errorMsg("该用户不存在");
        }
        //判断用户是否被禁用
        if(user.getUserStatus()==(byte) 0){
            return JSONResult.errorMsg("用户被禁用，请找管理员解禁");
        }
        //检查密码是否正确
        if(!user.getUserPassword().equals(MD5.stringMD5(userPassword))) {
            System.out.println(user.getUserPassword()+"/t"+MD5.stringMD5(userPassword));
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
        jedis.expire(token,tokenValidTime);
        //将前端所需数据存入json
        jsonObject.put("firstLogin",firstLogin);
        jsonObject.put("token",token);
        jsonObject.put("permission",user.getUserPermission());
        jedis.close();
        //返回前端json
        return new JSONResult(200,"",jsonObject);
    }

    /**
     * 用前端token换取手机号
     * @return 包含手机号的json字符串
     */
    @RequestMapping("/user/firstLogin/getPhone")
    public JSONResult getPhone(@RequestBody JSONObject json) throws JsonProcessingException {
        String token = json.getString("token");
        Jedis jedis = jedisPool.getResource();
        ObjectMapper mapper = new ObjectMapper();
        //从redis查出token的值
        String tokenValue = jedis.get(token);
        //将值转换为CdstorageUser对象
        CdstorageUser user = mapper.readValue(tokenValue, CdstorageUser.class);
        //将手机号转成json字符串返回
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("userPhone",user.getUserMobie());
        jedis.close();
        return new JSONResult(200,"",jsonObject);
    }

	/**
     * 用前端token换取手机验证码
     * @return 包含验证码有效时间的json字符串
     * @throws JsonProcessingException 字符串转化错误
     */
    @RequestMapping("/user/firstLogin/getPhoneCode")
    public JSONResult getPhoneCode(@RequestBody JSONObject json) throws JsonProcessingException {
        String token = json.getString("token");
        Jedis jedis = jedisPool.getResource();
        ObjectMapper mapper = new ObjectMapper();
        //从redis查出token的值
        String tokenValue = jedis.get(token);
        //将值转换为CdstorageUser对象
        CdstorageUser user = mapper.readValue(tokenValue, CdstorageUser.class);
        //查询redis是否已经有存入验证码，有则刷新验证码时间并返回重复发送的错误提示
        if (jedis.get(user.getUserMobie())!=null){
            jedis.expire(user.getUserMobie(),codeValidTime);
            return new JSONResult(500,"重复发送",null);
        }
        //生成6位数字随机数作为验证码
        String code = RandomChar.getRandomChar(6,3);
        //用手机号作为redis的key，值为验证码
        jedis.set(user.getUserMobie(),code);
        //设置有效时间，60s
        jedis.expire(user.getUserMobie(),codeValidTime);
        //向对应手机号发送验证码
        String sendMsg = util.sendMsg(user.getUserMobie(), code);
        System.out.println(sendMsg);
        // 返回有效时间
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("timegap",codeValidTime);
        jedis.close();
        return new JSONResult(200,null,jsonObject);
    }

    /**
     * 验证手机验证码并修改密码
     * userPhone 手机号
     * checkNum 验证码
     * userPassword 新密码
     * @return 返回操作结果
     */
    @RequestMapping("/user/firstLogin/checkPhoneCode")
    public JSONResult checkPhoneCode(@RequestBody JSONObject json) throws JsonProcessingException {
        String userPhone = json.getString("userPhone");
        String checkNum = json.getString("checkNum");
        String userPassword = json.getString("userPassword");
        String token = json.getString("token");
        Jedis jedis = jedisPool.getResource();
        //从redis查出验证码
        String phoneCode = jedis.get(userPhone);
        //不存在即过期
        if (phoneCode==null){
            return new JSONResult(500,"验证码过期",null);
        }
        //验证码校验
        if (!checkNum.equals(phoneCode)){
            return new JSONResult(500,"验证码错误",null);
        }
        //修改密码
        if (!userService.updateUserPassword(userPhone,MD5.stringMD5(userPassword))){
            return new JSONResult(500,"修改密码失败",null);
        }
        //更新redis里的token的值
        CdstorageUser user = userService.queryByUserMobie(userPhone);
        ObjectMapper mapper = new ObjectMapper();
        jedis.set(token,mapper.writeValueAsString(user));
        jedis.close();
        return new JSONResult(200,"登录成功",null);
    }

   /**
     * 验证手机号正确性，根据手机号发送验证码
     * userPhone 手机号
     * @return 包含验证码有效时间的json字符串
     */
    @RequestMapping("/forgetPassword/sendPhoneCode")
    public JSONResult sendPhoneCode(@RequestBody JSONObject json){
        String userPhone = json.getString("userPhone");
        //根据电话号码查询数据库,有值就存在该用户,无则不存在
        CdstorageUser user = userService.queryByUserMobie(userPhone);
        if (user==null) {
            //不正确返回json
            return JSONResult.errorMsg("手机号错误");
        }
        Jedis jedis = jedisPool.getResource();
        //查询redis是否已经有存入验证码，有则刷新验证码时间并返回重复发送的错误提示
        if (jedis.get(userPhone)!=null){
            jedis.expire(user.getUserMobie(),codeValidTime);
            return new JSONResult(500,"重复发送",null);
        }
        JSONObject jsonObject = new JSONObject();
        //生成6位数字随机数作为验证码
        String code = RandomChar.getRandomChar(6,3);
        //用手机号作为redis的key，值为验证码
        jedis.set(user.getUserMobie(),code);
        //设置有效时间，60s
        jedis.expire(user.getUserMobie(),codeValidTime);
        //向对应手机号发送验证码
        String sendMsg = util.sendMsg(user.getUserMobie(), code);
        jsonObject.put("timegap",codeValidTime);
        jedis.close();
        return new JSONResult(200, "", jsonObject);
    }
   /**
     * 验证手机验证码并修改密码
     * userPhone 手机号
     * checkNum 验证码
     * userPassword 新密码
     * @return 返回操作结果
     */
    @RequestMapping("/forgetPassword/checkPhoneCode")
    public JSONResult checkPhoneCode1(@RequestBody JSONObject json) {
        String userPhone = json.getString("userPhone");
        String checkNum = json.getString("checkNum");
        Jedis jedis = jedisPool.getResource();
        //从redis查出验证码
        String phoneCode = jedis.get(userPhone);
        jedis.close();
        //不存在即过期
        if (phoneCode==null){
            return new JSONResult(500,"验证码过期",null);
        }
        //验证码校验
        if (!checkNum.equals(phoneCode)){
            return new JSONResult(500,"验证码错误",null);
        }else {
            return new JSONResult(200,"验证码正确",null);
        }

    }
    @RequestMapping("/forgetPassword/updatePass")
    public JSONResult updatePass(@RequestBody JSONObject json){
        String userPassword = json.getString("userPassword");
        String userPhone = json.getString("userPhone");
        //修改密码
        if (!userService.updateUserPassword(userPhone,MD5.stringMD5(userPassword))){
            return new JSONResult(500,"修改密码失败",null);
        }else {
            return new JSONResult(200,"修改成功",null);
        }
    }
	/**
     * 退出登录
     * @return 操作结果
     */
    @RequestMapping({"/user/logout"})
    public JSONResult logout(@RequestBody JSONObject json){
        String token = json.getString("token");
        Jedis jedis = jedisPool.getResource();
        try {
            jedis.del(token);
        }catch (Exception e){
            e.printStackTrace();
            return new JSONResult(500,"参数错误",null);
        }
        jedis.close();
        return new JSONResult(200,"退出成功",null);
    }

	/**
     * 根据token返回用户信息
     * @return 返回用户信息
     */
    @RequestMapping({"/user/userInfo"})
    public JSONResult userInfo(@RequestBody JSONObject json) throws JsonProcessingException {
        String token = json.getString("token");
        Jedis jedis = jedisPool.getResource();
        String tokenValue = jedis.get(token);
        jedis.close();
        if (tokenValue == null){
            return new JSONResult(500,"参数错误", null);
        }
        ObjectMapper mapper = new ObjectMapper();
        CdstorageUser user = mapper.readValue(tokenValue, CdstorageUser.class);
        user.setUserSize(user.getUserSize().divide(new BigDecimal(1000)));
        user.setUserUsed(user.getUserUsed().divide(new BigDecimal(1000)));
        UserBo userBo = new UserBo();
        UserBo userbo = userBo.userBo(user);
        return new JSONResult(userbo);
    }

   /**
     *修改手机号或邮箱
     * @return 操作结果
     * @throws JsonProcessingException 字符串转化错误
     */
    @RequestMapping({"/user/updateUserInfo"})
    public JSONResult updateUserInfo(@RequestBody JSONObject json) throws JsonProcessingException {
        String token = json.getString("token");
        Jedis jedis = jedisPool.getResource();
        ObjectMapper objectMapper = new ObjectMapper();
        String tokenValue = jedis.get(token);
        CdstorageUser user = objectMapper.readValue(tokenValue, CdstorageUser.class);
        user.setUserMobie(json.getString("userMobie"));
        user.setUserEmail(json.getString("userEmail"));
        //修改用户信息
        if(!userService.updateUser(user)){
            return JSONResult.errorMsg("修改失败");
        }
        //重新查出用户信息，并更新redis里存入的用户信息
        CdstorageUser user1 = userService.queryByUserMobie(user.getUserMobie());
        jedis.set(token,objectMapper.writeValueAsString(user1));
        jedis.expire(token,tokenValidTime);
        jedis.close();
        return JSONResult.ok("修改成功");
    }

    /**
     * 用户修改密码
     * @param json 前端传token和新密码
     * @return 返回操作结果
     * @throws JsonProcessingException JsonProcessingException
     */
    @RequestMapping({"/user/updatePass"})
    public JSONResult updateUserPass(@RequestBody JSONObject json) throws JsonProcessingException {
        String userPassword = json.getString("userPassword");
        String token = json.getString("token");
        Jedis jedis = jedisPool.getResource();
        ObjectMapper mapper = new ObjectMapper();
        String tokenValue = jedis.get(token);
        CdstorageUser user = mapper.readValue(tokenValue, CdstorageUser.class);
       jedis.close();
        //修改密码
        if (!userService.updateUserPassword(user.getUserMobie(),MD5.stringMD5(userPassword))){
            return new JSONResult(500,"修改密码失败",null);
        }else {
            return new JSONResult(200,"修改成功",null);
        }
    }
    @RequestMapping("/user/manage/sup/allManages")
    public JSONResult allManages(){
        System.out.println(userService.allManages());
        List<CdstorageUser> cdstorageUsers=userService.allManages();
        JSONArray jsonArray = (JSONArray) JSONArray.toJSON(cdstorageUsers);
        return new JSONResult(JSONResult.build(200,null,jsonArray));
    }

    @PostMapping("/user/manage/sup/addManage")
    @ResponseBody
    @CrossOrigin
    public JSONResult addManage(@RequestBody CdstorageUser cdstorageUser){
        if(userService.addManage(cdstorageUser.getUserId())){
            return new JSONResult(JSONResult.ok());
        }
        else{
            return new JSONResult(JSONResult.errorMsg("访问无权限"));
        }
    }


    @PostMapping("/user/manage/sup/delManage")
    @ResponseBody
    @CrossOrigin
    public JSONResult delManage(@RequestBody CdstorageUser cdstorageUser) {
        if (userService.delManage(cdstorageUser.getUserId())) {
            return new JSONResult(JSONResult.build(200, null, null));
        } else {
            return new JSONResult(JSONResult.errorMsg("访问无权限"));
        }
    }
}
