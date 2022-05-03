package com.devh.common.netty.interfaces;

import com.devh.common.netty.message.AbstractNettyMessage;
import com.devh.common.netty.message.NettyResponse;

public interface INettyController {
	NettyResponse handleRequest(AbstractNettyMessage message);
}
