package com.devh.common.api.service;

import java.util.ArrayList;
import java.util.List;

import com.devh.common.api.entity.WebUser;
import com.devh.common.api.entity.WebUserAuthority;
import com.devh.common.api.vo.AuthorityVO;
import com.devh.common.api.vo.WebUserVO;

public interface WebUserService extends AuthorityService {

    // default WebUser voToEntity(WebUserVO vo) {
    //     return WebUser.builder()
    //         .id(vo.getId())
    //         .userId(vo.getUserId())
    //         .name(vo.getName())
    //         .password(vo.getPassword())
    //         .email(vo.getEmail())
    //         .phone(vo.getPhone())
    //         .loginAt(vo.getLoginAt())
    //         .loginIp(vo.getLoginIp())
    //         .loginFailCount(vo.getLoginFailCount())
    //         .loginFailedAt(vo.getLoginFailedAt())
    //         .passwordChangedAt(vo.getPasswordChangedAt())
    //         .accessIp(vo.getAccessIp())
    //         .description(vo.getDescription())

    //         .build();
    // }

    default WebUserVO entityToVO(WebUser entity) {

        final List<AuthorityVO> authorities = new ArrayList<>();
        final List<WebUserAuthority> webUserAuthorities = entity.getWebUserAuthorities();

        webUserAuthorities.forEach(e -> {
            authorities.add(
                entityToVO(e.getAuthority())
            );
        });

        return WebUserVO.builder()
            .id(entity.getId())
            .userId(entity.getUserId())
            .password(entity.getPassword())
            .name(entity.getName())
            .email(entity.getEmail())
            .phone(entity.getPhone())
            .loginAt(entity.getLoginAt())
            .loginIp(entity.getLoginIp())
            .loginFailCount(entity.getLoginFailCount())
            .loginFailedAt(entity.getLoginFailedAt())
            .loginFailIp(entity.getLoginFailIp())
            .passwordChangedAt(entity.getPasswordChangedAt())
            .accessIp(entity.getAccessIp())
            .description(entity.getDescription())
            .authorities(authorities)
            .build();
    }
}
