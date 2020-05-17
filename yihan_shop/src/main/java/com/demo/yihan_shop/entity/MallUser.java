package com.demo.yihan_shop.entity;

import lombok.Data;

import java.util.Date;
import java.io.Serializable;

/**
 * (MallUser)实体类
 *
 * @author makejava
 * @since 2020-04-28 21:21:52
 */
@Data
public class MallUser implements Serializable {
    private static final long serialVersionUID = 957727945504466534L;
    /**
    * 用户表id
    */
    private Integer id;
    /**
    * 用户名
    */
    private String username;
    /**
    * 用户密码，MD5加密
    */
    private String password;
    
    private String email;
    
    private String phone;
    /**
    * 找回密码问题
    */
    private String question;
    /**
    * 找回密码答案
    */
    private String answer;
    /**
    * 角色0-管理员,1-普通用户
    */
    private Integer role;
    /**
    * 创建时间
    */
    private Date createTime;
    /**
    * 最后一次更新时间
    */
    private Date updateTime;

    public MallUser(String username, String password, String email, Integer role) {
        this.username = username;
        this.password = password;
        this.email = email;
        this.role = role;
    }

    public MallUser() {
    }
}
