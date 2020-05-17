package com.imooc.pay.config;

import com.lly835.bestpay.config.AliPayConfig;
import com.lly835.bestpay.config.WxPayConfig;
import com.lly835.bestpay.service.BestPayService;
import com.lly835.bestpay.service.impl.BestPayServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

/**
 * Created by 廖师兄
 */
@Component
public class BestPayConfig {

	@Autowired
	private WxAccountConfig wxAccountConfig;

	@Autowired
	private AlipayAccountConfig alipayAccountConfig;

	@Bean
	public BestPayService bestPayService(WxPayConfig wxPayConfig) {
		//支付宝配置
		AliPayConfig aliPayConfig = new AliPayConfig();
		aliPayConfig.setAppId(alipayAccountConfig.getAppId());//公众号appId
		aliPayConfig.setPrivateKey(alipayAccountConfig.getPrivateKey());//自己私钥
		aliPayConfig.setAliPayPublicKey(alipayAccountConfig.getPublicKey());//支付宝公钥
		aliPayConfig.setNotifyUrl(alipayAccountConfig.getNotifyUrl());//异步通知地址
		aliPayConfig.setReturnUrl(alipayAccountConfig.getReturnUrl());//成功返回页面地址
		//支付类, 所有方法都在这个类里
		BestPayServiceImpl bestPayService = new BestPayServiceImpl();
		//写入微信
		bestPayService.setWxPayConfig(wxPayConfig);
		//写入支付宝
		bestPayService.setAliPayConfig(aliPayConfig);
		return bestPayService;
	}

	//发起微信支付需要的的配置
	@Bean
	public WxPayConfig wxPayConfig() {
		WxPayConfig wxPayConfig = new WxPayConfig();
		wxPayConfig.setAppId(wxAccountConfig.getAppId());//公众号appId
		wxPayConfig.setMchId(wxAccountConfig.getMchId());//商户号
		wxPayConfig.setMchKey(wxAccountConfig.getMchKey());
		//192.168.50.101 同一局域网可访问
		//125.121.56.227 云服务器可行，家庭宽带不行(路由器、光猫)
		//规定必须使用外网
		wxPayConfig.setNotifyUrl(wxAccountConfig.getNotifyUrl());
		wxPayConfig.setReturnUrl(wxAccountConfig.getReturnUrl());
		return wxPayConfig;
	}
}
