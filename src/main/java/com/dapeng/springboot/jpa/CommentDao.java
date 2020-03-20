package com.dapeng.springboot.jpa;

import com.dapeng.springboot.entity.CommentEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

/**
 * @author lipeng
 * @version 1.0
 * @date 2020/3/21 0:28
 * @message：评论dao
 */
public interface CommentDao extends JpaRepository<CommentEntity,Long>, JpaSpecificationExecutor<CommentEntity> {
}
