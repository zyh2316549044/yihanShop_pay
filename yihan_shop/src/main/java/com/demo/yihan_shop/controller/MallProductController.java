package com.demo.yihan_shop.controller;

import com.demo.yihan_shop.service.MallProductService;
import com.demo.yihan_shop.vo.ProductDetailVo;
import com.demo.yihan_shop.vo.ResponseVo;
import com.github.pagehelper.PageInfo;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * (MallProduct)表控制层
 *
 * @author makejava
 * @since 2020-04-28 21:21:22
 */
@RestController
public class MallProductController {
    /**
     * 服务对象
     */
    @Resource
    private MallProductService mallProductService;

    /**
     * 通过主键查询单条数据
     *
     * @param id 主键
     * @return 单条数据
     */
    @GetMapping("/products")
    //required = false 不用必须问号传参
    //
    public ResponseVo<PageInfo> queryLimit(@RequestParam(required = false) Integer categorySetId,
                                           @RequestParam(required = false,defaultValue = "1") Integer pageNum,
                                           @RequestParam(required = false,defaultValue = "10") Integer pageSize) {
        return mallProductService.queryAllByLimit(categorySetId,pageNum,pageSize);
    }

    @GetMapping("/products/{productId}")
    public ResponseVo<ProductDetailVo> queryAll(@PathVariable Integer productId){
        return mallProductService.queryById(productId);
    }

}
