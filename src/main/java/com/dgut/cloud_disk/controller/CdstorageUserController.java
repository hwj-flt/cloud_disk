package com.dgut.cloud_disk.controller;

import com.alibaba.fastjson.JSONObject;
import com.dgut.cloud_disk.pojo.CdstorageUser;
import com.dgut.cloud_disk.pojo.vo.CdstorageUserVo;
import com.dgut.cloud_disk.service.CdstorageUserService;
import com.dgut.cloud_disk.util.JSONResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/user")
public class CdstorageUserController {

    @Autowired
    private CdstorageUserService cdstorageUserService;

    /**
     * 查询所有用户
     * @return
     */
    @PostMapping("/all")
    @ResponseBody
    public JSONResult allUser(@RequestBody JSONObject object){
        Integer pageNum = new Integer(object.getString("pageNum"));
        Integer pageSize = new Integer(object.getString("pageSize"));
        Integer showDisableUser = new Integer(object.getString("showDisableUser"));
        return new JSONResult(200,"用户列表",cdstorageUserService.allUser(pageNum,pageSize,showDisableUser));
    }

    /**
     * 用户禁用
     * @param object
     * @return
     */
    @PostMapping("/upStatus")
    @CrossOrigin
    @ResponseBody
    public JSONResult updateUserStatus(@RequestBody JSONObject object){
        String token =object.getString("token");
        String userID = object.getString("userID");
        int num = cdstorageUserService.updateUserStatus(token,userID);
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
    public JSONResult updateUsersStatus(@RequestBody List<CdstorageUser> cdstorageUsers){
        for (CdstorageUser user:cdstorageUsers) {
            cdstorageUserService.updateUserStatus(null,user.getUserId());
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
    public JSONResult updateUserStatus1(@RequestBody JSONObject object){
        String token =object.getString("token");
        String userID = object.getString("userID");
        int num = cdstorageUserService.updateUserStatus1(token,userID);
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
    public JSONResult updateUsersStatus1(@RequestBody List<CdstorageUser> cdstorageUsers){
        for (CdstorageUser user:cdstorageUsers) {
            cdstorageUserService.updateUserStatus1(null,user.getUserId());
        }
        return new JSONResult(200,"成功批量解禁用户",null);
    }

    /**
     * 更新用户信息
     * @param cdstorageUserVo
     * @return
     */
    @PostMapping("/upUser")
    @CrossOrigin
    @ResponseBody
    public JSONResult updateUser(@RequestBody CdstorageUserVo cdstorageUserVo){
        int num = cdstorageUserService.updateUser(cdstorageUserVo);
        if(num>0){
            return new JSONResult(200,"更新用户成功",null);
        }else {
            return new JSONResult(400,"更新用户失败",null);
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
        List<CdstorageUser> users = cdstorageUserService.selByUserName(token,userName);
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
        System.out.println(object);
        String token =object.getString("token");
        String userWorkId = object.getString("userWorkID");
        System.out.println(userWorkId);
        List<CdstorageUser> users = cdstorageUserService.selByWorkId(token,userWorkId);
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
        List<CdstorageUser> users = cdstorageUserService.selByUserPhone(token,userPhone);
        return new JSONResult(200,"success",users);
    }
}
