package com.solitarios;

import com.solitarios.bean.Result;
import com.solitarios.service.ArticleService;
import org.junit.jupiter.api.Test;

import java.io.IOException;

public class ArticleServiceTest {
    private ArticleService articleService = new ArticleService();
    private String token = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJjbGFpbXMiOnsiaWQiOjEsInVzZXJuYW1lIjoic29saXRhcmlvcyJ9LCJleHAiOjE3MjU3MTY0OTB9.6iYum7ARYtwgJ5RLTi6OBogS19lKcq83jmq4_0S7ys0";

    // 添加文章测试
    @Test
    public void addTest() throws IOException {
        Result result = articleService.add("火山爆发...",
                "富士山火山大爆发， 截至目前造成10万人伤亡...",
                "http://127.0.0.1/fuji.png", "已发布", 2);
        System.out.println("result = " + result);
    }

    // 通过条件查询文章测试
    @Test
    public void listTest() throws IOException {
        Result result = articleService.list(1, 5, null, null);
        System.out.println("result = " + result);
    }

    // 根据ID获取指定文章测试
    @Test
    public void detailTest() throws IOException {
        Result result = articleService.detail(2);
        System.out.println("result = " + result);
    }

    // 更新文章测试
    @Test
    public void updateTest() throws IOException {
        Result result = articleService.update(2, "火山爆发...",
                "2富士山火山大爆发， 截至目前造成10万人伤亡...",
                "http://127.0.0.1/fuji.png", "已发布", 2);
        System.out.println("result = " + result);
    }

    // 根据ID删除文章测试
    @Test
    public void deleteTest() throws IOException {
        Result result = articleService.delete(2);
        System.out.println("result = " + result);
    }
}
