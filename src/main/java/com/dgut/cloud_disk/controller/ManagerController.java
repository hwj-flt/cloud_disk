package com.dgut.cloud_disk.controller;

import com.alibaba.fastjson.JSONObject;
import com.dgut.cloud_disk.pojo.CdstorageUser;
import com.dgut.cloud_disk.pojo.Department;
import com.dgut.cloud_disk.pojo.DepartmentUser;
import com.dgut.cloud_disk.pojo.Directory;
import com.dgut.cloud_disk.pojo.vo.CdstorageUserVo;
import com.dgut.cloud_disk.pojo.vo.DepUserVo;
import com.dgut.cloud_disk.service.CdstorageUserService;
import com.dgut.cloud_disk.service.DirectoryService;
import com.dgut.cloud_disk.service.ManagerService;
import com.dgut.cloud_disk.util.JSONResult;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/cloud/user/manage")
public class ManagerController {
    @Autowired
    public ManagerService managerService;
    @Autowired
    private CdstorageUserService userService;
    @Autowired
    private DirectoryService directoryService;
    @Autowired
    private JedisPool jedisPool;
    /**
     * 查询所有用户
     * @return
     */
    @PostMapping("/all")
    @CrossOrigin
    @ResponseBody
    public JSONResult allUser(){
        return new JSONResult(200,"用户列表",userService.allUser());
    }
    /**
     * 用户禁用
     * @param object
     * @return
     */
    @PostMapping("/upStatus")
    @CrossOrigin
    @ResponseBody
    @Transactional/*(noRollbackFor = ArithmeticException.class)*/
    public JSONResult updateUserStatus(@RequestBody JSONObject object){
        String token =object.getString("token");
        String userID = object.getString("userID");
        int num = userService.updateUserStatus(token,userID);
//        int a = 1/0;
        if (num > 0){
            return new JSONResult(200,"成功禁用用户",null);
        }else {
            return new JSONResult(400,"禁用用户失败",null);
        }
    }
    /**
     * 批量用户禁用
     * @param cdstorageUsers
     * @return
     */
    @PostMapping("/upUsStatus")
    @CrossOrigin
    @ResponseBody
    @Transactional
    public JSONResult updateUsersStatus(@RequestBody List<CdstorageUser> cdstorageUsers){
        for (CdstorageUser user:cdstorageUsers) {
            userService.updateUserStatus(null,user.getUserId());
        }
        return new JSONResult(200,"成功批量禁用用户",null);
    }
    /**
     * 用户解禁
     * @param object
     * @return
     */
    @PostMapping("/upStatus1")
    @CrossOrigin
    @ResponseBody
    @Transactional
    public JSONResult updateUserStatus1(@RequestBody JSONObject object){
        String token =object.getString("token");
        String userID = object.getString("userID");
        int num = userService.updateUserStatus1(token,userID);
        if (num > 0){
            return new JSONResult(200,"成功解禁用户",null);
        }else {
            return new JSONResult(400,"解禁用户失败",null);
        }
    }
    /**
     * 批量用户解禁
     * @param cdstorageUsers
     * @return
     */
    @PostMapping("/upUsStatus1")
    @CrossOrigin
    @ResponseBody
    @Transactional
    public JSONResult updateUsersStatus1(@RequestBody List<CdstorageUser> cdstorageUsers){
        for (CdstorageUser user:cdstorageUsers) {
            userService.updateUserStatus1(null,user.getUserId());
        }
        return new JSONResult(200,"成功批量解禁用户",null);
    }

    /**
     * 更新用户信息
     * @param cdstorageUserVo
     * @return
     * @throws JsonProcessingException
     */
    @PostMapping("/upUser")
    @CrossOrigin
    @ResponseBody
    @Transactional
    public JSONResult updateUser(@RequestBody CdstorageUserVo cdstorageUserVo) throws JsonProcessingException {
        //从token中获取用户
        Jedis jedis = jedisPool.getResource();
        String tokenValue = jedis.get(cdstorageUserVo.getToken());
        ObjectMapper objectMapper=new ObjectMapper();
        //管理员
        CdstorageUser cdstorageUser = objectMapper.readValue(tokenValue, CdstorageUser.class);
        //被修改用户
        CdstorageUser cdstorageUser1 = userService.selByUserId(cdstorageUserVo.getUserId());
        if(cdstorageUser.getUserPermission() != 1){
            if (!cdstorageUserVo.getUserMobie().equals(cdstorageUser1.getUserMobie())){
                if (userService.selByUserMobie(cdstorageUserVo.getUserMobie()) != null) {
                    return new JSONResult(500,"用户电话号码已经存在",null);
                }else {
                    //连用户电话也更新
                    int num = userService.updateUser1(cdstorageUserVo);
                    if(num>0){
                        return new JSONResult(200,"更新用户成功",null);
                    }else{
                        return new JSONResult(500,"更新用户失败",null);
                    }
                }
            }else {
                //用户电话没变
                int num = userService.updateUser1(cdstorageUserVo);
                if(num>0){
                    return new JSONResult(200,"更新用户成功",null);
                }else{
                    return new JSONResult(500,"更新用户失败",null);
                }
            }
        }else {
            return new JSONResult(500,"无访问权限",null);
        }
    }
    /**
     * 用户名进行查询
     * @param object
     * @return
     */
    @PostMapping("/selByName")
    @CrossOrigin
    @ResponseBody
    public JSONResult selByUserName(@RequestBody JSONObject object){
        String token =object.getString("token");
        String userName = object.getString("userName");
        List<CdstorageUser> users = userService.selByUserName(token,userName);
        return new JSONResult(200,"success",users);
    }
    /**
     * 用工号进行查询
     * @param object
     * @return
     */
    @PostMapping("/selByWorkId")
    @CrossOrigin
    @ResponseBody
    public JSONResult selByWorkId(@RequestBody JSONObject object){
        String token =object.getString("token");
        String userWorkId = object.getString("userWorkID");
        List<CdstorageUser> users = userService.selByWorkId(token,userWorkId);
        return new JSONResult(200,"success",users);
    }
    /**
     * 用手机号进行查询
     * @param object
     * @return
     */
    @PostMapping("/selByTel")
    @CrossOrigin
    @ResponseBody
    public JSONResult selByUserPhone(@RequestBody JSONObject object){
        String token =object.getString("token");
        String userPhone = object.getString("userPhone");
        List<CdstorageUser> users = userService.selByUserPhone(token,userPhone);
        return new JSONResult(200,"success",users);
    }

    /**
     * 创建部门及部门根目录文件夹
     * @param object
     * @return
     * @throws JsonProcessingException
     */
    @PostMapping("/addDepart")
    @CrossOrigin
    @ResponseBody
    @Transactional
    public JSONResult addDepartment(@RequestBody JSONObject object) throws JsonProcessingException {
        String departId = UUID.randomUUID().toString().replaceAll("-","");
        String departRoot = UUID.randomUUID().toString().replaceAll("-","");
        String token =object.getString("token");
        String departName = object.getString("departName");
        //从token中获取用户
        Jedis jedis = jedisPool.getResource();
        String tokenValue = jedis.get(token);
        ObjectMapper objectMapper=new ObjectMapper();
        CdstorageUser cdstorageUser = objectMapper.readValue(tokenValue, CdstorageUser.class);
        //部门实体
        Department department = new Department(departId,departName,departRoot,new Date(),"00000000");
        //文件夹实体
        Directory directory = new Directory(departRoot,null,departName,new Date(),
                cdstorageUser.getUserId(), (byte)2,null,departId,(byte)1,
                new BigDecimal(0),null);
        //判断权限
        if (cdstorageUser.getUserPermission() != 1){
            //创建部门
            managerService.addDepartment(department);
            //创建部门根目录文件夹
            directoryService.insertDirectory(directory);
            //文档管理员加入部门
            List<String> a= new ArrayList<String>();
            a.add(cdstorageUser.getUserId());
            managerService.addUserToDepart(departId, a);
            return new JSONResult(200,"部门创建成功",null);
        }else {
            return new JSONResult(500,"无访问权限",null);
       }
    }

    /**
     * 添加用户到群组
     * @param depUserVo
     * @return
     * @throws JsonProcessingException
     */
    @PostMapping("/addUserToDep")
    @CrossOrigin
    @ResponseBody
    @Transactional
    public JSONResult addDepartment(@RequestBody DepUserVo depUserVo) throws JsonProcessingException {
        //从token中获取用户
        Jedis jedis = jedisPool.getResource();
        String tokenValue = jedis.get(depUserVo.getToken());
        ObjectMapper objectMapper=new ObjectMapper();
        CdstorageUser cdstorageUser = objectMapper.readValue(tokenValue, CdstorageUser.class);
        //判断权限
        if (cdstorageUser.getUserPermission() != 1){
            Boolean boo = managerService.addUserToDepart(depUserVo.getDepartId(),depUserVo.getUserIds());
            if (boo){
                return new JSONResult(200,"添加成功",null);
            }else {
                return new JSONResult(204,"添加失败",null);
            }
        }else {
            return new JSONResult(500,"无访问权限",null);
        }
    }

    /**
     * 设置群组权限
     * @param jsonObject 前端传回的token，departID，departPermission
     * @return
     * @throws JsonProcessingException
     */
    @PostMapping("/setDepPerm")
    @CrossOrigin
    @ResponseBody
    @Transactional
    public JSONResult setDepPerm(@RequestBody JSONObject jsonObject) throws JsonProcessingException {
        String token =jsonObject.getString("token");
        String departId =jsonObject.getString("departID");
        String departPermission =jsonObject.getString("departPermission");
        //从token中获取用户
        Jedis jedis = jedisPool.getResource();
        String tokenValue = jedis.get(token);
        ObjectMapper objectMapper=new ObjectMapper();
        CdstorageUser cdstorageUser = objectMapper.readValue(tokenValue, CdstorageUser.class);
        if (cdstorageUser.getUserPermission() != 1){
            Boolean boo = managerService.setDepPerm(departId,departPermission);
            if (boo){
                return new JSONResult(200,"成功设置权限",null);
            }else {
                return new JSONResult(204,"设置权限失败",null);
            }
        }else {
            return new JSONResult(500,"无访问权限",null);
        }
    }

    /**
     * 所有部门
     * @param token
     * @return
     * @throws JsonProcessingException
     */
    @GetMapping("/allDepart")
    @CrossOrigin
    @ResponseBody
    public JSONResult allDepart(@Param("token") String token) throws JsonProcessingException {
        System.out.println(token);
        //从token中获取用户
        Jedis jedis = jedisPool.getResource();
        String tokenValue = jedis.get(token);
        ObjectMapper objectMapper=new ObjectMapper();
        CdstorageUser cdstorageUser = objectMapper.readValue(tokenValue, CdstorageUser.class);
        if (cdstorageUser.getUserPermission() != 1){
            List<Department> departments = managerService.allDepart();
            return new JSONResult(200,"部门列表",departments);
        }else {
            return new JSONResult(500,"无访问权限",null);
        }
    }

    /**
     * 查询部门用户
     * @param jsonObject
     * @return
     * @throws JsonProcessingException
     */
    @PostMapping("/selUserAtDep")
    @CrossOrigin
    @ResponseBody
    public JSONResult selUserAtDep(@RequestBody JSONObject jsonObject) throws JsonProcessingException {
        String token =jsonObject.getString("token");
        String departId =jsonObject.getString("departID");
        //从token中获取用户
        Jedis jedis = jedisPool.getResource();
        String tokenValue = jedis.get(token);
        ObjectMapper objectMapper=new ObjectMapper();
        CdstorageUser cdstorageUser = objectMapper.readValue(tokenValue, CdstorageUser.class);
        if (cdstorageUser.getUserPermission() != 1){
            List<CdstorageUser> users = managerService.selUserAtDep(departId);
            if (users != null){
                return new JSONResult(200,"部门内用户列表",users);
            }else {
                return new JSONResult(204,"该部门内用户为空",null);
            }
        }else {
            return new JSONResult(500,"无访问权限",null);
        }
    }

    /**
     * 把组员移出群组
     * @param depUserVo
     * @return
     * @throws JsonProcessingException
     */
    @PostMapping("/delDepartUserByIds")
    @CrossOrigin
    @ResponseBody
    @Transactional
    public JSONResult delDepartUserByIds(@RequestBody DepUserVo depUserVo) throws JsonProcessingException {
        //从token中获取用户
        Jedis jedis = jedisPool.getResource();
        String tokenValue = jedis.get(depUserVo.getToken());
        ObjectMapper objectMapper=new ObjectMapper();
        CdstorageUser cdstorageUser = objectMapper.readValue(tokenValue, CdstorageUser.class);
        //判断权限
        if (cdstorageUser.getUserPermission() != 1){
            Boolean boo = managerService.delDepartUser(depUserVo.getDepartId(),depUserVo.getUserIds());
            if (boo){
                return new JSONResult(200,"移除成功",null);
            }else {
                return new JSONResult(204,"移除失败",null);
            }
        }else {
            return new JSONResult(500,"无访问权限",null);
        }
    }

}
