package com.dapeng;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;

/**
 *
 * 加解密
 */
public class JiaJieMiTest {

    public static void main(String[] args) {
        //md5加密，单向加密，不能解密
        MessageDigest md5 = null;
        String content = "加密内容";
        try {
            md5 = MessageDigest.getInstance("MD5");
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        BigInteger integer = new BigInteger(md5.digest(content.getBytes()));//hash值
        System.out.println(integer);

        //base64加密 二进制数据加密
        byte[] base = Base64.getEncoder().encode(content.getBytes());
        String baseStr = new String(base);
        System.out.println(baseStr);


    }

    /**
     *  key 密钥 8位最少
     * @param key
     * @param content
     */
    public void desEncryp(String key,String content){

    }

    //rsa加解密，key不同，根据key的长度决定加密的复杂度
    private void rsaEncryp(){

    }
}
