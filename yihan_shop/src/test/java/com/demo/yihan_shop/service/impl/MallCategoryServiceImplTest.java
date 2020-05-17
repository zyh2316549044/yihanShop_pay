package com.demo.yihan_shop.service.impl;

import com.demo.yihan_shop.YihanShopApplicationTests;
import com.demo.yihan_shop.service.MallCategoryService;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.HashSet;
import java.util.Set;

import static org.junit.Assert.*;

/**
 * @description:
 * @author: zhangyihan
 * @createDate: 2020-05-04 15:09
 * @version: 1.0
 */
@Slf4j
public class MallCategoryServiceImplTest extends YihanShopApplicationTests {

    @Autowired
    MallCategoryService mallCategoryService;

    @Test
    public void queryByAll() {
        mallCategoryService.queryByAll();

    }

    @Test
    public void queryCategoryById() {

        Set<Integer> Set = new HashSet<>();
        mallCategoryService.queryCategoryById(100001, Set);
        log.info("set={}",Set);
    }
}
