package com.util;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.http.HttpEntity;
import org.apache.http.HttpHeaders;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.alibaba.fastjson.JSON;

public class HttpUtil {
	
	public static Logger logger = LogManager.getLogger(HttpUtil.class);
	
	public static String doGet(String url) throws IOException{
		CloseableHttpClient httpclient = null;
		CloseableHttpResponse response = null;
		String content = "";
		try{
			httpclient = HttpClients.createDefault();
			HttpGet httpGet = new HttpGet(url);
			httpGet.setHeader(HttpHeaders.USER_AGENT, 
					"Mozilla/5.0 (compatible; MSIE 9.0; Windows NT 6.1; Trident/5.0; 360SE)");
			RequestConfig requestConfig = RequestConfig.custom()    
			        .setConnectTimeout(2000)  
			        .setSocketTimeout(2000).build();    // 2秒
			httpGet.setConfig(requestConfig);
			response = httpclient.execute(httpGet);
			HttpEntity entity = response.getEntity();
			content = EntityUtils.toString(entity);
			EntityUtils.consume(entity);
		}catch ( IOException e ){
			logger.error("接口调用异常", e.getMessage());
			e.printStackTrace();
		}finally{
			try{
				if ( response != null ){
					response.close();
				}
				if ( httpclient != null ){
					httpclient.close();
				}
			}catch (Exception ex) {
				ex.printStackTrace();
			}
		}
	    return content;
	}
	
	public static String doPostForm(String url, Map<String, String> postData, String encode ) throws IOException{  
		CloseableHttpClient httpclient = HttpClients.createDefault();
		HttpPost httpPost = new HttpPost(url);
		httpPost.setHeader(HttpHeaders.USER_AGENT, 
				"Mozilla/5.0 (Linux; U; Android 4.3; en-us; sdk Build/MR1) AppleWebKit/536.23 (KHTML, like Gecko) Version/4.3 Mobile Safari/536.23");
	    List <NameValuePair> nvps = new ArrayList <NameValuePair>();
	    for ( String key : postData.keySet() ){
	    	nvps.add( new BasicNameValuePair( key, postData.get(key) ) );
	    }
	    httpPost.setEntity(new UrlEncodedFormEntity(nvps, encode));
	    
	    CloseableHttpResponse response = httpclient.execute(httpPost);
        
        HttpEntity entity = response.getEntity(); 
        String content = EntityUtils.toString(entity);   
        EntityUtils.consume(entity);
        response.close();
        httpclient.close();
        logger.info(content);
        return content;
	}
	
	public static boolean checkURL(String url){
		
		boolean b = false;
		try {
			CloseableHttpClient httpclient = HttpClients.createDefault();
			HttpGet httpGet = new HttpGet(url);
			RequestConfig requestConfig = RequestConfig.custom().setConnectTimeout(4000).setConnectionRequestTimeout(4000).setSocketTimeout(4000).build();
			httpGet.setConfig(requestConfig);
			CloseableHttpResponse response = httpclient.execute(httpGet);
			Integer i = response.getStatusLine().getStatusCode();
			if(i == 200){
				b = true;
			}
		} catch (ClientProtocolException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return b;
	}
	
	public static String doPostJson(String url, Map<String, Object> postData, String encode ) throws IOException{  
		CloseableHttpClient httpclient = HttpClients.createDefault();
		HttpPost httpPost = new HttpPost(url);
		httpPost.setHeader(HttpHeaders.USER_AGENT, 
				"Mozilla/5.0 (Linux; U; Android 4.3; en-us; sdk Build/MR1) AppleWebKit/536.23 (KHTML, like Gecko) Version/4.3 Mobile Safari/536.23");

		StringEntity strEntity = new StringEntity(JSON.toJSONString(postData), encode);
		strEntity.setContentType("application/json");  
	    httpPost.setEntity(strEntity);
	    
	    CloseableHttpResponse response = httpclient.execute(httpPost);
        
        HttpEntity entity = response.getEntity(); 
        String content = EntityUtils.toString(entity);   
        EntityUtils.consume(entity);
        response.close();
        httpclient.close();
        return content;
	}
	
	public static void main(String[] args) throws IOException {
		
	}
}
