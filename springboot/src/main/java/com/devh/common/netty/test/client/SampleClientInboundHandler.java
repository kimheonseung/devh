package com.devh.common.netty.test.client;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import lombok.extern.slf4j.Slf4j;

/*
 * <pre>
 * Description : 
 *     Netty Inbound 핸들러
 * ===============================
 * Memberfields :
 *     
 * ===============================
 * 
 * Author : HeonSeung Kim
 * Date   : 2021. 11. 3.
 * </pre>
 */
@Slf4j
public class SampleClientInboundHandler extends ChannelInboundHandlerAdapter {
	
	@Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
    }

    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
    }


    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
    	SampleClientMessageReceiver.getInstance().handleMessageReceive(msg);
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
    	log.info(String.format("Channel Exception - %s %s", ctx.channel().remoteAddress(), cause.getMessage()));
    }
}
