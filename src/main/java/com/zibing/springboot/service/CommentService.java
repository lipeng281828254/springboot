package com.zibing.springboot.service;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.bean.copier.CopyOptions;
import com.zibing.springboot.entity.CommentEntity;
import com.zibing.springboot.jpa.CommentDao;
import com.zibing.springboot.dto.*;
import com.zibing.springboot.jpa.UserInfoDao;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @author zibing
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
    @Autowired
    private UserInfoDao userInfoDao;


    /**
     * 新增评论
     */
    @Transactional
    public boolean addComment(CommentDto commentDto, UserInfoDto userInfoDto) {
        log.info("新增评论---{},--->>>{}", commentDto, userInfoDto);
        commentDto.setCommentId(userInfoDto.getId());
        commentDto.setCommentName(userInfoDto.getUserName());
        CommentEntity entity = new CommentEntity();
//        BeanUtils.copyProperties(commentDto, entity);
        BeanUtil.copyProperties(commentDto, entity, CopyOptions.create().setIgnoreNullValue(true).setIgnoreError(true));
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
            noticeDto.setHandlerName(userInfoDao.getOne(jobDto.getCreateBy()).getUserName());
            noticeService.createNotice(noticeDto);
        }
        if (noticeDto.getCreateBy() != jobDto.getHandlerId()) {
            noticeDto.setHandlerId(jobDto.getHandlerId());
            noticeDto.setHandlerName(userInfoDao.getOne(jobDto.getCreateBy()).getUserName());
            noticeService.createNotice(noticeDto);
        }
    }

    /**
     * 根据jobId查询
     *
     * @param jobId
     * @return
     */
    public List<CommentDto> findByJoBId(Long jobId) {
        List<CommentEntity> entities = commentDao.findByJobId(jobId);
        if (entities == null || entities.size() < 1) {
            return null;
        }
        List<CommentDto> commentDtos = new ArrayList<>();
        entities.forEach(commentEntity -> {
            CommentDto commentDto = new CommentDto();
            BeanUtils.copyProperties(commentEntity,commentDto);
            commentDtos.add(commentDto);
        });
        return commentDtos;
    }
}