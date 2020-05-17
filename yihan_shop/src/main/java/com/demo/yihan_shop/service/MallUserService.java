package com.demo.yihan_shop.service;

import com.demo.yihan_shop.entity.MallUser;
import com.demo.yihan_shop.vo.ResponseVo;

import java.util.List;

/**
 * (MallUser)表服务接口
 *
 * @author makejava
 * @since 2020-04-28 21:21:52
 */
public interface MallUserService {

    /**
     * 通过ID查询单条数据
     *
     * @param id 主键
     * @return 实例对象
     */
    MallUser queryById(Integer id);

    /**
     * 查询多条数据
     *
     * @param offset 查询起始位置
     * @param limit 查询条数
     * @return 对象列表
     */
    List<MallUser> queryAllByLimit(int offset, int limit);

    /**
     * 新增数据
     *
     * @param mallUser 实例对象
     * @return 实例对象
     */
    MallUser insert(MallUser mallUser);

    /**
     * 修改数据
     *
     * @param mallUser 实例对象
     * @return 实例对象
     */
    MallUser update(MallUser mallUser);

    /**
     * 通过主键删除数据
     *
     * @param id 主键
     * @return 是否成功
     */
    boolean deleteById(Integer id);

    /**
     * 注册
     * @param mallUser 用户表
     */
    ResponseVo<MallUser> registered(MallUser mallUser);

    /**
     * 登录
     * @param
     * @return
     */
    ResponseVo<MallUser> login(String username,String password);

}
