package com.atguigu.msm.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.aliyuncs.CommonRequest;
import com.aliyuncs.CommonResponse;
import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.IAcsClient;

import com.aliyuncs.exceptions.ClientException;
import com.aliyuncs.exceptions.ServerException;
import com.aliyuncs.http.MethodType;
import com.aliyuncs.profile.DefaultProfile;
import com.atguigu.msm.service.MsmService;

import com.google.gson.Gson;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

/**
 *
 * Author: YZG
 * Date: 2022/9/1 19:19
 * Description: 
 */
@Service
public class MsmServiceImpl implements MsmService {
    /**
     * @description 发送验证码
     * @date 2022/9/3 21:36
     * @param code
     * @param phone
     * @return boolean
     */
    @Override
    public boolean send(String code, String phone) {

        if(StringUtils.isEmpty(phone)) return false;
        // 将 code 封装成 map
        Map<String, String> params = new HashMap<>();
        params.put("code",code);


        DefaultProfile profile =
                DefaultProfile.getProfile("default", "LTAI5tGjb9ShtDqouVfYRbQD", "N6Ee5EDar2YgQqTmegGWth7Tp3qmU7");
        IAcsClient client = new DefaultAcsClient(profile);

        CommonRequest request = new CommonRequest();
        //request.setProtocol(ProtocolType.HTTPS);
        request.setMethod(MethodType.POST);
        request.setDomain("dysmsapi.aliyuncs.com");
        request.setVersion("2017-05-25");
        request.setAction("SendSms");

        request.putQueryParameter("PhoneNumbers", phone); // 手机号
        request.putQueryParameter("SignName", "TaGao"); // 签名
        request.putQueryParameter("TemplateCode", "SMS_250740234"); // 模板CODE
        request.putQueryParameter("TemplateParam", JSONObject.toJSONString(params)); // 验证码map。转换成json

        try {
            CommonResponse response = client.getCommonResponse(request);
            System.out.println(response.getData());
            return response.getHttpResponse().isSuccess();
        } catch (ServerException e) {
            e.printStackTrace();
        } catch (ClientException e) {
            e.printStackTrace();
        }
        return false;
    }

}
