package com.lusen.shiro.shiro;

import org.apache.shiro.crypto.hash.SimpleHash;

/**
 * @author
 * @description
 * @data2020/1/10
 */
public class ShiroUtils
{
    public static String getEncryptPassword(String password, Object salt)
    {
        //加密算法
        String algorithmName = "MD5";
        // 密码
        Object source = password;
        // 加密次数，要与配置的相同
        int hashIterations = 10;
        SimpleHash simpleHash =
            new SimpleHash(algorithmName, source, salt, hashIterations);
        return simpleHash.toString();
    }
}
