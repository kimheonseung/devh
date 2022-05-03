package com.devh.common.netty.client;

import java.net.InetSocketAddress;
import java.net.SocketAddress;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.devh.common.netty.constant.SystemType;
import com.devh.common.netty.message.NettyData;
import com.devh.common.netty.message.NettyRequest;
import com.devh.common.netty.message.constant.Category;
import com.devh.common.netty.vo.SystemVO;
import com.devh.common.util.ExceptionUtils;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.serialization.ClassResolvers;
import io.netty.handler.codec.serialization.ObjectDecoder;
import io.netty.handler.codec.serialization.ObjectEncoder;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * <pre>
 * Description :
 *     서버와 통신을 여는 객체
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
public class NettyClientInitializer extends ChannelInitializer<SocketChannel> implements Runnable {
	
	private final long RETRY_SECONDS = 5 * 1000L;

	private EventLoopGroup mGroup;

	@Value("${server.ip}")
	private String serverIp;
	@Value("${server.port}")
	private int serverPort;
//	@Value("${client.ip}")
//	private String clientIp;
//	@Value("${client.port}")
//	private int clientPort;
	
	private Channel mManagerChannel;
	
	private final NettyClientMessageSender nettyClientMessageSender;
	private final NettyClientInboundHandler nettyClientInboundHandler;
	
	@Override
	protected void initChannel(SocketChannel ch) throws Exception {
		ChannelPipeline p = ch.pipeline();
//		p.addLast(new LoggingHandler(LogLevel.INFO));
		p.addLast("encoder", new ObjectEncoder());
		p.addLast("decoder", new ObjectDecoder(ClassResolvers.softCachingResolver(ClassLoader.getSystemClassLoader())));
		p.addLast("handler", nettyClientInboundHandler);
	}
	
	public void closeGracefully() {
		try {
			this.mManagerChannel.close().sync();
			log.info("Netty Client Closed");
		} catch (InterruptedException e) {
			log.error(ExceptionUtils.stackTraceToString(e));
		}
	}
	
	public void start() {
		this.mGroup = new NioEventLoopGroup();
		Bootstrap bootstrap = new Bootstrap();
		bootstrap.group(this.mGroup)
			.channel(NioSocketChannel.class)
			.option(ChannelOption.CONNECT_TIMEOUT_MILLIS, 5*1000)
			.handler(this);
		
		final SocketAddress server = new InetSocketAddress(serverIp, serverPort+1);
		final SocketAddress client = new InetSocketAddress(serverIp, serverPort+2);
//		final SocketAddress client = new InetSocketAddress(clientIp, clientPort+2);
		
		while(!Thread.currentThread().isInterrupted()) {
			log.info("Trying to connect server: " + server);
			
			try {
				ChannelFuture channelFuture = bootstrap.connect(server, client).sync();
				Channel connectedChannel = channelFuture.channel();
				/* Channel Set to Message Sender */
				nettyClientMessageSender.setChannel(connectedChannel);
				
				log.info("Success to connect server: " + server);
				this.mManagerChannel = connectedChannel;
				SystemVO systemVO = SystemVO.builder()
						.systemType(SystemType.CLIENT)
						.ip("127.0.0.1")
						.port(1234)
						.ssl(false)
					.build();
				nettyClientMessageSender.handleMessageSend(
						connectedChannel, 
						NettyRequest.buildGetRequest(systemVO, Category.EQUIP, NettyData.buildData(systemVO))
				);
				
				break;
			} catch (Exception e) {
				try {
					Thread.sleep(RETRY_SECONDS);
				} catch (InterruptedException ignored) {}
			}
		}
	}
	
	@Override
	public void run() {
		start();
	}

}
