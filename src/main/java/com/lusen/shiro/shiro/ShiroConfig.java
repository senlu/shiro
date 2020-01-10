package com.lusen.shiro.shiro;

import at.pollux.thymeleaf.shiro.dialect.ShiroDialect;
import org.apache.shiro.authc.credential.HashedCredentialsMatcher;
import org.apache.shiro.authc.credential.SimpleCredentialsMatcher;
import org.apache.shiro.cache.CacheManager;
import org.apache.shiro.cache.MemoryConstrainedCacheManager;
import org.apache.shiro.codec.Base64;
import org.apache.shiro.crypto.hash.Sha256Hash;
import org.apache.shiro.crypto.hash.SimpleHash;
import org.apache.shiro.mgt.RememberMeManager;
import org.apache.shiro.realm.Realm;
import org.apache.shiro.spring.web.config.DefaultShiroFilterChainDefinition;
import org.apache.shiro.spring.web.config.ShiroFilterChainDefinition;
import org.apache.shiro.web.mgt.CookieRememberMeManager;
import org.apache.shiro.web.servlet.SimpleCookie;
import org.springframework.aop.framework.autoproxy.DefaultAdvisorAutoProxyCreator;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author
 * @description
 * @data2020/1/8
 */
@Configuration
public class ShiroConfig
{
    /**
     * 凭证匹配器（密码校验交给Shiro的SimpleAuthenticationInfo进行处理)
     *
     * @return
     */
    @Bean
    public HashedCredentialsMatcher hashedCredentialsMatcher()
    {
        HashedCredentialsMatcher hashedCredentialsMatcher =
            new HashedCredentialsMatcher();
        //散列算法:这里使用MD5算法;
        hashedCredentialsMatcher.setHashAlgorithmName("md5");
        //散列的次数，比如散列两次，相当于 md5(md5(""));
        hashedCredentialsMatcher.setHashIterations(10);
        // true 指Hash散列值使用Hex加密存
        hashedCredentialsMatcher.setStoredCredentialsHexEncoded(true);

        return hashedCredentialsMatcher;
    }

    @Bean
    public Realm realm()
    {
        MyRealm myRealm = new MyRealm();
        myRealm.setCredentialsMatcher(hashedCredentialsMatcher());
        return myRealm;
    }

    @Bean
    public ShiroFilterChainDefinition shiroFilterChainDefinition()
    {
        DefaultShiroFilterChainDefinition chainDefinition =
            new DefaultShiroFilterChainDefinition();

        /*
         配置缩写	对应的过滤器	     功能
         anon	  AnonymousFilter	指定url可以匿名访问
         authc	  FormAuthenticationFilter	指定url需要form表单登录，默认会从请求中获取username、password,rememberMe等参数并尝试登录，如果登录不了就会跳转到loginUrl配置的路径。我们也可以用这个过滤器做默认的登录逻辑，但是一般都是我们自己在控制器写登录逻辑的，自己写的话出错返回的信息都可以定制嘛。
         authcBasic	BasicHttpAuthenticationFilter	指定url需要basic登录
         logout	  LogoutFilter	登出过滤器，配置指定url就可以实现退出功能，非常方便
         noSessionCreation	NoSessionCreationFilter	禁止创建会话
         perms	PermissionsAuthorizationFilter	需要指定权限才能访问
         port	PortFilter	需要指定端口才能访问
         rest	HttpMethodPermissionFilter	将http请求方法转化成相应的动词来构造一个权限字符串，这个感觉意义不大，有兴趣自己看源码的注释
         roles	RolesAuthorizationFilter	需要指定角色才能访问
         ssl	SslFilter	需要https请求才能访问
         user	UserFilter	需要已登录或“记住我”的用户才能访问
        */
        //不需要认证能访问的
        chainDefinition.addPathDefinition("/login", "anon");

        // 必须认证后的admin角色能访问
        chainDefinition.addPathDefinition("/admin/**", "authc, roles[admin]");

        // 必须认证后，且拥有document:read权限的能访问.
        chainDefinition.addPathDefinition("/docs/**",
            "authc, perms[document:read]");

        // 注销
        chainDefinition.addPathDefinition("/logout", "anon,logout");
        // 所有认证后的账号能访问
        chainDefinition.addPathDefinition("/**", "user");
        return chainDefinition;
    }

    /**
     * 配置ShiroDialect，用于Shiro和thymeleaf标签配合使用
     *
     * @return
     */
    @Bean
    public ShiroDialect shiroDialect()
    {
        return new ShiroDialect();
    }

    /**
     * 缓存启用
     * 启用缓存后，不会每次都去执行一次doGetAuthorizationInfo
     *
     * @return
     */
    @Bean
    protected CacheManager cacheManager()
    {
        return new MemoryConstrainedCacheManager();
    }

}
