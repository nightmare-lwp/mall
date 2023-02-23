package com.macro.mall.tiny.service.Impl;

import com.github.pagehelper.util.StringUtil;
import com.macro.mall.tiny.common.api.CommonResult;
import com.macro.mall.tiny.service.RedisService;
import com.macro.mall.tiny.service.UmsMemberService;
import com.mysql.cj.util.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Random;

/**
 * 会员管理service实现类
 *
 * @author lwp
 * @date 2023/2/22 9:14
 */
@Service
public class UmsMemberServiceImpl implements UmsMemberService {
    @Resource
    RedisService redisService;
    @Value("${redis.key.prefix.authCode}")
    private String REDIS_KEY_PREFIX_AUTH_CODE;
    @Value("${redis.key.expire.authCode}")
    private Long AUTH_CODE_EXPIRE_SECONDS;

    @Override
    public CommonResult generateAuthCode(String telephone) {
        StringBuilder sb=new StringBuilder();
        Random random=new Random();
        //6位验证码
        for(int i=1;i<=6;i++){
            sb.append(random.nextInt(10));
        }
        //redis存储验证码
        redisService.set(REDIS_KEY_PREFIX_AUTH_CODE+telephone,sb.toString());
        //设置验证码过期时间
        redisService.expire(REDIS_KEY_PREFIX_AUTH_CODE+telephone,AUTH_CODE_EXPIRE_SECONDS);
        return CommonResult.success(sb.toString(),"获取验证码成功");
    }
    //校验验证码
    @Override
    public CommonResult verifyAuthCode(String telephone, String authCode) {
        if(StringUtil.isEmpty(authCode)){
            return CommonResult.failed("请输入验证码");
        }
        String realAuthCode=redisService.get(REDIS_KEY_PREFIX_AUTH_CODE+telephone);
        if(StringUtil.isEmpty(realAuthCode)){
            return CommonResult.failed("验证码已过期");
        }
        if(authCode.equals(realAuthCode)){
            return CommonResult.success(null,"验证码校验成功");
        }else {
            return CommonResult.failed("验证码错误");
        }
    }
}
