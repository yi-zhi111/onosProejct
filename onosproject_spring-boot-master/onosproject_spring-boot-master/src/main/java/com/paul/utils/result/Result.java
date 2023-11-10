package com.paul.utils.result;

import com.alibaba.fastjson.JSONObject;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @author pxj
 * @date 2022-09-07 21:20
 */
@Component
@Data
public class Result<T> {
    //状态码
    private Status code;
    //状态信息
    private String message;
    //数据
    private T data;

    public Result<T> success(Status status,T data){
        Result result = new Result();
        result.setCode(Status.OK);
        result.setMessage(status.getMessage());
        result.setData(data);
        return result;
    }

    public Result<T> badRequest(Status status){
        Result result = new Result();
        result.setCode(Status.BAD_REQUEST);
        result.setMessage(status.getMessage());
        return result;
    }

    public Result<T> unauthorized(Status status){
        Result result = new Result();
        result.setCode(Status.UNAUTHORIZED);
        result.setMessage(status.getMessage());
        return result;
    }

    public Result<T> forbidden(Status status){
        Result result = new Result();
        result.setCode(Status.FORBIDDEN);
        result.setMessage(status.getMessage());
        return result;
    }

    public Result<T> notFound(Status status){
        Result result = new Result();
        result.setCode(Status.NOT_FOUND);
        result.setMessage(status.getMessage());
        return result;
    }

    public Result<T> internalServerError(Status status){
        Result result = new Result();
        result.setCode(Status.INTERNAL_SERVER_ERROR);
        result.setMessage(status.getMessage());
        return result;
    }


}

