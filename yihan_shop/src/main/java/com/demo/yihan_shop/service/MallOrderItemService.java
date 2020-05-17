package com.demo.yihan_shop.service;

import com.demo.yihan_shop.entity.MallOrderItem;
import java.util.List;

/**
 * (MallOrderItem)表服务接口
 *
 * @author makejava
 * @since 2020-04-28 21:20:16
 */
public interface MallOrderItemService {

    /**
     * 通过ID查询单条数据
     *
     * @param id 主键
     * @return 实例对象
     */
    MallOrderItem queryById(Integer id);

    /**
     * 查询多条数据
     *
     * @param offset 查询起始位置
     * @param limit 查询条数
     * @return 对象列表
     */
    List<MallOrderItem> queryAllByLimit(int offset, int limit);

    /**
     * 新增数据
     *
     * @param mallOrderItem 实例对象
     * @return 实例对象
     */
    MallOrderItem insert(MallOrderItem mallOrderItem);

    /**
     * 修改数据
     *
     * @param mallOrderItem 实例对象
     * @return 实例对象
     */
    MallOrderItem update(MallOrderItem mallOrderItem);

    /**
     * 通过主键删除数据
     *
     * @param id 主键
     * @return 是否成功
     */
    boolean deleteById(Integer id);

}