package com.demo.yihan_shop.enums;

import lombok.Getter;

/**
 * @description:角色0-管理员,1-普通用户
 * @author: zhangyihan
 * @createDate: 2020-05-01 17:15
 * @version: 1.0
 */
@Getter
public enum  RoleEnum {

    ADMIN(0),
    CUSTOMER(1);

    Integer code;

    RoleEnum(Integer code){
        this.code = code;
    }

}
