package com.metropolis.common.web;

import com.alibaba.fastjson.JSON;
import com.metropolis.common.entity.Response;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;

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

    public static Response doPostResponse(String url, Map<String,String> params){
        String result = doPost(url, params);
        return JSON.parseObject(result,Response.class);
    }

    public static String doPost(String url,Map<String,String> params){
        CloseableHttpClient httpClient = (CloseableHttpClient) getHttpClient();
        CloseableHttpResponse response = null;
        try{
            return getPostResult(url, params, httpClient,response);
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            close(httpClient,response);
        }
        return null;
    }

    private static String appendParams(String url, Map<String,String> params) throws UnsupportedEncodingException {
        StringBuilder sb = new StringBuilder(url).append(PARAMS_PREFIX);
        for (Map.Entry e:params.entrySet()) {
            sb.append(e.getKey()).append(EQUAL).
                    append(URLEncoder.encode((String)e.getValue(),CHARSET_UTF8)).append(AND);
        }
        return sb.toString();
    }
    private static HttpClient getHttpClient(){ return HttpClientBuilder.create().build(); }

    public static String doGet(String url, Map<String,String> params) {
        CloseableHttpClient httpClient = (CloseableHttpClient) getHttpClient();
        CloseableHttpResponse response = null;
        try {
            HttpGet httpGet = null;
            if(null==params){
                return getGetResult(url,httpClient,httpGet,response);
            }else{
                return getGetResult(appendParams(url, params),httpClient,httpGet,response);
            }
        }catch (IOException e){
            e.printStackTrace();
        }finally {
            close(httpClient,response);
        }
        return null;
    }
    private static void close(CloseableHttpClient httpClient,CloseableHttpResponse response){
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
    private static String getPostResult(String url, Map<String, String> params, CloseableHttpClient httpClient, CloseableHttpResponse response) throws IOException {
        HttpPost post = new HttpPost(url);
        if(Objects.nonNull(params)){
            List<BasicNameValuePair> parameters = new ArrayList<>();
            for (Map.Entry<String,String> entry: params.entrySet()) {
                parameters.add(new BasicNameValuePair(entry.getKey(),entry.getValue()));
            }
            post.setEntity(new UrlEncodedFormEntity(parameters));
        }
        response =httpClient.execute(post);
        return EntityUtils.toString(response.getEntity(), Charset.forName(CHARSET_UTF8));
    }


    private static String getGetResult(String url,CloseableHttpClient httpClient,HttpGet get,CloseableHttpResponse response) throws IOException {
        get = new HttpGet(url);
        response =httpClient.execute(get);
        return EntityUtils.toString(response.getEntity(), Charset.forName(CHARSET_UTF8));
    }
    public static String doPost(String url){return doPost(url,null);}
    public static String doGet(String url){
        return doGet(url,null);
    }


}
