package com.paul.domain;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;


import java.sql.Date;

/**
 * @author pxj
 * @date 2022-07-18 20:13
 */
@Data
@TableName("user")
public class User {
    private String id;
    private String username;
    private String password;
    private String address;
    private Integer age;
    @TableField("date")
    private Date birth;
    @TableField("phoneNum")
    private String tel;
    private Integer priority;
    private String email;
    @TableField(exist = false)
    private String token;
}
