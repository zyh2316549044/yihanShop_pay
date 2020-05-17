package com.demo.yihan_shop.service.impl;

import com.demo.yihan_shop.entity.MallShipping;
import com.demo.yihan_shop.dao.MallShippingDao;
import com.demo.yihan_shop.enums.ResponseEnum;
import com.demo.yihan_shop.form.ShippingForm;
import com.demo.yihan_shop.service.MallShippingService;
import com.demo.yihan_shop.vo.ResponseVo;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * (MallShipping)表服务实现类
 *
 * @author makejava
 * @since 2020-04-28 21:21:39
 */
@Service("mallShippingService")
public class MallShippingServiceImpl implements MallShippingService {
    @Resource
    private MallShippingDao mallShippingDao;

    /**
     * 通过ID查询单条数据
     *
     * @param id 主键
     * @return 实例对象
     */
    @Override
    public MallShipping queryById(Integer id) {
        return this.mallShippingDao.queryById(id);
    }

    /**
     * 查询多条数据
     *
     * @param pageNum 查询起始位置
     * @param pageSize 查询条数
     * @return 对象列表
     */
    @Override
    public ResponseVo<PageInfo> queryAllByLimit(int uid,int pageNum, int pageSize) {
        List<MallShipping> mallShippings = mallShippingDao.queryAllByLimit(uid,pageNum, pageSize);

        PageInfo pageInfo = new PageInfo(mallShippings);
        return ResponseVo.success(pageInfo);
    }

    /**
     * 增加收货地址
     * @param uid 用户id
     * @param shippingForm 给前端分装的收货信息提交
     * @return
     */
    @Override
    public ResponseVo<Map<String, Integer>> insert(Integer uid, ShippingForm shippingForm) {

        MallShipping mallShipping = new MallShipping();
        BeanUtils.copyProperties(shippingForm,mallShipping);
        mallShipping.setUserId(uid);
        int row = mallShippingDao.insert(mallShipping);
        if (row == 0){
            return ResponseVo.errer(ResponseEnum.ERROR);
        }

        Map<String ,Integer> map = new HashMap<>();
        map.put("shipping",mallShipping.getId());
        return ResponseVo.success(map);
    }

    /**
     * 修改数据
     * @return 实例对象
     */

    @Override
    public ResponseVo update(Integer uid, Integer shoppingId, ShippingForm shippingForm) {
        MallShipping mallShipping = new MallShipping();
        BeanUtils.copyProperties(shippingForm,mallShipping);
        mallShipping.setUserId(uid);
        mallShipping.setId(shoppingId);
        int row = mallShippingDao.update(mallShipping);
        if (row == 0){
            return ResponseVo.errer(ResponseEnum.ERROR);
        }
        return ResponseVo.success();
    }



    @Override
    public ResponseVo deleteById(Integer uid, Integer shoppingId) {
        int row = mallShippingDao.deleteByIdandUid(shoppingId, uid);
        if (row == 0){
            return ResponseVo.errer(ResponseEnum.ERROR);
        }
        return ResponseVo.success();
    }


}
