package com.demo.yihan_shop.controller;

import com.demo.yihan_shop.entity.MallOrderItem;
import com.demo.yihan_shop.service.MallOrderItemService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * (MallOrderItem)表控制层
 *
 * @author makejava
 * @since 2020-04-28 21:20:16
 */
@RestController
@RequestMapping("mallOrderItem")
public class MallOrderItemController {
    /**
     * 服务对象
     */
    @Resource
    private MallOrderItemService mallOrderItemService;

    /**
     * 通过主键查询单条数据
     *
     * @param id 主键
     * @return 单条数据
     */
    @GetMapping("selectOne")
    public MallOrderItem selectOne(Integer id) {
        return this.mallOrderItemService.queryById(id);
    }

}