package com.demo.yihan_shop.entity;

import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * (MallOrderItem)实体类
 *
 * @author makejava
 * @since 2020-04-28 21:20:16
 */
@Data
public class MallOrderItem implements Serializable {
    private static final long serialVersionUID = 948028320635698867L;
    /**
     * 订单子表id
     */
    private Integer id;

    private Integer userId;

    private Long orderNo;
    /**
     * 商品id
     */
    private Integer productId;
    /**
     * 商品名称
     */
    private String productName;
    /**
     * 商品图片地址
     */
    private String productImage;
    /**
     * 生成订单时的商品单价，单位是元,保留两位小数
     */
    private BigDecimal currentUnitPrice;
    /**
     * 商品数量
     */
    private Integer quantity;
    /**
     * 商品总价,单位是元,保留两位小数
     */
    private BigDecimal totalPrice;

    private Date createTime;

    private Date updateTime;


}
