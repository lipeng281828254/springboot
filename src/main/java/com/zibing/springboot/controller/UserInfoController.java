package com.zibing.springboot.controller;

import com.zibing.springboot.dto.*;
import com.zibing.springboot.service.UserInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.support.SessionStatus;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;

/**
 * @author zibing
 * @version 1.0
 * @date 2020/3/8 0:14
 * @message：用户信息控制层
 */
@RestController
@RequestMapping(value = "api/userInfo")
public class UserInfoController {

    @Autowired
    private UserInfoService userInfoService;

    /**
     * 添加用户
     *
     * @param dto
     * @return
     */
    @PostMapping(value = "/addUserInfo.json")
    public UserInfoDto addUserInfo(@Valid @RequestBody UserInfoDto dto) {
        return userInfoService.addUserInfo(dto);
    }

    @PostMapping("/updateUserInfo.json")
    public UserInfoDto updateUserInfo(@RequestBody UpdateUserInfoDto dto, HttpServletRequest request) {
        HttpSession session = request.getSession();
        UserInfoDto userInfoDto = (UserInfoDto) session.getAttribute("userInfo");
        dto.setId(userInfoDto.getId());
        return userInfoService.updateUserInfo(dto, session);
    }

    @PostMapping("/updateLoginName.json")
    public Boolean updateLoginName(@Valid @RequestBody UpLoginDto dto, HttpServletRequest request) {
        HttpSession session = request.getSession();
        UserInfoDto userInfoDto = (UserInfoDto) session.getAttribute("userInfo");
        dto.setId(userInfoDto.getId());
        return userInfoService.updateLoginName(dto, session);
    }

    @PostMapping("/checkPassword.json")
    public void checkPassword(@Valid @RequestBody ChekcPasswordDto dto) {
        userInfoService.checkPassword(dto);
    }

    @PostMapping("/updatePassword.json")
    public Boolean updatePassword(@Valid @RequestBody UpdatePassword dto, HttpServletRequest request) {
        HttpSession session = request.getSession();
        UserInfoDto userInfoDto = (UserInfoDto) session.getAttribute("userInfo");
        dto.setId(userInfoDto.getId());
        return userInfoService.updatePassword(dto);
    }

    @PostMapping("/login.json")
    public UserInfoDto login(@Valid @RequestBody LoginDto loginDto, HttpServletRequest request) {
        HttpSession session = request.getSession();
        session.setMaxInactiveInterval(60*60*24);//一天
        return userInfoService.login(loginDto, session);
    }

    @GetMapping("/getByLoginName.json")
    public UserInfoDto getByLoginName(String loginName) {
        return userInfoService.getByLoginName(loginName);
    }

    @GetMapping("/getById.json")
    public UserInfoDto getById(Long id) {
        return userInfoService.getById(id);
    }

    @GetMapping("/getUserInfo.json")
    public UserInfoDto getUserInfo(HttpServletRequest request) {
        HttpSession session = request.getSession();
        if (session == null) {
            throw new RuntimeException("请先登录");
        }
        return (UserInfoDto) session.getAttribute("userInfo");
    }

    @GetMapping("/exit.json")
    public void tuichu(HttpServletRequest request, SessionStatus sessionStatus) {
        HttpSession session = request.getSession();
        if (session == null) {
            return;
        }
        session.invalidate();
        sessionStatus.setComplete();
    }
}
