package com.dgut.cloud_disk.controller;

import com.dgut.cloud_disk.pojo.CdstorageUser;
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
    @GetMapping("/all")
    @ResponseBody
    public List<CdstorageUser> allUser(){
        return cdstorageUserService.allUser();
    }

    /**
     * 用户禁用
     * @param cdstorageUser
     * @return
     */
    @PostMapping("/upStatus")
    @CrossOrigin
    @ResponseBody
    public JSONResult updateUserStatus(@RequestBody CdstorageUser cdstorageUser){
        int num = cdstorageUserService.updateUserStatus(cdstorageUser);
        if (num > 0){
            return new JSONResult(200,"成功禁用用户",null);
        }else {
            return new JSONResult();
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
            cdstorageUserService.updateUserStatus(user);
        }
        return new JSONResult();
    }

    /**
     * 用户解禁
     * @param cdstorageUser
     * @return
     */
    @PostMapping("/upStatus1")
    @CrossOrigin
    @ResponseBody
    public JSONResult updateUserStatus1(@RequestBody CdstorageUser cdstorageUser){
        int num = cdstorageUserService.updateUserStatus1(cdstorageUser);
        if (num > 0){
            return new JSONResult(200,"成功解禁用户",null);
        }else {
            return new JSONResult();
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
            cdstorageUserService.updateUserStatus1(user);
        }
        return new JSONResult();
    }

    /**
     * 更新用户（邮箱、手机号）
     * @param cdstorageUser
     * @return
     */
    @PostMapping("/upUser")
    @CrossOrigin
    @ResponseBody
    public JSONResult updateUser(@RequestBody CdstorageUser cdstorageUser){
        int num = cdstorageUserService.updateUser(cdstorageUser);
        return null;
    }
}
