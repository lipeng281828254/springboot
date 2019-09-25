package com.dapeng.springboot.jpa;

import com.dapeng.springboot.demoOne.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * ==================================================
 * <p>
 * FileName: UserJPA
 *
 * @author : lipeng
 * @create 2019/9/24
 * @since 1.0.0
 * 〈功能〉：TODO
 * ==================================================
 */
public interface UserJPA extends JpaRepository<UserEntity,Long> {
}
