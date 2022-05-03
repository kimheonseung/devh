package com.devh.common.netty.interfaces;

import com.devh.common.netty.message.NettyRequest;
import com.devh.common.netty.message.NettyResponse;

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
	void handleRequest(ChannelHandlerContext ctx, NettyRequest nettyRequest);
	void handleResponse(ChannelHandlerContext ctx, NettyResponse nettyResponse);
}
