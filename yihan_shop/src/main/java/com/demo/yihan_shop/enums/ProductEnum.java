package com.demo.yihan_shop.enums;

import lombok.Getter;

/**
 * @description:
 * @author: zhangyihan
 * @createDate: 2020-05-07 13:46
 * @version: 1.0
 */
@Getter
public enum  ProductEnum {

    ON_SALE(1),
    OFF_SALE(2),
    DELETE(3);

    Integer code;

    ProductEnum(Integer code) {
        this.code = code;
    }
}
