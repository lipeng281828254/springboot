package com.zibing.springboot.controller;

import com.zibing.springboot.dto.OperateInfoDto;
import com.zibing.springboot.dto.UserInfoDto;
import com.zibing.springboot.service.OperateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.util.List;

/**
 * @author zibing
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
    public boolean addChangeLog(@Valid @RequestBody OperateInfoDto dto, HttpServletRequest request) {
        HttpSession session = request.getSession();
        UserInfoDto userInfoDto = (UserInfoDto) session.getAttribute("userInfo");
        return operateService.addChangeLog(dto, userInfoDto);
    }

    //查询变更记录
    @GetMapping("listChangeLog.json")
    public List<OperateInfoDto> list(Long jobId) {
        return operateService.listLogs(jobId);
    }
}
