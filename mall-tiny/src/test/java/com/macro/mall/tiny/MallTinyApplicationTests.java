package com.macro.mall.tiny;

import com.macro.mall.tiny.mbg.mapper.PmsBrandMapper;
import com.macro.mall.tiny.mbg.model.PmsBrand;
import com.macro.mall.tiny.service.PmsBrandService;
import com.macro.mall.tiny.service.UmsMemberService;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;

@SpringBootTest
class MallTinyApplicationTests {

    @Resource
    PmsBrandMapper pmsBrandMapper;
    @Resource
    PmsBrandService pmsBrandService;
    @Resource
    UmsMemberService umsMemberService;
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
        System.out.println(umsMemberService.generateAuthCode("123").getMessage());
        System.out.println(umsMemberService.generateAuthCode("123").getData());
//        System.out.println(umsMemberService.verifyAuthCode("123", "").getMessage());
    }
}
