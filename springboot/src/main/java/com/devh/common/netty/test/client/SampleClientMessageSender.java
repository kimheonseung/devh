package com.devh.common.netty.test.client;

import com.devh.common.datastructure.CircularQueue;
import com.devh.common.netty.message.AbstractNettyMessageSender;
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
public class SampleClientMessageSender extends AbstractNettyMessageSender {
	
	private final int QUEUE_SIZE = 500;
	private final long DEQUEUE_SLEEP_MILLIS = 500L;
	private Thread mDequeueThread;
	
	/* Singleton */
	private static SampleClientMessageSender instance;
	public static SampleClientMessageSender getInstance() {
		if(instance == null)
			instance = new SampleClientMessageSender();
		return instance;
	}
	/* Singleton */
	
	/* Constructor */
	private SampleClientMessageSender() {
		super();
		this.mCircularQueue = new CircularQueue(QUEUE_SIZE);
		startDequeueThread();
	}
	/* Constructor */
	
	private void startDequeueThread() {
		if(this.mDequeueThread == null || !this.mDequeueThread.isAlive()) {
			mDequeueThread = new Thread(new SampleClientMessageSenderThread());
			mDequeueThread.start();
		}
	}
	
	/* Implements */
	@Override
	public void setChannel(Channel ch) {
		this.mChannel = ch;
	}
	
	@Override
	public void handleMessageSend(Channel ch, Object message) {
		/* Server용 */
	}

	@Override
	public void handleMessageSend(Object message) {
		enqueue(message);
	}
	
	@Override
	protected void send(Object message) {
		if(isChannelActive()) {
			ChannelFuture cf = writeAndFlush(message);
			cf.awaitUninterruptibly();
			if(!cf.isSuccess()) {
				try {
					log.warn("Failed to send message. Try to send after 1 seconds... " + cf.cause().getMessage());
					Thread.sleep(1000L);
				} catch (InterruptedException ignored) {}
				send(message);
			}
		}
	}
	
	@Override
	protected void send(Channel ch, Object message) {
		/* Server용 */
	}
	/* Implements */
	
	/* Inner Class Thread */
	private class SampleClientMessageSenderThread implements Runnable {

		@Override
		public void run() {
			Object item;
			while(!Thread.currentThread().isInterrupted()) {
				item = dequeue();
				if(item != null)
					send(item);
				else {
					try {
						Thread.sleep(DEQUEUE_SLEEP_MILLIS);
					} catch(InterruptedException ignored) {}
				}
			}
		}
		
	}
	/* Inner Class Thread */

}
