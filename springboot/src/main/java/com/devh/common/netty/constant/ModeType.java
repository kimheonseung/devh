package com.devh.common.netty.constant;

import lombok.Getter;

@Getter
public enum ModeType {
	RUN("run"),
	CONFIG("config"),
	VERSION("version"),
	RESET_TEMPLATE("reset_template");
	
	private String value;
	private ModeType(String value) {
		this.value = value;
	}
}
