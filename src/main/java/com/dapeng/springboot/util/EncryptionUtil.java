package com.dapeng.springboot.util;

import lombok.extern.slf4j.Slf4j;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * @author lipeng
 * @version 1.0
 * @date 2020/3/8 2:01
 * @message：加密处理工具
 */
@Slf4j
public class EncryptionUtil {

    /**
     * 密码加密，MD5
     * @param password
     * @return
     */
    public static String getEncryp(String password) throws NoSuchAlgorithmException {
        MessageDigest md = MessageDigest.getInstance("MD5");//算法对象md5
        md.update(password.getBytes());//字节数组对象更新处理
        String newPassword = new BigInteger(1,md.digest()).toString(16);//正数，哈希算法，生成32字符串
//        log.info("加密后密码，{}",newPassword);
        return newPassword;
    }

}
