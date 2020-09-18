package com.dgut.cloud_disk.controller;

import com.dgut.cloud_disk.pojo.CdstorageUser;
import com.dgut.cloud_disk.service.CdstorageUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/user")
public class CdstorageUserController {

    @Autowired
    private CdstorageUserService cdstorageUserService;
    @RequestMapping("/all")
    public List<CdstorageUser> allUser(){
        System.out.println("------"+cdstorageUserService.allUser());
        return cdstorageUserService.allUser();
    }

}
