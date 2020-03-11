package com.dapeng.springboot.dto;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * @author lipeng
 * @version 1.0
 * @date 2020/3/11 23:10
 * @message：
 */
@Data
public class ProjectUserReltiveDto implements Serializable {

    private Long id;

    //项目id
    private Long projectId;

    //用户id
    private Long userId;

    //用户名
    private String userName;

    //项目中角色
    private String projectRole;

    private Date createTime;

    private Date updateTime;

}
