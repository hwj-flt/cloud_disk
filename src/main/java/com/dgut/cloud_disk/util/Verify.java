package com.dgut.cloud_disk.util;

public class Verify {

    private static String emailReg ="^[a-zA-Z0-9_-]+@[a-zA-Z0-9_-]+(\\.[a-zA-Z0-9_-]+)+$";

    private static String phoneReg = "^1[0-9]{10}$";

    public static  Boolean verifyEmail(String email){
        return email.matches(emailReg);
    }
    public static Boolean verifyPhone(String phone){
        System.out.println(phone.matches(phoneReg));
        return  phone.matches(phoneReg);
    }
}
