package com.devh.common.api.service;

import com.devh.common.api.entity.Authority;
import com.devh.common.api.vo.AuthorityVO;

public interface AuthorityService {
    default AuthorityVO entityToVO(Authority entity) {
        return AuthorityVO.builder()
            .code(entity.getCode())
            .auth(entity.getAuth())
            .name(entity.getName())
            .description(entity.getDescription())
            .build();
    }
    default Authority voToEntity(AuthorityVO vo) {
        return Authority.builder()
            .code(vo.getCode())
            .auth(vo.getAuth())
            .name(vo.getName())
            .description(vo.getDescription())
            .build();
    }
}
