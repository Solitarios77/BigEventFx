package com.solitarios.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.solitarios.bean.Category;
import com.solitarios.bean.Result;
import com.solitarios.global.GlobalData;
import com.solitarios.utils.HttpClientUtil;
import com.solitarios.utils.JsonUtil;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CategoryService {
    private ObjectMapper mapper = new ObjectMapper();

    // 添加分类
    public Result add(String categoryName, String categoryAlias) throws IOException {
        String token = (String) GlobalData.get("token");
        if (categoryName == null || categoryAlias == null) throw new IllegalArgumentException("参数不能为空！");
        // 拼接参数
        Map<String, String> params = new HashMap<>();
        params.put("categoryName", categoryName);
        params.put("categoryAlias", categoryAlias);
        String ret = HttpClientUtil.sendPostByJson("http://localhost:8080/category", token, params);
        return JsonUtil.jsonToResult(ret);
    }

    // 获取所有分类
    public Result<List<Category>> list() throws IOException {
        String token = (String) GlobalData.get("token");
        String ret = HttpClientUtil.sendGet("http://localhost:8080/category", token);
        return JsonUtil.jsonToListResult(ret, Category.class);
    }

    // 根据ID获取指定分类
    public Result<Category> detail(Integer id) throws IOException {
        String token = (String) GlobalData.get("token");
        Map<String, String> params = new HashMap<>();
        params.put("id", id.toString());
        String ret = HttpClientUtil.sendGetByUrlEncoded("http://localhost:8080/category/detail", token, params);
        return JsonUtil.jsonToResult(ret, Category.class);
    }

    // 根据ID修改指定分类
    public Result update(Integer id, String categoryName, String categoryAlias) throws IOException {
        String token = (String) GlobalData.get("token");
        if (categoryName == null || categoryAlias == null) throw new IllegalArgumentException("参数不能为空！");
        Map<String, String> params = new HashMap<>();
        params.put("id", id.toString());
        params.put("categoryName", categoryName);
        params.put("categoryAlias", categoryAlias);
        String ret = HttpClientUtil.sendPutByJson("http://localhost:8080/category", token, params);
        return JsonUtil.jsonToResult(ret);
    }

    // 根据ID删除指定分类
    public Result delete(Integer id) throws IOException {
        String token = (String) GlobalData.get("token");
        Map<String, String> params = new HashMap<>();
        params.put("id", id.toString());
        String ret = HttpClientUtil.sendDeleteByUrlEncoded("http://localhost:8080/category", token, params);
        return JsonUtil.jsonToResult(ret);
    }
}
