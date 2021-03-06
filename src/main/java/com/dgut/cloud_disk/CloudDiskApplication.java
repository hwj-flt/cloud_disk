package com.dgut.cloud_disk;


import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;
import tk.mybatis.spring.annotation.MapperScan;

@SpringBootApplication
@MapperScan("com.dgut.cloud_disk.mapper")
public class CloudDiskApplication {

    public static void main(String[] args) {
        SpringApplication.run(CloudDiskApplication.class, args);
    }
}
