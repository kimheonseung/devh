package com.devh.common.util;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;

import com.devh.common.exception.JsonFileReadingException;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import lombok.extern.slf4j.Slf4j;

/**
 * <pre>
 * Description :
 *     JSON 확장자 파일을 읽어 JSONObject 또는 JSONArray로 반환해주는 유틸 클래스
 * ===============================
 * Memberfields :
 *     
 * ===============================
 * 
 * Author : HeonSeung Kim
 * Date   : 2021. 12. 29.
 * </pre>
 */
@Slf4j
public class JsonFileReader {
	
	/*
	 * <pre>
	 * Description : 
	 *     json 파일이 배열의 형태인 경우
	 * ===============================
	 * Parameters :
	 *     
	 * Returns :
	 *     JSONArray
	 * Throws :
	 *     
	 * ===============================
	 * 
	 * Author : HeonSeung Kim
	 * Date   : 2021. 12. 29.
	 * </pre>
	 */
	public static JSONArray readJsonFileToJSONArray(String jsonFileAbsolutePath) throws JsonFileReadingException {
		try {
			JSONArray result = new JSONArray();
			
			File jsonFile = new File(jsonFileAbsolutePath);
			if(jsonFile.exists()) {
				BufferedReader br = new BufferedReader(new FileReader(jsonFile));
				JSONParser jsonParser = new JSONParser();
				result = (JSONArray) jsonParser.parse(br);
				br.close();
				
			} else {
				throw new FileNotFoundException(jsonFileAbsolutePath + " does not exists.");
			}
			
			return result;
		} catch (Exception e) {
			log.error(ExceptionUtils.stackTraceToString(e));
			throw new JsonFileReadingException(e.getMessage());
		}
	}
	
	/*
	 * <pre>
	 * Description : 
	 *     json 파일이 하나의 객체만 있는 경우
	 * ===============================
	 * Parameters :
	 *     
	 * Returns :
	 *     JSONObject
	 * Throws :
	 *     
	 * ===============================
	 * 
	 * Author : HeonSeung Kim
	 * Date   : 2021. 12. 29.
	 * </pre>
	 */
	public static JSONObject readJsonFileToJSONObject(String jsonFileAbsolutePath) throws JsonFileReadingException {
		try {
			JSONObject result = new JSONObject();
			
			File jsonFile = new File(jsonFileAbsolutePath);
			if(jsonFile.exists()) {
				BufferedReader br = new BufferedReader(new FileReader(jsonFile));
				JSONParser jsonParser = new JSONParser();
				result = (JSONObject) jsonParser.parse(br);
				br.close();
				
			} else {
				throw new FileNotFoundException(jsonFileAbsolutePath + " does not exists.");
			}
			
			return result;
		} catch (Exception e) {
			log.error(ExceptionUtils.stackTraceToString(e));
			throw new JsonFileReadingException(e.getMessage());
		}
	}
}
