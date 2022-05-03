package com.devh.common.netty.message;

import java.util.concurrent.BlockingQueue;

import com.devh.common.netty.interfaces.INettyMessageReceiveHandler;

public abstract class AbstractNettyMessageReceiver implements INettyMessageReceiveHandler {
	protected final BlockingQueue<NettyRequest> mRequestQueue;
	protected final BlockingQueue<NettyResponse> mResponseQueue;
	
	public AbstractNettyMessageReceiver(BlockingQueue<NettyRequest> requestQueue, BlockingQueue<NettyResponse> responseQueue) {
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
}
