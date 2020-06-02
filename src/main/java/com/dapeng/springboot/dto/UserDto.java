package com.dapeng.springboot.dto;

import lombok.Data;

import java.io.Serializable;

@Data
public class UserDto implements Serializable {

    private Long id;

    private Long age;

    private String address;

    //无参构造方法
    public UserDto() {
    }
}
