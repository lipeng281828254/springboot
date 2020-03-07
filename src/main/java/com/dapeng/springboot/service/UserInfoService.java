package com.dapeng.springboot.service;

import com.dapeng.springboot.dto.UserInfoDto;
import com.dapeng.springboot.entity.UserInfoEntity;
import com.dapeng.springboot.jpa.UserInfoDao;
import com.dapeng.springboot.util.EncryptionUtil;
import com.dapeng.springboot.util.ParamCheckUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * @author lipeng
 * @version 1.0
 * @date 2020/3/8 0:17
 * @message：用户服务
 */
@Slf4j
@Service
public class UserInfoService {

    @Resource
    private UserInfoDao userInfoDao;

    /**
     * 添加用户
     *
     * @param dto
     * @return
     */
    public UserInfoDto addUserInfo(UserInfoDto dto) {
        log.info("添加用户入参：{}", dto);
        UserInfoDto resp = new UserInfoDto();
        try {
            if (!ParamCheckUtil.passwordCheck(dto.getPassword())){
                throw new RuntimeException("密码必须包含数字和英文，可以包含特殊字符");
            }
            if (!dto.getPassword().equals(dto.getConfirmPassword())) {
                throw new RuntimeException("密码和确认密码不一致");
            }
            UserInfoEntity entity = new UserInfoEntity();
            BeanUtils.copyProperties(dto, entity);
            entity.setEmail(dto.getLoginName());//邮箱就是登录名
            //密码加密
            String password = dto.getPassword();
            entity.setPassword(EncryptionUtil.getEncryp(password));//加密
            UserInfoEntity entity1 = userInfoDao.save(entity);
            BeanUtils.copyProperties(entity1, resp);
        } catch (RuntimeException e) {
            log.error("保存用户失败，原因：{}", e.getMessage());
            throw new RuntimeException(e.getMessage());
        } catch (Exception e) {
            log.error("保存用户失败，原因：", e);
            throw new RuntimeException("系统异常，请稍后重试");
        }
        log.info("添加用户结果：{}", resp);
        return resp;
    }
}
