package com.dgut.cloud_disk.service;

import com.dgut.cloud_disk.exception.ParameterException;
import com.dgut.cloud_disk.pojo.vo.ImportUserVo;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

public interface ManagerImportUserService {
    /***
     * 管理员插入用户信息的业务层
     * @param userVo 前端传回的用户信息
     */
    void insertUser(ImportUserVo.UserVo userVo);

    /**
     * 创建根文件夹
     * @return 文件夹的UUID
     */
    String initialRootDirectory(String userId);

    /**
     * 创建回收站
     * @param userId   用户ID
     * @return 回收站的UUID
     */
    String initialGarbage(String userId);

    /**
     * 将上传的xls保存到当前目录下
     * @param file 上传的文件
     * @param token 用户token
     */
    void storageFile(MultipartFile file,String token) throws ParameterException, IOException;

    /**
     * 批量插入用户
     * @param list  用户数组
     */
    void insertUsers(List<ImportUserVo.UserVo> list);
}
