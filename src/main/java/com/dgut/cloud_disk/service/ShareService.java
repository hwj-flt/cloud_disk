package com.dgut.cloud_disk.service;

import com.dgut.cloud_disk.pojo.bo.BeShareBo;

import java.util.List;

public interface ShareService {
    /**
     * 显示被分享列表
     * @param beshareID 被分享用户ID
     * @return  分享表BO
     */
    List<BeShareBo> showBeShare(String beshareID);

    /**
     * 返回下载链接
     * @param shareID 分享表ID
     * @param userID  被分享用户ID
     * @return  下载链接
     */
    String downloadFileByShare(String shareID,String userID) throws Exception;

    /**
     * 转存文件
     * @param shareID 分析表ID
     * @param directID 要被转存的文件夹
     * @param userID 操作的用户ID
     *
     */
    void storeFileByShare(String shareID,String directID,String userID) throws Exception;

    /**
     * 转存文件夹
     * @param shareID 分析表ID
     * @param directID 要被转存的文件夹
     * @param userID 操作的用户ID
     * @throws Exception
     */
    void storeDirectByShare(String shareID,String directID,String userID) throws Exception;

}
