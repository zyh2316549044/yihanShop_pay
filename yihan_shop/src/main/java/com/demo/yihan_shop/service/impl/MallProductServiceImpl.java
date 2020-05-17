package com.demo.yihan_shop.service.impl;

import com.demo.yihan_shop.dao.MallProductDao;
import com.demo.yihan_shop.entity.MallProduct;
import com.demo.yihan_shop.enums.ProductEnum;
import com.demo.yihan_shop.enums.ResponseEnum;
import com.demo.yihan_shop.service.MallCategoryService;
import com.demo.yihan_shop.service.MallProductService;
import com.demo.yihan_shop.vo.ProductDetailVo;
import com.demo.yihan_shop.vo.ProductVo;
import com.demo.yihan_shop.vo.ResponseVo;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * (MallProduct)表服务实现类
 *
 * @author makejava
 * @since 2020-04-28 21:21:22
 */
@Service("mallProductService")
public class MallProductServiceImpl implements MallProductService {
    @Resource
    private MallProductDao mallProductDao;

    @Autowired
    private MallCategoryService mallCategoryService;
    /**
     * 通过ID查询单条数据
     *
     * @param id 主键
     * @return 实例对象
     */
    @Override
    public ResponseVo<ProductDetailVo> queryById(Integer id) {
        MallProduct mallProduct = mallProductDao.queryById(id);
        //创建一个枚举表示状态
        if (mallProduct.getStatus().equals(ProductEnum.DELETE) || mallProduct.getStatus().equals(ProductEnum.OFF_SALE)){
            ResponseVo.errer(ResponseEnum.PRODUCT_OFF_OR_DELETE);
        }

        ProductDetailVo productDetailVo = new ProductDetailVo();
        BeanUtils.copyProperties(mallProduct,productDetailVo);
        //对库存的敏感处理，如果大于100就显示100，否侧就显示当前库存
        productDetailVo.setStock(productDetailVo.getStock()> 100 ?100 :productDetailVo.getStock());
        return ResponseVo.success(productDetailVo);

    }

    /**
     * 查询多条数据
     *
     * @return 对象列表
     */
    @Override
    public ResponseVo<PageInfo> queryAllByLimit(Integer categoryId, Integer pageNum, Integer pageSize) {
        Set<Integer> categoryIdSet = new HashSet();
        if (categoryId != null){
            //传入的参数进行数据库比较，只有和符目录的id对应的表数据，直接封装到set集合中
            mallCategoryService.queryCategoryById(categoryId,categoryIdSet);
            categoryIdSet.add(categoryId);
        }
        List<MallProduct> mallProducts = mallProductDao.queryAllByLimit(categoryIdSet, pageNum, pageSize);

        List<ProductVo> collect = mallProducts.stream()
                .map(e -> {
                    ProductVo productVo = new ProductVo();
                    BeanUtils.copyProperties(e, productVo);
                    return productVo;
                })
                .collect(Collectors.toList());

        //使用pageInfo包装查询后的结果，封装了详细的查询数据，其中参数5是页码导航连续显示的页数
        PageInfo pageInfo = new PageInfo<>(mallProducts);
        pageInfo.setList(collect);//将mallProducts对象拿出封装到了pageInfo，再讲vo对象添加到里面
        return ResponseVo.success(pageInfo);

    }

    /**
     * 新增数据
     *
     * @param mallProduct 实例对象
     * @return 实例对象
     */
    @Override
    public MallProduct insert(MallProduct mallProduct) {
        this.mallProductDao.insert(mallProduct);
        return mallProduct;
    }

    /**
     * 修改数据
     *
     * @param mallProduct 实例对象
     * @return 实例对象
     */
    @Override
    public MallProduct update(MallProduct mallProduct) {
         this.mallProductDao.update(mallProduct);
         return mallProduct;
    }

    /**
     * 通过主键删除数据
     *
     * @param id 主键
     * @return 是否成功
     */
    @Override
    public boolean deleteById(Integer id) {
        return this.mallProductDao.deleteById(id) > 0;
    }
}
