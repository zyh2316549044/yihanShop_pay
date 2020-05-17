package com.demo.yihan_shop.listener;

import com.demo.yihan_shop.entity.PayInfo;
import com.demo.yihan_shop.service.MallOrderService;
import com.google.gson.Gson;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @description:
 * @author: zhangyihan
 * 关于获取payInfo，正式姿势：pay项目提供client.jar,mall项目引入jar包
 * @createDate: 2020-05-16 16:46
 * @version: 1.0
 */
@Component
@RabbitListener(queues = "payNotify")
@Slf4j
public class PayMsgListener {

    @Autowired
    private MallOrderService mallOrderService;

    @RabbitHandler
    public void process(String msg) {
        log.info("【接收到消息】=> {}", msg);

        PayInfo payInfo = new Gson().fromJson(msg, PayInfo.class);
        if (payInfo.getPlatformStatus().equals("SUCCESS")) {
            //修改订单里的状态
            mallOrderService.paid(payInfo.getOrderNo());
        }
    }
}
