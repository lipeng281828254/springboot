package com.zibing.springboot.controller;

import com.zibing.springboot.dto.NoticeDto;
import com.zibing.springboot.dto.UserInfoDto;
import com.zibing.springboot.service.NoticeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.List;

/**
 * @author zibing
 * @version 1.0
 * @date 2020/3/11 0:46
 * @message：消息控制层
 */
@RestController
@RequestMapping("api/notice/")
public class NoticeController {

    @Autowired
    private NoticeService noticeService;


    //查询登录者的消息
    @GetMapping("listNotices.json")
    public List<NoticeDto> queryByHandler(HttpServletRequest request){
        HttpSession session = request.getSession();
        return noticeService.listNoticeByHanlder(session);
    }

    //修改消息状态 未读变已读
    @GetMapping("changeStatusToRead.json")
    public boolean changeStatusToRead(Long id){
        return noticeService.chageStatus(id);
    }

//    //定制，修改状态或
//    @PostMapping("createNotice.json")
//    private Long createNotice(@Valid @RequestBody NoticeDto noticeDto,HttpServletRequest request){
//        HttpSession session = request.getSession();
//        UserInfoDto userInfoDto = (UserInfoDto) session.getAttribute("userInfo");
//        noticeDto.setCreateBy(userInfoDto.getId());
//        noticeDto.setCreateName(userInfoDto.getUserName());
//        noticeDto.setCreateTime(new Date());
//        noticeService.createNotice(noticeDto);
//    }

    @PostMapping("chageAllStatus.json")
    public Boolean chageAllStatus(HttpServletRequest request){
        HttpSession session = request.getSession();
        UserInfoDto userInfoDto = (UserInfoDto) session.getAttribute("userInfo");
        return noticeService.chageAllStatus(userInfoDto.getId());
    }
}