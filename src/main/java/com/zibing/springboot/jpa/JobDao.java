package com.zibing.springboot.jpa;

import com.zibing.springboot.entity.JobEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @author zibing
 * @version 1.0
 * @date 2020/3/16 23:33
 * @message：
 */
public interface JobDao extends JpaRepository<JobEntity,Long>, JpaSpecificationExecutor<JobEntity> {

    @Transactional
    @Query(value = "update job_info set demand_id = null where id = ?1 ",nativeQuery = true)
    @Modifying
    int deleteRelation(Long id);

    List<JobEntity> findByDemandIdAndType(Long demandId,String type);

    List<JobEntity> findByProjectIdAndTypeAndHandlerId(Long projectId,String type,Long handlerId);

    List<JobEntity> findByProjectIdAndTypeAndCreateBy(Long projectId,String type,Long createBy);

    List<JobEntity> findByProjectIdAndType(Long projectId, String type);

    List<JobEntity> findByIterationIdAndTypeAndHandlerId(Long iterationId, String type,Long handlerId);

    @Query(value = "select handler_id from job_info where iteration_id =?1  group by  handler_id",nativeQuery = true)
    List<Long> queryHandlers(Long jobId);


    List<JobEntity> findByIterationId(Long iterationId);

    List<JobEntity> findByIterationIdAndType(Long iterationId, String type);

    List<JobEntity>  findByIterationIdAndHandlerId(Long jobId, Long handlerId);

//    @Query(value = "SELECT * FROM job_info WHERE  iteration_id=?1 and status not in ('已发布','已拒绝','') ", nativeQuery = true)
//    int findIterationByStatus(Long iterationId);
}
