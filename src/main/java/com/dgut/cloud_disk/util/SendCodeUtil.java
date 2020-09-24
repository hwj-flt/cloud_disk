package com.dgut.cloud_disk.util;

import com.github.qcloudsms.SmsSingleSender;
import com.github.qcloudsms.SmsSingleSenderResult;
import org.springframework.beans.factory.annotation.Value;

public class SendCodeUtil {
    @Value("${tencent.appId}")
    private static Integer appId;
    @Value("${tencent.appKey}")
    private static String appKey;
    @Value("${tencent.templateId}")
    private static Integer templateId;
    @Value("${tencent.smsSign}")
    private static String smsSign;
    @Value("${tencent.smsEffectiveTime}")
    private static String smsEffectiveTime;
    public static String sendMsg(String phoneNum,String code){
        String res = "";
        try {
            String[] params = {code,smsEffectiveTime};
            SmsSingleSender smsSingleSender = new SmsSingleSender(appId, appKey);
            // 签名参数未提供或者为空时，会使用默认签名发送短信
            SmsSingleSenderResult smsSingleSenderResult = smsSingleSender.sendWithParam("86", phoneNum,
                    templateId, params, smsSign, "", "");
            // 如果返回码不是0，说明配置有错，返回错误信息
            if (smsSingleSenderResult.result == 0) {
                res = "success";
            } else {
                res = smsSingleSenderResult.errMsg;
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return res;
    }
}
