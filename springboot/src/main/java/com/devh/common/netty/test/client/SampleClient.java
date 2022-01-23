package com.devh.common.netty.test.client;

public class SampleClient {
	
	public static void main(String[] args) {
		new SampleClient();
	}
	
	public SampleClient() {
		new Thread(SampleClientInitializer.getInstance()).start();
	}
	
}
