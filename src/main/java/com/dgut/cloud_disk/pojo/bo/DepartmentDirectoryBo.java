package com.dgut.cloud_disk.pojo.bo;

import java.util.List;

public class DepartmentDirectoryBo {
    private String directID;
    private String name;
    private String size;
    private String modificationDate;
    private String permission;
    private List<DepartmentDirectoryBo> includeDirects;
    private List<FileBo> includeFiles;

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

    public String getPermission() {
        return permission;
    }

    public void setPermission(String permission) {
        this.permission = permission;
    }

    public List<DepartmentDirectoryBo> getIncludeDirects() {
        return includeDirects;
    }

    public void setIncludeDirects(List<DepartmentDirectoryBo> includeDirects) {
        this.includeDirects = includeDirects;
    }

    public List<FileBo> getIncludeFiles() {
        return includeFiles;
    }

    public void setIncludeFiles(List<FileBo> includeFiles) {
        this.includeFiles = includeFiles;
    }
}
