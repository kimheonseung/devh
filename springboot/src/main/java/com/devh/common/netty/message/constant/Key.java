package com.devh.common.netty.message.constant;

/*
 * <pre>
 * Description : 
 *     송수신 관련 밸류 맵 키
 * ===============================
 * Memberfields :
 *     
 * ===============================
 * 
 * Author : HeonSeung Kim
 * Date   : 2021. 11. 8.
 * </pre>
 */
public enum Key {
	
	/* EXCEPTION */
	EXCEPTION,
	
	/* SHUTDOWN */
	SHUTDOWN_MESSAGE,
	
	/* Policy */
	POLICY_VO,
	
	/* Integrity */
	INTEGRITY_VO_SET,
	INTEGRITY_DEBUG,
	INTEGRITY_RESULT;
	
}
