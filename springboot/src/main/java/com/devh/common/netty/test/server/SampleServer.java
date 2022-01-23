package com.devh.common.netty.test.server;


public class SampleServer {
	
	public static void main(String[] args) {
		new SampleServer();
	}
	
	public SampleServer() {
		new Thread(SampleServerInitializer.getInstance()).start();
	}

}
