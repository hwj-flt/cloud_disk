package com.dgut.cloud_disk.service;

public interface ToshareService {
    int deleteRecord(String shareFileId, String shareDirectId);
    int deleteDirectRecord(String shareDirectId);
}
