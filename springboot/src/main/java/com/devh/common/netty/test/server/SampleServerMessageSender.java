package com.devh.common.netty.test.server;

import com.devh.common.netty.message.AbstractNettyMessageSender;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import lombok.extern.slf4j.Slf4j;

/**
 * <pre>
 * Description :
 *     메시지 송신 클래스
 * ===============================
 * Memberfields :
 *     
 * ===============================
 * 
 * Author : HeonSeung Kim
 * Date   : 2021. 11. 9.
 * </pre>
 */
@Slf4j
public class SampleServerMessageSender extends AbstractNettyMessageSender {

	/* Singleton */
	private static SampleServerMessageSender instance;
	public static SampleServerMessageSender getInstance() {
		if(instance == null)
			instance = new SampleServerMessageSender();
		return instance;
	}
	/* Singleton */
	
	/* Implements */
	@Override
	public void handleMessageSend(Channel ch, Object message) {
		send(ch, message);
	}
	
	@Override
	protected void send(Channel ch, Object message) {
		if(isChannelActive(ch)) {
			ChannelFuture cf = ch.writeAndFlush(message);
			cf.awaitUninterruptibly();
			if(!cf.isSuccess()) {
				try {
					log.warn("Failed to send message. Try to send after 1 seconds... " + cf.cause().getMessage());
					Thread.sleep(1000L);
				} catch (InterruptedException ignored) {}
				send(ch, message);
			}
		}
	}

	@Override
	public void handleMessageSend(Object message) {
		/* Client용 */
		
	}

	@Override
	public void setChannel(Channel ch) {
		/* Client용 */
		
	}

	@Override
	protected void send(Object message) {
		/* Client용 */
		
	}
	/* Implements */

	
	
}
