package com.devh.common.api.runner;

import com.devh.common.api.entity.Authority;
import com.devh.common.api.entity.WebUser;
import com.devh.common.api.entity.WebUserAuthority;
import com.devh.common.api.repository.AuthorityRepository;
import com.devh.common.api.repository.WebUserAuthorityRepository;
import com.devh.common.api.repository.WebUserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Component
@RequiredArgsConstructor
@Slf4j
public class InitDataRunner implements ApplicationRunner {

    private final AuthorityRepository authorityRepository;
    private final WebUserRepository webUserRepository;
    private final WebUserAuthorityRepository webUserAuthorityRepository;

    @Value("${table.init:false}")
    private boolean init;

    private WebUser createUserDefault(String id, String userId, String password, String accessIp) {
        return WebUser.builder()
            .id(id)
            .userId(userId)
            .name(userId)
            .password(password)
            .email(userId+"@devh.com")
            .phone("010-1234-5678")
            .loginAt(LocalDateTime.now())
            .loginIp("127.0.0.1")
            .loginFailCount(0)
            .loginFailedAt(LocalDateTime.now())
            .loginFailIp(null)
            .passwordChangedAt(LocalDateTime.now())
            .accessIp(accessIp)
            .description(userId+" user")
            .build();
    }

    private WebUserAuthority createWebUserAuthority(Authority authority, WebUser webUser) {
        return WebUserAuthority.builder()
                .authority(authority)
                .webUser(webUser)
                .build();
    }


    public void init() {

        if(init) {
            log.info("Init Table Data.");

            webUserAuthorityRepository.deleteAll();
            authorityRepository.deleteAll();
            webUserRepository.deleteAll();
            log.info("Delete Complete.");

            final BCryptPasswordEncoder bpwe = new BCryptPasswordEncoder();
            final Authority authorityRoot   = Authority.builder().code("AT00").name("루트").auth("root").description("루트").build();
            final Authority authorityAdmin  = Authority.builder().code("AT01").name("관리자").auth("admin").description("관리자").build();
            final Authority authorityNormal = Authority.builder().code("AT98").name("일반").auth("normal").description("일반").build();
            final Authority authorityGuest  = Authority.builder().code("AT99").name("게스트").auth("guest").description("게스트").build();
            final WebUser webUserRoot   = createUserDefault("USR0000000000000", "root", bpwe.encode("!Root1357@$^*!"), "127.0.0.1");
            final WebUser webUserAdmin  = createUserDefault("USR0000000000001", "admin", bpwe.encode("admin1992"), "127.0.0.1");
            final WebUser webUserNormal = createUserDefault("USR0000000000002", "normal", bpwe.encode("1234qwer"), "*.*.*.*");
            final WebUser webUserGuest  = createUserDefault("USR0000000000003", "guest", bpwe.encode("guest"), "*.*.*.*");
            final WebUserAuthority webUserAuthorityRootRoot     = createWebUserAuthority(authorityRoot, webUserRoot);
            final WebUserAuthority webUserAuthorityRootAdmin    = createWebUserAuthority(authorityAdmin, webUserRoot);
            final WebUserAuthority webUserAuthorityRootNormal   = createWebUserAuthority(authorityNormal, webUserRoot);
            final WebUserAuthority webUserAuthorityRootGuest    = createWebUserAuthority(authorityGuest, webUserRoot);
            final WebUserAuthority webUserAuthorityAdminAdmin   = createWebUserAuthority(authorityAdmin, webUserAdmin);
            final WebUserAuthority webUserAuthorityAdminNormal  = createWebUserAuthority(authorityNormal ,webUserAdmin);
            final WebUserAuthority webUserAuthorityAdminGuest   = createWebUserAuthority(authorityGuest ,webUserAdmin);
            final WebUserAuthority webUserAuthorityNormalNormal = createWebUserAuthority(authorityNormal ,webUserNormal);
            final WebUserAuthority webUserAuthorityNormalGuest  = createWebUserAuthority(authorityGuest ,webUserNormal);
            final WebUserAuthority webUserAuthorityGuestGuest   = createWebUserAuthority(authorityGuest ,webUserGuest);

            final List<Authority> authorities = new ArrayList<>();
            authorities.add(authorityRoot);
            authorities.add(authorityAdmin);
            authorities.add(authorityNormal);
            authorities.add(authorityGuest);
            final List<Authority> savedAuthorities = authorityRepository.saveAll(authorities);
            log.info(String.format("Saved Authority %s", savedAuthorities));

            final List<WebUser> webUsers = new ArrayList<>();
            webUsers.add(webUserRoot);
            webUsers.add(webUserAdmin);
            webUsers.add(webUserNormal);
            webUsers.add(webUserGuest);
            final List<WebUser> savedUsers = webUserRepository.saveAll(webUsers);
            log.info(String.format("Saved WebUser %s", savedUsers));

            final List<WebUserAuthority> webUserAuthorities = new ArrayList<>();
            webUserAuthorities.add(webUserAuthorityRootRoot);
            webUserAuthorities.add(webUserAuthorityRootAdmin);
            webUserAuthorities.add(webUserAuthorityRootNormal);
            webUserAuthorities.add(webUserAuthorityRootGuest);
            webUserAuthorities.add(webUserAuthorityAdminAdmin);
            webUserAuthorities.add(webUserAuthorityAdminNormal);
            webUserAuthorities.add(webUserAuthorityAdminGuest);
            webUserAuthorities.add(webUserAuthorityNormalNormal);
            webUserAuthorities.add(webUserAuthorityNormalGuest);
            webUserAuthorities.add(webUserAuthorityGuestGuest);
            final List<WebUserAuthority> savedWebUserAuthorities = webUserAuthorityRepository.saveAll(webUserAuthorities);
            log.info(String.format("Saved WebUserAuthority %s", savedWebUserAuthorities));

        } else {
            log.info("Init Data Skip.");
        }
    }

    @Override
    public void run(ApplicationArguments args) throws Exception {
        init();
    }
}

