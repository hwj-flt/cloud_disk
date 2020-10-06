package com.dgut.cloud_disk.util;

import com.issCollege.util.MD5;
import com.obs.services.ObsClient;
import com.obs.services.model.HttpMethodEnum;
import com.obs.services.model.TemporarySignatureRequest;
import com.obs.services.model.TemporarySignatureResponse;

import java.util.HashMap;
import java.util.Map;

public class test {
    public static void main(String[] args) {
//        String ak = "QDTDAOD1R8MIXYV2LWWN";
//        String sk = "xPF0opcKE3oMNbNwNrdlOl9BK7jdFOzkgJFOWBjn";
//        String endPoint = "obs.cn-south-1.myhuaweicloud.com";
//
//        // 创建ObsClient实例
//        ObsClient obsClient = new ObsClient(ak, sk, endPoint);
//        // URL有效期，3600秒
//        long expireSeconds = 3600L;
//        Map<String, String> headers = new HashMap<String, String>();
//        String contentType = "multipart/form-data";
//        headers.put("Content-Type", contentType);
//
//        TemporarySignatureRequest request = new TemporarySignatureRequest(HttpMethodEnum.PUT, expireSeconds);
//        request.setBucketName("obs-signal-zrx");
//        request.setObjectKey("aaa.docx");
//        request.setHeaders(headers);
////        System.out.println(request);
//        TemporarySignatureResponse response = obsClient.createTemporarySignature(request);
//        for (Map.Entry<String, String> entry : response.getActualSignedRequestHeaders().entrySet()) {
//            System.out.println(entry.getKey()+":"+entry.getValue());
//        }
//        System.out.println(response.getSignedUrl());

        String s = MD5.stringMD5("111111");
        System.out.println(s);
    }
}
