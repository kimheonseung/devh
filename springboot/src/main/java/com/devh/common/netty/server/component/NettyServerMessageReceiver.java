package com.devh.common.netty.server.component;

import org.springframework.stereotype.Component;

import com.devh.common.netty.constant.SystemType;
import com.devh.common.netty.interfaces.INettyMessageReceiveHandler;
import com.devh.common.netty.message.NettyRequest;
import com.devh.common.netty.message.NettyResponse;
import com.devh.common.netty.message.constant.Method;

import io.netty.channel.ChannelHandlerContext;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/*
 * <pre>
 * Description : 
 *     여러 클라이언트로부터 수신되는 메세지 송신 객체
 * ===============================
 * Memberfields :
 *     
 * ===============================
 * 
 * Author : HeonSeung Kim
 * Date   : 2021. 11. 8.
 * </pre>
 */
@Component
@RequiredArgsConstructor
@Slf4j
public class NettyServerMessageReceiver implements INettyMessageReceiveHandler {

	private final NettyServerClientGetController nettyServerClientGetController;
	private final NettyServerMessageSender nettyServerMessageSender;
	
	@Override
	public void handleMessageReceive(ChannelHandlerContext ctx, Object message) {
		/* Message Type 'Request' */
		boolean isRequest = message instanceof NettyRequest;
//		boolean isRequest = message.getClass().getCanonicalName().equals(NettyRequest.class.getCanonicalName());
		/* Message Type 'Response' */
		boolean isResponse = message instanceof NettyResponse;
		
		if(isRequest) {
			NettyRequest request = (NettyRequest) message;
			handleRequest(ctx, request);
		}
		else if(isResponse) {
			NettyResponse response = (NettyResponse) message;
			handleResponse(ctx, response);
		}
	}

	@Override
	public void handleRequest(ChannelHandlerContext ctx, NettyRequest nettyRequest) {
		nettyRequest.setChannel(ctx.channel());
		final SystemType systemType = nettyRequest.getSystem().getSystemType();
		final Method method = nettyRequest.getMethod();
		log.info(String.format("[REQUEST] [%s - %s]", nettyRequest.getMethod(), nettyRequest.getCategory()));
		switch (systemType) {
		case CLIENT:
			switch (method) {
			case GET:
				NettyResponse getResponse = nettyServerClientGetController.handleRequest(nettyRequest);
				if(getResponse != null)
					nettyServerMessageSender.handleMessageSend(nettyRequest.getChannel(), getResponse);
				break;
			case POST:
//				NettyResponse postResponse = NettyAgentPostController.getInstance().handleRequest(message);
//				if(postResponse != null)
//					MessageSender.getInstance().handleMessageSend(message.getChannel(), postResponse);
				break;

			default:
				break;
			}
			
			break;

		default:
			break;
		}
	}

	@Override
	public void handleResponse(ChannelHandlerContext ctx, NettyResponse nettyResponse) {
		// TODO Auto-generated method stub
		
	}

}
