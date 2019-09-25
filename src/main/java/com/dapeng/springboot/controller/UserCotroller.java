package com.dapeng.springboot.controller;

import com.dapeng.springboot.demoOne.UserEntity;
import com.dapeng.springboot.jpa.UserJPA;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * ==================================================
 * <p>
 * FileName: UserCotroller
 *
 * @author : lipeng
 * @create 2019/9/24
 * @since 1.0.0
 * 〈功能〉：用户信息控制器
 * ==================================================
 */
@RestController
@RequestMapping("/user")
public class UserCotroller {

    @Autowired
    private UserJPA userJPA;

    @RequestMapping("/listAll.json")
    public List<UserEntity> lisUsers(){
        return userJPA.findAll();
    }

    @RequestMapping("/getUser.json")
    public UserEntity getUser(Long id){
       return userJPA.getOne(id);
    }

}
