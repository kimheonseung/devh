package com.devh.common.netty.interfaces;

import io.netty.channel.Channel;

/**
 * <pre>
 * Description :
 *     통신 연결 관련 인터페이스
 *     연결 직후, 연결해제 직후 행위
 * ===============================
 * Memberfields :
 *     
 * ===============================
 * 
 * Author : HeonSeung Kim
 * Date   : 2021. 11. 9.
 * </pre>
 */
public interface INettyConnectionHandler {
	void afterConnected(Channel channel);
	void afterDisconnected();
}
