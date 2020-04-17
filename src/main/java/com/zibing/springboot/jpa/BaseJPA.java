package com.zibing.springboot.jpa;

import com.zibing.springboot.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.io.Serializable;

/**
 * ==================================================
 * <p>
 * FileName: BaseJPA
 *
 * @author zibing
 * @create 2019/9/24
 * @since 1.0.0
 * 〈功能〉：baseJpa，所有业务jpa的父接口
 * ==================================================
 */
public interface BaseJPA extends JpaRepository<UserEntity,Long>, JpaSpecificationExecutor<UserEntity>, Serializable {
}
