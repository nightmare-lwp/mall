package com.macro.mall.tiny.controller;

import com.macro.mall.tiny.common.api.CommonResult;
import com.macro.mall.tiny.dto.UmsAdminLoginParam;
import com.macro.mall.tiny.mbg.model.UmsAdmin;
import com.macro.mall.tiny.mbg.model.UmsPermission;
import com.macro.mall.tiny.service.UmsAdminService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 后台用户管理
 *
 * @author lwp
 * @date 2023/2/26 21:30
 */
@RestController
@Api(tags = "UmsAdminController",description = "后台用户管理")
@RequestMapping("/admin")
public class UmsAdminController {
    @Resource
    UmsAdminService umsAdminService;
    @Value("${jwt.tokenHead}")
    private String tokenHead;
    @PostMapping("/register")
    @ApiOperation("用户注册")
    public CommonResult<UmsAdmin> register(@RequestBody UmsAdmin umsAdminParam, BindingResult result){
        UmsAdmin umsAdmin=umsAdminService.register(umsAdminParam);
        if(umsAdmin==null){
            return CommonResult.failed();
        }
        return CommonResult.success(umsAdmin);
    }

    @PostMapping("/login")
    @ApiOperation("登录以后返回token")
    public CommonResult login(@RequestBody UmsAdminLoginParam umsAdminLoginParam,BindingResult result){
        String token=umsAdminService.login(umsAdminLoginParam.getUsername(), umsAdminLoginParam.getPassword());
        if(token==null){
            return CommonResult.validateFailed("用户名或密码错误");
        }
        Map<String,String> tokenMap=new HashMap<>();
        tokenMap.put("token",token);
        tokenMap.put("tokenHead",tokenHead);
        return CommonResult.success(tokenMap);
    }

    @GetMapping("/permission/{adminId}")
    @ApiOperation("获取用户所有权限（包括+-权限）")
    public CommonResult<List<UmsPermission>> getPermissionList(@PathVariable("adminId") Long adminId){
        List<UmsPermission> permissionList=umsAdminService.getPermissionList(adminId);
        return CommonResult.success(permissionList);
    }
}
