package com.dgut.cloud_disk.controller;

import com.dgut.cloud_disk.mapper.CdstorageUserMapper;
import com.dgut.cloud_disk.pojo.CdstorageUser;
import com.dgut.cloud_disk.pojo.bo.BeShareBo;
import com.dgut.cloud_disk.pojo.vo.DownloadFileByShareVo;
import com.dgut.cloud_disk.pojo.vo.ReStorageFileVo;
import com.dgut.cloud_disk.pojo.vo.TokenVo;
import com.dgut.cloud_disk.service.ShareService;
import com.dgut.cloud_disk.service.impl.ShareServiceImpl;
import com.dgut.cloud_disk.util.JSONResult;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

import javax.persistence.AssociationOverrides;
import java.net.URL;
import java.util.List;

@RestController
@RequestMapping("/cloud/user/")
public class ShareController {

    @Autowired
    private ShareService shareService;

    @Autowired
    private JedisPool jedisPool;


    /**
     * 显示分享给我的信息
     * @param tokenVo   token
     * @return  分享列表
     */
    @RequestMapping("/showBeShare")
    public JSONResult showBeShare(@RequestBody TokenVo tokenVo) throws JsonProcessingException {
        Jedis jedis = jedisPool.getResource();
        String tokenValue = jedis.get(tokenVo.getToken());
        ObjectMapper objectMapper = new ObjectMapper();
        CdstorageUser cdstorageUser = objectMapper.readValue(tokenValue, CdstorageUser.class);
        jedis.close();
        List<BeShareBo> result= shareService.showBeShare(cdstorageUser.getUserId());
        return  JSONResult.ok(result);
    }

    /**
     * 下载文件
     * @param downloadFileByShareVo 包含token和分析表ID
     * @return 下载链接
     */
    public  JSONResult downloadFileByShare(@RequestBody DownloadFileByShareVo downloadFileByShareVo) throws Exception {
        Jedis jedis = jedisPool.getResource();
        String tokenValue = jedis.get(downloadFileByShareVo.getToken());
        ObjectMapper objectMapper = new ObjectMapper();
        CdstorageUser cdstorageUser = objectMapper.readValue(tokenValue, CdstorageUser.class);
        jedis.close();
        String url = shareService.downloadFileByShare(downloadFileByShareVo.getShareID(), cdstorageUser.getUserId());
        return JSONResult.ok(url);
    }

    /**
     *转存文件
     * @param reStorageVo  前端的分享表ID和被转存的文件夹ID
     * return
     */
    public JSONResult reStorageFile(@RequestBody ReStorageFileVo reStorageVo){

        return null;
    }

    /**
     * 转存文件夹
     * ---------
     * @param reStorageVo  前端的分享表ID和被转存的文件夹ID
     * @return
     */
    public  JSONResult reStorageDirectory(@RequestBody ReStorageFileVo reStorageVo){

        return null;
    }


}
