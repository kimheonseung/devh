package com.devh.common.netty.test.client;

import java.io.File;
import java.net.InetSocketAddress;
import java.net.SocketAddress;
import java.nio.file.Path;
import java.nio.file.Paths;

import com.devh.common.exception.PropertiesException;
import com.devh.common.interfaces.IPropertiesHandler;
import com.devh.common.netty.interfaces.INettyConnectionHandler;
import com.devh.common.netty.vo.SystemVO;
import com.devh.common.util.ExceptionUtils;
import com.devh.common.util.PropertiesUtils;
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
 * Date   : 2020. 3. 24.
 * </pre>
 */
@Slf4j
public class SampleClientInitializer extends ChannelInitializer<SocketChannel> implements Runnable, IPropertiesHandler, INettyConnectionHandler {
	
	private EventLoopGroup mGroup;
	
	private final long RETRY_SECONDS = 5 * 1000L;
	
	/* 통신에 필요한 정보를 담는 객체 */
	private SystemVO mServerSystemVO;
	private SystemVO mClientSystemVO;
	
	private Channel mServerChannel;
	
	/* Singleton */
	private static SampleClientInitializer instance;
	public static SampleClientInitializer getInstance() {
		if(instance == null)
			instance = new SampleClientInitializer();
		return instance;
	}
	/* Singleton */
	
	public SystemVO getSystemVO() {
		return this.mClientSystemVO;
	}

	@Override
	protected void initChannel(SocketChannel ch) throws Exception {
		ChannelPipeline p = ch.pipeline();
//		p.addLast(new LoggingHandler(LogLevel.INFO));
		p.addLast("encoder", new ObjectEncoder());
		p.addLast("decoder", new ObjectDecoder(ClassResolvers.softCachingResolver(ClassLoader.getSystemClassLoader())));
		p.addLast("handler", new SampleClientInboundHandler());
		
	}
	
	@Override
	public void run() {
		Path rootPath = Paths.get("");
		File configFile = new File(rootPath.toAbsolutePath() + File.separator + "conf" + File.separator + "config.properties");
		
		if(!this.load(configFile))
			System.exit(0);
		
		this.mGroup = new NioEventLoopGroup();
		Bootstrap bootstrap = new Bootstrap();
		/* Mina LoggingFilter, MdcInjeckionFilter 등록? */
		bootstrap.group(this.mGroup)
			.channel(NioSocketChannel.class)
			.option(ChannelOption.CONNECT_TIMEOUT_MILLIS, 5*1000)
			.handler(this);
		
		final SocketAddress remoteServer = new InetSocketAddress(mServerSystemVO.getIp(), mServerSystemVO.getPort());
		final SocketAddress localClient  = new InetSocketAddress(mClientSystemVO.getIp(), mClientSystemVO.getPort());
		
		while(!Thread.currentThread().isInterrupted()) {
			log.info("Trying to connect Server " + remoteServer);
			
			try {
				ChannelFuture channelFuture = bootstrap.connect(remoteServer, localClient).sync();
				Channel connectedChannel = channelFuture.channel();
				/* Channel Set to Message Sender */
				SampleClientMessageSender.getInstance().setChannel(connectedChannel);
				
				/* Message Receiver init */
				SampleClientMessageReceiver.getInstance();
				
				log.info("Success to connect Server " + remoteServer);
				
				afterConnected(connectedChannel);
				
				/* Wait for channel close */
				channelFuture.channel().closeFuture().sync();
				
				log.info("Closed Server " + remoteServer);
			} catch (Exception e) {
				try {
					Thread.sleep(RETRY_SECONDS);
				} catch (InterruptedException ignored) {}
			}
			
		}
		
	}

	@Override
	public boolean load(File propertiesFile) {
		boolean result = false;
		log.info("Start to load client information...");
		
		final PropertiesUtils propertiesUtils = new PropertiesUtils(propertiesFile.getAbsolutePath());
		
		if(propertiesUtils.load()) {
			final boolean ssl;
			final String serverIp, clientIp;
			final int serverPort, clientPort;
			
			try {
				ssl        = "y".equalsIgnoreCase(propertiesUtils.getPropertyValue("ssl"));
				serverIp   = propertiesUtils.getPropertyValue("server.ip");
				clientIp   = propertiesUtils.getPropertyValue("client.ip");
				serverPort = Integer.parseInt(propertiesUtils.getPropertyValue("server.port"));
				clientPort = Integer.parseInt(propertiesUtils.getPropertyValue("client.port"));
				
				this.mServerSystemVO = SystemVO.builder()
						.systemType(SystemVO.SystemType.SERVER)
						.ssl(ssl)
						.ip(serverIp)
						.port(serverPort)
						.build();
				
				if(!this.mServerSystemVO.isValid())
					throw new PropertiesException(String.format("Properties [%s, %d] value is not valid", "server.ip", "server.port"));
				
				this.mClientSystemVO = SystemVO.builder()
						.systemType(SystemVO.SystemType.CLIENT)
						.ssl(ssl)
						.ip(clientIp)
						.port(clientPort)
						.build();
				
				if(!this.mClientSystemVO.isValid())
					throw new PropertiesException(String.format("Properties [%s, %s] value is not valid", "client.ip", "client.port"));
				
				log.info("Finished to load client information.");
				
				result = true;
			} catch (Exception e) {
				log.error(ExceptionUtils.stackTraceToString(e));
			}
			
		}
		
		return result;
	}

	@Override
	public void afterConnected(Channel channel) {
		this.mServerChannel = channel;
	}

	@Override
	public void afterDisconnected() {
		// TODO Auto-generated method stub
		
	}
	
	public void disconnectGracefully() {
		try {
			ChannelFuture cf = this.mServerChannel.close().sync();
			cf.awaitUninterruptibly();
			
			System.exit(0);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
