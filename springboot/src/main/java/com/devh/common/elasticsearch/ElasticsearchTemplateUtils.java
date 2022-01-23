package com.devh.common.elasticsearch;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.file.Path;
import java.nio.file.Paths;

import com.devh.common.elasticsearch.client.ElasticsearchClientProvider;
import com.devh.common.exception.JsonFileReadingException;
import com.devh.common.exception.PropertiesException;
import com.devh.common.util.ExceptionUtils;
import com.devh.common.util.JsonFileReader;
import com.devh.common.util.PropertiesUtils;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import co.elastic.clients.elasticsearch._types.ElasticsearchException;
import co.elastic.clients.elasticsearch.indices.DeleteTemplateRequest;
import co.elastic.clients.elasticsearch.indices.ExistsTemplateRequest;
import lombok.extern.slf4j.Slf4j;

/**
 * <pre>
 * Description :
 *     Elasticsearch Template 관련 처리 클래스
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
public class ElasticsearchTemplateUtils {
	
	/*
	 * <pre>
	 * Description : 
	 *     템플릿을 templates.json 기준으로 초기화
	 * ===============================
	 * Parameters :
	 *     
	 * Returns :
	 *     
	 * Throws :
	 *     
	 * ===============================
	 * 
	 * Author : KimHeonSeung
	 * Date   : 2021. 12. 29.
	 * </pre>
	 */
	public static void resetTemplate() {
		Path templatesJsonPath = Paths.get("conf/templates.json").toAbsolutePath();
		
		try {
			JSONArray templatesJsonArray = JsonFileReader.readJsonFileToJSONArray(templatesJsonPath.toString());
			
			for(Object object : templatesJsonArray) {
				JSONObject jsonObject = (JSONObject) object;
				for(Object key : jsonObject.keySet()) {
					final String templateName = (String) key;
					final JSONObject templateFormat = (JSONObject) jsonObject.get(templateName);
					if(deleteTemplate(templateName)) {
						log.info(String.format("[%s] template put result: %b", templateName, putTemplate(templateName, templateFormat)));
					};
				}
			}
			
		} catch (JsonFileReadingException e) {
			log.error("Failed to reset template.");
			
		}
		
	}
	
	/*
	 * <pre>
	 * Description : 
	 *     템플릿 제거 (Java Client 사용)
	 * ===============================
	 * Parameters :
	 *     
	 * Returns :
	 *     
	 * Throws :
	 *     
	 * ===============================
	 * 
	 * Author : KimHeonSeung
	 * Date   : 2021. 12. 29.
	 * </pre>
	 */
	private static boolean deleteTemplate(String templateName) {
		try {
			DeleteTemplateRequest deleteTemplateRequest = new DeleteTemplateRequest.Builder().name(templateName).build();
			ExistsTemplateRequest existsTemplateRequest = new ExistsTemplateRequest.Builder().name(templateName).build();
			
			final boolean isTemplateExist = ElasticsearchClientProvider.getInstance().getClient().indices().existsTemplate(existsTemplateRequest).value();
			
			if(isTemplateExist) {
				final boolean deleteResult = ElasticsearchClientProvider.getInstance().getClient().indices().deleteTemplate(deleteTemplateRequest).acknowledged();
				log.info(String.format("[%s] template delete result: %b", templateName, deleteResult));
				return deleteResult;
			} else {
				log.info(String.format("[%s] template does not exist.", templateName));
				return true;
			}
		} catch (ElasticsearchException | IOException e) {
			log.error(ExceptionUtils.stackTraceToString(e));
			return true;
		}
	}
	
	/*
	 * <pre>
	 * Description : 
	 *     템플릿 생성 (HttpUrlConnection 이용)
	 * ===============================
	 * Parameters :
	 *     
	 * Returns :
	 *     
	 * Throws :
	 *     
	 * ===============================
	 * 
	 * Author : KimHeonSeung
	 * Date   : 2021. 12. 29.
	 * </pre>
	 */
	private static boolean putTemplate(String templateName, JSONObject templateFormat) {
		boolean result = false;

		PropertiesUtils propertiesUtils = new PropertiesUtils(Paths.get("").toAbsolutePath().toString() + File.separator + "conf" + File.separator + "config.properties");
		String ip;
		int port;
		if(propertiesUtils.load()) {
			try {
				ip   = propertiesUtils.getPropertyValue("elasticsearch.ip");
				port = Integer.parseInt(propertiesUtils.getPropertyValue("elasticsearch.port"));
			} catch (PropertiesException e) {
				ip   = "127.0.0.1";
				port = 9200;
			}
		} else {
			ip   = "127.0.0.1";
			port = 9200;
		}
		
		try {
			URL url = new URL(String.format("http://%s:%d/_template/%s", ip, port, templateName));
			HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
			httpURLConnection.setDoOutput(true);
			httpURLConnection.setRequestMethod("PUT");
			httpURLConnection.setRequestProperty("Content-Type", "application/json");
			
			OutputStream os = httpURLConnection.getOutputStream();
			os.write(templateFormat.toJSONString().getBytes("UTF-8"));
			os.flush();
			
			
			BufferedReader br = new BufferedReader(new InputStreamReader(httpURLConnection.getInputStream(), "UTF-8"));
			StringBuffer connectionResult = new StringBuffer();
			String input = null;
			while((input = br.readLine()) != null)
				connectionResult.append(input);
			
			br.close();
			os.close();
			httpURLConnection.disconnect();
			
			result = true;
		} catch (Exception e) {
			log.error(ExceptionUtils.stackTraceToString(e));
		}
		
		return result;
	}
	
}
