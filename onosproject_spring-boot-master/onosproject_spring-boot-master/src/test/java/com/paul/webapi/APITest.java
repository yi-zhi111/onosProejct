package com.paul.webapi;

import com.paul.utils.result.Result;
import com.paul.utils.result.Status;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

/**
 * @author pxj
 * @date 2022-11-28 15:14
 */
@SpringBootTest
public class APITest {
    public Result success(Status status){
        Result result = new Result();
        result.setCode(Status.OK);
        result.setMessage(status.getMessage());
        return result;
    }

    @Test
    public void OKTest(){
        System.out.println(success(Status.OK));
    }
}
