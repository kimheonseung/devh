package com.devh.common.netty.message.component;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

import org.springframework.stereotype.Component;

import com.devh.common.netty.message.NettyRequest;
import com.devh.common.netty.message.NettyResponse;

import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

@Component
@Getter
@Slf4j
public class NettyBlockingQueueManager {
	private final BlockingQueue<NettyRequest> requestQueue;
	private final BlockingQueue<NettyResponse> responseQueue;
	
	public NettyBlockingQueueManager() {
		log.info("### NettyBlockingQueueManager created.");
		this.requestQueue = new ArrayBlockingQueue<NettyRequest>(5000, true);
		this.responseQueue = new ArrayBlockingQueue<NettyResponse>(5000, true);
	}
}
