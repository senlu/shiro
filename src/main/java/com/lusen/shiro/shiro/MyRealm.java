package com.lusen.shiro.shiro;

import com.lusen.shiro.domain.Account;
import com.lusen.shiro.service.AccountService;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.util.ByteSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @author
 * @description
 * @data2020/1/8
 */
public class MyRealm extends AuthorizingRealm
{
    private final Logger log = LoggerFactory.getLogger(MyRealm.class);

    @Autowired
    private AccountService accountService;

    /**
     * 授权，用户权限
     *
     * @param principals
     * @return
     */
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(
        PrincipalCollection principals)
    {
        // 获取已认证的用户数据
        Account account = (Account)getAvailablePrincipal(principals);
        ;

        SimpleAuthorizationInfo info = new SimpleAuthorizationInfo();
        // 设置用户角色
        info.setRoles(account.getRoles());
        // 设置用户权限authorizationAttributeSourceAdvisor
        info.setStringPermissions(account.getPermissions());
        log.error("权限验证");
        return info;
    }

    /**
     * 认证，用户登录
     *
     * @param token
     * @return
     * @throws AuthenticationException
     */
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(
        AuthenticationToken token)
        throws AuthenticationException
    {

        String name = ((UsernamePasswordToken)token).getUsername();

        // 从数据库中根据用户名查找用户信息。
        Account account = accountService.findAccountByName(name);

        if (account == null)
        {
            //用户名不存在,shiro底层会抛出UnKnowAccountException
            return null;
        }
        log.error("登录认证");
        // 获取盐值，即用户名
        ByteSource salt = ByteSource.Util.bytes(name);
        //2.判断密码
        return new SimpleAuthenticationInfo(account,
            account.getPassword(),
            salt,
            this.getName());

    }

    /**
     * 重写方法,清除当前用户的的 授权缓存
     */
    public void clearCachedAuthorizationInfo()
    {
        super.clearCachedAuthorizationInfo(SecurityUtils.getSubject()
            .getPrincipals());
    }

    /**
     * 重写方法，清除当前用户的 认证缓存
     */
    public void clearCachedAuthenticationInfo()
    {
        super.clearCachedAuthenticationInfo(SecurityUtils.getSubject()
            .getPrincipals());
    }

    /**
     * 清除某个用户认证和授权缓存
     */
    @Override
    public void clearCache(PrincipalCollection principals)
    {
        super.clearCache(principals);
    }

    /**
     * 自定义方法：清除所有 授权缓存
     */
    public void clearAllCachedAuthorizationInfo()
    {
        getAuthorizationCache().clear();
    }

    /**
     * 自定义方法：清除所有 认证缓存
     */
    public void clearAllCachedAuthenticationInfo()
    {
        getAuthenticationCache().clear();
    }

    /**
     * 自定义方法：清除所有的  认证缓存  和 授权缓存
     */
    public void clearAllCache()
    {
        clearAllCachedAuthenticationInfo();
        clearAllCachedAuthorizationInfo();
    }
}
