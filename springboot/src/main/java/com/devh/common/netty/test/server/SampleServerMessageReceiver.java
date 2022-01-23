package com.devh.common.netty.test.server;

import com.devh.common.datastructure.CircularQueue;
import com.devh.common.netty.message.AbstractNettyMessageReceiver;
import com.devh.common.netty.message.constant.Category;
import com.devh.common.netty.message.constant.Key;
import com.devh.common.netty.message.vo.NettyRequestVO;
import com.devh.common.netty.message.vo.NettyResponseVO;
import com.devh.common.netty.vo.SystemVO;
import io.netty.channel.ChannelHandlerContext;
import lombok.extern.slf4j.Slf4j;

import java.util.HashMap;
import java.util.Set;

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
@Slf4j
public class SampleServerMessageReceiver extends AbstractNettyMessageReceiver {
	
	private final int QUEUE_SIZE = 5000;
	private final long DEQUEUE_SLEEP_MILLIS = 500L;
	private Thread mDequeueThread;
	
	/* Singleton */
	private static SampleServerMessageReceiver instance;
	public static SampleServerMessageReceiver getInstance() {
		if(instance == null)
			instance = new SampleServerMessageReceiver();
		return instance;
	}
	/* Singleton */
	
	/* Constructor */
	private SampleServerMessageReceiver() {
		super();
		this.mCircularQueue = new CircularQueue(QUEUE_SIZE);
		startDequeueThread();
	}
	/* Constructor */
	
	private void startDequeueThread() {
		if(this.mDequeueThread == null || !this.mDequeueThread.isAlive()) {
			mDequeueThread = new Thread(new SampleServerMessageReceiverThread());
			mDequeueThread.start();
		}
	}
	
	/* Implements */
	@Override
	public void handleMessageReceive(Object message) {
		enqueue(message);
	}
	
	@Override
	public void handleMessageReceive(ChannelHandlerContext ctx, Object message) {
		enqueue(message);
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

	@SuppressWarnings("unchecked")
	@Override
	public void handleRequest(NettyRequestVO message) {
		Category category = message.getCategory();
		
		switch (category) {
		case TEST_CATEGORY:
			final SystemVO remoteSystemVO = message.getSystemVO();
			final HashMap<Key, Object> messageValueMap = message.getValueMap();
			final Set<Key> messageKeyKeySet = messageValueMap.keySet();

			if(messageKeyKeySet.contains(Key.TEST_KEY)) {
				break;
			}

			break;
		default:
			break;
		}
		
	}

	@Override
	public void handleResponse(NettyResponseVO message) {
		// TODO Auto-generated method stub
		
	}
	/* Implements */
	
	/* Inner Class Thread */
	private class SampleServerMessageReceiverThread implements Runnable {

		@Override
		public void run() {
			Object item;
			while(!Thread.currentThread().isInterrupted()) {
				item = dequeue();
				if(item != null)
					distribute(item);
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
