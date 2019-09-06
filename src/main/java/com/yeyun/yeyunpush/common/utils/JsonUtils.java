package com.yeyun.yeyunpush.common.utils;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.util.Assert;
import org.springframework.util.StringUtils;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 *  Json工具
 * </p>
 *
 * @创建人 zhaozq
 * @创建时间 2019/8/19
 */
public class JsonUtils {
	/** 线程安全*/
	private static final ObjectMapper MAPPER = new ObjectMapper()
			.setSerializationInclusion(JsonInclude.Include.NON_NULL);

	private JsonUtils() {
	}

	public static String toJsonString(Object object) {
		Assert.notNull(object, "object cannot be null");
		try {
			return MAPPER.writeValueAsString(object);
		} catch (JsonProcessingException e) {
			throw new RuntimeException(e);
		}
	}

	public static <T> T toObject(String json, Class<T> clazz) {
		Assert.isTrue(!StringUtils.isEmpty(json), "json cannot be empty");
		Assert.notNull(clazz, "clazz cannot be null");
		try {
			return MAPPER.readValue(json, clazz);
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}

	public static <T> List<T> toList(String json, Class<T> clazz) {
		try {
			JavaType javaType = MAPPER.getTypeFactory().constructParametricType(ArrayList.class, clazz);
			List<T> list = MAPPER.readValue(json, javaType);
			return list;
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}
}
