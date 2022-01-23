package com.devh.common.api.constant;

import lombok.Getter;

/**
 * <pre>
 * Description :
 *   api 결과 상태코드 상수 클래스
 * ===============================================
 * Member fields :
 * 
 * ===============================================
 *
 * Author : HeonSeung Kim
 * Date   : 2021-10-20
 * </pre>
 */
public class ApiStatus {

    @Getter
    public enum Success {
        OK("Ok", 200, "Standard response for successful HTTP requests."), 
        CREATED("Created", 201, "The request has been fulfilled, resulting in the creation of a new resource."),
        ACCEPTED("Accepted", 202, "The request has been accepted for processing, but the processing has not been completed."),
        NO_CONTENT("No Content", 204, "The server successfully processed the request, and is not returning any content."),
        RESET_CONTENT("Reset Content", 205, "The server successfully processed the request, and is not returning any content.");

        private String status;
        private int code;
        private String description;

        private Success(String status, int code, String description) {
            this.status = status;
            this.code = code;
            this.description = description;
        }
    }

    @Getter
    public enum ClientError {
        BAD_REQUEST("Bad Request", 400, "Apparent client error (e.g., malformed request syntax, size too large, invalid request message framing, or deceptive request routing)."),
        UNAUTHORIZED("Unauthorized", 401, "User does not have valid authentication credentials for the target resource."),
        FORBIDDEN("Forbidden", 403, "This may be due to the user not having the necessary permissions for a resource or needing an account of some sort, or attempting a prohibited action."),
        NOT_FOUND("Not Found", 404, "The requested resource could not be found but may be available in the future."),
        METHOD_NOT_ALLOWD("Method Not Allowed", 405, "A request method is not supported for the requested resource."),
        NOT_ACCEPTABLE("Not Acceptable", 406, "The requested resource is capable of generating only content not acceptable according to the Accept headers sent in the request."),
        REQUEST_TIMEOUT("Request Timeout", 408, "The server timed out waiting for the request."),
        URI_TOO_LONG("URI Too Long", 414, "The URI provided was too long for the server to process."),
        UNSUPPORTED_MEDIA_TYPE("Unsupported Media Type", 415, "The request entity has a media type which the server or resource does not support."),
        TOO_MANY_REQUESTS("Too Many Requests", 429, "The user has sent too many requests in a given amount of time."),
        HEADER_TOO_LARGE("Request Header Fields Too Large", 431, "Either an individual header field, or all the header fields collectively, are too large.");

        private String status;
        private int code;
        private String description;

        private ClientError(String status, int code, String description) {
            this.status = status;
            this.code = code;
            this.description = description;
        }
    }

    @Getter
    public enum ServerError {
        INTERNAL_SERVER_ERROR("Internal Server Error", 500, "Unexpected condition was encountered."),
        NOT_IMPLEMENTED("Not Implemented", 501, "The server either does not recognize the request method, or it lacks the ability to fulfil the request."),
        BAD_GATEWAY("Bad Gateway", 502, "The server was acting as a gateway or proxy and received an invalid response from the upstream server."),
        SERVICE_UNAVAILABLE("Service Unavailable", 503, "The server cannot handle the request (because it is overloaded or down for maintenance)."),
        GATEWAY_TIMEOUT("Gateway Timeout", 504, "The server was acting as a gateway or proxy and did not receive a timely response from the upstream server.");

        private String status;
        private int code;
        private String description;

        private ServerError(String status, int code, String description) {
            this.status = status;
            this.code = code;
            this.description = description;
        }
    }

    @Getter
    public enum CustomError {
        ELASTICSEARCH_QUERY_ERROR("Elasticsearch Query Error", 800, "Failed to execute query."),
        DATABASE_QUERY_ERROR("Database Query Error", 800, "Failed to execute query."),
        QUERY_ERROR("Query Error", 800, "Failed to execute query."),
        AUTH_ERROR("Authentication Error", 801, "Something wrong with your auth."),
        TOKEN_INVALID_ERROR("Token Error (Invalid)", 802, "Token is invalid."),
        TOKEN_EXPIRED_ERROR("Token Error (Expired)", 803, "Token has expired.");

        private String status;
        private int code;
        private String description;

        private CustomError(String status, int code, String description) {
            this.status = status;
            this.code = code;
            this.description = description;
        }
    }

}
