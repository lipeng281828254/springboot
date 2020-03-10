package com.dapeng.springboot.jpa;

import com.dapeng.springboot.entity.NoticeEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @author lipeng
 * @version 1.0
 * @date 2020/3/10 23:50
 * @message：消息
 */
public interface NoticeDao extends JpaRepository<NoticeEntity,Long>, JpaSpecificationExecutor<NoticeEntity> {

    List<NoticeEntity> findByhandlerId(Long handlerId);

    @Transactional
    @Query(value = "update notice_info set status='已读' where id = ?1 ",nativeQuery = true)
    @Modifying
    int changeToRead(Long id);
}
