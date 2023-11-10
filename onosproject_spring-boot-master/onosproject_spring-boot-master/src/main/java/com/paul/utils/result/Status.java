package com.paul.utils.result;

import lombok.Data;
import lombok.Getter;
import org.springframework.stereotype.Component;

/**
 * @author pxj
 * @date 2022-09-15 21:34
 */

@Getter
public enum Status {
    OK(200,"Success"),
    BAD_REQUEST(400,"Bad Request"),
    UNAUTHORIZED(401,"Unauthorized"),
    FORBIDDEN(403,"Forbidden"),
    NOT_FOUND(404,"Not Found"),
    INTERNAL_SERVER_ERROR(500,"Internal Server Error");

    private Integer code;

    private String message;

    Status(Integer code, String message){
        this.code = code;
        this.message = message;
    }
}