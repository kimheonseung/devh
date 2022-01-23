package com.devh.common.netty.interfaces;

import com.devh.common.netty.message.vo.NettyRequestVO;
import com.devh.common.netty.message.vo.NettyResponseVO;

import io.netty.channel.ChannelHandlerContext;

/*
 * <pre>
 * Description : 
 *     Netty 메세지 수신 관련 인터페이스
 * ===============================
 * Memberfields :
 *     
 * ===============================
 * 
 * Author : HeonSeung Kim
 * Date   : 2021. 11. 8.
 * </pre>
 */
public interface INettyMessageReceiveHandler {
	void handleMessageReceive(ChannelHandlerContext ctx, Object message);
	void handleMessageReceive(Object message);
	void distribute(Object message);
	void handleRequest(NettyRequestVO message);
	void handleResponse(NettyResponseVO message);
}
