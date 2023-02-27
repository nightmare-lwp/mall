package com.macro.mall.tiny;

import cn.hutool.json.JSONUtil;
import com.macro.mall.tiny.mbg.mapper.PmsBrandMapper;
import com.macro.mall.tiny.mbg.model.PmsBrand;
import com.macro.mall.tiny.mbg.model.UmsPermission;
import com.macro.mall.tiny.service.PmsBrandService;
import com.macro.mall.tiny.service.UmsAdminService;
import com.macro.mall.tiny.service.UmsMemberService;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.w3c.dom.stylesheets.LinkStyle;

import javax.annotation.Resource;
import java.util.List;

@SpringBootTest
class MallTinyApplicationTests {

    @Resource
    PmsBrandMapper pmsBrandMapper;
    @Resource
    PmsBrandService pmsBrandService;
    @Resource
    UmsMemberService umsMemberService;
    @Resource
    UmsAdminService umsAdminService;
    @Resource
    PasswordEncoder passwordEncoder;
    @Test
    void contextLoads() {
        PmsBrand pmsBrand=pmsBrandMapper.selectByPrimaryKey((long)1);
        System.out.println(pmsBrand);
    }
    @Test
    void test1(){
        System.out.println(pmsBrandService.listBrand(1, 10));
        System.out.println("-----------------");
        System.out.println(pmsBrandService.listBrand(1,2));
    }
    @Test
    void test2(){
        List<UmsPermission> permissionList=umsAdminService.getPermissionList(7L);
        for(UmsPermission permission:permissionList){
            System.out.println(permission.getId()+" "+permission.getName());
        }
    }
}
