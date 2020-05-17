package com.demo.yihan_shop.service;

import com.demo.yihan_shop.entity.MallCategory;
import com.demo.yihan_shop.vo.CategoryVo;
import com.demo.yihan_shop.vo.ResponseVo;

import java.util.List;
import java.util.Set;

/**
 * (MallCategory)表服务接口
 *
 * @author makejava
 * @since 2020-04-28 21:19:28
 */
public interface MallCategoryService {

    /**
     * 通过ID查询单条数据
     *
     * @param id 主键
     * @return 实例对象
     */
    MallCategory queryById(Integer id);

    /**
     * 查询多条数据
     *
     * @param offset 查询起始位置
     * @param limit 查询条数
     * @return 对象列表
     */
    List<MallCategory> queryAllByLimit(int offset, int limit);

    /**
     * 新增数据
     *
     * @param mallCategory 实例对象
     * @return 实例对象
     */
    MallCategory insert(MallCategory mallCategory);

    /**
     * 修改数据
     *
     * @param mallCategory 实例对象
     * @return 实例对象
     */
    MallCategory update(MallCategory mallCategory);

    /**
     * 通过主键删除数据
     *
     * @param id 主键
     * @return 是否成功
     */
    boolean deleteById(Integer id);


    /**
     * 查询类目的信息
     *
     * @param
     * @return 实例对象
     */
    ResponseVo<List<CategoryVo>> queryByAll();


    /***
     *查询商品的列表
     *
     */

    void queryCategoryById(Integer id, Set<Integer> categorySet);

}
