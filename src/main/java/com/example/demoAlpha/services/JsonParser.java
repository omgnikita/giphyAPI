package com.example.demoAlpha.services;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.type.CollectionType;
import com.fasterxml.jackson.databind.type.MapType;

import java.util.List;
import java.util.Map;

public class JsonParser {
	private static final ObjectMapper JSON_MAPPER = new ObjectMapper();

	public static <T> List<T> convertJsonToList(String json, Class<T> valueClass) throws JsonProcessingException {
		CollectionType collectionType = JSON_MAPPER.getTypeFactory().constructCollectionType(List.class, valueClass);
		return JSON_MAPPER.readValue(json, collectionType);
	}

	public static <T> Map<String, T> convertJsonToMap(String json, Class<T> valueClass) throws JsonProcessingException {
		MapType collectionType = JSON_MAPPER.getTypeFactory().constructMapType(Map.class, String.class, valueClass);
		return JSON_MAPPER.readValue(json, collectionType);
	}

	public static Double findDoubleValue(String json, String field) throws JsonProcessingException {
//		System.out.println(field);
//		System.out.println(json);
		return JSON_MAPPER.readTree(json).findValue(field).doubleValue();
	}

	public static <T> String convertListToJson(List<T> list) throws JsonProcessingException {
		return JSON_MAPPER.writeValueAsString(list);
	}
}
