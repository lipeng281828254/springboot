package com.dapeng.springboot.jpa;

import com.dapeng.springboot.entity.OperateInfoEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

/**
 * @author lipeng
 * @version 1.0
 * @date 2020/3/21 1:01
 * @message：操作记录
 */
public interface OperateDao extends JpaRepository<OperateInfoEntity,Long>, JpaSpecificationExecutor<OperateInfoEntity> {
}
