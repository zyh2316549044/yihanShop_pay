package com.demo.yihan_shop.service;

import com.demo.yihan_shop.entity.MallProduct;
import com.demo.yihan_shop.vo.ProductDetailVo;
import com.demo.yihan_shop.vo.ResponseVo;
import com.github.pagehelper.PageInfo;

/**
 * (MallProduct)表服务接口
 *
 * @author makejava
 * @since 2020-04-28 21:21:22
 */
public interface MallProductService {

    /**
     * 通过ID查询单条数据
     *
     * @param productid 主键
     * @return 实例对象
     */
    ResponseVo<ProductDetailVo> queryById(Integer productid);

    /**
     * 查询商品列表
     *
     * @param pageNum 查询起始位置
     * @param pageSize 查询条数
     * @return 对象列表
     */
    ResponseVo<PageInfo> queryAllByLimit(Integer categoryId, Integer pageNum, Integer pageSize);

    /**
     * 新增数据
     *
     * @param mallProduct 实例对象
     * @return 实例对象
     */
    MallProduct insert(MallProduct mallProduct);

    /**
     * 修改数据
     *
     * @param mallProduct 实例对象
     * @return 实例对象
     */
    MallProduct update(MallProduct mallProduct);

    /**
     * 通过主键删除数据
     *
     * @param id 主键
     * @return 是否成功
     */
    boolean deleteById(Integer id);

}
