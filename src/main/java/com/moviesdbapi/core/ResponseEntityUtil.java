package com.moviesdbapi.core;

import java.util.HashMap;
import java.util.Map;

import com.moviesdbapi.exception.MessageConstants;

public class ResponseEntityUtil {
//	public static Map<String, Object> getRes(String success, String successMessage, int code) {
//		Map<String, Object> res = new HashMap<>();
//		res.put(MessageConstants.STATUS, success);
//		res.put(MessageConstants.MESSAGE, successMessage);
//		res.put(MessageConstants.CODE, code);
//		return res;
//	}
//	
//	public static Map<String, Object> getErrorResponse(String errorMessage, int errorCode) {
//		Map<String, Object> res = new HashMap<>();
//		res.put(MessageContants.STATUS, MessageContants.ERROR);
//		res.put(MessageContants.MESSAGE, errorMessage);
//		res.put(MessageContants.CODE, errorCode);
//		return res;
//	}
//	
//	public static Map<String, Object> getSuccessResponse(String successMessage,Object data,Object meta ) {
//		Map<String, Object> res = new HashMap<>();
//		res.put(MessageContants.STATUS, MessageContants.SUCCESS);
//		res.put(MessageContants.MESSAGE, successMessage);
//		res.put(MessageContants.CODE, 200);
//		res.put(MessageContants.DATA, data);
//		res.put(MessageContants.META, meta);
//		return res;
//	}
}
