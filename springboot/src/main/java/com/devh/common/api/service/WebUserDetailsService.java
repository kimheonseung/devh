package com.devh.common.api.service;

import java.util.Optional;

import com.devh.common.api.entity.WebUser;
import com.devh.common.api.repository.WebUserRepository;

import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Slf4j
public class WebUserDetailsService implements UserDetailsService {
    private final WebUserRepository webUserRepository;
    // private final PasswordEncoder passwordEncoder;

    public WebUser getWebUserByUserId(String userId) {

        Optional<WebUser> webUser = webUserRepository.findByUserId(userId);
        if(webUser.isPresent()) {
            log.info("==== User Found ====");
            return webUser.get();
        } else
            throw new UsernameNotFoundException(String.format("[%s] User not found", userId));
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        log.info("USERNAME: " + username);
        Optional<WebUser> webUser = webUserRepository.findById(username);
        if(webUser.isPresent()) {
            log.info("==== User Found ====");
            return webUser.get();
        } else
            throw new UsernameNotFoundException(String.format("[%s] User not found", username));
    }

    
}
