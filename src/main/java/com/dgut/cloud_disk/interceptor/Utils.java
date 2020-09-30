package com.dgut.cloud_disk.interceptor;

import com.alibaba.fastjson.JSONObject;
import com.dgut.cloud_disk.util.JSONResult;
import com.fasterxml.jackson.databind.ObjectMapper;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;

public class Utils {
    /**
     * 将返回结果对象，转换成json，通过http响应，字符打印到界面
     * @param response response
     * @param jsonData jsonData
     */
    static void renderJson(HttpServletResponse response, JSONResult jsonData) {
        response.setCharacterEncoding("UTF-8");
        response.setContentType("application/json");

        try {
            PrintWriter writer = response.getWriter();
            ObjectMapper mapper = new ObjectMapper();
            writer.print(mapper.writeValueAsString(jsonData));
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
    static String getBodyString(HttpServletRequest request) throws IOException {
        BufferedReader streamReader = new BufferedReader( new InputStreamReader(request.getInputStream(), "UTF-8"));
        StringBuilder responseStrBuilder = new StringBuilder();
        String inputStr;
        while ((inputStr = streamReader.readLine()) != null) {
            responseStrBuilder.append(inputStr);
        }
        return responseStrBuilder.toString();
    }
}
