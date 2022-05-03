package com.devh.common.netty.server.component;

import org.springframework.stereotype.Component;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.channel.ChannelHandler.Sharable;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Sharable
@Component
@RequiredArgsConstructor
public class NettyServerInboundHandler extends ChannelInboundHandlerAdapter {
	
	private final NettyServerMessageReceiver nettyServerMessageReceiver;
	
	@Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
    }

    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
    	nettyServerMessageReceiver.handleMessageReceive(ctx, msg);
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
    	log.warn(String.format("%s - %s", ctx.channel().remoteAddress(), cause.getMessage()));
    }
}
