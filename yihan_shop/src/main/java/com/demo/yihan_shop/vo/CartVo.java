package com.demo.yihan_shop.vo;

import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

/**
 * @description: 购物车
 * @author: zhangyihan
 * @createDate: 2020-05-07 15:42
 * @version: 1.0
 */
@Data
public class CartVo {

    private List<CartProductVo> cartProductList;

    //全选
    private Boolean selectAll;
    //总价
    private BigDecimal cartTotalPrice;
    //总数量
    private Integer cartTotalQuantity;


}
