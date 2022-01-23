package com.devh.common.api.vo;

import lombok.Builder;
import lombok.Data;
import lombok.ToString;

@Builder
@Data
@ToString
public class WebUserAuthorityVO {
    private Long id;
    private WebUserVO webUser;
    private AuthorityVO authority;
}
