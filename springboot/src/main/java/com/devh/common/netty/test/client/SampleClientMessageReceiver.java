package com.devh.common.netty.test.client;

import com.devh.common.netty.message.AbstractNettyMessageReceiver;
import com.devh.common.netty.message.constant.Category;
import com.devh.common.netty.message.constant.Key;
import com.devh.common.netty.message.vo.NettyRequestVO;
import com.devh.common.netty.message.vo.NettyResponseVO;
import io.netty.channel.ChannelHandlerContext;
import lombok.extern.slf4j.Slf4j;

import java.util.HashMap;

@Slf4j
public class SampleClientMessageReceiver extends AbstractNettyMessageReceiver {
	
	/* Singleton */
	private static SampleClientMessageReceiver instance;
	public static SampleClientMessageReceiver getInstance() {
		if(instance == null)
			instance = new SampleClientMessageReceiver();
		return instance;
	}
	/* Singleton */
	
	@Override
	public void handleMessageReceive(Object message) {
		distribute(message);
	}
	
	@Override
	public void handleMessageReceive(ChannelHandlerContext ctx, Object message) {
		/* Server용 */
	}

	@Override
	public void distribute(Object message) {
		
		/* Message Type 'Request' */
		boolean isRequest = message instanceof NettyRequestVO;
		/* Message Type 'Response' */
		boolean isResponse = message instanceof NettyResponseVO;
		
		
		if(isRequest)
			handleRequest((NettyRequestVO) message);
		else if(isResponse)
			handleResponse((NettyResponseVO) message);
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
	public void handleRequest(NettyRequestVO message) {
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
	public void handleResponse(NettyResponseVO message) {
		Category category = message.getCategory();
		
		switch (category) {
		case TEST_CATEGORY:
			final HashMap<Key, Object> messageValueMap = message.getValueMap();
			final boolean isNormal = (boolean) messageValueMap.get(Key.TEST_KEY);

			break;

		default:
			break;
		}
		
	}

	

}
