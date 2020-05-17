package com.demo.yihan_shop.form;

import lombok.Data;

import javax.validation.constraints.NotBlank;

/**
 * @description: 用于登录的一些判断
 * @author: zhangyihan
 * @createDate: 2020-05-02 16:30
 * @version: 1.0
 */
@Data
public class UserRegisteredForm {

    //@notnull 判断空
    //@notEmpty 用于集合
    @NotBlank//用于String 判断空格
    private String username;
    @NotBlank
    private String password;
    @NotBlank
    private String email;

}
