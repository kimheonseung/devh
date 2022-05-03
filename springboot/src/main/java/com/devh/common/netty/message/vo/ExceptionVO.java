package com.devh.common.netty.message.vo;

import java.io.Serializable;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Builder
@Getter
@Setter
@ToString
public class ExceptionVO implements Serializable {
	private static final long serialVersionUID = -2525428462764913293L;
	private Exception e;
	private String stackTrace;
}
