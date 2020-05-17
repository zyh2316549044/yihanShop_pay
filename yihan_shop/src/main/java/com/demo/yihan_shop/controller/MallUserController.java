package com.demo.yihan_shop.controller;

import com.demo.yihan_shop.consts.YiHanShopConst;
import com.demo.yihan_shop.entity.MallUser;
import com.demo.yihan_shop.form.UserLoginForm;
import com.demo.yihan_shop.form.UserRegisteredForm;
import com.demo.yihan_shop.service.MallUserService;
import com.demo.yihan_shop.vo.ResponseVo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;

/**
 * (MallUser)表控制层
 *
 * @author makejava
 * @since 2020-04-28 21:21:52
 */
@RestController
@Slf4j
public class MallUserController {
    /**
     * 服务用户对象
     */
    @Resource
    private MallUserService mallUserService;


    @PostMapping(value = "/mallUser/registered")
    public ResponseVo<MallUser> registered(@Valid @RequestBody UserRegisteredForm userRegisteredForm){

        MallUser mallUser = new MallUser();
        BeanUtils.copyProperties(userRegisteredForm,mallUser);//spring提供的将一个类copy提供给另一个类
        return mallUserService.registered(mallUser);

    }

    /**
     * 登录
     * @param userLoginForm
     * @param bindingResult
     * @param session
     * @return
     */
    @PostMapping(value = "/mallUser/login")
    public ResponseVo<MallUser> login(@Valid @RequestBody UserLoginForm userLoginForm , HttpSession session){

        ResponseVo<MallUser> userResponseVo = mallUserService.login(userLoginForm.getUsername(),userLoginForm.getPassword());
        session.setAttribute(YiHanShopConst.CURRENT_USER,userResponseVo.getData());
        return userResponseVo;
    }

    @GetMapping(value = "/mallUser")
    public ResponseVo<MallUser> loginSession(HttpSession session){
        MallUser mallUser = (MallUser) session.getAttribute(YiHanShopConst.CURRENT_USER);
        return ResponseVo.success(mallUser);

    }
    @PostMapping(value = "/mallUser/loginout")
    public ResponseVo<MallUser> loginout(HttpSession session){
        session.removeAttribute(YiHanShopConst.CURRENT_USER);
        return ResponseVo.success();

    }


}
