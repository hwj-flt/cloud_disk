package com.dgut.cloud_disk.controller;

import com.alibaba.fastjson.JSONObject;
import com.dgut.cloud_disk.pojo.DepartmentUser;
import com.dgut.cloud_disk.service.ManagerService;
import com.dgut.cloud_disk.util.JSONResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("cloud/user/manager")
public class ManagerController {
    @Autowired
    public ManagerService managerService;
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
