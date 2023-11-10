package com.paul.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.paul.dao.UserDao;
import com.paul.domain.User;
import com.paul.service.IUserService;
import com.paul.utils.jwt.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.sql.Date;
import java.util.Calendar;

/**
 * @author pxj
 * @date 2022-07-21 17:21
 */
@Service
public class IUserServiceImpl implements IUserService {
    @Autowired
    private UserDao userDao;

    @Autowired
    private JwtUtil jwtUtil;

    @Override
    public boolean register(User user) {
        //获取注册用户年龄
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(user.getBirth());
        int userAge = calendar.get(Calendar.YEAR);
        //获取当前年份
        Date date = new Date(System.currentTimeMillis());
        calendar.setTime(date);
        int currentYear = calendar.get(Calendar.YEAR);
        //设计剩余的数据库字段
        user.setAge(currentYear - userAge);
        user.setPriority(10);
        //校验数据库更新结果
        int insert = userDao.insert(user);//insert：操作的记录条数
        if (insert == 0)
            return false;
        return true;
    }

    @Override
    public User login(User user) {
            LambdaQueryWrapper<User> lambdaQueryWrapper = new LambdaQueryWrapper<>();
            lambdaQueryWrapper.eq(User::getUsername, user.getUsername()).eq(User::getPassword,user.getPassword());//select * from user where username = ? and password = ?
            User loginUser = userDao.selectOne(lambdaQueryWrapper);
            if (loginUser != null){//如果查询到了，说明有该用户
                loginUser.setToken(jwtUtil.createToken(loginUser.getUsername(),loginUser.getPriority()));
                return loginUser;
            }
        return null;
    }

    @Override
    public JSONObject checkToken(String token) {
        return jwtUtil.checkToken(token);
    }
}
