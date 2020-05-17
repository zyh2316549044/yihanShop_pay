package com.demo.yihan_shop.vo;

import com.demo.yihan_shop.enums.ResponseEnum;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;
import org.springframework.validation.BindingResult;

import java.util.Objects;

/**
 * @description: 成功或失败返回的
 * @author: zhangyihan
 * @createDate: 2020-05-02 15:34
 * @version: 1.0
 */
@Data
@JsonInclude(value = JsonInclude.Include.NON_NULL)//为空的字段直接去除，不在返回之里面先显示
public class ResponseVo<T> {

    private Integer status;

    private String msg;

    private T data;//传入任意对象

    public ResponseVo(Integer status, String msg) {
        this.status = status;
        this.msg = msg;
    }
    public ResponseVo(Integer status, T data) {
        this.status = status;
        this.data = data;
    }
    public static <T> ResponseVo<T> success(String msg){
        return new ResponseVo<T>(0,msg);
    }

    public static <T> ResponseVo<T> successToString(ResponseEnum responseEnum){
        return new ResponseVo<T>(responseEnum.getCode(),responseEnum.getMsg());
    }

    public static <T> ResponseVo<T> success(){
        return new ResponseVo<T>(ResponseEnum.SUCCESS.getCode(), ResponseEnum.SUCCESS.getMsg());
    }
    public static <T> ResponseVo<T> success(T data){
        return new ResponseVo<T>(ResponseEnum.SUCCESS.getCode(),data);
    }
    public static <T> ResponseVo<T> errer(ResponseEnum responseEnum){
        return new ResponseVo<T>(responseEnum.getCode(),responseEnum.getMsg());
    }
    public static <T> ResponseVo<T> errer(ResponseEnum responseEnum,String msg){
        return new ResponseVo<T>(responseEnum.getCode(),responseEnum.getMsg());
    }
    public static <T> ResponseVo<T> errer(ResponseEnum responseEnum, BindingResult bindingResult){
        return new ResponseVo<T>(responseEnum.getCode(), Objects.requireNonNull(bindingResult.getFieldError().getField()+" "+bindingResult.getFieldError().getDefaultMessage()));
    }
}
