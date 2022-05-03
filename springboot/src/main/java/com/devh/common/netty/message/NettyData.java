package com.devh.common.netty.message;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import com.devh.common.api.search.vo.PagingVO;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@Builder
@ToString
@SuppressWarnings("unchecked")
public class NettyData<T> implements Serializable {
	private static final long serialVersionUID = -177455922750545272L;
	private PagingVO paging;
	private List<T> dataList;
	
	
	public static <T> NettyData<T> buildData(List<T> dataList) {
		NettyData<T> result = (NettyData<T>) NettyData.builder().build();
		result.setDataList(dataList);
		return result;
	}
	
	public static <T> NettyData<T> buildData(T data) {
		NettyData<T> result = (NettyData<T>) NettyData.builder().build();
		List<T> list = new ArrayList<T>();
		list.add(data);
		result.setDataList(list);
		return result;
	}
	
	public static <T> NettyData<T> buildData(List<T> dataList, PagingVO paging) {
		NettyData<T> result = (NettyData<T>) NettyData.builder().paging(paging).build();
		result.setDataList(dataList);
		return result;
	}
	
	public static <T> NettyData<T> buildData(T data, PagingVO paging) {
		NettyData<T> result = (NettyData<T>) NettyData.builder().paging(paging).build();
		List<T> list = new ArrayList<T>();
		list.add(data);
		result.setDataList(list);
		return result;
	}
}
