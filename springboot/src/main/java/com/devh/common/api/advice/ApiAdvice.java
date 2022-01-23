package com.devh.common.api.advice;

import com.devh.common.api.constant.ApiStatus;
import com.devh.common.api.response.ApiResponse;
import com.devh.common.exception.DatabaseQueryException;
import com.devh.common.exception.ElasticsearchQueryException;
import com.devh.common.exception.JwtExpiredException;
import com.devh.common.exception.QueryException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * <pre>
 * Description :
 *     API 공통 에러 핸들링
 * ===============================================
 * Member fields :
 *
 * ===============================================
 *
 * Author : HeonSeung Kim
 * Date   : 2021-12-18
 * </pre>
 */
@RestControllerAdvice
@Slf4j
public class ApiAdvice {

    @ExceptionHandler({ElasticsearchQueryException.class})
    public <T> ApiResponse<T> handleElasticsearchQueryException(Exception e) {
        return ApiResponse.customError(ApiStatus.CustomError.ELASTICSEARCH_QUERY_ERROR, e.getMessage());
    }

    @ExceptionHandler({DatabaseQueryException.class})
    public <T> ApiResponse<T> handleDatabaseQueryException(Exception e) {
        return ApiResponse.customError(ApiStatus.CustomError.DATABASE_QUERY_ERROR, e.getMessage());
    }

    @ExceptionHandler({QueryException.class})
    public <T> ApiResponse<T> handleQueryException(Exception e) {
        return ApiResponse.customError(ApiStatus.CustomError.QUERY_ERROR, e.getMessage());
    }

    @ExceptionHandler({AuthenticationException.class})
    public <T> ApiResponse<T> handleAuthException(Exception e) {
        return ApiResponse.customError(ApiStatus.CustomError.AUTH_ERROR, "Authentication Failed.");
    }

    @ExceptionHandler({JwtExpiredException.class})
    public <T> ApiResponse<T> handleTokenExpiredException(Exception e) {
        return ApiResponse.customError(ApiStatus.CustomError.TOKEN_EXPIRED_ERROR, e.getMessage());
    }

    @ExceptionHandler({Exception.class})
    public <T> ApiResponse<T> handleBadRequest(Exception e) {
        return ApiResponse.clientError(ApiStatus.ClientError.BAD_REQUEST, e.getMessage());
    }
}
