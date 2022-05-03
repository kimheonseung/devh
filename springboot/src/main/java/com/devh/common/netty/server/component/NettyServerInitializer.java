package com.devh.common.netty.server.component;

import java.net.InetSocketAddress;
import java.net.SocketAddress;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.devh.common.util.ExceptionUtils;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.serialization.ClassResolvers;
import io.netty.handler.codec.serialization.ObjectDecoder;
import io.netty.handler.codec.serialization.ObjectEncoder;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * <pre>
 * Description :
 *     클라이언트와 통신을 여는 객체
 * ===============================================
 * Member fields :
 *
 * ===============================================
 *
 * Author : HeonSeung Kim
 * Date   : 2022. 3. 24.
 * </pre>
 */
@Slf4j
@Component
@RequiredArgsConstructor
public class NettyServerInitializer extends ChannelInitializer<SocketChannel> implements Runnable {
	
	private EventLoopGroup mBossGroup;
	private EventLoopGroup mWorkerGroup;
	
	@Value("${server.ip}")
	private String serverIp;
	@Value("${server.port}")
	private int serverPort;
	
	private Channel channel;
	
	private final NettyServerInboundHandler nettyServerInboundHandler;
	
	@Override
	protected void initChannel(SocketChannel ch) throws Exception {
		ChannelPipeline p = ch.pipeline();
//		p.addLast(new LoggingHandler(LogLevel.INFO));
		p.addLast("encoder", new ObjectEncoder());
		p.addLast("decoder", new ObjectDecoder(ClassResolvers.softCachingResolver(ClassLoader.getSystemClassLoader())));
		p.addLast("handler", nettyServerInboundHandler);
	}
	
	public void closeGracefully() {
		try {
			channel.closeFuture().sync();
			log.info("Netty Server Closed");
		} catch (Exception e) {
			log.error(ExceptionUtils.stackTraceToString(e));
		}
	}
	
	public void start() {
		this.mBossGroup = new NioEventLoopGroup(1);
		this.mWorkerGroup = new NioEventLoopGroup();
		
		ServerBootstrap serverBootstrap = new ServerBootstrap();
		serverBootstrap.group(mBossGroup, mWorkerGroup)
			.channel(NioServerSocketChannel.class)
			.option(ChannelOption.CONNECT_TIMEOUT_MILLIS, 5*1000)
			.childHandler(this);
		
		final SocketAddress server = new InetSocketAddress(serverIp, serverPort+1);
		
		try {
			channel = serverBootstrap.bind(server).sync().channel();
			log.info("Success to open Netty Server: " + server);
		} catch (Exception e) {
			log.error(ExceptionUtils.stackTraceToString(e));
		}
	}
	
	@Override
	public void run() {
		start();
	}

}
