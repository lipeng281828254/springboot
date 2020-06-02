package com.dapeng.springboot.service;

import com.dapeng.springboot.dto.UserDto;

public interface UserDubboInterface {

    /**
     * 查询
     * @param userDto
     * @return
     */
    UserDto queryUser(UserDto userDto);
}
