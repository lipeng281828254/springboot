package com.dapeng.springboot.jpa;

import com.dapeng.springboot.entity.TeamEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author zibing
 * @version 1.0
 * @date 2020/3/10 17:44
 * @message：
 */
public interface TeamDao extends JpaRepository<TeamEntity,Long>, JpaSpecificationExecutor<TeamEntity> {

    @Transactional
    @Query(value = "update team_info set team_name=?2 where id=?1 ",nativeQuery = true)
    @Modifying
    int updateTeamName(Long id, String teamName);

    TeamEntity findByTeamName(String teamName);
}
