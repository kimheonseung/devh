package com.devh.common.netty.test.server;

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
 * Date   : 2020. 3. 24.
 * </pre>
 */
@Slf4j
public class SampleServerInitializer extends ChannelInitializer<SocketChannel> implements Runnable, IPropertiesHandler, INettyConnectionHandler {
	
	private EventLoopGroup mBossGroup;
	private EventLoopGroup mWorkerGroup;
	
	private SystemVO mServerSystemVO;
	
	/* Singleton */
	private static SampleServerInitializer instance;
	public static SampleServerInitializer getInstance() {
		if(instance == null)
			instance = new SampleServerInitializer();
		return instance;
	}
	/* Singleton */
	
	public SystemVO getSystemVO() {
		return this.mServerSystemVO;
	}
	
	@Override
	protected void initChannel(SocketChannel ch) throws Exception {
		ChannelPipeline p = ch.pipeline();
//		p.addLast(new LoggingHandler(LogLevel.INFO));
		p.addLast("encoder", new ObjectEncoder());
		p.addLast("decoder", new ObjectDecoder(ClassResolvers.softCachingResolver(ClassLoader.getSystemClassLoader())));
		p.addLast("handler", new SampleServerInboundHandler());
	}
	
	@Override
	public void run() {
		Path rootPath = Paths.get("");
		File configFile = new File(rootPath.toAbsolutePath() + File.separator + "conf" + File.separator + "config.properties");
		

		if(!this.load(configFile))
			System.exit(0);
		
		this.mBossGroup = new NioEventLoopGroup(1);
		this.mWorkerGroup = new NioEventLoopGroup();
		
		ServerBootstrap serverBootstrap = new ServerBootstrap();
		/* Mina LoggingFilter, MdcInjeckionFilter 등록? */
		serverBootstrap.group(mBossGroup, mWorkerGroup)
			.channel(NioServerSocketChannel.class)
			.option(ChannelOption.CONNECT_TIMEOUT_MILLIS, 5*1000)
			.childHandler(this);
		
		final SocketAddress localServer = new InetSocketAddress(mServerSystemVO.getIp(), mServerSystemVO.getPort());
		
		try {
			/* Server Message Receiver Start */
			SampleServerMessageReceiver.getInstance();
			
			Channel channel = serverBootstrap.bind(localServer).sync().channel();
			
			log.info("Success to open " + localServer);
			
			/* Wait for channel close */
			channel.closeFuture().sync();
			
			log.info(String.format("Closed ", localServer));
		} catch (InterruptedException e) {
			log.error(ExceptionUtils.stackTraceToString(e));
		}
		
	}

	@Override
	public boolean load(File propertiesFile) {
		boolean result = false;
		log.info("Start to load server information...");
		
		final PropertiesUtils propertiesUtils = new PropertiesUtils(propertiesFile.getAbsolutePath());
		
		if(propertiesUtils.load()) {
			final boolean ssl;
			final String serverIp;
			final int serverPort;
			
			try {
				ssl        = "y".equalsIgnoreCase(propertiesUtils.getPropertyValue("ssl"));
				serverIp   = propertiesUtils.getPropertyValue("server.ip");
				serverPort = Integer.parseInt(propertiesUtils.getPropertyValue("server.port"));
				
				this.mServerSystemVO = SystemVO.builder()
						.systemType(SystemVO.SystemType.SERVER)
						.ssl(ssl)
						.ip(serverIp)
						.port(serverPort)
						.build();
				
				if(!this.mServerSystemVO.isValid())
					throw new PropertiesException(String.format("Properties [%s, %s] value is not valid", "server.ip", "server.port"));

				log.info("Finished to load server information.");
				
				result = true;
			} catch(Exception e) {
				log.error(ExceptionUtils.stackTraceToString(e));
			}
		}
		
		return result;
	}

	@Override
	public void afterConnected(Channel channel) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void afterDisconnected() {
		// TODO Auto-generated method stub
		
	}

}
