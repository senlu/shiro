package com.lusen.shiro.service.impl;

import com.lusen.shiro.domain.Account;
import com.lusen.shiro.service.AccountService;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * @author
 * @description
 * @data2020/1/8
 */
@Service
public class AccountServiceImpl implements AccountService
{
    //暂时存放用户,每次重启后重新初始化用户。
    private Map<String,Account> accountMap = new HashMap<>();

    @PostConstruct
    public void init()
    {
        Account account = new Account();
        account.setName("lusen");
        account.setPassword("096cd7645ceaa6495d336656ea2c4fc1");
        Set<String> roles = new HashSet<>();
        roles.add("admin");
        Set<String> permissions = new HashSet<>();
        permissions.add("login");
        permissions.add("test");
        account.setPermissions(permissions);
        account.setRoles(roles);
        accountMap.put(account.getName(),account);
    }

    @Override
    public Account findAccountByName(String name)
    {
        return accountMap.get(name);
    }
}
