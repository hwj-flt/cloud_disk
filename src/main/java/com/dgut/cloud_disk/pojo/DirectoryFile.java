package com.dgut.cloud_disk.pojo;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.*;

@Table(name = "`directory_file`")
public class DirectoryFile implements Serializable {
    /**
     * 映射ID
     */
    @Id
    @Column(name = "`DIRECT_FILE_ID`")
    private String directFileId;

    /**
     * 文件夹ID
     */
    @Column(name = "`DF_DIRECT_ID`")
    private String dfDirectId;

    /**
     * 文件ID
     */
    @Column(name = "`DF_FILE_ID`")
    private String dfFileId;

    /**
     * 1-未被删除 2被删除
     */
    @Column(name = "`DF_GARBAGE`")
    private Integer dfGarbage;

    /**
     * 删除时间
     */
    @Column(name = "`DF_DELETE_TIME`")
    private Date dfDeleteTime;

    /**
     * 删除用户ID
     */
    @Column(name = "`DF_DELETE_ID`")
    private String dfDeleteId;

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
     * 获取1-未被删除 2被删除
     *
     * @return DF_GARBAGE - 1-未被删除 2被删除
     */
    public Integer getDfGarbage() {
        return dfGarbage;
    }

    /**
     * 设置1-未被删除 2被删除
     *
     * @param dfGarbage 1-未被删除 2被删除
     */
    public void setDfGarbage(Integer dfGarbage) {
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
}