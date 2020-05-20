package com.demo.yihan_shop;

import com.demo.yihan_shop.consts.YiHanShopConst;
import com.demo.yihan_shop.entity.MallUser;
import com.demo.yihan_shop.exceptions.UserloginExecption;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @description:
 * @author: zhangyihan
 * @createDate: 2020-05-03 20:22
 * @version: 1.0
 */
@Slf4j
public class UserLoginInterceptor implements HandlerInterceptor{
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        MallUser mallUser = (MallUser) request.getSession().getAttribute(YiHanShopConst.CURRENT_USER);
        if (mallUser == null){
            log.info("username == null");
            throw new UserloginExecption();
        }
        return true;
    }
}
