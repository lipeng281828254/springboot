package com.dapeng.springboot.jpa;

import com.dapeng.springboot.entity.JobEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

/**
 * @author lipeng
 * @version 1.0
 * @date 2020/3/16 23:33
 * @message：
 */
public interface JobDao extends JpaRepository<JobEntity,Long>, JpaSpecificationExecutor<JobEntity> {
}
