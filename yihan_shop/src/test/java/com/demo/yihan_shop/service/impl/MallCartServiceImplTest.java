package com.demo.yihan_shop.service.impl;

import com.demo.yihan_shop.YihanShopApplicationTests;
import com.demo.yihan_shop.form.CartAddForm;
import com.demo.yihan_shop.service.MallCartService;
import com.demo.yihan_shop.vo.CartVo;
import com.demo.yihan_shop.vo.ResponseVo;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @description:
 * @author: zhangyihan
 * @createDate: 2020-05-07 17:30
 * @version: 1.0
 */
@Slf4j
public class MallCartServiceImplTest extends YihanShopApplicationTests {


    @Autowired
    private MallCartService mallCartService;

    private Gson gson = new GsonBuilder().setPrettyPrinting().create();
    @Test
    public void add() {
        CartAddForm cartAddForm = new CartAddForm();
        cartAddForm.setProductId(26);
        cartAddForm.setSelect(true);
        mallCartService.add(1,cartAddForm);

    }

    @Test
    public void list() {

        ResponseVo<CartVo> list = mallCartService.list(1);
        log.info("list={}", gson.toJson(list));
    }
}
