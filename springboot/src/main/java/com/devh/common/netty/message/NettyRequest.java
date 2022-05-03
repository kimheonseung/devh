package com.devh.common.netty.message;

import java.io.Serializable;

import com.devh.common.netty.message.constant.Category;
import com.devh.common.netty.message.constant.Method;
import com.devh.common.netty.vo.SystemVO;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.SuperBuilder;

@Getter
@Setter
@SuperBuilder
@ToString
@SuppressWarnings("rawtypes")
public class NettyRequest extends AbstractNettyMessage implements Serializable {
	private static final long serialVersionUID = -4024943545440985910L;
	
	private SystemVO system;
	
	public static NettyRequest buildGetRequest(SystemVO system, Category category, NettyData nettyData) {
		return NettyRequest.builder()
				.timestamp(getTimestampString())
				.system(system)
				.method(Method.GET)
				.category(category)
				.nettyData(nettyData)
		.build();
	}
	public static NettyRequest buildPostRequest(SystemVO system, Category category, NettyData nettyData) {
		return NettyRequest.builder()
				.timestamp(getTimestampString())
				.system(system)
				.method(Method.POST)
				.category(category)
				.nettyData(nettyData)
				.build();
	}
	public static NettyRequest buildPutRequest(SystemVO system, Category category, NettyData nettyData) {
		return NettyRequest.builder()
				.timestamp(getTimestampString())
				.system(system)
				.method(Method.PUT)
				.category(category)
				.nettyData(nettyData)
				.build();
	}
	public static NettyRequest buildDeleteRequest(SystemVO system, Category category, NettyData nettyData) {
		return NettyRequest.builder()
				.timestamp(getTimestampString())
				.system(system)
				.method(Method.DELETE)
				.category(category)
				.nettyData(nettyData)
				.build();
	}
	
}
