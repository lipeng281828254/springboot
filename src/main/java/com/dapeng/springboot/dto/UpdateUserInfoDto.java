package com.dapeng.springboot.dto;

import lombok.Data;

import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * @author lipeng
 * @version 1.0
 * @date 2020/3/8 23:11
 * @message：更新入参
 */
@Data
public class UpdateUserInfoDto implements Serializable {

    private Long id;

    private String userName;

    private String position;

    //图片地址
    private String headImgId;

}
