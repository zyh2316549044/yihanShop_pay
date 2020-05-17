package com.demo.yihan_shop;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan(basePackages = "com.demo.yihan_shop.dao")
public class YihanShopApplication {

    public static void main(String[] args) {
        SpringApplication.run(YihanShopApplication.class, args);
    }

}
