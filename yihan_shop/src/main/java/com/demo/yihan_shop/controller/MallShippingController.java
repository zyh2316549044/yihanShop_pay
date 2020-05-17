package com.demo.yihan_shop.controller;

import com.demo.yihan_shop.consts.YiHanShopConst;
import com.demo.yihan_shop.entity.MallUser;
import com.demo.yihan_shop.form.ShippingForm;
import com.demo.yihan_shop.service.MallShippingService;
import com.demo.yihan_shop.vo.ResponseVo;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;

/**
 * (MallShipping)表控制层
 *
 * @author makejava
 * @since 2020-04-28 21:21:39
 */
@RestController
public class MallShippingController {
    /**
     * 服务对象
     */
    @Resource
    private MallShippingService mallShippingService;

    /**
       添加收货地址
     */
    @PostMapping("/shippings")
    public ResponseVo shippingsInsert(@Valid @RequestBody ShippingForm shippingForm, HttpSession session) {
        MallUser mallUser = (MallUser) session.getAttribute(YiHanShopConst.CURRENT_USER);
        return  mallShippingService.insert(mallUser.getId(), shippingForm);
    }

    @DeleteMapping("/shippings/{shippingId}")
    public ResponseVo delete(@PathVariable Integer shippingId, HttpSession session) {
        MallUser mallUser = (MallUser) session.getAttribute(YiHanShopConst.CURRENT_USER);
        return  mallShippingService.deleteById(mallUser.getId(), shippingId);
    }

    @PutMapping("/shippings/{shippingId}")
    public ResponseVo delete(@PathVariable Integer shippingId,@Valid @RequestBody ShippingForm shippingForm, HttpSession session) {
        MallUser mallUser = (MallUser) session.getAttribute(YiHanShopConst.CURRENT_USER);
        return  mallShippingService.update(mallUser.getId(), shippingId,shippingForm);
    }

    @GetMapping("/shippings")
    public ResponseVo list(@RequestParam(required = false,defaultValue = "1")Integer pageNum,
                           @RequestParam(required = false,defaultValue = "10")Integer pageSize,
                           HttpSession session){
        MallUser mallUser = (MallUser) session.getAttribute(YiHanShopConst.CURRENT_USER);
        return  mallShippingService.queryAllByLimit(mallUser.getId(), pageNum,pageSize);
    }
}
