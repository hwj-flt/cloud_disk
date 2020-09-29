package com.dgut.cloud_disk.pojo;

import javax.persistence.Column;
import javax.persistence.Id;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

public class DirectoryFileMyFile implements Serializable {
    /**
     * 映射ID
     */
    private String directFileId;

    /**
     * 文件夹ID
     */
    private String dfDirectId;

    /**
     * 文件ID
     */
    private String dfFileId;

    /**
     * 文件名
     */
    private String dfFileName;

    /**
     * 1-未被删除 2被删除
     */
    private Byte dfGarbage;

    /**
     * 删除时间
     */
    private Date dfDeleteTime;

    /**
     * 删除用户ID
     */
    private String dfDeleteId;

    /**
     * 文件ID
     */
    private String fileId;

    /**
     * 文件链接
     */
    private String fileLink;

    /**
     * 文件大小
     */
    private BigDecimal fileSize;

    /**
     * 文件类型
     */
    private String fileType;

    /**
     * 上传时间
     */
    private Date fileUploadTime;

    /**
     * 上传用户ID
     */
    private String fileUploadId;

    /**
     * 引用文件夹个数
     */
    private Byte fileRefere;


    private static final long serialVersionUID = 1L;

    /**
     * 获取映射ID
     *
     * @return DIRECT_FILE_ID - 映射ID
     */
    public String getDirectFileId() {
        return directFileId;
    }

    /**
     * 设置映射ID
     *
     * @param directFileId 映射ID
     */
    public void setDirectFileId(String directFileId) {
        this.directFileId = directFileId;
    }

    /**
     * 获取文件夹ID
     *
     * @return DF_DIRECT_ID - 文件夹ID
     */
    public String getDfDirectId() {
        return dfDirectId;
    }

    /**
     * 设置文件夹ID
     *
     * @param dfDirectId 文件夹ID
     */
    public void setDfDirectId(String dfDirectId) {
        this.dfDirectId = dfDirectId;
    }

    /**
     * 获取文件ID
     *
     * @return DF_FILE_ID - 文件ID
     */
    public String getDfFileId() {
        return dfFileId;
    }

    /**
     * 设置文件ID
     *
     * @param dfFileId 文件ID
     */
    public void setDfFileId(String dfFileId) {
        this.dfFileId = dfFileId;
    }

    /**
     * 获取文件名
     *
     * @return DF_FILE_NAME - 文件名
     */
    public String getDfFileName() {
        return dfFileName;
    }

    /**
     * 设置文件名
     *
     * @param dfFileName 文件名
     */
    public void setDfFileName(String dfFileName) {
        this.dfFileName = dfFileName;
    }

    /**
     * 获取1-未被删除 2被删除
     *
     * @return DF_GARBAGE - 1-未被删除 2被删除
     */
    public Byte getDfGarbage() {
        return dfGarbage;
    }

    /**
     * 设置1-未被删除 2被删除
     *
     * @param dfGarbage 1-未被删除 2被删除
     */
    public void setDfGarbage(Byte dfGarbage) {
        this.dfGarbage = dfGarbage;
    }

    /**
     * 获取删除时间
     *
     * @return DF_DELETE_TIME - 删除时间
     */
    public Date getDfDeleteTime() {
        return dfDeleteTime;
    }

    /**
     * 设置删除时间
     *
     * @param dfDeleteTime 删除时间
     */
    public void setDfDeleteTime(Date dfDeleteTime) {
        this.dfDeleteTime = dfDeleteTime;
    }

    /**
     * 获取删除用户ID
     *
     * @return DF_DELETE_ID - 删除用户ID
     */
    public String getDfDeleteId() {
        return dfDeleteId;
    }

    /**
     * 设置删除用户ID
     *
     * @param dfDeleteId 删除用户ID
     */
    public void setDfDeleteId(String dfDeleteId) {
        this.dfDeleteId = dfDeleteId;
    }

    public String getFileId() {
        return fileId;
    }

    public void setFileId(String fileId) {
        this.fileId = fileId;
    }

    public String getFileLink() {
        return fileLink;
    }

    public void setFileLink(String fileLink) {
        this.fileLink = fileLink;
    }

    public BigDecimal getFileSize() {
        return fileSize;
    }

    public void setFileSize(BigDecimal fileSize) {
        this.fileSize = fileSize;
    }

    public String getFileType() {
        return fileType;
    }

    public void setFileType(String fileType) {
        this.fileType = fileType;
    }

    public Date getFileUploadTime() {
        return fileUploadTime;
    }

    public void setFileUploadTime(Date fileUploadTime) {
        this.fileUploadTime = fileUploadTime;
    }

    public String getFileUploadId() {
        return fileUploadId;
    }

    public void setFileUploadId(String fileUploadId) {
        this.fileUploadId = fileUploadId;
    }

    public Byte getFileRefere() {
        return fileRefere;
    }

    public void setFileRefere(Byte fileRefere) {
        this.fileRefere = fileRefere;
    }

    public static long getSerialVersionUID() {
        return serialVersionUID;
    }
}
