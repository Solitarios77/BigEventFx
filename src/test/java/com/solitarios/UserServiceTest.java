package com.solitarios;

import com.solitarios.bean.Result;
import com.solitarios.service.UserService;
import org.junit.jupiter.api.Test;

import java.io.IOException;

public class UserServiceTest {
    private UserService userService = new UserService();
    private String token = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJjbGFpbXMiOnsiaWQiOjEsInVzZXJuYW1lIjoic29saXRhcmlvcyJ9LCJleHAiOjE3MjU3MTY0OTB9.6iYum7ARYtwgJ5RLTi6OBogS19lKcq83jmq4_0S7ys0";

    // 登录测试
    @Test
    public void loginTest() throws IOException {
        Result result = userService.login("solitarios", "123456");
        System.out.println("result = " + result);
    }

    // 注册测试
    @Test
    public void registerTest() throws IOException {
        Result result = userService.register("solitarios", "123456");
        System.out.println("result = " + result);
    }

    // 获取用户信息测试
    @Test
    public void userInfoTest() throws IOException {
        Result result = userService.userInfo();
        System.out.println("result = " + result);
    }

    // 更新用户信息测试
    @Test
    public void updateTest() throws IOException {
        Result result = userService.update(1, "孤独", "solitarios@163.com");
        System.out.println("result = " + result);
    }

    // 更新用户头像测试
    @Test
    public void updateAvatarTest() throws IOException {
        Result result = userService.updateAvatar("http://127.0.0.1/user.png");
        System.out.println("result = " + result);
    }

    // 更新用户密码测试
    @Test
    public void updatePwdTest() throws IOException {
        Result result = userService.updatePwd("123456", "123456", "123456");
        System.out.println("result = " + result);
    }
}
