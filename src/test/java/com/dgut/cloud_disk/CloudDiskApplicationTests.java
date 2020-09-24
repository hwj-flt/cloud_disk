package com.dgut.cloud_disk;

import com.dgut.cloud_disk.mapper.DirectoryMapper;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;
import java.util.Iterator;
import java.util.List;

@SpringBootTest
class CloudDiskApplicationTests {

    @Resource
    private DirectoryMapper Dmapper;
    @Test
    void contextLoads() {
    }

    @Test
    void delete(){
        int i=Dmapper.deleteDirectoryByPId("2");
        System.out.println(i);
    }
    @Test
    void select(){
//        List<String> list=Dmapper.selectIdByPid("1");
////        System.out.println(list);
        List<String> list =Dmapper.selectIdByPid("1");
        for(int i=0;i<list.size();i++){
            System.out.println(list.get(i));
        }


    }
}
