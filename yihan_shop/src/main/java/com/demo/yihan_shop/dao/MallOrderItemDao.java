package com.demo.yihan_shop.dao;

import com.demo.yihan_shop.entity.MallOrderItem;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Set;

/**
 * (MallOrderItem)表数据库访问层
 *
 * @author makejava
 * @since 2020-04-28 21:20:16
 */

public interface MallOrderItemDao {

    /**
     * 通过ID查询单条数据
     *
     * @param id 主键
     * @return 实例对象
     */
    MallOrderItem queryById(Integer id);

    /**
     * 查询指定行数据
     *
     * @param offset 查询起始位置
     * @param limit 查询条数
     * @return 对象列表
     */
    List<MallOrderItem> queryAllByLimit(@Param("offset") int offset, @Param("limit") int limit);


    /**
     * 通过实体作为筛选条件查询
     *
     * @param mallOrderItem 实例对象
     * @return 对象列表
     */
    List<MallOrderItem> queryAll(MallOrderItem mallOrderItem);

    /**
     * 新增数据
     *
     * @param mallOrderItem 实例对象
     * @return 影响行数
     */
    int insert(MallOrderItem mallOrderItem);

    /**
     * 修改数据
     *
     * @param mallOrderItem 实例对象
     * @return 影响行数
     */
    int update(MallOrderItem mallOrderItem);

    /**
     * 通过主键删除数据
     *
     * @param id 主键
     * @return 影响行数
     */
    int deleteById(Integer id);

    /**
     * 新增数据
     *
     * @param mallOrderItem 实例对象
     * @return 影响行数
     */
    int batchInsert(@Param("mallOrderItemList") List<MallOrderItem> mallOrderItemList);

    List<MallOrderItem> selectByOrderNoSet(@Param("orderNoSet") Set orderNoSet);
}
