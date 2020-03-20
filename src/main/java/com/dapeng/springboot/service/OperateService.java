package com.dapeng.springboot.service;

import com.dapeng.springboot.dto.OperateInfoDto;
import com.dapeng.springboot.dto.UserInfoDto;
import com.dapeng.springboot.entity.OperateInfoEntity;
import com.dapeng.springboot.jpa.OperateDao;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * @author lipeng
 * @version 1.0
 * @date 2020/3/21 0:59
 * @message：变更操作记录
 */
@Slf4j
@Service
public class OperateService {


    @Resource
    private OperateDao operateDao;

    /**
     * 操作添加
     * @param operateInfoDto
     * @return
     */
    public boolean addChangeLog(OperateInfoDto operateInfoDto, UserInfoDto userInfoDto){
        log.info("添加变更记录入参：---{}",operateInfoDto);
        OperateInfoEntity entity = new OperateInfoEntity();
        BeanUtils.copyProperties(operateInfoDto,entity);
        entity.setOperatorId(userInfoDto.getId());
        entity.setOperatorName(userInfoDto.getUserName());
        operateDao.save(entity);
        operateInfoDto.setId(entity.getId());
        return true;
    }
}
