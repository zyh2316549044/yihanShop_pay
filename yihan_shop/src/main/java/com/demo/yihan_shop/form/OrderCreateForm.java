package com.demo.yihan_shop.form;

import lombok.Data;

import javax.validation.constraints.NotNull;

/**
 * @description:
 * @author: zhangyihan
 * @createDate: 2020-05-14 17:42
 * @version: 1.0
 */
@Data
public class OrderCreateForm {


    @NotNull
    private  Integer shippingId;
}
