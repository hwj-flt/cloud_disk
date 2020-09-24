package com.dgut.cloud_disk.service.impl;

import com.dgut.cloud_disk.exception.ParameterException;
import com.dgut.cloud_disk.mapper.CdstorageUserMapper;
import com.dgut.cloud_disk.mapper.DirectoryMapper;
import com.dgut.cloud_disk.pojo.CdstorageUser;
import com.dgut.cloud_disk.pojo.Directory;
import com.dgut.cloud_disk.pojo.vo.ImportUserVo;
import com.dgut.cloud_disk.service.ManagerImportUserService;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.issCollege.util.MD5;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.multipart.MultipartFile;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

import java.io.*;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.UUID;

@Service
public class ManagerImportUserServiceImpl implements ManagerImportUserService {


    //默认用户空间大小
    @Value("${space.size}")
    private int s;

    @Autowired
    private JedisPool jedisPool;

    //指定上传的位置为 d:/upload/
    @Value("${upload.path}")
    private String path;

    @Autowired(required = false)
    private CdstorageUserMapper cdstorageUserMapper;

    @Autowired(required = false)
    private DirectoryMapper directoryMapper;
    /***
     * 管理员插入用户信息的业务层
     * @param userVo 前端传回的用户信息
     */
    @Override
    @Transactional
    public void insertUser(ImportUserVo.UserVo userVo) {
        String userId = UUID.randomUUID().toString().replaceAll("-","");
        CdstorageUser cdstorageUser = new CdstorageUser();
        //写入用户名
        cdstorageUser.setUserName(userVo.getUserName());
        //写入用户UUID
        cdstorageUser.setUserId(userId);
        //写入用户邮箱
        cdstorageUser.setUserEmail(userVo.getUserEmail());
        //写入用户手机
        cdstorageUser.setUserMobie(userVo.getUserPhone());
        //创建文件夹，返回文件夹ID，写入文件夹ID
        String duuid = initialRootDirectory(userId);
        cdstorageUser.setUserRootId(duuid);
        cdstorageUser.setUserPassword(MD5.stringMD5(userVo.getUserPhone()));
        //创建回收站，返回回收站ID，写入回收站ID
        String guuid = initialGarbage(userId);
        cdstorageUser.setUserGarbage(guuid);
        //写入用户性别
        String sex = ("男".equals(userVo.getUserSex())) ? "M":"F";
        cdstorageUser.setUserSex(sex);
        //写入默认空间大小
        cdstorageUser.setUserSize(new BigDecimal(s));
        //工号使用数据库的自增
        //cdstorageUser.setUserWorkId();                                                                                                      );
        //用户状态
        cdstorageUser.setUserStatus((byte) 1);
        //用户权限
        cdstorageUser.setUserPermission(0);
        //用户空间大小
        cdstorageUser.setUserUsed(new BigDecimal(0));
        //用户创建时间
        cdstorageUser.setUserTime(new Date());
        cdstorageUserMapper.insert(cdstorageUser);
    }

    /**
     * 创建根文件夹
     *
     * @return 文件夹的UUID
     */
    @Override
    public String initialRootDirectory(String userId) {
        //文件夹uuid
        String uuid = UUID.randomUUID().toString().replaceAll("-","");
        Directory directory = new Directory();
        //写入根文件uuid
        directory.setDirectId(uuid);
        //文件所属用户ID
        directory.setDirectBelongUser(userId);
        //文件所属群组为空
        directory.setDirectBelongDepart(null);
        //文件创建用户ID
        directory.setDirectCreateId(userId);
        //文件名根目录默认为ROOT
        directory.setDirectName("ROOT");
        //创建时间
        directory.setDirectCreateTime(new Date());
        //文件大小
        directory.setDirectSize(new BigDecimal(0));
        //文件类型为个人根目录
        directory.setDirectType((byte)1);
        //根目录的父文件ID为1
        directory.setParentDirectId("1");
        //是否在回收站中为否
        directory.setDirectDelete((byte)1);
        directoryMapper.insert(directory);
        return uuid;
    }

    /**
     * 建回收站
     *
     * @return 回收站的UUID
     */
    @Override
    public String initialGarbage(String userId) {
        //文件夹uuid
        String uuid = UUID.randomUUID().toString().replaceAll("-","");
        Directory directory = new Directory();
        //写入根文件uuid
        directory.setDirectId(uuid);
        //文件所属用户ID
        directory.setDirectBelongUser(userId);
        //文件所属群组为空
        directory.setDirectBelongDepart(null);
        //文件创建用户ID
        directory.setDirectCreateId(userId);
        //文件名根目录默认为ROOT
        directory.setDirectName("ROOT");
        //创建时间
        directory.setDirectCreateTime(new Date());
        //文件大小
        directory.setDirectSize(new BigDecimal(0));
        //文件类型为个人根目录
        directory.setDirectType((byte)5);
        //根目录的父文件ID为1
        directory.setParentDirectId("1");
        //是否在回收站中为否
        directory.setDirectDelete((byte)2);
        directoryMapper.insert(directory);
        return uuid;
    }

    /**
     * 将上传的xls保存到当前目录下
     *
     * @param file 上传的文件
     */
    @Override
    public void storageFile(MultipartFile file,String token) throws IOException  {
        //创建输入输出流
        InputStream inputStream = null;
        OutputStream outputStream = null;
        //获取文件的输入流
        inputStream = file.getInputStream();
        //文件名使用工号表示
        //创建所需对象
        Jedis jedis = jedisPool.getResource();
        String tokenValue = jedis.get(token);
        jedis.close();
        ObjectMapper mapper = new ObjectMapper();
        CdstorageUser user = mapper.readValue(tokenValue, CdstorageUser.class);
        //注意是路径+文件名
        File targetFile = new File(path + user.getUserWorkId().toString()+".xls");
        //如果之前的 String path = "d:/upload/" 没有在最后加 / ，那就要在 path 后面 + "/"
        //判断文件父目录是否存在
        if (!targetFile.getParentFile().exists()) {
            //不存在就创建一个
            targetFile.getParentFile().mkdir();
        }
        //获取文件的输出流
        outputStream = new FileOutputStream(targetFile);
        //最后使用资源访问器FileCopyUtils的copy方法拷贝文件
        FileCopyUtils.copy(inputStream, outputStream);
        outputStream.close();
        inputStream.close();
    }

    /**
     * 批量插入用户
     *
     * @param list 用户数组
     */
    @Override
    @Transactional
    public void insertUsers(List<ImportUserVo.UserVo> list) {
        for(ImportUserVo.UserVo user : list){
            insertUser(user);
        }
    }
}
