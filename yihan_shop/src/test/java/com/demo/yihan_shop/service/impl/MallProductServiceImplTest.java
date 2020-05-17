package com.demo.yihan_shop.service.impl;

import com.demo.yihan_shop.YihanShopApplicationTests;
import com.demo.yihan_shop.service.MallProductService;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @description:
 * @author: zhangyihan
 * @createDate: 2020-05-05 16:01
 * @version: 1.0
 */
public class MallProductServiceImplTest extends YihanShopApplicationTests {

    @Autowired
    MallProductService mallProductService;
    @Test
    public void queryAllByLimit() {
//        ResponseVo<List<ProductVo>> listResponseVo = mallProductService.queryAllByLimit(null, 1, 1);
//        Assert.assertEquals(ResponseEnum.SUCCESS.getCode(),listResponseVo.getStatus());
    }
}
