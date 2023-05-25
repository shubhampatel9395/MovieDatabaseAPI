package com.moviesdbapi.core;

import java.util.HashMap;
import java.util.Map;

public class ResponseEntityUtil {
	public static Map<String, Object> getRes(String success, String successMessage, int code) {
		Map<String, Object> res = new HashMap<>();
		res.put("status", success);
		res.put("message", successMessage);
		res.put("code", code);
		return res;
	}
	
	public static Map<String, Object> getErrorResponse(String errorMessage, int errorCode) {
		Map<String, Object> res = new HashMap<>();
		res.put("status", "error");
		res.put("message", errorMessage);
		res.put("code", errorCode);
		return res;
	}
	
	public static Map<String, Object> getSuccessResponse(String successMessage,int code,Object data,Object meta ) {
		Map<String, Object> res = new HashMap<>();
		res.put("status", "success");
		res.put("message", successMessage);
		res.put("code", code);
		res.put("data", data);
		res.put("meta", meta);
		return res;
	}
}
