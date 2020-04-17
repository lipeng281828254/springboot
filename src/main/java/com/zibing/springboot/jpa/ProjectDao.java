package com.zibing.springboot.jpa;

import com.zibing.springboot.entity.ProjectEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @author zibing
 * @version 1.0
 * @date 2020/3/11 22:53
 * @message：
 */
public interface ProjectDao extends JpaRepository<ProjectEntity,Long>, JpaSpecificationExecutor<ProjectEntity> {

    @Query(value = "select i.id,i.project_name,i.descript,i.status,i.create_by,i.create_name,i.create_time,i.update_time from project_info i,project_user_relative r where i.id=r.project_id and r.user_id=?1",nativeQuery = true)
    List<ProjectEntity> queryByUserId(Long userId);

    @Transactional
    @Query(value = "update project_info set project_name=?2,descript=?3 where id=?1",nativeQuery = true)
    @Modifying
    int updateInfoById(Long id, String projectName, String descript);
}
