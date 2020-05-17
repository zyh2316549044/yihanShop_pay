package com.demo.yihan_shop.controller;

import com.demo.yihan_shop.consts.YiHanShopConst;
import com.demo.yihan_shop.entity.MallUser;
import com.demo.yihan_shop.form.OrderCreateForm;
import com.demo.yihan_shop.service.MallOrderService;
import com.demo.yihan_shop.vo.OrderVo;
import com.demo.yihan_shop.vo.ResponseVo;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;

/**
 * (MallOrder)表控制层
 *
 * @author makejava
 * @since 2020-04-28 21:20:02
 */
@RestController
public class MallOrderController {


        @Autowired
        private MallOrderService mallOrderService;

        @PostMapping("/orders")
        public ResponseVo<OrderVo> create(@Valid @RequestBody OrderCreateForm form,
                                          HttpSession session) {
            MallUser mallUser = (MallUser) session.getAttribute(YiHanShopConst.CURRENT_USER);
            return mallOrderService.insert(mallUser.getId(), form.getShippingId());
        }

        @GetMapping("/orders")
        public ResponseVo<PageInfo> list(@RequestParam Integer pageNum,
                                         @RequestParam Integer pageSize,
                                         HttpSession session) {
            MallUser mallUser = (MallUser) session.getAttribute(YiHanShopConst.CURRENT_USER);
            return mallOrderService.list(mallUser.getId(), pageNum, pageSize);
        }

        @GetMapping("/orders/{orderNo}")
        public ResponseVo<OrderVo> detail(@PathVariable Long orderNo,
                                          HttpSession session) {
            MallUser mallUser = (MallUser) session.getAttribute(YiHanShopConst.CURRENT_USER);
            return mallOrderService.detail(mallUser.getId(), orderNo);
        }

        @PutMapping("/orders/{orderNo}")
        public ResponseVo cancel(@PathVariable Long orderNo,
                                 HttpSession session) {
            MallUser mallUser = (MallUser) session.getAttribute(YiHanShopConst.CURRENT_USER);
            return mallOrderService.cancel(mallUser.getId(), orderNo);
        }
    }
