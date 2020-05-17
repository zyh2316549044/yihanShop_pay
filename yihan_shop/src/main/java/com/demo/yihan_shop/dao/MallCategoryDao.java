package com.demo.yihan_shop.dao;

import com.demo.yihan_shop.entity.MallCategory;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * (MallCategory)表数据库访问层
 *
 * @author makejava
 * @since 2020-04-28 21:19:27
 */

public interface MallCategoryDao {

    /**
     * 通过ID查询单条数据
     *
     * @param id 主键
     * @return 实例对象
     */
    MallCategory queryById(Integer id);

    /**
     * 查询指定行数据
     *
     * @param offset 查询起始位置
     * @param limit 查询条数
     * @return 对象列表
     */
    List<MallCategory> queryAllByLimit(@Param("offset") int offset, @Param("limit") int limit);


    /**
     * 通过实体作为筛选条件查询
     *
     *
     * @return 对象列表
     */
    List<MallCategory> queryAll();

    /**
     * 新增数据
     *
     * @param mallCategory 实例对象
     * @return 影响行数
     */
    int insert(MallCategory mallCategory);

    /**
     * 修改数据
     *
     * @param mallCategory 实例对象
     * @return 影响行数
     */
    int update(MallCategory mallCategory);

    /**
     * 通过主键删除数据
     *
     * @param id 主键
     * @return 影响行数
     */
    int deleteById(Integer id);

}
