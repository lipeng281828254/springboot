package com.zibing.springboot.jpa;

import com.zibing.springboot.entity.UserInfoEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @author zibing
 * @version 1.0
 * @date 2020/3/7 23:58
 * @message：用户信息jpa
 */
public interface UserInfoDao extends JpaRepository<UserInfoEntity,Long>, JpaSpecificationExecutor<UserInfoEntity> {

//    //根据手机号  账号查找出相应账号  1表示存在 >=1表示不存在
//    @Query(value = "SELECT 1 FROM tab_register WHERE cellphone=?1 OR act=?1 ", nativeQuery = true)
//    int getActIsExistByCellPhone(String cellphone);
//
//    //新增一条（注册）
//    @Query(value = "INSERT IGNORE INTO tab_register (cellphone,psd) VALUES (?1, ?2)", nativeQuery = true)
//    @Modifying
//    int insertRegisterModelOne(String cellphone, String psd);
//
//    //登录
    @Query(value = "SELECT * FROM user_info WHERE  login_name=?1 ", nativeQuery = true)
    UserInfoEntity getByLoginName(String loginName);

    @Transactional
    @Query(value = "UPDATE user_info SET team_id=?1,team_name=?2 where id=?3",nativeQuery = true)
    @Modifying
    int addTeamId(Long teamId,String teamName, Long userId);

    List<UserInfoEntity> findByTeamId(Long teamId);

    @Transactional
    @Query(value = "update user_info set team_name=?2 where team_id=?1",nativeQuery =true)
    @Modifying
    void updateTeamNameByTeamId(Long teamId, String teamName);


//
//    //修改密码
//    @Query(value = "UPDATE tab_register SET psd=?2 WHERE id=?1", nativeQuery = true)
//    @Modifying
//    int upDateActPsdById(Integer id, String psd);
//
//    //删除账号
//    @Query(value = "DELETE FROM tab_register WHERE id=?1", nativeQuery = true)
//    @Modifying
//    int delAct(Integer id);

}
