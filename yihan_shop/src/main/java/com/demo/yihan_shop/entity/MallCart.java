package com.demo.yihan_shop.entity;

import lombok.Data;

/**
 * @description:
 * @author: zhangyihan
 * @createDate: 2020-05-07 17:18
 * @version: 1.0
 */
@Data
public class MallCart {

    private Integer productId;

    //数量
    private Integer quantity;

    private Boolean productSelected;

    public MallCart() {
    }

    public MallCart(Integer productId, Integer quantity, Boolean productSelected) {
        this.productId = productId;
        this.quantity = quantity;
        this.productSelected = productSelected;
    }
}
