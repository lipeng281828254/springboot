package com.dapeng.springboot.service;

import com.dapeng.springboot.dto.CommentDto;
import com.dapeng.springboot.dto.UserInfoDto;
import com.dapeng.springboot.entity.CommentEntity;
import com.dapeng.springboot.jpa.CommentDao;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * @author lipeng
 * @version 1.0
 * @date 2020/3/21 0:27
 * @message：评论service
 */
@Slf4j
@Service
public class CommentService {

    @Resource
    private CommentDao commentDao;

    /**
     * 新增评论
     */
    public boolean addComment(CommentDto commentDto, UserInfoDto userInfoDto){
        log.info("新增评论---{},--->>>{}",commentDto,userInfoDto);
        commentDto.setCommentId(userInfoDto.getId());
        commentDto.setCommentName(userInfoDto.getUserName());
        CommentEntity entity = new CommentEntity();
        BeanUtils.copyProperties(commentDto,entity);
        commentDao.save(entity);
        commentDto.setId(entity.getId());
        log.info("新增评论结果--->>>{}",commentDto);
        return true;
    }
}
