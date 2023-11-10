package com.paul.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.paul.domain.User;
import org.apache.ibatis.annotations.Mapper;

/**
 * @author pxj
 * @date 2022-07-18 20:15
 */
@Mapper
public interface UserDao extends BaseMapper<User> {

}
