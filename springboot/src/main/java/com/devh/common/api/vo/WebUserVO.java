package com.devh.common.api.vo;

import java.time.LocalDateTime;
import java.util.List;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Builder
@Getter
@Setter
@ToString
public class WebUserVO {
    private String id;
    private String userId;
    private String name;
    private String password;
    private String email;
    private String phone;
    private LocalDateTime loginAt;
    private String loginIp;
    private int loginFailCount;
    private LocalDateTime loginFailedAt;
    private String loginFailIp;
    private LocalDateTime passwordChangedAt;
    private String accessIp;
    private String description;
    private List<AuthorityVO> authorities;

    @Override
    public boolean equals(Object obj) {
        if(obj instanceof WebUserVO) {
            WebUserVO target = (WebUserVO) obj;
            return this.id.equals(target.getId());
        }
        return super.equals(obj);
    }
}
