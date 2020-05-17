package com.demo.yihan_shop.dao;

import com.demo.yihan_shop.entity.MallShipping;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Set;

/**
 * (MallShipping)表数据库访问层
 *
 * @author makejava
 * @since 2020-04-28 21:21:39
 */
public interface MallShippingDao {

    /**
     * 通过ID查询单条数据
     *
     * @param id 主键
     * @return 实例对象
     */
    MallShipping queryById(Integer id);

    /**
     * 查询指定行数据
     *
     * @param pageNum 查询起始位置
     * @param pageSize 查询条数
     * @return 对象列表
     */
    List<MallShipping> queryAllByLimit(@Param("uid") int uid,@Param("pageNum") int pageNum, @Param("pageSize") int pageSize);


    /**
     * 通过实体作为筛选条件查询
     *
     * @param mallShipping 实例对象
     * @return 对象列表
     */
    List<MallShipping> queryAll(MallShipping mallShipping);

    /**
     * 新增数据
     *
     * @param mallShipping 实例对象
     * @return 影响行数
     */
    int insert(MallShipping mallShipping);

    /**
     * 修改数据
     *
     * @param mallShipping 实例对象
     * @return 影响行数
     */
    int update(MallShipping mallShipping);

    /**
     * 通过主键删除数据
     *
     * @param id 主键
     * @return 影响行数
     */
    int deleteByIdandUid(@Param("shippingId") Integer shippingId,@Param("uid") Integer uid);
    MallShipping queryByIdandUidAndShipping(@Param("uid") Integer uid,@Param("shippingId") Integer shippingId);

    List<MallShipping> selectBySet(@Param("idSet") Set idSet);
}
