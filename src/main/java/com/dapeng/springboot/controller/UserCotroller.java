package com.dapeng.springboot.controller;

import com.dapeng.springboot.entity.UserEntity;
import com.dapeng.springboot.jpa.UserJPA;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Optional;

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
        try {
            Optional optional = userJPA.findById(id);
            return (UserEntity) optional.get();
        } catch (RuntimeException e){
            System.out.println("没有查询到信息");
        }
        return null;
    }

    @RequestMapping("/addUser.json")
    public UserEntity addUser(UserEntity userEntity){
        return userJPA.save(userEntity);
    }

    @RequestMapping("/delete.json")
    public void deleteUser(Long id){
        userJPA.delete(getUser(id));
    }
}
