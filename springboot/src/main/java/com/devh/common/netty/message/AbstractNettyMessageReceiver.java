package com.devh.common.netty.message;

import com.devh.common.datastructure.CircularQueue;
import com.devh.common.netty.interfaces.INettyMessageReceiveHandler;

public abstract class AbstractNettyMessageReceiver implements INettyMessageReceiveHandler {
	protected CircularQueue mCircularQueue;
	protected void enqueue(Object message) {
		this.mCircularQueue.enqueue(message);
	}
	protected Object dequeue() {
		return this.mCircularQueue.dequeue();
	}
}
