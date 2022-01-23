package com.devh.common.netty.test.server;

import java.net.InetSocketAddress;

import com.devh.common.netty.ConnectedChannelMap;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class SampleServerInboundHandler extends ChannelInboundHandlerAdapter {
	
	@Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        String ip = ((InetSocketAddress) ctx.channel().remoteAddress()).getAddress().getHostAddress();
        ConnectedChannelMap.getInstance().add(ip, ctx.channel());
    }

    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        String ip = ((InetSocketAddress) ctx.channel().remoteAddress()).getAddress().getHostAddress();
        ConnectedChannelMap.getInstance().remove(ip);
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
    	SampleServerMessageReceiver.getInstance().handleMessageReceive(ctx, msg);
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
    	log.warn(String.format("%s - %s", ctx.channel().remoteAddress(), cause.getMessage()));
    }
}
