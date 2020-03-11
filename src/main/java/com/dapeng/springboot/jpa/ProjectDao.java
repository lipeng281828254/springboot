package com.dapeng.springboot.jpa;

import com.dapeng.springboot.entity.ProjectEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

/**
 * @author lipeng
 * @version 1.0
 * @date 2020/3/11 22:53
 * @message：
 */
public interface ProjectDao extends JpaRepository<ProjectEntity,Long>, JpaSpecificationExecutor<ProjectEntity> {
}
