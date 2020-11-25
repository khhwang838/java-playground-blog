package com.keichee.util;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class JsonUtil {
    private static final ObjectMapper objectMapper = new ObjectMapper();

    public static <T> T parse(String json, Class<T> c) throws JsonProcessingException {
        return objectMapper.readValue(json, c);
    }
}
