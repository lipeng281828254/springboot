package com.dapeng.springboot.service;

import com.dapeng.springboot.dto.*;
import com.dapeng.springboot.entity.UserInfoEntity;
import com.dapeng.springboot.jpa.UserInfoDao;
import com.dapeng.springboot.util.EncryptionUtil;
import com.dapeng.springboot.util.ParamCheckUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;

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
    @Autowired
    private TeamService teamService;

    /**
     * 添加用户
     *
     * @param dto
     * @return
     */
    @Transactional
    public UserInfoDto addUserInfo(UserInfoDto dto) {
        log.info("添加用户入参：{}", dto);
        UserInfoDto resp = new UserInfoDto();
        try {
            dto.checkTeamName();
            if (!ParamCheckUtil.passwordCheck(dto.getPassword())) {
                throw new RuntimeException("密码必须包含数字和英文且在8-16位，可以包含特殊字符");
            }
            if (!dto.getPassword().equals(dto.getConfirmPassword())) {
                throw new RuntimeException("密码和确认密码不一致");
            }
            if(getByLoginName(dto.getLoginName())!=null){
                throw new RuntimeException("用户名已存在");
            }
            UserInfoEntity entity = new UserInfoEntity();
            BeanUtils.copyProperties(dto, entity);
            entity.setEmail(dto.getLoginName());//邮箱就是登录名
            //密码加密
            String password = dto.getPassword();
            entity.setPassword(EncryptionUtil.getEncryp(password));//加密
            UserInfoEntity entity1 = userInfoDao.save(entity);
            BeanUtils.copyProperties(entity1, resp);
            //负责人注册团队
            createTeam(dto.getTeamName(), resp.getId(), dto.getUserType());
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

    //创建团队
    private void createTeam(String teamName, Long userId, String userType) {
        //创建团队
        if ("02".equals(userType)) {
            TeamDto teamDto = new TeamDto();
            teamDto.setTeamName(teamName);
            teamDto.setUserId(userId);
            boolean success = teamService.createTeam(teamDto);
            if (!success) {
                throw new RuntimeException("负责人注册失败");
            }
        }
    }

    /**
     * 根据用户id更新信息
     *
     * @param dto
     */
    public void updateUserInfo(UpdateUserInfoDto dto,HttpSession session) {
        log.info("更新用户信息入参：{}", dto);
        UserInfoEntity entity = userInfoDao.getOne(dto.getId());
        if (entity == null) {
            throw new RuntimeException("没有查询到用户信息");
        }
        log.info("用户信息：{}", entity);
        BeanUtils.copyProperties(dto, entity);
        UserInfoEntity result = userInfoDao.save(entity);
        log.info("更新用户信息结束,{}", result);
        BeanUtils.copyProperties(entity, dto);
        session.setAttribute("userInfo",dto);
    }

    //校验密码是否当前用户密码
    public UserInfoEntity checkPassword(ChekcPasswordDto dto) {
        UserInfoEntity entity = userInfoDao.getOne(dto.getId());
        if (entity == null) {
            throw new RuntimeException("未查询到用户信息");
        }
        String password = EncryptionUtil.getEncryp(dto.getPassword());
        log.info("传入密码，{}---已有密码：{}", password, entity.getPassword());
        if (!password.equals(entity.getPassword())) {
            throw new RuntimeException("密码错误");
        }
        return entity;
    }

    /**
     * 更新邮箱
     */
    public void updateLoginName(UpLoginDto upLoginDto,HttpSession session) {
        UserInfoEntity entity = userInfoDao.getOne(upLoginDto.getId());
        if (entity == null) {
            throw new RuntimeException("未查询到用户信息");
        }
        BeanUtils.copyProperties(upLoginDto, entity);
        entity.setEmail(upLoginDto.getLoginName());
        userInfoDao.save(entity);
        BeanUtils.copyProperties(entity,upLoginDto);
        session.setAttribute("userInfo",upLoginDto);
    }

    /**
     * 修改密码
     *
     * @param dto
     */
    public void updatePassword(UpdatePassword dto) {
        if (!ParamCheckUtil.passwordCheck(dto.getPassword())) {
            throw new RuntimeException("密码必须包含数字和英文且在8-16位，可以包含特殊字符");
        }
        if (!dto.getPassword().equals(dto.getConfirmPassword())) {
            throw new RuntimeException("密码和确认密码不一致");
        }
        UserInfoEntity entity = userInfoDao.getOne(dto.getId());
        BeanUtils.copyProperties(dto, entity);
        entity.setPassword(EncryptionUtil.getEncryp(dto.getPassword()));//加密
        userInfoDao.save(entity);
    }

    /**
     * 登录功能
     *
     * @param loginDto
     * @return
     */
    public UserInfoDto login(LoginDto loginDto, HttpSession session) {
        UserInfoEntity entity = userInfoDao.getByLoginName(loginDto.getLoginName());
        if (entity == null) {
            throw new RuntimeException("用户不存在");
        }
        String password = EncryptionUtil.getEncryp(loginDto.getPassword());
        if (!password.equals(entity.getPassword())) {
            throw new RuntimeException("密码或用户名错误");
        }
        UserInfoDto userInfoDto = new UserInfoDto();
        BeanUtils.copyProperties(entity, userInfoDto);
        //放入session
        session.setAttribute("userInfo", userInfoDto);
        return userInfoDto;
    }


    //根据登录名查询用户信息
    public UserInfoDto getByLoginName(String loginName) {
        UserInfoEntity entity = userInfoDao.getByLoginName(loginName);
        if (entity == null) {
            throw new RuntimeException("用户不存在");
        }
        UserInfoDto userInfoDto = new UserInfoDto();
        BeanUtils.copyProperties(entity, userInfoDto);
        return userInfoDto;
    }

    //根据Id查询用户
    public UserInfoDto findById(Long userId) {
        UserInfoEntity entity = userInfoDao.getOne(userId);
        if (entity == null){
            throw new RuntimeException("用户不存在");
        }
        UserInfoDto dto = new UserInfoDto();
        BeanUtils.copyProperties(entity,dto);
        return dto;
    }

    public UserInfoDto getById(Long id) {
        UserInfoEntity entity = userInfoDao.getOne(id);
        if (entity == null){
            throw new RuntimeException("未查询到成员用户");
        }
        UserInfoDto infoDto = new UserInfoDto();
        BeanUtils.copyProperties(entity,infoDto);
        return infoDto;
    }
}
