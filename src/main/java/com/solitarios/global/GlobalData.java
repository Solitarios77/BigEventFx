package com.solitarios.global;

import java.util.HashMap;
import java.util.Map;

public class GlobalData {
    private static final Map<String, Object> map = new HashMap<>();

    public static void put(String key, Object value) {
        map.put(key, value);
    }

    public static Object get(String key) {
        return map.get(key);
    }
}
