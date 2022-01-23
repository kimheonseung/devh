package com.devh.common.api.response;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import com.devh.common.api.constant.ApiStatus;
import com.devh.common.api.search.vo.PagingVO;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.json.simple.JSONObject;

/**
 * <pre>
 * Description :
 *   api 결과 공통 클래스
 * ===============================================
 * Member fields :
 * 
 * ===============================================
 *
 * Author : HeonSeung Kim
 * Date   : 2021-10-20
 * </pre>
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@SuppressWarnings("unchecked")
public class ApiResponse<T> {

    private LocalDateTime timestamp;
    private int status;
    private String message;
    private String description;

    private PagingVO paging;
    private List<T> dataArray;

    public static <T> ApiResponse<T> success(ApiStatus.Success status) {
        return (ApiResponse<T>) ApiResponse.builder()
            .timestamp(LocalDateTime.now())
            .status(status.getCode())
            .message(status.getStatus())
            .description(status.getDescription())
        .build();
    }

    public static <T> ApiResponse<T> success(ApiStatus.Success status, List<T> dataArray) {
        ApiResponse<T> hResponse = (ApiResponse<T>) ApiResponse.builder()
                                    .timestamp(LocalDateTime.now())
                                    .status(status.getCode())
                                    .message(status.getStatus())
                                    .description(status.getDescription())
                                .build();
        hResponse.setDataArray(dataArray);
        return hResponse;
    }

    public static <T> ApiResponse<T> success(ApiStatus.Success status, List<T> dataArray, PagingVO pagingVO) {
        ApiResponse<T> hResponse = (ApiResponse<T>) ApiResponse.builder()
            .timestamp(LocalDateTime.now())
            .status(status.getCode())
            .message(status.getStatus())
            .description(status.getDescription())
            .paging(pagingVO)
        .build();
        hResponse.setDataArray(dataArray);
        return hResponse;
    }

    public static <T> ApiResponse<T> success(ApiStatus.Success status, T data) {
        ApiResponse<T> hResponse = (ApiResponse<T>) ApiResponse.builder()
                                    .timestamp(LocalDateTime.now())
                                    .status(status.getCode())
                                    .message(status.getStatus())
                                    .description(status.getDescription())
                                .build();
        List<T> dataArray = new ArrayList<>();
        dataArray.add(data);                                
        hResponse.setDataArray(dataArray);
        return hResponse;
    }

    public static <T> ApiResponse<T> clientError(ApiStatus.ClientError status, String stacktrace) {
        return (ApiResponse<T>) ApiResponse.builder()
            .timestamp(LocalDateTime.now())
            .status(status.getCode())
            .message(status.getStatus())
            .description(stacktrace)
        .build();
    }

    public static <T> ApiResponse<T> serverError(ApiStatus.ServerError status, String stacktrace) {
        return (ApiResponse<T>) ApiResponse.builder()
            .timestamp(LocalDateTime.now())
            .status(status.getCode())
            .message(status.getStatus())
            .description(stacktrace)
        .build();
    }

    public static <T> ApiResponse<T> customError(ApiStatus.CustomError status, String stacktrace) {
        return (ApiResponse<T>) ApiResponse.builder()
                .timestamp(LocalDateTime.now())
                .status(status.getCode())
                .message(status.getStatus())
                .description(stacktrace)
                .build();
    }

    public String convertToJsonString() {
        JSONObject json = new JSONObject();
        json.put("timestamp", timestamp);
        json.put("status", status);
        json.put("message", message);
        json.put("description", description);
        json.put("paging", paging);
        json.put("dataArray", dataArray);
        return json.toJSONString();
    }
}
