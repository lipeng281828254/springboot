package com.dapeng.springboot.controller;

import com.dapeng.springboot.dto.UserInfoDto;
import com.dapeng.springboot.service.UserInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

/**
 * @author lipeng
 * @version 1.0
 * @date 2020/3/8 0:14
 * @message：用户信息控制层
 */
@RestController
@RequestMapping("/userInfo")
public class UserInfoController {

    @Autowired
    private UserInfoService userInfoService;

    /**
     * 添加用户
     * @param dto
     * @return
     */
    @PostMapping("/addUserInfo.json")
    public UserInfoDto addUserInfo(@Valid UserInfoDto dto){
        return userInfoService.addUserInfo(dto);
    }
}
