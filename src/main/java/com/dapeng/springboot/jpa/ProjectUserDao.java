package com.dapeng.springboot.jpa;

import com.dapeng.springboot.entity.ProjectUserReltiveEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

/**
 * @author lipeng
 * @version 1.0
 * @date 2020/3/11 23:30
 * @message：
 */
public interface ProjectUserDao extends JpaRepository<ProjectUserReltiveEntity,Long>, JpaSpecificationExecutor<ProjectUserReltiveEntity> {
}
