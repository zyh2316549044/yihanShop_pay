package com.demo.yihan_shop.enums;

import lombok.Getter;

/**
 * @description:
 * @author: zhangyihan
 * @createDate: 2020-05-02 16:11
 * @version: 1.0
 */
@Getter
public enum  ResponseEnum {

    ERROR(-1,"服务器错误"),
    SUCCESS(0,"成功"),
    PASSWORD_ERROR(1,"密码作物"),
    USERNAME_EXIST(2,"用户名已存在"),
    PARAM_ERROR(3,"参数错误"),
    EMAIL_EXIST(2,"邮箱已存在"),
    NEED_LOGIN(10,"用户未登陆，请先登录"),
    USERNAME_OR_PASSWORD_ERROR(11,"用户名或者密码为空"),
    PRODUCT_OFF_OR_DELETE(12,"用户名或者密码为空"),
    PRODUCT_NOT_EXIST(13,"商品不存在"),
    PRODUCT_NOT_STACT(14,"库存为空"),
    CART_PRODUCT_NOT_EXIST(15,"购物车商品不存在"),
    SHIPPING_NOT_EXIST(16,"购物车商品不存在"),
    CART_SELECTED_IS_EMPTY(17,"请选择商品后下单"),
    ORDER_NULL(17,"订单不存在"),
    ORDER_STATUS_ERROR(17,"订单不存在")
            ;

    private Integer code;

    private String msg;

    ResponseEnum(Integer code, String msg) {
        this.code = code;
        this.msg = msg;
    }
}
