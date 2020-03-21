package com.dapeng.springboot.service;

import com.dapeng.springboot.dto.*;
import com.dapeng.springboot.entity.CommentEntity;
import com.dapeng.springboot.jpa.CommentDao;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import java.util.Date;

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
    @Autowired
    private JobSerivce jobSerivce;
    @Autowired
    private ProjectService projectService;
    @Autowired
    private NoticeService noticeService;


    /**
     * 新增评论
     */
    @Transactional
    public boolean addComment(CommentDto commentDto, UserInfoDto userInfoDto) {
        log.info("新增评论---{},--->>>{}", commentDto, userInfoDto);
        commentDto.setCommentId(userInfoDto.getId());
        commentDto.setCommentName(userInfoDto.getUserName());
        CommentEntity entity = new CommentEntity();
        BeanUtils.copyProperties(commentDto, entity);
        commentDao.save(entity);
        commentDto.setId(entity.getId());
        log.info("新增评论结果--->>>{}", commentDto);
        //新增一个通知
        log.info("新增评论通知开始");
        createNoticeComment(commentDto, userInfoDto);
        log.info("新增评论通知结束");
        return true;
    }


    private void createNoticeComment(CommentDto commentDto, UserInfoDto userInfoDto) {

        JobDto jobDto = jobSerivce.getById(commentDto.getJobId());
        if (jobDto == null) {
            throw new RuntimeException("jobId不能为空");
        }
        ProjectInfoDto projectInfoDto = projectService.findById(jobDto.getProjectId());

        NoticeDto noticeDto = new NoticeDto();
        noticeDto.setCreateTime(new Date());
        noticeDto.setCreateBy(userInfoDto.getId());
        noticeDto.setCreateName(userInfoDto.getUserName());
        noticeDto.setProjectId(projectInfoDto.getId());
        noticeDto.setProjectName(projectInfoDto.getProjectName());
        noticeDto.setJobId(commentDto.getJobId());
        noticeDto.setJobTitle(jobDto.getTitle());
        noticeDto.setJobType(jobDto.getType());
        noticeDto.setStatus("未读");
        noticeDto.setAction("评论通知");
        String name = StringUtils.isEmpty(userInfoDto.getUserName()) ? userInfoDto.getId().toString() : userInfoDto.getUserName();
        noticeDto.setContent("用户".concat(name).concat(":").concat(commentDto.getCommentContent()));
        //自己评论，不发消息通知自己
        if (noticeDto.getCreateBy() != jobDto.getCreateBy()) {
            noticeDto.setHandlerId(jobDto.getCreateBy());
            noticeDto.setHandlerName(jobDto.getCreateName());
            noticeService.createNotice(noticeDto);
        }
        if (noticeDto.getCreateBy() != jobDto.getHandlerId()) {
            noticeDto.setHandlerId(jobDto.getHandlerId());
            noticeDto.setHandlerName(jobDto.getHandlerName());
            noticeService.createNotice(noticeDto);
        }
    }
}
