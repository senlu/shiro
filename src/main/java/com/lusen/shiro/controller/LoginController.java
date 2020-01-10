package com.lusen.shiro.controller;

import com.lusen.shiro.domain.Account;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.apache.shiro.subject.Subject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @author
 * @description
 * @data2020/1/8
 */
@Controller
public class LoginController
{
    private final Logger log= LoggerFactory.getLogger(LoginController.class);

    @GetMapping("/login")
    public String goToLogin(Account account)
    {
        Subject subject = SecurityUtils.getSubject();
        if(subject.isAuthenticated() || subject.isRemembered())
        {
            return "index";
        }
        return "login";
    }

    @PostMapping("/login")
    public String login(Account account, Model model,boolean rememberMe)
    {
        log.info("获取到用户名："+account.getName()+",密码："+ account.getPassword());
        // 开始认证

        //1.获取Subject
        Subject subject = SecurityUtils.getSubject();

        //2.封装用户数据
        UsernamePasswordToken token = new UsernamePasswordToken(account.getName(), account.getPassword(),rememberMe);

        //3.执行登录方法
        try
        {
            subject.login(token);
            model.addAttribute("msg", account.getName());

            //登录成功，转到首页
            return "index";
        }
        catch (UnknownAccountException e)
        {
            log.error("用户名不存在",e);
            model.addAttribute("msg", "用户名不存在");
            return "login";
        }
        catch (IncorrectCredentialsException e)
        {
            log.error("密码错误",e);
            model.addAttribute("msg", "密码错误");
            return "login";
        }
    }

    @RequestMapping("/logout")
    public String logout()
    {
        log.error("注销登录");
        return "login";
    }

    @RequiresPermissions("test")
    @GetMapping("/test")
    public String test()
    {
        log.error("测试test!");
        return "index";
    }
}
