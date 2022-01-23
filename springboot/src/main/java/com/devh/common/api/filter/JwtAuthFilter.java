package com.devh.common.api.filter;

import com.devh.common.api.constant.ApiStatus;
import com.devh.common.api.response.ApiResponse;
import com.devh.common.exception.JwtException;
import com.devh.common.exception.JwtExpiredException;
import com.devh.common.exception.JwtInvalidException;
import com.devh.common.util.component.JwtTokenUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetails;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@RequiredArgsConstructor
@Slf4j
public class JwtAuthFilter extends OncePerRequestFilter {

    private final UserDetailsService userDetailsService;
    private final JwtTokenUtils jwtTokenUtils;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        final String authToken = jwtTokenUtils.getAuthHeaderFromHeader(request);

        if(authToken == null) {
            filterChain.doFilter(request, response);
        } else {
            try {
                final String id = jwtTokenUtils.getIdFromToken(authToken);
                UserDetails userDetails = userDetailsService.loadUserByUsername(id);
                if(jwtTokenUtils.validateToken(authToken, userDetails)) {
                    UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
                    authentication.setDetails(new WebAuthenticationDetails(request));
                    SecurityContextHolder.getContext().setAuthentication(authentication);
                }
                filterChain.doFilter(request, response);
            } catch (JwtExpiredException jwtExpiredException) {
                response.setStatus(ApiStatus.CustomError.TOKEN_EXPIRED_ERROR.getCode());
                response.setContentType("application/json");
                response.getWriter().write(ApiResponse.customError(ApiStatus.CustomError.TOKEN_EXPIRED_ERROR, jwtExpiredException.getMessage()).convertToJsonString());
            } catch (JwtInvalidException jwtInvalidException) {
                response.setStatus(ApiStatus.CustomError.TOKEN_INVALID_ERROR.getCode());
                response.setContentType("application/json");
                response.getWriter().write(ApiResponse.customError(ApiStatus.CustomError.TOKEN_INVALID_ERROR, jwtInvalidException.getMessage()).convertToJsonString());
            } catch (JwtException | AuthenticationException authException) {
                response.setStatus(ApiStatus.CustomError.AUTH_ERROR.getCode());
                response.setContentType("application/json");
                response.getWriter().write(ApiResponse.customError(ApiStatus.CustomError.AUTH_ERROR, authException.getMessage()).convertToJsonString());
            } catch (Exception e) {
                response.setStatus(ApiStatus.ServerError.INTERNAL_SERVER_ERROR.getCode());
                response.setContentType("application/json");
                response.getWriter().write(ApiResponse.serverError(ApiStatus.ServerError.INTERNAL_SERVER_ERROR, e.getMessage()).convertToJsonString());
            }
        }

    }
    
}
