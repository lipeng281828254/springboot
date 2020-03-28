package com.dapeng.springboot.controller;

import com.dapeng.springboot.dto.CommentDto;
import com.dapeng.springboot.dto.UserInfoDto;
import com.dapeng.springboot.service.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.util.List;

/**
 * @author lipeng
 * @version 1.0
 * @date 2020/3/21 0:25
 * @message：评论
 */
@RestController
@RequestMapping("api/comment/")
public class CommentController {

    @Autowired
    private CommentService commentService;

    //添加评论时，生成两条消息
    @PostMapping("addComment.json")//@RequestBody
    public boolean addComment(@Valid @RequestBody CommentDto commentDto, HttpServletRequest request) {
        HttpSession session = request.getSession();
        UserInfoDto userInfoDto = (UserInfoDto) session.getAttribute("userInfo");//获取评论人
//        UserInfoDto userInfoDto = new UserInfoDto();
//        userInfoDto.setId(11l);
//        userInfoDto.setUserName("小明");
        return commentService.addComment(commentDto, userInfoDto);
    }

    @GetMapping("getByJobId.json")
    public List<CommentDto> queryByJobId(Long jobId){
        return commentService.findByJoBId(jobId);
    }
}
