package com.lusen.shiro.service;

import com.lusen.shiro.domain.Account;

/**
 * @author
 * @description
 * @data2020/1/8
 */
public interface AccountService
{
    /**
     * 更据用户名查找用户
     * @param name
     * @return
     */
    Account findAccountByName(String name);
}
