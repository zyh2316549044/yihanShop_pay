package com.demo.yihan_shop.dao;

import com.demo.yihan_shop.entity.MallProduct;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Set;

/**
 * (MallProduct)表数据库访问层
 *
 * @author makejava
 * @since 2020-04-28 21:21:22
 */

public interface MallProductDao {

    /**
     * 通过ID查询单条数据
     *
     * @param id 主键
     * @return 实例对象
     */
    MallProduct queryById(Integer id);

    /**
     * 查询指定行数据
     *
     *
     * @return 对象列表
     */
    List<MallProduct> queryAllByLimit(@Param("categoryIdSet")Set<Integer> categoryIdSet,@Param("pageNum")Integer pageNum,@Param("pageSize") Integer pageSize);

    List<MallProduct> queryAllByProductId(@Param("productIdSet")Set<Integer> productIdSet);
    /**
     * 通过实体作为筛选条件查询
     *
     * @param mallProduct 实例对象
     * @return 对象列表
     */
    List<MallProduct> queryAll(MallProduct mallProduct);

    /**
     * 新增数据
     *
     * @param mallProduct 实例对象
     * @return 影响行数
     */
    int insert(MallProduct mallProduct);

    /**
     * 修改数据
     *
     * @param mallProduct 实例对象
     * @return 影响行数
     */
    int update(MallProduct mallProduct);

    /**
     * 通过主键删除数据
     *
     * @param id 主键
     * @return 影响行数
     */
    int deleteById(Integer id);

}
