package com.solitarios.utils;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.solitarios.bean.Result;
import org.apache.http.HttpEntity;
import org.apache.http.NameValuePair;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.*;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class HttpClientUtil {
    // Json转换器
    private static ObjectMapper mapper = new ObjectMapper();
    // 添加设置
    private static RequestConfig requestConfig = RequestConfig.custom()
            // 设置连接超时时间
            .setConnectTimeout(3000)
            // 设置请求超时时间
            .setConnectionRequestTimeout(3000)
            // 设置socket读写超时时间
            .setSocketTimeout(3000)
            // 设置是否允许重定向
            .setRedirectsEnabled(true).build();

    public static String sendPostByUrlEncoded(String url, Map<String, String> params) throws IOException {
        // 创建HttpPost请求
        HttpPost httpPost = new HttpPost(url);
        // 设置参数
        httpPost.setConfig(requestConfig);
        // 设置请求参数
        List<NameValuePair> list = new ArrayList<>();
        for (String key : params.keySet()) {
            list.add(new BasicNameValuePair(key, params.get(key)));
        }
        httpPost.setEntity(new UrlEncodedFormEntity(list, "UTF-8"));
        // 添加请求头
        httpPost.setHeader("Content-Type", "application/x-www-form-urlencoded;charset=utf-8");
        return execute(httpPost);
    }

    public static String sendGet(String url, String token) throws IOException {
        // 创建HttpGet请求
        HttpGet httpGet = new HttpGet(url);
        // 设置参数
        httpGet.setConfig(requestConfig);
        // 设置请求token
        setToken(httpGet, token);
        return execute(httpGet);
    }

    public static String sendGetByUrlEncoded(String url, String token, Map<String, String> params) throws IOException {
        // 设置请求参数
        String paramString = getParamString(params);
        // 创建HttpGet请求
        HttpGet httpGet = new HttpGet(url + "?" + paramString);
        // 设置参数
        httpGet.setConfig(requestConfig);
        // 设置请求token
        setToken(httpGet, token);
        return execute(httpGet);
    }

    public static String sendDeleteByUrlEncoded(String url, String token, Map<String, String> params) throws IOException {
        // 设置请求参数
        String paramString = getParamString(params);
        // 创建HttpDelete请求
        HttpDelete httpDelete = new HttpDelete(url + "?" + paramString);
        // 设置参数
        httpDelete.setConfig(requestConfig);
        // 设置请求token
        setToken(httpDelete, token);
        return execute(httpDelete);
    }

    private static String getParamString(Map<String, String> params) throws IOException {
        List<NameValuePair> list = new ArrayList<>();
        for (String key : params.keySet()) {
            list.add(new BasicNameValuePair(key, params.get(key)));
        }
        return EntityUtils.toString(new UrlEncodedFormEntity(list, "UTF-8"));
    }

    public static String sendPutByJson(String url, String token, Map<String, String> params) throws IOException {
        // 创建HttpPut请求
        HttpPut httpPut = new HttpPut(url);
        // 设置参数
        httpPut.setConfig(requestConfig);
        // 添加请求头
        httpPut.setHeader("Content-Type", "application/json");
        // 设置请求参数
        String json = mapper.writerWithDefaultPrettyPrinter().writeValueAsString(params);
        httpPut.setEntity(new StringEntity(json, "UTF-8"));
        // 设置请求token
        setToken(httpPut, token);
        return execute(httpPut);
    }

    public static String sendPostByJson(String url, String token, Map<String, String> params) throws IOException {
        // 创建HttpPut请求
        HttpPost httpPost = new HttpPost(url);
        // 设置参数
        httpPost.setConfig(requestConfig);
        // 添加请求头
        httpPost.setHeader("Content-Type", "application/json");
        // 设置请求参数
        String json = mapper.writerWithDefaultPrettyPrinter().writeValueAsString(params);
        httpPost.setEntity(new StringEntity(json, "UTF-8"));
        // 设置请求token
        setToken(httpPost, token);
        return execute(httpPost);
    }

    public static String sendPatchByJson(String url, String token, Map<String, String> params) throws IOException {
        // 创建HttpPatch请求
        HttpPatch httpPatch = new HttpPatch(url);
        // 设置参数
        httpPatch.setConfig(requestConfig);
        // 添加请求头
        httpPatch.setHeader("Content-Type", "application/json");
        // 设置请求参数
        String json = mapper.writerWithDefaultPrettyPrinter().writeValueAsString(params);
        httpPatch.setEntity(new StringEntity(json, "UTF-8"));
        // 设置请求token
        setToken(httpPatch, token);
        return execute(httpPatch);
    }

    public static String sendPatchByUrlFile(String url, String token, String urlFileName, String urlFileValue) throws IOException {
        // 创建HttpPatch请求
        HttpPatch httpPatch = new HttpPatch(url);
        // 设置参数
        httpPatch.setConfig(requestConfig);
        // 设置请求参数
        List<NameValuePair> list = new ArrayList<>();
        list.add(new BasicNameValuePair(urlFileName, urlFileValue));
        httpPatch.setEntity(new UrlEncodedFormEntity(list, "UTF-8"));
        // 设置请求token
        setToken(httpPatch, token);
        return execute(httpPatch);
    }

    private static void setToken(HttpUriRequest request, String token) {
        if (token != null) {
            request.setHeader("Authorization", token);
        }
    }

    private static String execute(HttpUriRequest request) throws IOException {
        // 获取结果
        String result = null;
        try {
            // 创建客户端
            CloseableHttpClient closeableHttpClient = HttpClientBuilder.create().build();
            // 由客户端发送请求
            CloseableHttpResponse response = closeableHttpClient.execute(request);
            // 获取响应实体
            HttpEntity responseEntity = response.getEntity();
            if (responseEntity != null) {
                result = EntityUtils.toString(responseEntity, "UTF-8");
            }
        } catch (IOException e) {
            throw e;
        }
        return result;
    }
}
