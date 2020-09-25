package com.dgut.cloud_disk.controller;

import com.alibaba.fastjson.JSONObject;
import com.dgut.cloud_disk.pojo.CdstorageUser;
import com.dgut.cloud_disk.pojo.DepartmentUser;
import com.dgut.cloud_disk.pojo.vo.CdstorageUserVo;
import com.dgut.cloud_disk.service.CdstorageUserService;
import com.dgut.cloud_disk.service.ManagerService;
import com.dgut.cloud_disk.util.JSONResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/cloud/user/manage")
public class ManagerController {
    @Autowired
    public ManagerService managerService;
    @Autowired
    private CdstorageUserService userService;
    /**
     * 查询所有用户
     * @return
     */
    @PostMapping("/all")
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
    public JSONResult updateUserStatus(@RequestBody JSONObject object){
        String token =object.getString("token");
        String userID = object.getString("userID");
        int num = userService.updateUserStatus(token,userID);
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
     */
    @PostMapping("/upUser")
    @CrossOrigin
    @ResponseBody
    public JSONResult updateUser(@RequestBody CdstorageUserVo cdstorageUserVo){
        int num = userService.updateUser1(cdstorageUserVo);
        if(num>0){
            return new JSONResult(200,"更新用户成功",null);
        }else{
            return new JSONResult(500,"更新用户失败",null);
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
        System.out.println(object);
        String token =object.getString("token");
        String userWorkId = object.getString("userWorkID");
        System.out.println(userWorkId);
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
     * 未完成（把组员移出群组）
     * @param jsonObject
     * @return
     */
    @RequestMapping("/delDepartUserByIds")
    public JSONResult delDepartUserByIds(@RequestBody JSONObject jsonObject){
        int sum = 0;
        String token = jsonObject.getString("token");
        String departID = jsonObject.getString("departId");
        String [] userIDs = jsonObject.getString("userIDs").replace("[","").replace("]","").split(",");

        /*for (int i = 0;i<userIDs.length;i++){
            if (managerService.delDepartUser(departID,userIDs[i])){
                sum++;
            }
        }*/
       List<String> userId = new ArrayList<String>();
        for (String user: userIDs) {
            userId.add(user);
        }
         /*System.out.println(userId.size());
        if(managerService.delDepartUser(departID,userId)){
            return new JSONResult(200,"删除成功",null);
        }*/
        List<DepartmentUser> list = managerService.delDepartUser(departID,userId);
        if (list!=null){
            return new JSONResult(200,"删除成功",list);
        }else {
            return JSONResult.errorMsg("参数错误");
        }
    }

}
