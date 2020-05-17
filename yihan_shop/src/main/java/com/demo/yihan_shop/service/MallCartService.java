package com.demo.yihan_shop.service;

import com.demo.yihan_shop.entity.MallCart;
import com.demo.yihan_shop.form.CartAddForm;
import com.demo.yihan_shop.form.CartUpdateForm;
import com.demo.yihan_shop.vo.CartVo;
import com.demo.yihan_shop.vo.ResponseVo;

import java.util.List;

/**
 * @description:购物车
 * @author: zhangyihan
 * @createDate: 2020-05-07 16:53
 * @version: 1.0
 */
public interface MallCartService {

    ResponseVo<CartVo> add(Integer uid, CartAddForm cartAddForm);
    ResponseVo<CartVo> list(Integer uid);
    ResponseVo<CartVo> update(Integer uid, Integer productId, CartUpdateForm form);
    ResponseVo<CartVo> delete(Integer uid, Integer productId);

    ResponseVo<CartVo> selectAll(Integer uid);

    ResponseVo<CartVo> unSelectAll(Integer uid);

    ResponseVo<Integer> sum(Integer uid);

    List<MallCart> listForCart(Integer uid);


}
