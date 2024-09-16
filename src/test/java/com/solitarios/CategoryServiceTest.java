package com.solitarios;

import com.solitarios.bean.Result;
import com.solitarios.service.CategoryService;
import org.junit.jupiter.api.Test;

import java.io.IOException;

public class CategoryServiceTest {
    private CategoryService categoryService = new CategoryService();
    private String token = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJjbGFpbXMiOnsiaWQiOjEsInVzZXJuYW1lIjoic29saXRhcmlvcyJ9LCJleHAiOjE3MjU3MTY0OTB9.6iYum7ARYtwgJ5RLTi6OBogS19lKcq83jmq4_0S7ys0";

    // 添加分类测试
    @Test
    public void addTest() throws IOException {
        Result result = categoryService.add("军事", "js");
        System.out.println("result = " + result);
    }

    // 获取所有分类测试
    @Test
    public void listTest() throws IOException {
        Result result = categoryService.list();
        System.out.println("result = " + result);
    }

    // 根据ID获取指定分类测试
    @Test
    public void detailTest() throws IOException {
        Result result = categoryService.detail(3);
        System.out.println("result = " + result);
    }

    // 根据ID修改指定分类测试
    @Test
    public void updateTest() throws IOException {
        Result result = categoryService.update(2, "图书", "bk");
        System.out.println("result = " + result);
    }

    // 根据ID删除指定分类测试
    @Test
    public void deleteTest() throws IOException {
        Result result = categoryService.delete(3);
        System.out.println("result = " + result);
    }
}
