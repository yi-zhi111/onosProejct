package com.paul.mybatis;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.paul.dao.UserDao;
import com.paul.domain.User;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

/**
 * @author pxj
 * @date 2022-11-28 15:07
 */
@SpringBootTest
public class MybatisAPITest {

    @Autowired
    private UserDao userDao;

    /**
     * select * from sys_user
     */
    @Test
    public void findAll(){
        List<User> userList = userDao.selectList(null);
        System.out.println(userList);
    }

    /**
     * 新增操作
     */
    @Test
    public void insert(){
        User user = new User();
        user.setUsername("pxj");
        user.setPassword("123");
        user.setAge(25);
        user.setTel("18562608737");
        userDao.insert(user);
    }

    /**
     * 删除操作
     */
    @Test
    public void delete(){
        userDao.deleteById(1549009925547618305L);
    }

    /**
     * 修改
     */
    @Test
    public void modify(){
        User user = new User();
        user.setId("1");
        user.setUsername("Tom66");
        user.setPassword("888");
        userDao.updateById(user);
    }

    /**
     * 查全部
     */
    @Test
    public void findAll1(){
        User user = userDao.selectById(2L);
        System.out.println(user);
    }

    /**
     * 分页
     */
    @Test
    public void page(){
        IPage page = new Page(1,2);//第current页，每页size条
        userDao.selectPage(page,null);
        System.out.println("当前页码值：" + page.getCurrent());
        System.out.println("每页显示数：" + page.getSize());
        System.out.println("一共多少页：" + page.getPages());
        System.out.println("一共多少条数据：" + page.getTotal());
        System.out.println("数据：" + page.getRecords());
    }

    /**
     * 条件查询
     */
    @Test
    public void queryAccordingToCondition(){
        //方式一：按条件查询
        /*QueryWrapper queryWrapper = new QueryWrapper();
        queryWrapper.lt("age",21);//lt是小于，gt是大于，查询年龄小于21岁的
        List list = userDao.selectList(queryWrapper);
        System.out.println(list);*/

        //方式二：用lamda格式
        /*QueryWrapper<User> queryWrapper = new QueryWrapper();
        queryWrapper.lambda().lt(User::getAge,21);//lt是小于，gt是大于，查询年龄小于21岁的
        List list = userDao.selectList(queryWrapper);
        System.out.println(list);*/

        //*方式三：用lamda格式
        /*LambdaQueryWrapper<User> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        lambdaQueryWrapper.lt(User::getAge,21);//lt是小于，gt是大于，查询年龄小于21岁的
        List list = userDao.selectList(lambdaQueryWrapper);
        System.out.println(list);*/

        LambdaQueryWrapper<User> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        //21到25之间的
        //lambdaQueryWrapper.lt(User::getAge,25).gt(User::getAge,21);//lt是小于，gt是大于，查询年龄大于21岁，小于25岁的
        //小于21或大于23的
        lambdaQueryWrapper.lt(User::getAge,21).or().gt(User::getAge,23);
        List list = userDao.selectList(lambdaQueryWrapper);
        System.out.println(list);
    }
}
