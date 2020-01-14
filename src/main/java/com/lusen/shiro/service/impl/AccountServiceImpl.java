package com.lusen.shiro.service.impl;

import com.lusen.shiro.domain.Account;
import com.lusen.shiro.service.AccountService;
import org.apache.shiro.codec.Base64;
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
        account.setPassword("7d34be07f598c9dd5083f3b4877a56aa7b365a849aee63dd96522008ba26559c71ca3ce818c37444299fa0e7358fdbb9ed9cad2472293e5b2a0bc1c87376a178");
        Set<String> roles = new HashSet<>();
        roles.add("admin");
        Set<String> permissions = new HashSet<>();
        permissions.add("login");
        permissions.add("clear");
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
