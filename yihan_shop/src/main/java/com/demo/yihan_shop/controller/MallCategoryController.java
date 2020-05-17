package com.demo.yihan_shop.controller;

import com.demo.yihan_shop.service.MallCategoryService;
import com.demo.yihan_shop.vo.CategoryVo;
import com.demo.yihan_shop.vo.ResponseVo;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

/**
 * (MallCategory)表控制层
 *
 * @author makejava
 * @since 2020-04-28 21:19:28
 */
@RestController
public class MallCategoryController {
    /**
     * 服务对象
     */
    @Resource
    private MallCategoryService mallCategoryService;

    /**
     * 查询数据
     *
     * @return 单条数据
     */
    @GetMapping("/cate")
    public ResponseVo<List<CategoryVo>> selectAll() {
        return mallCategoryService.queryByAll();
    }



}
