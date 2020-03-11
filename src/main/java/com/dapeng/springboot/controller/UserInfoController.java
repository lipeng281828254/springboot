package com.dapeng.springboot.controller;

import com.dapeng.springboot.dto.*;
import com.dapeng.springboot.service.UserInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
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
    @GetMapping("/addUserInfo.json")
    public UserInfoDto addUserInfo(@Valid  UserInfoDto dto){
        return userInfoService.addUserInfo(dto);
    }

    @PostMapping("/updateUserInfo.json")
    public void updateUserInfo(@Valid @RequestBody UpdateUserInfoDto dto){
        userInfoService.updateUserInfo(dto);
    }

    @PostMapping("/updateLoginName.json")
    public void updateLoginName(@Valid @RequestBody UpLoginDto dto){
        userInfoService.updateLoginName(dto);
    }

    @PostMapping("/checkPassword.json")
    public void checkPassword(@Valid @RequestBody ChekcPasswordDto dto){
        userInfoService.checkPassword(dto);
    }

    @PostMapping("/updatePassword.json")
    public void updatePassword(@Valid @RequestBody UpdatePassword dto){
        userInfoService.updatePassword(dto);
    }

    @PostMapping("/login.json")
    public UserInfoDto login( @Valid @RequestBody LoginDto loginDto, HttpServletRequest request){
        HttpSession session = request.getSession();
        return userInfoService.login(loginDto,session);
    }
}
