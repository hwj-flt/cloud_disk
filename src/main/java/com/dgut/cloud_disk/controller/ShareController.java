package com.dgut.cloud_disk.controller;

import com.dgut.cloud_disk.exception.ParameterException;
import com.dgut.cloud_disk.mapper.CdstorageUserMapper;
import com.dgut.cloud_disk.pojo.CdstorageUser;
import com.dgut.cloud_disk.pojo.bo.BeShareBo;
import com.dgut.cloud_disk.pojo.bo.ShareBo;
import com.dgut.cloud_disk.pojo.bo.ShareUserBo;
import com.dgut.cloud_disk.pojo.bo.ToShareBo;
import com.dgut.cloud_disk.pojo.vo.*;
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
    @RequestMapping("/showShare")
    public JSONResult showBeShare(@RequestBody TokenVo tokenVo) throws Exception {
        Jedis jedis = jedisPool.getResource();
        String tokenValue = jedis.get(tokenVo.getToken());
        if (tokenValue==null){
            throw  new ParameterException("请登录");
        }
        ObjectMapper objectMapper = new ObjectMapper();
        CdstorageUser cdstorageUser = objectMapper.readValue(tokenValue, CdstorageUser.class);
        jedis.close();
        List<BeShareBo> result= shareService.showBeShare(cdstorageUser.getUserId());
        ShareBo shareBo = new ShareBo();
        shareBo.setBeShares(result);
        List<ToShareBo> result1 = shareService.showToShare(cdstorageUser.getUserId());
        shareBo.setToShares(result1);
        return  JSONResult.ok(shareBo);
    }

    /**
     * 下载文件
     * @param downloadFileByShareVo 包含token和分析表ID
     * @return 下载链接
     */
    @RequestMapping("/downloadFileByShare")
    public  JSONResult downloadFileByShare(@RequestBody DownloadFileByShareVo downloadFileByShareVo) throws Exception {
        Jedis jedis = jedisPool.getResource();
        String tokenValue = jedis.get(downloadFileByShareVo.getToken());
        if (tokenValue==null){
            throw  new ParameterException("请登录");
        }
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
    @RequestMapping("reStorageFile")
    public JSONResult reStorageFile(@RequestBody ReStorageFileVo reStorageVo) throws Exception {
        Jedis jedis = jedisPool.getResource();
        String tokenValue = jedis.get(reStorageVo.getToken());
        if (tokenValue==null){
            throw  new ParameterException("请登录");
        }
        ObjectMapper objectMapper = new ObjectMapper();
        CdstorageUser cdstorageUser = objectMapper.readValue(tokenValue, CdstorageUser.class);
        jedis.close();
        shareService.storeFileByShare(reStorageVo.getShareID(),reStorageVo.getNewDirectID(),cdstorageUser.getUserId());
        return JSONResult.ok();
    }

    /**
     * 转存文件夹
     * ---------
     * @param reStorageVo  前端的分享表ID和被转存的文件夹ID
     * @return
     */
    @RequestMapping("reStorageDirectory")
    public  JSONResult reStorageDirectory(@RequestBody ReStorageFileVo reStorageVo) throws Exception {
        Jedis jedis = jedisPool.getResource();
        String tokenValue = jedis.get(reStorageVo.getToken());
        if (tokenValue==null){
            throw  new ParameterException("请登录");
        }
        ObjectMapper objectMapper = new ObjectMapper();
        CdstorageUser cdstorageUser = objectMapper.readValue(tokenValue, CdstorageUser.class);

        jedis.close();
        shareService.storeDirectByShare(reStorageVo.getShareID(),reStorageVo.getNewDirectID(),cdstorageUser.getUserId());
        return JSONResult.ok();
    }

    @RequestMapping("deleteShare")
    public JSONResult deleteShare(@RequestBody DeleteShareVo deleteShareVo){
        shareService.deleteShareByShareIDs(deleteShareVo.getShareIDs());
        return JSONResult.ok();
    }

    @RequestMapping("changeExpire")
    public JSONResult changeExpire(@RequestBody  ChangeExpireVo changeExpireVo){
        shareService.changeExpireByMinute(changeExpireVo.getShareID(),changeExpireVo.getShareTime());
        return  JSONResult.ok();
    }

    @RequestMapping("changeShareCode")
    public JSONResult changeShareCode(@RequestBody  ChangeShareCodeVo changeShareCodeVo){
        shareService.changeShareCodeByCode(changeShareCodeVo.getShareID(),changeShareCodeVo.getSharecode());
        return  JSONResult.ok();
    }

    @RequestMapping("showUserForShare")
    public JSONResult showUserForShare(@RequestBody TokenVo tokenVo){
        List<ShareUserBo> shareUserBos = shareService.showShareUser();
        return  JSONResult.ok(shareUserBos);
    }
}
