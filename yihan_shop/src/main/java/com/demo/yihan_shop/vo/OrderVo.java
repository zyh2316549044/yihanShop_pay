package com.demo.yihan_shop.vo;


import com.demo.yihan_shop.entity.MallShipping;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 * Created by 廖师兄
 */
@Data
public class OrderVo {

	private Long orderNo;

	private BigDecimal payment;

	private Integer paymentType;

	private Integer postage;

	private Integer status;

	private Date paymentTime;

	private Date sendTime;

	private Date endTime;

	private Date closeTime;

	private Date createTime;

	private List<OrderItemVo> orderItemVoList;

	private Integer shippingId;

	private MallShipping mallShipping;
}
