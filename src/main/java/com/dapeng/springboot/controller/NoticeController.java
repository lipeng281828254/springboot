package com.dapeng.springboot.controller;

import com.dapeng.springboot.dto.NoticeDto;
import com.dapeng.springboot.service.NoticeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.List;

/**
 * @author lipeng
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
    @GetMapping("/changeStatusToRead.json")
    public boolean changeStatusToRead(Long id){
        return noticeService.chageStatus(id);
    }
}
