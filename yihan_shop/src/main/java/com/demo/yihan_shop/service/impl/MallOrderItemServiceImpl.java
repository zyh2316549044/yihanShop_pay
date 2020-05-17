package com.demo.yihan_shop.service.impl;

import com.demo.yihan_shop.entity.MallOrderItem;
import com.demo.yihan_shop.dao.MallOrderItemDao;
import com.demo.yihan_shop.service.MallOrderItemService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * (MallOrderItem)表服务实现类
 *
 * @author makejava
 * @since 2020-04-28 21:20:16
 */
@Service("mallOrderItemService")
public class MallOrderItemServiceImpl implements MallOrderItemService {
    @Resource
    private MallOrderItemDao mallOrderItemDao;

    /**
     * 通过ID查询单条数据
     *
     * @param id 主键
     * @return 实例对象
     */
    @Override
    public MallOrderItem queryById(Integer id) {
        return this.mallOrderItemDao.queryById(id);
    }

    /**
     * 查询多条数据
     *
     * @param offset 查询起始位置
     * @param limit 查询条数
     * @return 对象列表
     */
    @Override
    public List<MallOrderItem> queryAllByLimit(int offset, int limit) {
        return this.mallOrderItemDao.queryAllByLimit(offset, limit);
    }

    /**
     * 新增数据
     *
     * @param mallOrderItem 实例对象
     * @return 实例对象
     */
    @Override
    public MallOrderItem insert(MallOrderItem mallOrderItem) {
        this.mallOrderItemDao.insert(mallOrderItem);
        return mallOrderItem;
    }

    /**
     * 修改数据
     *
     * @param mallOrderItem 实例对象
     * @return 实例对象
     */
    @Override
    public MallOrderItem update(MallOrderItem mallOrderItem) {
        this.mallOrderItemDao.update(mallOrderItem);
        return this.queryById(mallOrderItem.getId());
    }

    /**
     * 通过主键删除数据
     *
     * @param id 主键
     * @return 是否成功
     */
    @Override
    public boolean deleteById(Integer id) {
        return this.mallOrderItemDao.deleteById(id) > 0;
    }
}