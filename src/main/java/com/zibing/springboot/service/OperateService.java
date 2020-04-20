package com.zibing.springboot.service;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.bean.copier.CopyOptions;
import com.zibing.springboot.dto.OperateInfoDto;
import com.zibing.springboot.dto.UserInfoDto;
import com.zibing.springboot.entity.OperateInfoEntity;
import com.zibing.springboot.jpa.OperateDao;
import com.zibing.springboot.jpa.UserInfoDao;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
 * @author zibing
 * @version 1.0
 * @date 2020/3/21 0:59
 * @message：变更操作记录
 */
@Slf4j
@Service
public class OperateService {


    @Resource
    private OperateDao operateDao;
    @Resource
    private UserInfoDao userInfoDao;

    /**
     * 操作添加
     *
     * @param operateInfoDto
     * @return
     */
    public boolean addChangeLog(OperateInfoDto operateInfoDto, UserInfoDto userInfoDto) {
        log.info("添加变更记录入参：---{}", operateInfoDto);
        OperateInfoEntity entity = new OperateInfoEntity();
//        BeanUtils.copyProperties(operateInfoDto, entity);
        BeanUtil.copyProperties(operateInfoDto,entity, CopyOptions.create().setIgnoreNullValue(true).setIgnoreError(true));
        entity.setOperatorId(userInfoDto.getId());
        entity.setOperatorName(userInfoDto.getUserName());
        operateDao.save(entity);
        operateInfoDto.setId(entity.getId());
        return true;
    }

    //查询所有变更记录
    public List<OperateInfoDto> listLogs(Long jobId) {
        log.info("变更列表查询入参：{}",jobId);
        if (jobId == null){
            throw new RuntimeException("jobId不能为空");
        }
        List<OperateInfoEntity> entities = operateDao.findByJobId(jobId);
        if (entities != null && entities.size() >= 1) {
            List<OperateInfoDto> dtos = new ArrayList<>();
            entities.forEach(operateInfoEntity -> {
                OperateInfoDto dto = new OperateInfoDto();
                BeanUtils.copyProperties(operateInfoEntity, dto);
                dto.setOperatorName(userInfoDao.getOne(operateInfoEntity.getOperatorId()).getUserName());
                dtos.add(dto);
            });
            return dtos;
        }
        return null;
    }
}
