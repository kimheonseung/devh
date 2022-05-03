package com.devh.common.netty.message;

import java.io.Serializable;

import com.devh.common.netty.message.constant.Category;
import com.devh.common.netty.message.constant.Method;
import com.devh.common.netty.message.constant.Result;
import com.devh.common.netty.message.vo.ExceptionVO;
import com.devh.common.util.ExceptionUtils;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.SuperBuilder;

@Getter
@Setter
@SuperBuilder
@ToString
@SuppressWarnings("rawtypes")
public class NettyResponse extends AbstractNettyMessage implements Serializable {
	private static final long serialVersionUID = -4024943545440985910L;
	private Result result;
	private ExceptionVO exception;
	
	public static NettyResponse buildSuccessResponse(Category category, Method method, NettyData nettyData) {
		return NettyResponse.builder()
				.timestamp(getTimestampString())
				.category(category)
				.method(method)
				.result(Result.SUCCESS)
				.nettyData(nettyData)
		.build();
	}
	public static NettyResponse buildExceptionResponse(Category category, Method method, NettyData nettyData, Exception e) {
		return NettyResponse.builder()
				.timestamp(getTimestampString())
				.category(category)
				.method(method)
				.result(Result.EXCEPTION)
				.exception(ExceptionVO.builder().e(e).stackTrace(ExceptionUtils.stackTraceToString(e)).build())
				.nettyData(nettyData)
				.build();
	}
	public static NettyResponse buildFailResponse(Category category, Method method, NettyData nettyData) {
		return NettyResponse.builder()
				.timestamp(getTimestampString())
				.category(category)
				.method(method)
				.result(Result.FAIL)
				.nettyData(nettyData)
				.build();
	}
}
