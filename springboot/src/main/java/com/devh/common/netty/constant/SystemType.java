package com.devh.common.netty.constant;

import lombok.Getter;

/*
 * <pre>
 * Description : 
 *     시스템 타입 ( SERVER / CLIENT )
 * ===============================
 * Memberfields :
 *     
 * ===============================
 * 
 * Author : HeonSeung Kim
 * Date   : 2021. 11. 2.
 * </pre>
 */
@Getter
public enum SystemType {
	SERVER("SVR"),
	CLIENT("CLT"),
	MANAGER("MGR"),
	AGENT("AGT");
	
	private String typeCode;
	private SystemType(String typeCode) {
		this.typeCode = typeCode;
	}
}
