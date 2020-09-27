package com.dgut.cloud_disk.pojo.bo;

import java.util.List;

public class DirectoryVo {
    private String directID;
    private String name;
    private String size;
    private String modificationDate;
    private List<DirectoryVo> includeDrects;
    private List<FileVo> includeFiles;

    public String getDirectID() {
        return directID;
    }

    public void setDirectID(String directID) {
        this.directID = directID;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSize() {
        return size;
    }

    public void setSize(String size) {
        this.size = size;
    }

    public String getModificationDate() {
        return modificationDate;
    }

    public void setModificationDate(String modificationDate) {
        this.modificationDate = modificationDate;
    }

    public List<DirectoryVo> getIncludeDrects() {
        return includeDrects;
    }

    public void setIncludeDrects(List<DirectoryVo> includeDrects) {
        this.includeDrects = includeDrects;
    }

    public List<FileVo> getIncludeFiles() {
        return includeFiles;
    }

    public void setIncludeFiles(List<FileVo> includeFiles) {
        this.includeFiles = includeFiles;
    }
}
