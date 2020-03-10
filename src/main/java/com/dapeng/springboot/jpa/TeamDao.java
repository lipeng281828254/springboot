package com.dapeng.springboot.jpa;

import com.dapeng.springboot.entity.TeamEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

/**
 * @author lipeng
 * @version 1.0
 * @date 2020/3/10 17:44
 * @message：
 */
public interface TeamDao extends JpaRepository<TeamEntity,Long>, JpaSpecificationExecutor<TeamEntity> {
}
