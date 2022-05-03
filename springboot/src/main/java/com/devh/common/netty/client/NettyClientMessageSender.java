package com.devh.common.netty.client;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.devh.common.netty.message.AbstractNettyMessageSender;
import com.devh.common.netty.message.component.NettyBlockingQueueManager;

import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import lombok.extern.slf4j.Slf4j;

/*
 * <pre>
 * Description : 
 *     메세지 송신 클래스
 * ===============================
 * Memberfields :
 *     
 * ===============================
 * 
 * Author : HeonSeung Kim
 * Date   : 2021. 11. 8.
 * </pre>
 */
@Slf4j
@Component
public class NettyClientMessageSender extends AbstractNettyMessageSender {
	
	public NettyClientMessageSender(@Autowired NettyBlockingQueueManager nettyBlockingQueueManager) {
		super(nettyBlockingQueueManager.getRequestQueue(), nettyBlockingQueueManager.getResponseQueue());
		log.info("### NettyClientMessageSender created.");
	}

	@Override
	public void handleMessageSend(Channel ch, Object message) {
		send(ch, message);
	}

	@Override
	public void handleMessageSend(Object message) {
		send(message);
	}

	@Override
	protected void setChannel(Channel ch) {
		this.mChannel = ch;
	}

	@Override
	protected boolean send(Object message) {
		if(isChannelActive()) {
			ChannelFuture cf = writeAndFlush(message);
			cf.awaitUninterruptibly();
			if(!cf.isSuccess()) {
				try {
					log.warn("Failed to send message. Try to send after 1 seconds... " + cf.cause().getMessage());
					Thread.sleep(1000L);
				} catch (InterruptedException ignored) {}
				return send(message);
			}
			return true;
		} else {
			return false;
		}
	}

	@Override
	protected boolean send(Channel ch, Object message) {
		return send(message);
	}

}
