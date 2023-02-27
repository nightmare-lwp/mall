package com.macro.mall.tiny.controller;

import com.macro.mall.tiny.dto.OrderParam;
import com.macro.mall.tiny.service.OmsPortalOrderService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * 注释
 *
 * @author lwp
 * @date 2023/2/27 21:48
 */
@RestController
@RequestMapping("/order")
@Api(tags = "OmsPortalOrderController", description = "订单管理")
public class OmsPortalOrderController {
    @Resource
    private OmsPortalOrderService portalOrderService;

    @ApiOperation("根据购物车信息生成订单")
    @PostMapping("/generateOrder")
    public Object generateOrder(@RequestBody OrderParam orderParam){
        return portalOrderService.generateOrder(orderParam);
    }
}
