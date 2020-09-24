package com.dgut.cloud_disk.util;

import com.github.qcloudsms.SmsSingleSender;
import com.github.qcloudsms.SmsSingleSenderResult;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

@Configuration
@PropertySource(value = "classpath:tencentConfig.properties")
public class SendCodeUtil {
    @Value("${tencent.appId}")
    private Integer appId;
    @Value("${tencent.appKey}")
    private String appKey;
    @Value("${tencent.templateId}")
    private Integer templateId;
    @Value("${tencent.smsSign}")
    private String smsSign;
    public String sendMsg(String phoneNum,String code){
        String res = "";
        try {
            String[] params = {code};
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
