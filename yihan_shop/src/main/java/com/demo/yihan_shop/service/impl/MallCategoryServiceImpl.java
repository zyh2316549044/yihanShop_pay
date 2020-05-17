package com.demo.yihan_shop.service.impl;

import com.demo.yihan_shop.dao.MallCategoryDao;
import com.demo.yihan_shop.entity.MallCategory;
import com.demo.yihan_shop.service.MallCategoryService;
import com.demo.yihan_shop.vo.CategoryVo;
import com.demo.yihan_shop.vo.ResponseVo;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Set;

import static com.demo.yihan_shop.consts.YiHanShopConst.ROOT_PARENT_ID;

/**
 * (MallCategory)表服务实现类
 *
 * @author makejava
 * @since 2020-04-28 21:19:28
 */
@Service("mallCategoryService")
public class MallCategoryServiceImpl implements MallCategoryService {
    @Resource
    private MallCategoryDao mallCategoryDao;

    /**
     * 通过ID查询单条数据
     *
     * @param id 主键
     * @return 实例对象
     */
    @Override
    public MallCategory queryById(Integer id) {
        return this.mallCategoryDao.queryById(id);
    }

    /**
     * 查询多条数据
     *
     * @param offset 查询起始位置
     * @param limit 查询条数
     * @return 对象列表
     */
    @Override
    public List<MallCategory> queryAllByLimit(int offset, int limit) {
        return this.mallCategoryDao.queryAllByLimit(offset, limit);
    }

    /**
     * 新增数据
     *
     * @param mallCategory 实例对象
     * @return 实例对象
     */
    @Override
    public MallCategory insert(MallCategory mallCategory) {
        this.mallCategoryDao.insert(mallCategory);
        return mallCategory;
    }

    /**
     * 修改数据
     *
     * @param mallCategory 实例对象
     * @return 实例对象
     */
    @Override
    public MallCategory update(MallCategory mallCategory) {
        this.mallCategoryDao.update(mallCategory);
        return this.queryById(mallCategory.getId());
    }

    /**
     * 通过主键删除数据
     *
     * @param id 主键
     * @return 是否成功
     */
    @Override
    public boolean deleteById(Integer id) {
        return this.mallCategoryDao.deleteById(id) > 0;
    }

    /**
     * 查询类目表
     * @return
     */
    @Override
    public ResponseVo<List<CategoryVo>> queryByAll() {
        //存放一级目录
        List<CategoryVo> categoryVoList = new ArrayList<>();
        List<MallCategory> mallCategories = mallCategoryDao.queryAll();

        for (MallCategory mallCategory:mallCategories) {
            //讲数据全部查出，如果parenId等于0的时候就等于查询出第一层数据
            if (mallCategory.getParentId().equals(ROOT_PARENT_ID)){
                //查询出来的数据复制给表现层对象CategoryVo
                CategoryVo categoryVo= categoryToCategoryVo(mallCategory);

                //排序（按照从大到小排序）
                categoryVoList.sort(Comparator.comparing(CategoryVo::getSortOrder).reversed());
                categoryVoList.add(categoryVo);
            }
            //查询子目录
            findSubCategory(categoryVoList,mallCategories);

        }
        return ResponseVo.success(categoryVoList);
    }



    @Override
    public void queryCategoryById(Integer id, Set<Integer> categorySet) {
        List<MallCategory> mallCategories = mallCategoryDao.queryAll();
        queryCategoryById(id,categorySet,mallCategories);

    }
    //避免每一次执行sql语句，就把业务剥离出来
    public void queryCategoryById(Integer id, Set<Integer> categorySet,List<MallCategory> mallCategories){
        //如果parenId和传入的id相同就说明查到了
        for (MallCategory mallCategory:mallCategories) {
            if (mallCategory.getParentId().equals(id)){
                //将查询到的id再添加到set集合中
                categorySet.add(mallCategory.getId());
                //再次递归调用这个方法，将查询出来的子集再次传入的方法中，再进行查询直到没有
                queryCategoryById(mallCategory.getId(), categorySet,mallCategories);
            }
        }
    }

    /**
     * 查询子目录
     * @param categoryVoList 一级目录
     * @param mallCategories 数据这张表的区别
     */
    private void findSubCategory(List<CategoryVo> categoryVoList, List<MallCategory> mallCategories) {
        //将一级目录查询出来
        for (CategoryVo vo : categoryVoList){
            //创建一个表现层对象，就是遍历出来二级目录
            List<CategoryVo> subCategory = new ArrayList<>();
            //将数据数据拿出来
            for (MallCategory category_shuju: mallCategories) {
                //如果数据库数据和第一层的对应
                if (vo.getId().equals(category_shuju.getParentId())){
                    //就吧数据的数据复制给表现层对象
                    CategoryVo categoryVo = categoryToCategoryVo(category_shuju);

                    subCategory.add(categoryVo);

                }
                //排序（按照从大到小排序）
                subCategory.sort(Comparator.comparing(CategoryVo::getSortOrder).reversed());

                //将二级目录查询出来复制给表现层
                vo.setCategoryVos(subCategory);

                //用递归继续向下查询直到为空,参数对应的是二级目录
                findSubCategory(subCategory,mallCategories);
            }
        }
    }


    /***
     * 将查询出来的数据复制给表现层对象CategoryVo
     * @param mallCategory
     * @return
     */
    private CategoryVo categoryToCategoryVo(MallCategory mallCategory){
        CategoryVo categoryVo = new CategoryVo();
        BeanUtils.copyProperties(mallCategory,categoryVo);
        return categoryVo;
    }
}
