package com.devh.common.api.controller;

import javax.annotation.PostConstruct;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.devh.common.netty.client.NettyClientInitializer;
import com.devh.common.netty.server.component.NettyServerInitializer;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("netty")
@RequiredArgsConstructor
public class NettyController {
	
	private final NettyServerInitializer nettyServerInitializer;
	private final NettyClientInitializer nettyClientInitializer;
	
	private Thread nettyServerThread;
	private Thread nettyClientThread;
	
	@PostConstruct
	public void startServer() {
		nettyServerThread = new Thread(nettyServerInitializer);
		nettyServerThread.start();
		nettyClientThread = new Thread(nettyClientInitializer);
		nettyClientThread.start();
	}
	
}
