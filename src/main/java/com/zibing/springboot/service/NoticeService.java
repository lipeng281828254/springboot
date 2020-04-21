package com.zibing.springboot.service;

import com.zibing.springboot.dto.NoticeDto;
import com.zibing.springboot.dto.UserInfoDto;
import com.zibing.springboot.entity.NoticeEntity;
import com.zibing.springboot.jpa.NoticeDao;
import com.zibing.springboot.jpa.UserInfoDao;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.List;

/**
 * @author zibing
 * @version 1.0
 * @date 2020/3/10 22:50
 * @message：通知信息
 */
@Slf4j
@Service
public class NoticeService {

    @Resource
    private NoticeDao noticeDao;
    @Resource
    private UserInfoDao userInfoDao;

    //创建
    public NoticeEntity createNotice(NoticeDto noticeDto) {
        NoticeEntity entity = new NoticeEntity();
        BeanUtils.copyProperties(noticeDto, entity);
        return noticeDao.save(entity);
    }

    //获取登录者通知消息列表，登录者为处理人
    public List<NoticeDto> listNoticeByHanlder(HttpSession session) {
        UserInfoDto user = (UserInfoDto) session.getAttribute("userInfo");
        List<NoticeEntity> entities = noticeDao.findByhandlerId(user.getId());
        if (entities == null || entities.size() == 0) {
            return new ArrayList<>();
        }
        List<NoticeDto> result = new ArrayList<>();
        entities.forEach(noticeEntity -> {
            NoticeDto noticeDto = new NoticeDto();
            BeanUtils.copyProperties(noticeEntity, noticeDto);
            noticeDto.setCreateName(userInfoDao.getOne(noticeEntity.getCreateBy()).getUserName());
            noticeDto.setHandlerName(userInfoDao.getOne(noticeEntity.getHandlerId()).getUserName());
            result.add(noticeDto);
        });
        return result;
    }

    //修改消息状态未读变已读
    public boolean chageStatus(Long id) {
        int update = noticeDao.changeToRead(id);
        return update == 1;
    }

    //根据id获取详情
    public NoticeDto getById(Long noticeId) {
        NoticeEntity entity = noticeDao.getOne(noticeId);
        if (entity == null){
            throw new RuntimeException("未查询到通知");
        }
        NoticeDto dto = new NoticeDto();
        BeanUtils.copyProperties(entity,dto);
        dto.setCreateName(userInfoDao.getOne(entity.getCreateBy()).getUserName());
        dto.setHandlerName(userInfoDao.getOne(entity.getHandlerId()).getUserName());
        return dto;
    }

    //修改所有的状态为已读
    public Boolean chageAllStatus(Long id) {
        noticeDao.updateByHandlerId(id);
        return Boolean.TRUE;
    }
}