package com.devh.common.util;

import java.io.FileInputStream;
import java.util.Properties;

import com.devh.common.exception.PropertiesException;
import com.devh.common.interfaces.IPropertyEnumHandler;
import org.apache.commons.lang3.StringUtils;

/*
 * <pre>
 * Description : 
 *     설정파일 값을 읽는 클래스
 * ===============================
 * Memberfields :
 *     
 * ===============================
 * 
 * Author : HeonSeung Kim
 * Date   : 2019. 11. 24.
 * </pre>
 */
public class PropertiesUtils {
	
	private String mFileAbsolutePath;
	private Properties mProperties;
	
	public PropertiesUtils(String fileAbsolutePath) {
		this.mFileAbsolutePath = fileAbsolutePath;
	}
	
	/*
	 * <pre>
	 * Description : 
	 *     설정파일 로딩
	 * ===============================
	 * Parameters :
	 *     
	 * Returns :
	 *     boolean
	 * Throws :
	 *     
	 * ===============================
	 * 
	 * Author : HeonSeung Kim
	 * Date   : 2019. 11. 24.
	 * </pre>
	 */
	public boolean load() {
		boolean result = false;
		
		if(StringUtils.isEmpty(mFileAbsolutePath)) {
			System.out.println(String.format("Check properties file. [%s]", mFileAbsolutePath));
			return result;
		}
		
		
		try(FileInputStream fis = new FileInputStream(mFileAbsolutePath)) {
			
			if(mProperties == null)
				mProperties = new Properties();
			
			mProperties.load(fis);
			
			result = true;
			
		} catch (Exception e) {
			System.out.println(ExceptionUtils.stackTraceToString(e));
			System.out.println(String.format("Failed to load properties file. [%s]", mFileAbsolutePath));
		}
		
		return result;
		
	}
	
	/**
     * <pre>
     * Description
     *     설정파일을 읽어 키에 해당하는 값을 반환
     * ===============================================
     * Parameters
     *     String key
     * Returns
     *     String
     * Throws
     *     
     * ===============================================
     *
     * Author : HeonSeung Kim
     * Date   : 2020-01-24
     * </pre>
     */
	public String getPropertyValue(String key) throws PropertiesException {
		if(mProperties == null)
			throw new PropertiesException("Load properties file before read value.");

		return mProperties.getProperty(key);
	}
	
	/**
	 * <pre>
	 * Description
	 *     설정파일을 읽어 Props키에 해당하는 값을 반환
	 *     값이 없다면 기본값 반환
	 * ===============================================
	 * Parameters
	 *     Props props
	 * Returns
	 *     String
	 * Throws
	 *     
	 * ===============================================
	 *
	 * Author : HeonSeung Kim
	 * Date   : 2020-01-24
	 * </pre>
	 */
	public String getPropertyValue(IPropertyEnumHandler enumHandler) throws PropertiesException {
		if(mProperties == null)
			throw new PropertiesException("Load properties file before read value.");
		
		return mProperties.getProperty(enumHandler.getKey(), enumHandler.getDefaultValue());
	}

	
}
