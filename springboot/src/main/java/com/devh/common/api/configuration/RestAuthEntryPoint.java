package com.devh.common.api.configuration;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.devh.common.api.constant.ApiStatus;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

/**
 * <pre>
 * Description :
 *     시큐리티 컨텍스트 내에 존재하는 인증절차 실패 또는 거부시 처리 로직
 * ===============================================
 * Member fields :
 *     
 * ===============================================
 *
 * Author : HeonSeung Kim
 * Date   : 2021-12-06
 * </pre>
 */
@Component
public class RestAuthEntryPoint implements AuthenticationEntryPoint {

    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) throws IOException, ServletException {
//        response.sendError(HttpServletResponse.SC_UNAUTHORIZED, authException.getMessage());
        response.sendError(ApiStatus.CustomError.AUTH_ERROR.getCode(), authException.getMessage());
    }
}
