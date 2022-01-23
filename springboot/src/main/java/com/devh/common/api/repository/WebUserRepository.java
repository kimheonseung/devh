package com.devh.common.api.repository;

import java.util.Optional;

import com.devh.common.api.entity.WebUser;

import org.springframework.data.jpa.repository.JpaRepository;

public interface WebUserRepository extends JpaRepository<WebUser, String> {
    Optional<WebUser> findByName(String name);
    Optional<WebUser> findByUserId(String userId);
}
