package com.dapeng.springboot.jpa;

import com.dapeng.springboot.entity.UserInfoEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

/**
 * @author lipeng
 * @version 1.0
 * @date 2020/3/7 23:58
 * @message：用户信息jpa
 */
public interface UserInfoDao extends JpaRepository<UserInfoEntity,Long>, JpaSpecificationExecutor<UserInfoEntity> {
}
