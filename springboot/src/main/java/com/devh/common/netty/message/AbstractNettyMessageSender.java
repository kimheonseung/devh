package com.devh.common.netty.message;

import com.devh.common.datastructure.CircularQueue;
import com.devh.common.netty.interfaces.INettyMessageSendHandler;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;

/*
 * <pre>
 * Description : 
 *     메세지 송신 추상 클래스
 * ===============================
 * Memberfields :
 *     CircularQueue mCircularQueue 자료 큐
 *     Channel mChannel = null      대상 채널
 * ===============================
 * 
 * Author : HeonSeung Kim
 * Date   : 2021. 11. 8.
 * </pre>
 */
public abstract class AbstractNettyMessageSender implements INettyMessageSendHandler {
	protected CircularQueue mCircularQueue;
	protected Channel mChannel = null;
	protected abstract void setChannel(Channel ch);
	protected abstract void send(Object message);
	protected abstract void send(Channel ch, Object message);
	
	protected void enqueue(Object message) {
		this.mCircularQueue.enqueue(message);
	}
	protected Object dequeue() {
		return this.mCircularQueue.dequeue();
	}
	
	protected boolean isChannelActive() {
		return this.mChannel != null && this.mChannel.isActive()/* && this.mChannel.isWritable()*/;
	}
	
	protected boolean isChannelActive(Channel ch) {
		return ch != null && ch.isActive()/* && this.mChannel.isWritable()*/;
	}
	
	protected ChannelFuture writeAndFlush(Object message) {
		return this.mChannel.writeAndFlush(message);
	}
}
