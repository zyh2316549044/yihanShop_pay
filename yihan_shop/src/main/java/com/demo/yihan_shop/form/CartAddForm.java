package com.demo.yihan_shop.form;

import lombok.Data;

import javax.validation.constraints.NotNull;

/**
 * @description: 添加商品
 * @author: zhangyihan
 * @createDate: 2020-05-07 15:55
 * @version: 1.0
 */
@Data
public class CartAddForm {

    @NotNull
    private Integer productId;

    private Boolean Select = true;



}
