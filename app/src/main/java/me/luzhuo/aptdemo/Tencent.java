package me.luzhuo.aptdemo;


import me.luzhuo.lib_compiler.annotations.WechatLogin;
import me.luzhuo.lib_compiler.annotations.WechatPay;
import me.luzhuo.lib_tencent.wechat.template.WechatLoginTemplate;
import me.luzhuo.lib_tencent.wechat.template.WechatPayTemplate;

/**
 * Description: 微信支付 微信登录
 * 注意, 这两个注解分别注在不同的类上
 * 不能注在同一个类上, 否则只有一个注解的效果
 *
 * 该注解可以与其他种类的注解功能, 可以注解在同一个类上
 *
 * @Author: Luzhuo
 * @Creation Date: 2020/8/9 1:04
 * @Copyright: Copyright 2020 Luzhuo. All rights reserved.
 **/
@WechatPay(applicationId = BuildConfig.APPLICATION_ID, entryTemplete = WechatPayTemplate.class)
@WechatLogin(applicationId = BuildConfig.APPLICATION_ID, entryTemplete = WechatLoginTemplate.class)
public class Tencent {
}
