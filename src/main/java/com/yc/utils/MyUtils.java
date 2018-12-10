package com.yc.utils;

import java.util.HashMap;
import java.util.Map;

public class MyUtils {

	public static Map<String,Object> getRetMsg(String retCode,String retMsg,Object data){
		Map<String,Object> ret = new HashMap<>();
		ret.put("retCode", retCode);
		ret.put("retMsg", retMsg);
		ret.put("data", data);
		return ret;
	}
	
	public static String toJson(Object obj) {
		
		
		return "";
	}
	
	public static Object fromJson(Object obj) {
		
		
		return "";
	}
	
}
