package com.metropolis.common.web;

import com.alibaba.fastjson.JSON;
import com.metropolis.common.entity.Response;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;

import java.io.IOException;
import java.io.InputStream;
import java.net.URLEncoder;
import java.util.Map;

/**
 * @author Pop
 * @date 2020/2/27 18:12
 *
 * 发送 http 请求工具
 */
public class HttpClients {

    private static final String PARAMS_PREFIX="?";
    private static final String AND = "&";
    private static final String EQUAL="=";
    private static final String CHARSET_UTF8="utf-8";

    public static Response doGetResponse(String url, Map<String,String> params){
        String result = doGet(url, params);
        return JSON.parseObject(result,Response.class);
    }

    public static String doGet(String url, Map<String,String> params) {
        CloseableHttpClient httpClient = HttpClientBuilder.create().build();
        CloseableHttpResponse response = null;
        try {
            HttpGet httpGet = null;
            if(null==params){
                return getResult(url,httpClient,httpGet,response);
            }else{
                StringBuilder sb = new StringBuilder(url).append(PARAMS_PREFIX);
                for (Map.Entry e:params.entrySet()) {
                    sb.append(e.getKey()).append(EQUAL).
                            append(URLEncoder.encode((String)e.getValue(),CHARSET_UTF8)).append(AND);
                }
                return getResult(url,httpClient,httpGet,response);
            }
        }catch (IOException e){
            e.printStackTrace();
        }finally {
            try {
                if(null!=httpClient){
                    httpClient.close();
                }
                if(null!=response){
                    response.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    private static String getResult(String url,CloseableHttpClient httpClient,HttpGet get,CloseableHttpResponse response) throws IOException {
        get = new HttpGet(url);
        response =httpClient.execute(get);
        InputStream inputStream=response.getEntity().getContent();
        byte[] bytes = new byte[inputStream.available()];
        inputStream.read(bytes);
        return new String(bytes);
    }

    public static String doGet(String url){
        return doGet(url,null);
    }

}
