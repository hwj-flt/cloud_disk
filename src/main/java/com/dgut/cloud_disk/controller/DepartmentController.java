package com.dgut.cloud_disk.controller;

import com.dgut.cloud_disk.exception.ParameterException;
import com.dgut.cloud_disk.mapper.DepartmentMapper;
import com.dgut.cloud_disk.pojo.CdstorageUser;
import com.dgut.cloud_disk.pojo.Department;
import com.dgut.cloud_disk.pojo.bo.DepartmentBo;
import com.dgut.cloud_disk.pojo.bo.DirectoryBo;
import com.dgut.cloud_disk.pojo.vo.DepartmentCatalogueVo;
import com.dgut.cloud_disk.pojo.vo.TokenVo;
import com.dgut.cloud_disk.service.DepartmentService;
import com.dgut.cloud_disk.service.PersonalCatalogueService;
import com.dgut.cloud_disk.util.JSONResult;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

import java.util.List;

@RestController
@RequestMapping("/cloud/user")
public class DepartmentController {

    @Autowired
    private JedisPool jedisPool;

    @Autowired
    private DepartmentService departmentService;

    @Autowired
    private PersonalCatalogueService personalCatalogueService;

    @Autowired(required = false)
    private DepartmentMapper departmentMapper;

    @RequestMapping("showDepartment")
    public JSONResult showDepartment(@RequestBody  TokenVo tokenVo) throws ParameterException, JsonProcessingException {
        Jedis jedis = jedisPool.getResource();
        String tokenValue = jedis.get(tokenVo.getToken());
        if (tokenValue==null){
            throw  new ParameterException("请登录");
        }
        ObjectMapper objectMapper = new ObjectMapper();
        CdstorageUser cdstorageUser = objectMapper.readValue(tokenValue, CdstorageUser.class);
        jedis.close();
        Integer f = 0;
        if(cdstorageUser.getUserPermission()!=0){
            f=1;
        }
        List<DepartmentBo> departmentBoList = departmentService.showDepart(cdstorageUser.getUserId(),f);
        return  JSONResult.ok(departmentBoList);
    }

    @RequestMapping("departmentCatalogue")
    public JSONResult departmentCatalogue(@RequestBody DepartmentCatalogueVo departmentCatalogueVo){
        String departID = departmentCatalogueVo.getDepartID();
        Department department = new Department();
        department.setDepartId(departmentCatalogueVo.getDepartID());
        Department department1 = departmentMapper.selectOne(department);
        DirectoryBo directoryBo = personalCatalogueService.getAllCatalogue(department1.getDepartRoot());
        return  JSONResult.ok(directoryBo);
    }
}
