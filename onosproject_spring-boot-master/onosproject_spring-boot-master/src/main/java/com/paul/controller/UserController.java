package com.paul.controller;

import com.alibaba.fastjson.JSONObject;
import com.paul.domain.User;
import com.paul.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;


/**
 * @author pxj
 * @date 2022-07-20 22:15
 */
@RestController
@CrossOrigin
public class UserController {
    @Autowired
    private IUserService IUserService;

    @PostMapping("/register")
    public boolean register(@RequestBody User user) {
        return IUserService.register(user);
    }

    @PostMapping("/login")
    public JSONObject login(@RequestBody User user) {
        return (JSONObject) JSONObject.toJSON(IUserService.login(user));
    }

    @GetMapping("/checkToken")
    public JSONObject checkToken(@RequestParam("token")String token){
        return IUserService.checkToken(token);
    }

}
