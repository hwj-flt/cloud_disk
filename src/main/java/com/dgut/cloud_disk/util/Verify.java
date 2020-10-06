package com.dgut.cloud_disk.util;

import com.dgut.cloud_disk.exception.ParameterException;
import com.dgut.cloud_disk.mapper.CdstorageUserMapper;
import com.dgut.cloud_disk.pojo.CdstorageUser;
import org.apache.logging.log4j.core.tools.picocli.CommandLine;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class Verify {


    private static CdstorageUserMapper cdstorageUserMapper;

    private static String emailReg ="^[a-zA-Z0-9_-]+@[a-zA-Z0-9_-]+(\\.[a-zA-Z0-9_-]+)+$";

    private static String phoneReg = "^1[0-9]{10}$";

    public static  Boolean verifyEmail(String email){
        return email.matches(emailReg);
    }
    public static int verifyPhone(String phone) throws ParameterException {
        CdstorageUser cdstorageUser  = new CdstorageUser();
        cdstorageUser.setUserMobie(phone);
        List<CdstorageUser> cdstorageUsers = cdstorageUserMapper.select(cdstorageUser);
        Integer r = 0;
        if(cdstorageUsers.size()>0){
            r=1;
        }else if(!phone.matches(phoneReg)){
            r=2;
        }
        return r;
    }

    public CdstorageUserMapper getCdstorageUserMapper() {
        return cdstorageUserMapper;
    }

    @Autowired(required = false)
    public void setCdstorageUserMapper(CdstorageUserMapper cdstorageUserMapper) {
        Verify.cdstorageUserMapper = cdstorageUserMapper;
    }
}
