package com.dapeng.springboot.jpa;

import com.dapeng.springboot.entity.ProjectUserReltiveEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @author lipeng
 * @version 1.0
 * @date 2020/3/11 23:30
 * @message：
 */
public interface ProjectUserDao extends JpaRepository<ProjectUserReltiveEntity, Long>, JpaSpecificationExecutor<ProjectUserReltiveEntity> {
    //查询成员所在项目
    List<ProjectUserReltiveEntity> findByUserId(Long userId);

    ProjectUserReltiveEntity findByProjectIdAndUserId(Long projectId, Long userId);

    List<ProjectUserReltiveEntity> findByProjectId(Long projectId);

    @Transactional
    @Query(value = "update project_user_relative set project_role=?3 where user_id=?1 and project_id=?2", nativeQuery = true)
    @Modifying
    int updateRoleByPidAndUid(Long userId, Long projectId, String project_role);
}
