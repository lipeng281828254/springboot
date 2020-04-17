package com.dapeng.springboot.util;

import lombok.extern.slf4j.Slf4j;

/**
 * @author zibing
 * @version 1.0
 * @date 2020/3/8 2:48
 * @message：参与基本验证
 */
@Slf4j
public class ParamCheckUtil {

    //邮箱必须是数字加英文，可以包含特殊字符"
    private final static String reqFormat = "^(?![0-9]+$)(?![a-zA-Z]+$)[0-9A-Za-z~@*/#()_]{8,16}$";
    /**
     * 密码组合验证
     * @param password
     */
    public static boolean passwordCheck(String password){
        return password.matches(reqFormat);
    }

    public static void main(String[] args) {
        String loginName = "1a311a#a131";
        String t1 = "^[0-9A-Za-z]{2,16}$"; //判断密码用户名和密码是否为数字，字母
        String t2="^(?![0-9]+$)(?![a-zA-Z]+$)[0-9A-Za-z~@*/#()_]{8,16}$";
        if (loginName.matches(reqFormat)){
            System.out.println(loginName.matches(reqFormat));
        } else {
            System.out.println("在这里");
        }
    }
}
