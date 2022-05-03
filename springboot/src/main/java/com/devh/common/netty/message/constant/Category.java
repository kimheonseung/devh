package com.devh.common.netty.message.constant;

/*
 * <pre>
 * Description : 
 *     송수신 관련 카테고리
 * ===============================
 * Memberfields :
 *     
 * ===============================
 * 
 * Author : HeonSeung Kim
 * Date   : 2021. 11. 8.
 * </pre>
 */
public enum Category {
	/* SHUTDOWN */
	SHUTDOWN,
	
	/* EXCEPTION */
	QUERY_EXCEPTION,
	
	/* INTEGRITY */
	INTEGRITY_CHECK,
	INTEGRITY_DEBUG,
	INTEGRITY_SUCCESS,
	INTEGRITY_FAIL,
	
	/* POLICY */
	POLICY,
	/* EQUIP */
	EQUIP,
	/* LOG */
	LOG
	;
}
