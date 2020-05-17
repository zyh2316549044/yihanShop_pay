package com.demo.yihan_shop.service.impl;

import com.demo.yihan_shop.YihanShopApplicationTests;
import com.demo.yihan_shop.enums.ResponseEnum;
import com.demo.yihan_shop.form.ShippingForm;
import com.demo.yihan_shop.service.MallShippingService;
import com.demo.yihan_shop.vo.ResponseVo;
import com.github.pagehelper.PageInfo;
import lombok.extern.slf4j.Slf4j;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Map;

/**
 * @description:
 * @author: zhangyihan
 * @createDate: 2020-05-10 14:23
 * @version: 1.0
 */
@Slf4j
public class MallShippingServiceImplTest extends YihanShopApplicationTests {

    @Autowired
    private MallShippingService mallShippingService;

    private Integer uid = 1;

    private Integer shoppingId = 20;
    @Test
    public void queryById() {
    }

    @Test
    public void queryAllByLimit() {
        ResponseVo<PageInfo> pageInfoResponseVo = mallShippingService.queryAllByLimit(uid, 1, 5);
        Assert.assertEquals(ResponseEnum.SUCCESS.getCode(),pageInfoResponseVo.getStatus());
    }

    @Test
    public void insert() {
        ShippingForm form = new ShippingForm();
        form.setReceiverName("zhangyiahan");
        form.setReceiverAddress("baoji");
        form.setReceiverCity("xian");
        form.setReceiverMobile("565655");
        form.setReceiverPhone("2325232");
        form.setReceiverProvince("beiji");
        form.setReceiverDistrict("haidin");
        form.setReceiverZip("000000");
        ResponseVo<Map<String, Integer>> insert = mallShippingService.insert(uid, form);
        Assert.assertEquals(ResponseEnum.SUCCESS.getCode(),insert.getStatus());
    }

    @Test
    public void update() {
        ShippingForm form = new ShippingForm();
        form.setReceiverZip("000001");
        ResponseVo<Map<String, Integer>> insert = mallShippingService.update(uid,shoppingId, form);
        Assert.assertEquals(ResponseEnum.SUCCESS.getCode(),insert.getStatus());
    }

    @Test
    public void deleteById() {
        ResponseVo<Map<String, Integer>> insert = mallShippingService.deleteById(uid, shoppingId);
        Assert.assertEquals(ResponseEnum.SUCCESS.getCode(),insert.getStatus());
    }
}
