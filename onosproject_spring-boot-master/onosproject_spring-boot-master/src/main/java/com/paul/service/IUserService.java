package com.paul.service;

import com.alibaba.fastjson.JSONObject;
import com.paul.domain.User;

/**
 * @author pxj
 * @date 2022-07-21 17:20
 */
public interface IUserService {
    public boolean register(User user);

    public User login(User user);

    public JSONObject checkToken(String token);
}
