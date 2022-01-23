package com.devh.common.api.controller;

import com.devh.common.api.constant.ApiStatus;
import com.devh.common.api.entity.WebUser;
import com.devh.common.api.request.AuthRequest;
import com.devh.common.api.response.ApiResponse;
import com.devh.common.api.response.AuthResponse;
import com.devh.common.api.service.WebUserDetailsService;
import com.devh.common.exception.JwtException;
import com.devh.common.util.component.JwtTokenUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth/webuser")
@RequiredArgsConstructor
@Slf4j
public class WebUserController {
    private final AuthenticationManager authenticationManager;
    private final JwtTokenUtils jwtTokenUtils;
    private final WebUserDetailsService userDetailsService;

    @PostMapping("/login")
    public ApiResponse<AuthResponse> login(@RequestBody AuthRequest authRequest) throws JwtException {
        log.info("[POST] /api/v1/webuser/login... " + authRequest);

        WebUser tempWebUSer = userDetailsService.getWebUserByUserId(authRequest.getUserId());
        final Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(tempWebUSer.getUsername(), authRequest.getPassword()));
        log.info("AUTHENTICATED " + authentication);

        SecurityContextHolder.getContext().setAuthentication(authentication);
        WebUser webUser = (WebUser) authentication.getPrincipal();

        String jwtToken = jwtTokenUtils.generateToken(webUser.getUsername());
        log.info("TOKEN GENERATED " + jwtToken);

        return ApiResponse.success(ApiStatus.Success.OK, AuthResponse.builder().token(jwtToken).build());
    }
}
