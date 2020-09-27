package com.dgut.cloud_disk.controller;

import com.dgut.cloud_disk.pojo.vo.DownloadFileByShareVo;
import com.dgut.cloud_disk.pojo.vo.ReStorageFileVo;
import com.dgut.cloud_disk.pojo.vo.TokenVo;
import com.dgut.cloud_disk.util.JSONResult;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/cloud/user/")
public class ShareController {

    /**
     * 显示分享给我的信息
     * @param tokenVo   token
     * @return  分享列表
     */
    public JSONResult showBeShare(@RequestBody TokenVo tokenVo){

        return  null;
    }

    /**
     * 下载文件
     * @param downloadFileByShareVo 包含token和分析表ID
     * @return 下载链接
     */
    public  JSONResult downloadFileByShare(@RequestBody DownloadFileByShareVo downloadFileByShareVo){

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
     * @param reStorageVo  前端的分享表ID和被转存的文件夹ID
     * @return
     */
    public  JSONResult reStorageDirectory(@RequestBody ReStorageFileVo reStorageVo){

        return null;
    }


}
