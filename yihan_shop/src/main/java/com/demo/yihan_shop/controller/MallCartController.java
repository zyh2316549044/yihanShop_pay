package com.demo.yihan_shop.controller;

import com.demo.yihan_shop.consts.YiHanShopConst;
import com.demo.yihan_shop.entity.MallUser;
import com.demo.yihan_shop.form.CartAddForm;
import com.demo.yihan_shop.form.CartUpdateForm;
import com.demo.yihan_shop.service.MallCartService;
import com.demo.yihan_shop.vo.CartVo;
import com.demo.yihan_shop.vo.ResponseVo;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;

/**
 * @description: 购物车
 * @author: zhangyihan
 * @createDate: 2020-05-07 15:52
 * @version: 1.0
 */
@RestController
public class MallCartController {


    @Resource
    private MallCartService mallCartService;

    @GetMapping("/carts")
    public ResponseVo<CartVo> list(HttpSession session) {
        MallUser mallUser = (MallUser) session.getAttribute(YiHanShopConst.CURRENT_USER);
        //拿出session域中的用户id
        return mallCartService.list(mallUser.getId());
    }

    @PostMapping("/cartsa")
    public ResponseVo<CartVo> add(@Valid @RequestBody CartAddForm cartAddForm,
                                  HttpSession session) {
        MallUser mallUser = (MallUser) session.getAttribute(YiHanShopConst.CURRENT_USER);
        return mallCartService.add(mallUser.getId(), cartAddForm);
    }

    @PutMapping("/carts/{productId}")
    public ResponseVo<CartVo> update(@PathVariable Integer productId,
                                     @Valid @RequestBody CartUpdateForm form,
                                     HttpSession session) {
        MallUser mallUser = (MallUser) session.getAttribute(YiHanShopConst.CURRENT_USER);
        return mallCartService.update(mallUser.getId(), productId, form);
    }

    @DeleteMapping("/carts/{productId}")
    public ResponseVo<CartVo> delete(@PathVariable Integer productId,
                                     HttpSession session) {
        MallUser mallUser = (MallUser) session.getAttribute(YiHanShopConst.CURRENT_USER);
        return mallCartService.delete(mallUser.getId(), productId);
    }

    @PutMapping("/carts/selectAll")
    public ResponseVo<CartVo> selectAll(HttpSession session) {
        MallUser mallUser = (MallUser) session.getAttribute(YiHanShopConst.CURRENT_USER);
        return mallCartService.selectAll(mallUser.getId());
    }

    @PutMapping("/carts/unSelectAll")
    public ResponseVo<CartVo> unSelectAll(HttpSession session) {
        MallUser mallUser = (MallUser) session.getAttribute(YiHanShopConst.CURRENT_USER);
        return mallCartService.unSelectAll(mallUser.getId());
    }

    @GetMapping("/carts/products/sum")
    public ResponseVo<Integer> sum(HttpSession session) {
        MallUser mallUser = (MallUser) session.getAttribute(YiHanShopConst.CURRENT_USER);
        return mallCartService.sum(mallUser.getId());
    }
}


