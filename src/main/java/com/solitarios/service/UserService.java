package com.solitarios.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.solitarios.bean.Result;
import com.solitarios.bean.User;
import com.solitarios.global.GlobalData;
import com.solitarios.utils.HttpClientUtil;
import com.solitarios.utils.JsonUtil;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class UserService {
    private ObjectMapper mapper = new ObjectMapper();

    // 注册
    public Result register(String username, String password) throws IOException {
        if (username == null || password == null) throw new IllegalArgumentException("参数不能为空！");
        Map<String, String> params = new HashMap<>();
        params.put("username", username);
        params.put("password", password);
        String ret = HttpClientUtil.sendPostByUrlEncoded("http://localhost:8080/user/register", params);
        return JsonUtil.jsonToResult(ret);
    }

    // 登录
    public Result login(String username, String password) throws IOException {
        if (username == null || password == null) throw new IllegalArgumentException("参数不能为空！");
        Map<String, String> params = new HashMap<>();
        params.put("username", username);
        params.put("password", password);
        String ret = HttpClientUtil.sendPostByUrlEncoded("http://localhost:8080/user/login", params);
        return JsonUtil.jsonToResult(ret);
    }

    // 获取用户信息
    public Result<User> userInfo() throws IOException {
        String token = (String) GlobalData.get("token");
        String ret = HttpClientUtil.sendGet("http://localhost:8080/user/userinfo", token);
        return JsonUtil.jsonToResult(ret, User.class);
    }

    // 更新用户信息
    public Result update(Integer id, String nickname, String email) throws IOException {
        String token = (String) GlobalData.get("token");
        if (nickname == null || email == null) throw new IllegalArgumentException("参数不能为空！");
        // 拼接参数
        Map<String, String> params = new HashMap<>();
        params.put("id", id.toString());
        params.put("nickname", nickname);
        params.put("email", email);
        String ret = HttpClientUtil.sendPutByJson("http://localhost:8080/user/update", token, params);
        return JsonUtil.jsonToResult(ret);
    }

    // 更新用户头像
    public Result updateAvatar(String url) throws IOException {
        String token = (String) GlobalData.get("token");
        if (url == null) throw new IllegalArgumentException("参数不能为空！");
        String ret = HttpClientUtil.sendPatchByUrlFile("http://localhost:8080/user/updateAvatar",
                token, "avatarUrl", url);
        return JsonUtil.jsonToResult(ret);
    }

    // 更新用户密码
    public Result updatePwd(String oldPwd, String newPwd, String rePwd) throws IOException {
        String token = (String) GlobalData.get("token");
        if (oldPwd == null || newPwd == null || rePwd == null) throw new IllegalArgumentException("参数不能为空！");
        // 拼接参数
        Map<String, String> params = new HashMap<>();
        params.put("old_pwd", oldPwd);
        params.put("new_pwd", newPwd);
        params.put("re_pwd", rePwd);
        String ret = HttpClientUtil.sendPatchByJson("http://localhost:8080/user/updatePwd", token, params);
        return JsonUtil.jsonToResult(ret);
    }
}
