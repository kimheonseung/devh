package com.devh.common.netty.client;

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
public class NettyClientInboundHandler extends ChannelInboundHandlerAdapter {
	
	private final NettyClientMessageReceiver nettyClientMessageReceiver;
	
	@Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
//		log.info(String.format("Channel Avtice - %s", ctx.channel().remoteAddress()));
    }

    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
//    	log.info(String.format("Channel Inactive - %s", ctx.channel().remoteAddress()));
    }


    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
    	nettyClientMessageReceiver.handleMessageReceive(ctx, msg);
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
    	log.info(String.format("Channel Exception - %s %s", ctx.channel().remoteAddress(), cause.getMessage()));
    }
}
