package com.devh.common.netty.interfaces;

import io.netty.channel.Channel;

/*
 * <pre>
 * Description : 
 *     Netty 메세지 송신 관련 인터페이스
 * ===============================
 * Memberfields :
 *     
 * ===============================
 * 
 * Author : HeonSeung Kim
 * Date   : 2021. 11. 8.
 * </pre>
 */
public interface INettyMessageSendHandler {
	void handleMessageSend(Channel ch, Object message);
	void handleMessageSend(Object message);
}
