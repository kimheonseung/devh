package com.devh.common.netty.client;

import org.springframework.stereotype.Component;

import com.devh.common.netty.interfaces.INettyMessageReceiveHandler;
import com.devh.common.netty.message.NettyData;
import com.devh.common.netty.message.NettyRequest;
import com.devh.common.netty.message.NettyResponse;
import com.devh.common.netty.message.constant.Category;
import com.devh.common.netty.message.constant.Method;
import com.devh.common.netty.message.constant.Result;
import com.devh.common.netty.vo.EquipVO;
import com.devh.common.netty.vo.SystemVO;

import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
@SuppressWarnings("unchecked")
public class NettyClientMessageReceiver implements INettyMessageReceiveHandler {
	
	public NettyClientMessageReceiver() {
		log.info("### NettyClientMessageReceiver created.");
	}
	
	@Override
	public void handleMessageReceive(ChannelHandlerContext ctx, Object message) {
		/* Message Type 'Request' */
		boolean isRequest = message instanceof NettyRequest;
		/* Message Type 'Response' */
		boolean isResponse = message instanceof NettyResponse;
		
		
		if(isRequest) {
			NettyRequest nettyRequest = (NettyRequest) message;
			handleRequest(ctx, nettyRequest);
		} else if(isResponse) {
			NettyResponse nettyResponse = (NettyResponse) message;
			handleResponse(ctx, nettyResponse);
		}
	}

	/*
	 * <pre>
	 * Description : 
	 *     요청 핸들링
	 * ===============================
	 * Parameters :
	 *     
	 * Returns :
	 *     
	 * Throws :
	 *     
	 * ===============================
	 * 
	 * Author : HeonSeung Kim
	 * Date   : 2021. 11. 8.
	 * </pre>
	 */
	@Override
	public void handleRequest(ChannelHandlerContext ctx, NettyRequest nettyRequest) {
		// TODO Auto-generated method stub
		
	}

	/*
	 * <pre>
	 * Description : 
	 *     응답 핸들링
	 * ===============================
	 * Parameters :
	 *     
	 * Returns :
	 *     
	 * Throws :
	 *     
	 * ===============================
	 * 
	 * Author : HeonSeung Kim
	 * Date   : 2021. 11. 8.
	 * </pre>
	 */
	@Override
	public void handleResponse(ChannelHandlerContext ctx, NettyResponse nettyResponse) {
		final Result result = nettyResponse.getResult();
		final Category category = nettyResponse.getCategory();
		final Channel channel = ctx.channel();
		
		switch (result) {
		case SUCCESS:
			log.info(String.format("[RESPONSE] [%s - %s]: SUCCESS", nettyResponse.getMethod(), nettyResponse.getCategory()));
			switch (category) {
			case EQUIP:
				NettyData<EquipVO> equipData = (NettyData<EquipVO>) nettyResponse.getNettyData();
				log.info("data: " + equipData.getDataList().get(0));
				break;

			default:
				break;
			}
			break;
		case EXCEPTION:
				Object data = nettyResponse.getNettyData().getDataList().get(0);
				if(data instanceof NettyRequest) {
					logException(category, (NettyRequest) nettyResponse.getNettyData().getDataList().get(0), nettyResponse.getException());
				}
				else if(data instanceof SystemVO) {
					log.warn(String.format("[RESPONSE] [%s - %s]: EXCEPTION - %s", Method.GET, Category.EQUIP, nettyResponse.getException().getE().getMessage()));
					System.exit(0);
				}

			break;
		default:
			break;
		}
	}

	private void logException(Category category, NettyRequest request, com.devh.common.netty.message.vo.ExceptionVO exception) {
		log.warn(String.format("[RESPONSE] [%s - %s]: EXCEPTION - %s", request.getMethod(), request.getCategory(), exception.getE().getMessage()));
	}
}
