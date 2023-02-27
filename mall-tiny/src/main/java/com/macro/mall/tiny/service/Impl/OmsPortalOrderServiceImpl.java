package com.macro.mall.tiny.service.Impl;

import com.macro.mall.tiny.common.api.CommonResult;
import com.macro.mall.tiny.component.CancelOrderSender;
import com.macro.mall.tiny.dto.OrderParam;
import com.macro.mall.tiny.service.OmsPortalOrderService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * 前台订单管理Service
 *
 * @author lwp
 * @date 2023/2/27 21:39
 */
@Service
public class OmsPortalOrderServiceImpl implements OmsPortalOrderService {
    private static Logger LOGGER= LoggerFactory.getLogger(OmsPortalOrderServiceImpl.class);
    @Resource
    private CancelOrderSender cancelOrderSender;
    @Override
    public CommonResult generateOrder(OrderParam orderParam) {
        //todo 执行一系列下单操作
        LOGGER.info("process generateOrder");
        //下单完成后开启一个延迟消息，用于当用户没有付款时取消订单（orderId应该在下单后生成）
        sendDelayMessageCancelOrder(11L);
        return CommonResult.success(null,"下单成功");
    }

    @Override
    public void cancelOrder(Long orderId) {
        //todo 执行一系列取消订单操作
        LOGGER.info("process cancelOrder orderId:{}",orderId);
    }
    private void sendDelayMessageCancelOrder(Long orderId){
        //获取订单超时时间,假设为30秒
        long delayTimes=30*1000;
        cancelOrderSender.sendMessage(orderId,delayTimes);
    }
}
