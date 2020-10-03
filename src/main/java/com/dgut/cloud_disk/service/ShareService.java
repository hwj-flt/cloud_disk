package com.dgut.cloud_disk.service;

import com.dgut.cloud_disk.pojo.bo.BeShareBo;
import com.dgut.cloud_disk.pojo.bo.ShareUserBo;
import com.dgut.cloud_disk.pojo.bo.ToShareBo;

import java.util.List;

public interface ShareService {
    /**
     * 显示被分享列表
     * @param beshareID 被分享用户ID
     * @return  被分享表BO
     */
    List<BeShareBo> showBeShare(String beshareID);

    /**
     *显示分享表
     * @param toshareID 分享用户ID
     * @return  分享表Bo
     */
    List<ToShareBo> showToShare(String toshareID);

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


    /**
     * 删除分享
     * @param shareIDs 分享表ID数组
     */
    void deleteShareByShareIDs(List<String> shareIDs);

    /**
     * 修改有效期
     * @param shareID  分享表ID
     * @param time  修改为多少秒
     */
    void changeExpireByMinute(String shareID,Integer time);

    /**
     * 分享密码
     * @param shareID 分享表ID
     * @param code  分享密码
     */
    void changeShareCodeByCode(String shareID,String code);

    /**
     * 显示让用户分享的用户信息
     * @param userId 用户ID
     * @return 简单用户信息
     */
    public List<ShareUserBo> showShareUser(String userId);
}
