package com.demo.yihan_shop.service.impl;

import com.demo.yihan_shop.YihanShopApplicationTests;
import com.demo.yihan_shop.entity.MallUser;
import com.demo.yihan_shop.enums.RoleEnum;
import com.demo.yihan_shop.service.MallUserService;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

/**
 * @description:
 * @author: zhangyihan
 * @createDate: 2020-05-01 17:12
 * @version: 1.0
 */
@Transactional
public class MallUserServiceImplTest extends YihanShopApplicationTests {

    @Autowired
    MallUserService mallUserService;

    @Test
    public void registered() {
        MallUser mallUser = new MallUser("616","123","616", RoleEnum.CUSTOMER.getCode());
      mallUserService.registered(mallUser);

    }
}
