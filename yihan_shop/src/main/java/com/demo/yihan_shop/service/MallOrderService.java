package com.demo.yihan_shop.service;

import com.demo.yihan_shop.entity.MallOrder;
import com.demo.yihan_shop.vo.OrderVo;
import com.demo.yihan_shop.vo.ResponseVo;
import com.github.pagehelper.PageInfo;

import java.util.List;

/**
 * (MallOrder)表服务接口
 *
 * @author makejava
 * @since 2020-04-28 21:20:02
 */
public interface MallOrderService {

    /**
     * 通过ID查询单条数据
     *
     * @param id 主键
     * @return 实例对象
     */
    MallOrder queryById(Integer id);

    /**
     * 查询多条数据
     *
     * @param offset 查询起始位置
     * @param limit 查询条数
     * @return 对象列表
     */
    List<MallOrder> queryAllByLimit(int offset, int limit);

    /**
     * 新增数据
     *
     * @return 实例对象
     */
    ResponseVo<OrderVo> insert(Integer uid, Integer shippingId);

    /**
     * 修改数据
     *
     * @param mallOrder 实例对象
     * @return 实例对象
     */
    MallOrder update(MallOrder mallOrder);

    /**
     * 通过主键删除数据
     *
     * @param id 主键
     * @return 是否成功
     */
    boolean deleteById(Integer id);


    //查询订单列表
    ResponseVo<PageInfo> list(Integer uid, Integer pageNum,Integer pageSize);

    //查询订单详情
    ResponseVo<OrderVo> detail(Integer uid ,Long orderNo);

    //取消订单
    ResponseVo cancel(Integer uid,Long orderNo);

    //修改订单状态
    void paid(Long orderNo);

}
