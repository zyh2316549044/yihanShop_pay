package com.demo.yihan_shop.form;

import lombok.Data;

/**
 * @description:
 * @author: zhangyihan
 * @createDate: 2020-05-10 14:09
 * @version: 1.0
 */
@Data
public class ShippingForm {
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
}
