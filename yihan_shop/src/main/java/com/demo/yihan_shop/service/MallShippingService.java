package com.demo.yihan_shop.service;

import com.demo.yihan_shop.entity.MallShipping;
import com.demo.yihan_shop.form.ShippingForm;
import com.demo.yihan_shop.vo.ResponseVo;
import com.github.pagehelper.PageInfo;

import java.util.Map;

/**
 * (MallShipping)表服务接口
 *
 * @author makejava
 * @since 2020-04-28 21:21:39
 */
public interface MallShippingService {

    /**
     * 通过ID查询单条数据
     *
     * @param id 主键
     * @return 实例对象
     */
    MallShipping queryById(Integer id);

    /**
     * 查询多条数据
     *
     * @param pageNum 查询起始位置
     * @param pageSize 查询条数
     * @return 对象列表
     */
    ResponseVo<PageInfo> queryAllByLimit(int uid,int pageNum, int pageSize);

    /**
     * 增加收货地址
     * @param uid 用户id
     * @param shippingForm 给前端分装的收货信息提交
     * @return
     */
    ResponseVo<Map<String,Integer>> insert(Integer uid, ShippingForm shippingForm);

    /**
     * 修改数据
     */
    ResponseVo update(Integer uid,Integer shoppingId, ShippingForm shippingForm);

    /**
     * 通过主键删除数据
     * @return 是否成功
     */
    ResponseVo deleteById(Integer uid, Integer shoppingId);

}
