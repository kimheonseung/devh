package com.devh.common.netty.message;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;

import com.devh.common.netty.message.constant.Category;
import com.devh.common.netty.message.constant.Method;

import io.netty.channel.Channel;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.SuperBuilder;

@Getter
@Setter
@SuperBuilder
@ToString
@SuppressWarnings("rawtypes")
public abstract class AbstractNettyMessage implements Serializable {
	private static final long serialVersionUID = -767700420417344872L;
	private static final SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");
	private Channel channel;
	private String timestamp;
	private Category category;
	private Method method;
	private NettyData nettyData;
	
	protected static String getTimestampString() {
		return sdf.format(new Date());
	}
}
