package com.demo.yihan_shop.enums;

import lombok.Getter;

/**
 * @description:
 * @author: zhangyihan
 * @createDate: 2020-05-12 14:14
 * @version: 1.0
 */
@Getter
public enum  PaymentTypeEnum {

    PAY_ONLINE(1)
        ;
    private Integer code;

     PaymentTypeEnum(Integer code) {
        this.code = code;
    }
}
