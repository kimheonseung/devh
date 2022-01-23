package com.devh.common.api.repository;

import com.devh.common.api.entity.Authority;

import org.springframework.data.jpa.repository.JpaRepository;

public interface AuthorityRepository extends JpaRepository<Authority, Long> {
        
}
