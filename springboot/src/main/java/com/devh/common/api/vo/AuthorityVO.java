package com.devh.common.api.vo;

import lombok.Builder;
import lombok.Data;
import lombok.ToString;

@Builder
@Data
@ToString
public class AuthorityVO {
    private String code;
    private String auth;
    private String name;
    private String description;
}
