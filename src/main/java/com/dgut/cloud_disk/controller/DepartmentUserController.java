package com.dgut.cloud_disk.controller;

import com.dgut.cloud_disk.service.DepartmentUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/user")
public class DepartmentUserController {

    @Autowired
    private DepartmentUserService departmentUserService;
    @RequestMapping("/all")
    public void allUser(){
        System.out.println(departmentUserService.allUser());
    }

}
