package com.dgut.cloud_disk.controller;

import com.alibaba.fastjson.JSONObject;
import com.dgut.cloud_disk.service.ManagerService;
import com.dgut.cloud_disk.util.JSONResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("cloud/user/manager")
public class ManagerController {
    @Autowired
    public ManagerService managerService;
    @RequestMapping("/delDepartUserByIds")
    public JSONResult delDepartUserByIds(@RequestBody JSONObject jsonObject){
        String token = jsonObject.getString("token");
        String departID = jsonObject.getString("departId");
        String [] userIDs = jsonObject.getString("userIDs").replace("[","").replace("]","").split(",");
        if (managerService.delDepartUser(userIDs,departID) == userIDs.length){
            return new JSONResult(200,"删除成功",null);
        }else {
            return JSONResult.errorMsg("参数错误");
        }
    }

}
