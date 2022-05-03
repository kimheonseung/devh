package com.devh.common.netty.vo;

import java.io.Serializable;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.validator.routines.InetAddressValidator;

import com.devh.common.netty.constant.SystemType;

import lombok.Builder;
import lombok.Getter;
import lombok.ToString;
import lombok.extern.slf4j.Slf4j;

/*
 * <pre>
 * Description : 
 *     시스템에 대한 설정값을 갖는 VO
 * ===============================
 * Memberfields :
 *     
 * ===============================
 * 
 * Author : HeonSeung Kim
 * Date   : 2021. 11. 2.
 * </pre>
 */
@Builder
@Getter
@ToString
@Slf4j
public class SystemVO implements Serializable {
	
	private static final long serialVersionUID = -4483210663545628936L;
	
	private SystemType systemType;
	private String ip;
	private int port;
	private boolean ssl;
	
	/*
	 * <pre>
	 * Description : 
	 *     유효성 체크
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
	 * Date   : 2021. 11. 2.
	 * </pre>
	 */
	public boolean isValid() {
		
		boolean isSystemTypeValid = this.systemType != null;
		boolean isIpValid         = StringUtils.isNotEmpty(this.ip) && InetAddressValidator.getInstance().isValidInet4Address(this.ip);
		boolean isPortValid       = this.port > 0 && this.port < 65536;
		
		if(!isSystemTypeValid)
			log.warn("Invalid SystemType. [NULL]");
		if(!isIpValid)
			log.warn(String.format("Invalid IP. [%s]", this.ip));
		if(!isPortValid)
			log.warn(String.format("Invalid Port. [%d]", this.port));
		
		return isSystemTypeValid && isIpValid && isPortValid;
	}
}
