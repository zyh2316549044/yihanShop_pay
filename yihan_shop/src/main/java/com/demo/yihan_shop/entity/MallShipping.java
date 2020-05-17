package com.demo.yihan_shop.entity;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * (MallShipping)实体类
 *
 * @author makejava
 * @since 2020-04-28 21:21:39
 */
@Data
public class MallShipping implements Serializable {
    private static final long serialVersionUID = 292015432492780414L;
    
    private Integer id;
    /**
    * 用户id
    */
    private Integer userId;
    /**
    * 收货姓名
    */
    private String receiverName;
    /**
    * 收货固定电话
    */
    private String receiverPhone;
    /**
    * 收货移动电话
    */
    private String receiverMobile;
    /**
    * 省份
    */
    private String receiverProvince;
    /**
    * 城市
    */
    private String receiverCity;
    /**
    * 区/县
    */
    private String receiverDistrict;
    /**
    * 详细地址
    */
    private String receiverAddress;
    /**
    * 邮编
    */
    private String receiverZip;
    
    private Date createTime;
    
    private Date updateTime;


}