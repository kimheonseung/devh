package com.devh.common.netty.message.vo;

import java.io.Serializable;
import java.util.HashMap;

import com.devh.common.netty.message.constant.Category;
import com.devh.common.netty.message.constant.Key;
import com.devh.common.netty.vo.SystemVO;

import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

/*
 * <pre>
 * Description : 
 *     Netty Request 래퍼
 * ===============================
 * Memberfields :
 *     
 * ===============================
 * 
 * Author : HeonSeung Kim
 * Date   : 2021. 11. 8.
 * </pre>
 */
@Builder
@Getter
@ToString
public class NettyRequestVO implements Serializable {

	private static final long serialVersionUID = -3561825770646538118L;
	
	private SystemVO systemVO;
	private Category category;
	private final HashMap<Key, Object> valueMap = new HashMap<Key, Object>();
	
	public SystemVO.SystemType getSystemType() {
		return this.systemVO.getSystemType();
	}
	
	public Object getValue(Key key) {
		return this.valueMap.get(key);
	}
	
	public void add(Key key, Object value) {
		this.valueMap.put(key, value);
	}
}
