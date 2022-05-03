package com.devh.common.netty.vo;


import java.io.Serializable;
import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

/**
 * <pre>
 * Description :
 *     EQUIP 테이블 모델 대응 VO
 * ===============================
 * Memberfields :
 *     
 * ===============================
 * 
 * Author : HeonSeung Kim
 * Date   : 2021. 12. 10.
 * </pre>
 */
@Builder
@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class EquipVO implements Serializable {
	private static final long serialVersionUID = 2991419788015866822L;
	private String code;
	private String name;
	private String type;
	private String version;
	private boolean enable;
	private String managerCode;
	private String ip;
	private String os;
	private Date registerDate;
	private String description;
	private Long groupId;
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((code == null) ? 0 : code.hashCode());
		return result;
	}
	@Override
	public boolean equals(Object obj) {
		return this.hashCode() == obj.hashCode();
	}
	
}
