package com.util;

import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.net.ssl.SSLContext;

import org.apache.http.HttpEntity;
import org.apache.http.HttpHeaders;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.config.Registry;
import org.apache.http.config.RegistryBuilder;
import org.apache.http.conn.ConnectionKeepAliveStrategy;
import org.apache.http.conn.socket.ConnectionSocketFactory;
import org.apache.http.conn.socket.LayeredConnectionSocketFactory;
import org.apache.http.conn.socket.PlainConnectionSocketFactory;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.DefaultConnectionKeepAliveStrategy;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.protocol.HttpContext;
import org.apache.http.util.EntityUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * 连接池 
 */
public class HttpPoolUtil {  
	
	public static Logger logger = LogManager.getLogger(HttpUtil.class);
	
	private static CloseableHttpClient httpClient = null;  
  
	private static CloseableHttpClient getHttpClient() {  
  
        if (httpClient == null) {  
        	synchronized (HttpPoolUtil.class) {
        		if ( httpClient != null ){
        			return httpClient;
        		}
						
	        	LayeredConnectionSocketFactory sslSocketFactory = null;
	            try {
	            	sslSocketFactory = new SSLConnectionSocketFactory(SSLContext.getDefault());
	            } catch (NoSuchAlgorithmException e) {
	                e.printStackTrace();
	            }
	            
	            Registry<ConnectionSocketFactory> registry = RegistryBuilder.<ConnectionSocketFactory>create()
	            		.register("http", new PlainConnectionSocketFactory())
	                    .register("https", sslSocketFactory)   // 支持https协议
	                    .build();
	        	
	            PoolingHttpClientConnectionManager clientManager = new PoolingHttpClientConnectionManager(registry);  
	            // 连接池最大连接数  
	            clientManager.setMaxTotal(300);  
	            // 单条链路最大连接数（一个ip + 一个端口为一个链路）  
	            clientManager.setDefaultMaxPerRoute(100);  
	  
	            ConnectionKeepAliveStrategy keepAliveStrategy = new DefaultConnectionKeepAliveStrategy() {  
	                @Override
	                public long getKeepAliveDuration(HttpResponse response, HttpContext context) {  
	                    long keepAlive = super.getKeepAliveDuration(response, context);  
	                    if (keepAlive == -1) {  
	                        keepAlive = 60000;  
	                    }  
	                    return keepAlive;  
	                }  
	            };  
	  
	             httpClient = HttpClients.custom()
	            		 .setConnectionManager(clientManager)
	            		 .setKeepAliveStrategy(keepAliveStrategy)
	            		 .build();  
	        }  
        }
  
        return httpClient;  
    }  
    
    public static String doGet(String url) throws IOException{
		CloseableHttpClient httpclient = null;
		CloseableHttpResponse response = null;
		String content = "";
		try{
			//httpclient = HttpClients.createDefault();
			httpclient = HttpPoolUtil.getHttpClient();
			HttpGet httpGet = new HttpGet(url);
			httpGet.setHeader(HttpHeaders.USER_AGENT, 
					"Mozilla/5.0 (compatible; MSIE 9.0; Windows NT 6.1; Trident/5.0; 360SE)");
			RequestConfig requestConfig = RequestConfig.custom()    
			        .setConnectTimeout(50)  
			        .setSocketTimeout(50).build();    
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
				/*if ( httpclient != null ){
					httpclient.close();  // 无需关闭，否则会关闭连接池
				}*/
			}catch (Exception ex) {
				ex.printStackTrace();
			}
		}
	    return content;
	}
    
    public static String doPost(String url){
    	return doPost(url, null, null);
    }
    
    public static String doPost(String url, Map<String, String> postData, String encode ){  
    	
    	if ( postData == null ){
    		postData = new HashMap<String, String>();
    	}
    	if ( encode == null ){
    		encode = "utf-8";
    	}
    	
		CloseableHttpClient httpclient = null;
		CloseableHttpResponse response = null;
		String content = "";
		try{
			//httpclient = HttpClients.createDefault();
			httpclient = HttpPoolUtil.getHttpClient();
			HttpPost httpPost = new HttpPost(url);
			httpPost.setHeader(HttpHeaders.USER_AGENT, 
					"Mozilla/5.0 (Linux; U; Android 4.3; en-us; sdk Build/MR1) AppleWebKit/536.23 (KHTML, like Gecko) Version/4.3 Mobile Safari/536.23");
		    List <NameValuePair> nvps = new ArrayList <NameValuePair>();
		    for ( String key : postData.keySet() ){
		    	nvps.add( new BasicNameValuePair( key, postData.get(key) ) );
		    }
		    httpPost.setEntity(new UrlEncodedFormEntity(nvps, encode));
		    RequestConfig requestConfig = RequestConfig.custom()    
			        .setConnectTimeout(50)  
			        .setSocketTimeout(50).build();    
		    httpPost.setConfig(requestConfig);
		    
		    response = httpclient.execute(httpPost);
	        
	        HttpEntity entity = response.getEntity(); 
	        content = EntityUtils.toString(entity);   
	        EntityUtils.consume(entity);
		}catch(IOException e){
			logger.error("接口调用异常", e.getMessage());
			//e.printStackTrace();
		}finally{
			try{
				if ( response != null ){
					response.close();
				}
				/*if ( httpclient != null ){
					httpclient.close();  // 无需关闭，否则会关闭连接池
				}*/
			}catch (Exception ex) {
				ex.printStackTrace();
			}
		}
        return content;
	}
  
}  
