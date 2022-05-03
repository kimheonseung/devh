package com.devh.common.netty.server.component;

import org.springframework.stereotype.Component;

import com.devh.common.netty.constant.SystemType;
import com.devh.common.netty.interfaces.INettyController;
import com.devh.common.netty.message.AbstractNettyMessage;
import com.devh.common.netty.message.NettyData;
import com.devh.common.netty.message.NettyRequest;
import com.devh.common.netty.message.NettyResponse;
import com.devh.common.netty.message.constant.Category;
import com.devh.common.netty.message.constant.Method;
import com.devh.common.netty.vo.EquipVO;
import com.devh.common.netty.vo.SystemVO;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
public class NettyServerClientGetController implements INettyController {
	private final Method METHOD = Method.GET;
	
	@Override
	public NettyResponse handleRequest(AbstractNettyMessage message) {
		NettyRequest request = (NettyRequest) message;
		final Category category = message.getCategory();
		
		switch (category) {
		case EQUIP:
			SystemVO systemVO4Equip = request.getSystem();
			final String ip = systemVO4Equip.getIp();
			final SystemType type = systemVO4Equip.getSystemType();
			log.info(String.format("[%s] %s GET EQUIP", type.getTypeCode(), systemVO4Equip.getIp()));
			
			try {
				EquipVO equipVO = EquipVO.builder()
						.code("TEST1234")
						.description("test")
						.enable(true)
						.ip(ip)
						.build();
				return NettyResponse.buildSuccessResponse(category, METHOD, NettyData.buildData(equipVO));
			} catch (Exception e) {
				return NettyResponse.buildExceptionResponse(category, METHOD, NettyData.buildData(request), e);
			}

		default:
			break;
		}
		return null;
	}

}
