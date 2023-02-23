package com.macro.mall.tiny.controller;

import com.macro.mall.tiny.common.api.CommonResult;
import com.macro.mall.tiny.service.UmsMemberService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * 会员登录注册管理Controller
 *
 * @author lwp
 * @date 2023/2/22 11:34
 */
@Api(tags = "UmsMemberController",description = "会员登录注册管理")
@RestController
@RequestMapping("/sso")
public class UmsMemberController {
    @Resource
    UmsMemberService umsMemberService;

    @ApiOperation("获取验证码")
    @GetMapping("/getAuthCode")
    public CommonResult getAuthCode(@RequestParam @ApiParam("电话号码") String telephone){
        return umsMemberService.generateAuthCode(telephone);
    }

    @ApiOperation("判断验证码是否正确")
    @PostMapping("/verifyAuthCode")
    public CommonResult verifyAuthCode(@RequestParam @ApiParam("电话号码") String telephone,
                                       @RequestParam @ApiParam("验证码") String authCode){
        return umsMemberService.verifyAuthCode(telephone,authCode);
    }
}
