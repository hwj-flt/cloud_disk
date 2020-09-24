package com.dgut.cloud_disk.pojo;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import javax.persistence.*;

@Table(name = "`myfile`")
public class Myfile implements Serializable {
    /**
     * 文件ID
     */
    @Id
    @Column(name = "`FILE_ID`")
    private String fileId;

    /**
     * 文件名
     */
    @Column(name = "`FILE_NAME`")
    private String fileName;

    /**
     * 文件链接
     */
    @Column(name = "`FILE_LINK`")
    private String fileLink;

    /**
     * 文件大小
     */
    @Column(name = "`FILE_SIZE`")
    private BigDecimal fileSize;

    /**
     * 文件类型
     */
    @Column(name = "`FILE_TYPE`")
    private String fileType;

    /**
     * 上传时间
     */
    @Column(name = "`FILE_UPLOAD_TIME`")
    private Date fileUploadTime;

    /**
     * 上传用户ID
     */
    @Column(name = "`FILE_UPLOAD_ID`")
    private String fileUploadId;

    /**
     * 引用文件夹个数
     */
    @Column(name = "`FILE_REFERE`")
    private Integer fileRefere;

    private static final long serialVersionUID = 1L;

    /**
     * 获取文件ID
     *
     * @return FILE_ID - 文件ID
     */
    public String getFileId() {
        return fileId;
    }

    /**
     * 设置文件ID
     *
     * @param fileId 文件ID
     */
    public void setFileId(String fileId) {
        this.fileId = fileId;
    }

    /**
     * 获取文件名
     *
     * @return FILE_NAME - 文件名
     */
    public String getFileName() {
        return fileName;
    }

    /**
     * 设置文件名
     *
     * @param fileName 文件名
     */
    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    /**
     * 获取文件链接
     *
     * @return FILE_LINK - 文件链接
     */
    public String getFileLink() {
        return fileLink;
    }

    /**
     * 设置文件链接
     *
     * @param fileLink 文件链接
     */
    public void setFileLink(String fileLink) {
        this.fileLink = fileLink;
    }

    /**
     * 获取文件大小
     *
     * @return FILE_SIZE - 文件大小
     */
    public BigDecimal getFileSize() {
        return fileSize;
    }

    /**
     * 设置文件大小
     *
     * @param fileSize 文件大小
     */
    public void setFileSize(BigDecimal fileSize) {
        this.fileSize = fileSize;
    }

    /**
     * 获取文件类型
     *
     * @return FILE_TYPE - 文件类型
     */
    public String getFileType() {
        return fileType;
    }

    /**
     * 设置文件类型
     *
     * @param fileType 文件类型
     */
    public void setFileType(String fileType) {
        this.fileType = fileType;
    }

    /**
     * 获取上传时间
     *
     * @return FILE_UPLOAD_TIME - 上传时间
     */
    public Date getFileUploadTime() {
        return fileUploadTime;
    }

    /**
     * 设置上传时间
     *
     * @param fileUploadTime 上传时间
     */
    public void setFileUploadTime(Date fileUploadTime) {
        this.fileUploadTime = fileUploadTime;
    }

    /**
     * 获取上传用户ID
     *
     * @return FILE_UPLOAD_ID - 上传用户ID
     */
    public String getFileUploadId() {
        return fileUploadId;
    }

    /**
     * 设置上传用户ID
     *
     * @param fileUploadId 上传用户ID
     */
    public void setFileUploadId(String fileUploadId) {
        this.fileUploadId = fileUploadId;
    }

    /**
     * 获取引用文件夹个数
     *
     * @return FILE_REFERE - 引用文件夹个数
     */
    public Integer getFileRefere() {
        return fileRefere;
    }

    /**
     * 设置引用文件夹个数
     *
     * @param fileRefere 引用文件夹个数
     */
    public void setFileRefere(Integer fileRefere) {
        this.fileRefere = fileRefere;
    }
}