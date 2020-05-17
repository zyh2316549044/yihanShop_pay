package com.demo.yihan_shop.form;

import lombok.Data;

import javax.validation.constraints.NotBlank;

/**
 * @description:
 * @author: zhangyihan
 * @createDate: 2020-05-03 14:10
 * @version: 1.0
 */
@Data
public class UserLoginForm {

    @NotBlank
    private String username;
    @NotBlank
    private String password;
}
