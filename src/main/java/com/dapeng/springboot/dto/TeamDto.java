package com.dapeng.springboot.dto;

import lombok.Data;

import java.io.Serializable;

/**
 * @author lipeng
 * @version 1.0
 * @date 2020/3/10 13:48
 * @message：
 */
@Data
public class TeamDto implements Serializable {

    private Long id;

    private String teamName;

    private Long createBy;

    private Long createName;

}
