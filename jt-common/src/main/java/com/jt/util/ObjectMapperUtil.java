package com.jt.util;

import com.fasterxml.jackson.databind.ObjectMapper;

//编辑工具类实现对象与json转化
public class ObjectMapperUtil {
	private static final ObjectMapper mapper=new ObjectMapper();
	//转换json
	public static String toJson(Object obj) {
		String json = null;
		try {
			json= mapper.writeValueAsString(obj);
		} catch (Exception e) {
			e.printStackTrace();
			//将检查异常转换成运行时异常
			throw new RuntimeException();
		}
		return json;
	} 
	public static <T> T toObj(String json,Class<T> T) {
		T obj =null;
		try {
		obj =  mapper.readValue(json, T);
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException();
		}
		return  obj;
	}
}
