package com.devh.common.netty.message;

import java.util.concurrent.BlockingQueue;

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
	protected final BlockingQueue<NettyRequest> mRequestQueue;
	protected final BlockingQueue<NettyResponse> mResponseQueue;
	
	protected Channel mChannel = null;
	protected abstract void setChannel(Channel ch);
	protected abstract boolean send(Object message);
	protected abstract boolean send(Channel ch, Object message);
	
	public AbstractNettyMessageSender(BlockingQueue<NettyRequest> requestQueue, BlockingQueue<NettyResponse> responseQueue) {
		this.mRequestQueue = requestQueue;
		this.mResponseQueue = responseQueue;
	}
	
	protected void putRequest(NettyRequest nettyRequest) {
		try {
			this.mRequestQueue.put(nettyRequest);
		} catch (InterruptedException e) {}
	}
	protected void putResponse(NettyResponse nettyResponse) {
		try {
			this.mResponseQueue.put(nettyResponse);
		} catch (InterruptedException e) {}
	}
	
	protected NettyRequest takeRequest() {
		NettyRequest req = null;
		try {
			req = this.mRequestQueue.take();
		} catch (InterruptedException e) {}
		return req;
	}
	protected NettyResponse takeResponse() {
		NettyResponse res = null;
		try {
			res = this.mResponseQueue.take();
		} catch (InterruptedException e) {}
		return res;
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
