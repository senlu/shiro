package com.lusen.shiro.domain;

import java.io.Serializable;
import java.util.Set;

/**
 * @author
 * @description
 * @data2020/1/8
 */
public class Account implements Serializable
{
    /**
     * 账号名
     */
    private String name;

    /**
     * 密码
     */
    private String password;

    /**
     * 所属角色
     */
    private Set<String> roles;

    /**
     * 拥有权限
     */
    private Set<String> permissions;

    public String getName()
    {
        return name;
    }

    public void setName(String name)
    {
        this.name = name;
    }

    public String getPassword()
    {
        return password;
    }

    public void setPassword(String password)
    {
        this.password = password;
    }

    public Set<String> getRoles()
    {
        return roles;
    }

    public void setRoles(Set<String> roles)
    {
        this.roles = roles;
    }

    public Set<String> getPermissions()
    {
        return permissions;
    }

    public void setPermissions(Set<String> permissions)
    {
        this.permissions = permissions;
    }
}
