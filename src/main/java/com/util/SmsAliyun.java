package com.util;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.alibaba.fastjson.JSON;
import com.aliyuncs.CommonRequest;
import com.aliyuncs.CommonResponse;
import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.IAcsClient;
import com.aliyuncs.exceptions.ClientException;
import com.aliyuncs.exceptions.ServerException;
import com.aliyuncs.http.MethodType;
import com.aliyuncs.profile.DefaultProfile;

public class SmsAliyun {
	
	public static final String AccessKeyId = "LTAIXUnH8jaKUEmP";
	public static final String AccessKeySecret = "******";
	
	public static final String SignName = "八O四微招聘";
	
	public static void sendSms(List<String> phoneNumberList, 
			String templateCode, Map<String, Object> templateParamMap){
		DefaultProfile profile = DefaultProfile.getProfile("default", AccessKeyId, AccessKeySecret);
        IAcsClient client = new DefaultAcsClient(profile);

        CommonRequest request = new CommonRequest();
        //request.setProtocol(ProtocolType.HTTPS);
        request.setMethod(MethodType.POST);
        request.setDomain("dysmsapi.aliyuncs.com");
        request.setVersion("2017-05-25");
        request.setAction("SendSms");
        
        request.putQueryParameter("SignName", SignName);
        String phoneNumbers = "";
        for ( String phone : phoneNumberList ){
        	if ( ! phoneNumbers.isEmpty() ){
        		phoneNumbers += ",";
        	}
        	phoneNumbers += phone;
        }
        request.putQueryParameter("PhoneNumbers", phoneNumbers);
        request.putQueryParameter("TemplateCode", templateCode);
        request.putQueryParameter( "TemplateParam", JSON.toJSONString(templateParamMap) );
        try {
            CommonResponse response = client.getCommonResponse(request);
            System.out.println(response.getData());
        } catch (ServerException e) {
            e.printStackTrace();
        } catch (ClientException e) {
            e.printStackTrace();
        }
	}

	public static void main(String[] args) {
		List<String> phoneNumberList = Arrays.asList("13917472445");
		String templateCode = "SMS_163620183";
		Map<String, Object> templateParamMap = new HashMap<String, Object>();
		templateParamMap.put("code", "123456");
		SmsAliyun.sendSms(phoneNumberList, templateCode, templateParamMap);
		
    }
}
