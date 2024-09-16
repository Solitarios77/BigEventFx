package com.solitarios.service;

import com.solitarios.bean.Article;
import com.solitarios.bean.PageBean;
import com.solitarios.bean.Result;
import com.solitarios.global.GlobalData;
import com.solitarios.utils.HttpClientUtil;
import com.solitarios.utils.JsonUtil;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class ArticleService {

    // 添加文章
    public Result add(String title, String content,
                      String coverImg, String state, Integer categoryId) throws IOException {
        String token = (String) GlobalData.get("token");
        Map<String, String> params = getArticleParams(title, content, coverImg, state, categoryId);
        String ret = HttpClientUtil.sendPostByJson("http://localhost:8080/article", token, params);
        return JsonUtil.jsonToResult(ret);
    }

    // 通过条件查询文章
    public Result<PageBean<Article>> list(Integer pageNum, Integer pageSize, Integer categoryId, String state) throws IOException {
        String token = (String) GlobalData.get("token");
        if (pageNum == null || pageSize == null) throw new IllegalArgumentException("参数不能为空！");
        Map<String, String> params = new HashMap<>();
        params.put("pageNum", pageNum.toString());
        params.put("pageSize", pageSize.toString());
        if (categoryId != null) params.put("categoryId", categoryId.toString());
        if (state != null) params.put("state", state);
        String ret = HttpClientUtil.sendGetByUrlEncoded("http://localhost:8080/article", token, params);
        return JsonUtil.jsonToPageBeanResult(ret, Article.class);
    }

    // 根据ID获取指定文章
    public Result<Article> detail(Integer id) throws IOException {
        String token = (String) GlobalData.get("token");
        Map<String, String> params = new HashMap<>();
        params.put("id", id.toString());
        String ret = HttpClientUtil.sendGetByUrlEncoded("http://localhost:8080/article/detail", token, params);
        return JsonUtil.jsonToResult(ret, Article.class);
    }

    // 更新文章
    public Result update(Integer id, String title, String content,
                         String coverImg, String state, Integer categoryId) throws IOException {
        String token = (String) GlobalData.get("token");
        Map<String, String> params = getArticleParams(title, content, coverImg, state, categoryId);
        params.put("id", id.toString());
        String ret = HttpClientUtil.sendPutByJson("http://localhost:8080/article", token, params);
        return JsonUtil.jsonToResult(ret);
    }

    private static Map<String, String> getArticleParams(String title, String content, String coverImg, String state, Integer categoryId) {
        if (title == null || content == null || coverImg == null || state == null || categoryId == null)
            throw new IllegalArgumentException("参数不能为空！");
        // 拼接参数
        Map<String, String> params = new HashMap<>();
        params.put("title", title);
        params.put("content", content);
        params.put("coverImg", coverImg);
        params.put("state", state);
        params.put("categoryId", categoryId.toString());
        return params;
    }

    // 根据ID删除文章
    public Result delete(Integer id) throws IOException {
        String token = (String) GlobalData.get("token");
        Map<String, String> params = new HashMap<>();
        params.put("id", id.toString());
        String ret = HttpClientUtil.sendDeleteByUrlEncoded("http://localhost:8080/article", token, params);
        return JsonUtil.jsonToResult(ret);
    }
}
