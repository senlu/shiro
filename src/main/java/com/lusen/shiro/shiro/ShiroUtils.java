package com.lusen.shiro.shiro;

import org.apache.shiro.crypto.hash.SimpleHash;

/**
 * @author
 * @description
 * @data2020/1/10
 */
public class ShiroUtils
{
    /**
     *
     * @param algorithmName 加密算法MD5，SHA-1，SHA-255，SHA-512
     * @param password 待加密的密码
     * @param salt 盐
     * @return
     */
    public static String getEncryptPassword(String algorithmName, String password, Object salt)
    {
        //加密算法

        // 密码
        Object source = password;
        // 加密次数，要与配置的相同
        int hashIterations = 10;
        SimpleHash simpleHash =
            new SimpleHash(algorithmName, source, salt, hashIterations);
        return simpleHash.toString();
    }

    public static void main(String[] args)
    {
        System.out.println(getEncryptPassword("MD5","123","lusen"));
        System.out.println(getEncryptPassword("SHA-1","123","lusen"));
        System.out.println(getEncryptPassword("SHA-256","123","lusen"));
        System.out.println(getEncryptPassword("SHA-512","123","lusen"));



    }
}
