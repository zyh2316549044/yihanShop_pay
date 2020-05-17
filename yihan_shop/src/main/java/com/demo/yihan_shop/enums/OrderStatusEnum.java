package com.demo.yihan_shop.enums;

import lombok.Getter;

/**
 * @description:
 * @author: zhangyihan
 * @createDate: 2020-05-12 14:17
 * @version: 1.0
 */
@Getter
public enum  OrderStatusEnum {

    CANCELED(0, "已取消"),

    NO_PAY(10, "未付款"),

    PAID(20, "已付款"),

    SHIPPED(40, "已发货"),

    TRADE_SUCCESS(50, "交易成功"),

    TRADE_CLOSE(60, "交易关闭"),
            ;

    Integer code;

    String desc;

    OrderStatusEnum(Integer code, String desc) {
        this.code = code;
        this.desc = desc;
    }
}
