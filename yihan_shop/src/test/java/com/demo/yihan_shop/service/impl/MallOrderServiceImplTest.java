package com.demo.yihan_shop.service.impl;

import com.demo.yihan_shop.YihanShopApplicationTests;
import com.demo.yihan_shop.enums.ResponseEnum;
import com.demo.yihan_shop.form.CartAddForm;
import com.demo.yihan_shop.service.MallCartService;
import com.demo.yihan_shop.service.MallOrderService;
import com.demo.yihan_shop.vo.OrderVo;
import com.demo.yihan_shop.vo.ResponseVo;
import com.github.pagehelper.PageInfo;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import lombok.extern.slf4j.Slf4j;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @description:
 * @author: zhangyihan
 * @createDate: 2020-05-11 22:19
 * @version: 1.0
 */
@Slf4j
public class MallOrderServiceImplTest extends YihanShopApplicationTests {

    @Autowired
    private MallOrderService mallOrderService;
    @Autowired
    private MallCartService mallCartService;

    private Integer uid = 1;
    private Integer shippingId = 4;

    private Gson gson = new GsonBuilder().setPrettyPrinting().create();

    @Before
    public void add() {
        CartAddForm cartAddForm = new CartAddForm();
        cartAddForm.setProductId(26);
        cartAddForm.setSelect(true);
        mallCartService.add(1,cartAddForm);

    }
    @Test
    public void insertTest() {
        ResponseVo<OrderVo> insert = insert();
        Assert.assertEquals(ResponseEnum.SUCCESS.getCode(),insert.getStatus());
    }


    private ResponseVo<OrderVo> insert() {
        ResponseVo<OrderVo> insert = mallOrderService.insert(uid, shippingId);
        log.info("resulf ={}",gson.toJson(insert));
        return insert;
    }


    @Test
    public void list() {

        ResponseVo<PageInfo> list = mallOrderService.list(uid, 1, 10);
        log.info("resulf ={}",gson.toJson(list));
        Assert.assertEquals(ResponseEnum.SUCCESS.getCode(),list.getStatus());

    }
    @Test
    public void detail() {
        ResponseVo<OrderVo> vo = insert();
        ResponseVo<OrderVo> detali = mallOrderService.detail(uid, vo.getData().getOrderNo());
        log.info("resulf ={}",gson.toJson(detali));
        Assert.assertEquals(ResponseEnum.SUCCESS.getCode(),detali.getStatus());

    }
    @Test
    public void cancel(){
        ResponseVo<OrderVo> vo = insert();
        ResponseVo<OrderVo> detali = mallOrderService.cancel(uid, vo.getData().getOrderNo());
        log.info("resulf ={}",gson.toJson(detali));
        Assert.assertEquals(ResponseEnum.SUCCESS.getCode(),detali.getStatus());
    }
}
