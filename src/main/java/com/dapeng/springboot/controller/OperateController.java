package com.dapeng.springboot.controller;

import com.dapeng.springboot.dto.OperateInfoDto;
import com.dapeng.springboot.dto.UserInfoDto;
import com.dapeng.springboot.service.OperateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

/**
 * @author lipeng
 * @version 1.0
 * @date 2020/3/21 1:22
 * @message：变更记录
 */
@RestController
@RequestMapping("api/operate/")
public class OperateController {

    @Autowired
    private OperateService operateService;

    @PostMapping("addChangeLog.json")
    public boolean addChangeLog(OperateInfoDto dto, HttpServletRequest request) {
        HttpSession session = request.getSession();
        UserInfoDto userInfoDto = (UserInfoDto) session.getAttribute("userInfo");
        return operateService.addChangeLog(dto, userInfoDto);
    }
}
