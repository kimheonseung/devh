package com.devh.common.api.repository;

import com.devh.common.api.entity.WebUserAuthority;

import org.springframework.data.jpa.repository.JpaRepository;

public interface WebUserAuthorityRepository extends JpaRepository<WebUserAuthority, Long> {
    
}
