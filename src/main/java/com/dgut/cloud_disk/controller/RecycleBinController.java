package com.dgut.cloud_disk.controller;

import com.alibaba.fastjson.JSONObject;
import com.dgut.cloud_disk.pojo.CdstorageUser;
import com.dgut.cloud_disk.pojo.Directory;
import com.dgut.cloud_disk.pojo.DirectoryFile;
import com.dgut.cloud_disk.service.DirectoryFileService;
import com.dgut.cloud_disk.util.JSONResult;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/cloud/user")
public class RecycleBinController {
    @Autowired
    private DirectoryFileService DFService;
    @Autowired
    private JedisPool jedisPool;
    @RequestMapping("/showRecycleBin")
    public JSONResult showRecycleBin(@RequestBody JSONObject jsonObject) throws JsonProcessingException {
        String token = jsonObject.getString("token");
        //从token中获取分享人id
        Jedis jedis = jedisPool.getResource();
        String tokenValue = jedis.get(token);
        ObjectMapper objectMapper=new ObjectMapper();
        CdstorageUser cdstorageUser = objectMapper.readValue(tokenValue, CdstorageUser.class);

        List<DirectoryFile> dfList=DFService.getDeletedFileByID(cdstorageUser.getUserId());
        //System.out.println(dfList.get(0).getDfFileName()+dfList.get(1).getDfFileName());

        List<Directory> dList=DFService.getDeletedDirectoryByID(cdstorageUser.getUserId());

        //System.out.println(dList);
        List list=new ArrayList();
        if(dfList!=null){//被删除文件列表
            for(int i=0;i<dfList.size();i++){
            JSONObject obj=new JSONObject();
            obj.put("type",1);
            obj.put("id",dfList.get(i).getDirectFileId());
            obj.put("name",dfList.get(i).getDfFileName());
            obj.put("deleteTime",dfList.get(i).getDfDeleteTime());

            list.add(obj);
            }
        }else {
            System.out.println("dflist为空！");
        }

        if(dList!=null){//被删除文件夹列表
            //System.out.println(dList);
            for(int i=0;i<dList.size();i++){
                JSONObject obj=new JSONObject();
                obj.put("type",2);
                obj.put("id",dList.get(i).getDirectId());
                obj.put("name",dList.get(i).getDirectName());
                obj.put("deleteTime",dList.get(i).getDirectDeleteTime());
                list.add(obj);
                //System.out.println(obj);
            }
        }else {
            System.out.println("dlist为空！");
        }
        if(dfList==null&&dList==null){
            return new JSONResult(500,"查询失败","");
        }
        //System.out.println(list);
        return new JSONResult(200,"查询成功",list);
    }

    @RequestMapping("/restore")//还原
    public JSONResult restore(@RequestBody JSONObject jsonObject) throws JsonProcessingException {
        String token = jsonObject.getString("token");
        int type =jsonObject.getInteger("type");
        String id =jsonObject.getString("id");
        //从token中获取分享人id
        Jedis jedis = jedisPool.getResource();
        String tokenValue = jedis.get(token);
        ObjectMapper objectMapper=new ObjectMapper();
        CdstorageUser cdstorageUser = objectMapper.readValue(tokenValue, CdstorageUser.class);

        if(type==1){//还原文件夹
            if(DFService.restoreDirectory(id)){
                return new JSONResult(200,"还原成功","");
            }else {
                return new JSONResult(601,"还原的文件夹不存在","");
            }
        }else if(type==2){
            if(DFService.restoreFile(id)){
                return new JSONResult(200,"还原成功","");
        }else{
            return new JSONResult(601,"还原的文件夹不存在","");
        }
        }else {
            return new JSONResult(500,"类型错误","");
        }
    }
    @RequestMapping("/restoreToNew")//当还原时原文件夹被删除或不存在时调用此方法，将文件或文件夹还原至新的指定文件夹中
    public JSONResult restoreToNew(@RequestBody JSONObject jsonObject) throws JsonProcessingException {
        String token = jsonObject.getString("token");
        int type =jsonObject.getInteger("type");
        String id =jsonObject.getString("id");
        String Did=jsonObject.getString("directID");
        //从token中获取分享人id
        Jedis jedis = jedisPool.getResource();
        String tokenValue = jedis.get(token);
        ObjectMapper objectMapper=new ObjectMapper();
        CdstorageUser cdstorageUser = objectMapper.readValue(tokenValue, CdstorageUser.class);

        if(type==1){
            if(DFService.restoreDirectorytoNew(id,Did)){
                return new JSONResult(200,"还原成功","");
            }else {
                return new JSONResult(500,"还原失败","");
            }
        }else if(type==2){
            if(DFService.restoreFiletoNew(id,Did)){
                return new JSONResult(200,"还原成功","");
            }else {
                return new JSONResult(500,"还原失败","");
            }
        }else{
            return new JSONResult(500,"类型错误","");
        }

    }
}