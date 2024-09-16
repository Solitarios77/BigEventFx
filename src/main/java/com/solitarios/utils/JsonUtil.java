package com.solitarios.utils;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.solitarios.bean.PageBean;
import com.solitarios.bean.Result;

import java.util.List;

public class JsonUtil {
    private static final ObjectMapper mapper = new ObjectMapper();

    public static Result jsonToResult(String value) throws JsonProcessingException {
        return mapper.readValue(value, Result.class);
    }

    public static <T> Result<T> jsonToResult(String value, Class<T> elementClass) throws JsonProcessingException {
        JavaType javaType = mapper.getTypeFactory().constructParametricType(Result.class, elementClass);
        return mapper.readValue(value, javaType);
    }

    public static <T> Result<PageBean<T>> jsonToPageBeanResult(String value, Class<T> elementClass) throws JsonProcessingException {
        JavaType javaType = mapper.getTypeFactory().constructParametricType(Result.class,
                mapper.getTypeFactory().constructParametricType(PageBean.class, elementClass));
        return mapper.readValue(value, javaType);
    }


    public static <T> Result<List<T>> jsonToListResult(String json, Class<T> elementClass) throws JsonProcessingException {
        JavaType javaType = mapper.getTypeFactory().constructParametricType(Result.class,
                mapper.getTypeFactory().constructCollectionType(List.class, elementClass));
        return mapper.readValue(json, javaType);
    }
}
